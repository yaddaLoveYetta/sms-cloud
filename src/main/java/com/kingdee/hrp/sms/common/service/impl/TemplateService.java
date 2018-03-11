package com.kingdee.hrp.sms.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingdee.hrp.sms.common.dao.customize.TemplateDaoMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassEntryMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormFieldsMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.DataTypeEnum;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import com.kingdee.hrp.sms.util.Common;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TemplateService extends BaseService implements ITemplateService {

    /**
     * 查询基础资料/单据模板数据
     *
     * @param classId 业务类别
     * @param type    查询方式（0:后端查询 1:前端获取）
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getFormTemplate(Integer classId, Integer type) {

        Map<String, Object> retMap = new LinkedHashMap<String, Object>();

        // 主表模板
        Map<String, Object> formFields = new LinkedHashMap<String, Object>();
        // 子表模板
        Map<String, Object> formEntry = new LinkedHashMap<String, Object>();

        // 获取单据类别描述信息
        FormClassMapper classMapper = sqlSession.getMapper(FormClassMapper.class);

        // 主表单据类别描述
        FormClass formClass = classMapper.selectByPrimaryKey(classId);

        if (null == formClass) {
            throw new BusinessLogicRunTimeException("模板不存在或不唯一,请联系管理员!");
        }


        // 获取单据模板page=0表头

        FormFieldsMapper formFieldsMapper = sqlSession.getMapper(FormFieldsMapper.class);

        FormFieldsExample fieldsExample = new FormFieldsExample();
        FormFieldsExample.Criteria fieldsCriteria = fieldsExample.createCriteria();

        fieldsCriteria.andClassIdEqualTo(classId);
        fieldsCriteria.andPageEqualTo(0);

        if (type == 1) {
            // 后端构建查询脚本时调用
            fieldsExample.setOrderByClause("ctrl_Index");

        } else {
            // 前端处理显示顺序时调用
            fieldsExample.setOrderByClause("`index`");
        }

        List<FormFields> headFields = formFieldsMapper.selectByExample(fieldsExample);

        // 子表单据类别描述
        FormClassEntryMapper classEntryMapper = sqlSession.getMapper(FormClassEntryMapper.class);
        FormClassEntryExample classEntryExample = new FormClassEntryExample();
        FormClassEntryExample.Criteria entryCriteria = classEntryExample.createCriteria();

        entryCriteria.andClassIdEqualTo(classId);
        List<FormClassEntry> formClassEntries = classEntryMapper.selectByExample(classEntryExample);

        // 打包表头字段模板
        Map<String, FormFields> formFields0 = new LinkedHashMap<String, FormFields>();
        for (FormFields item : headFields) {
            formFields0.put(item.getKey(), item);
        }
        // 表头模板
        formFields.put("0", formFields0);

        // 循环打包子表字段模板
        for (FormClassEntry entry : formClassEntries) {

            String entryIndex = entry.getEntryIndex().toString();

            formEntry.put(entryIndex, entry);

            // 查询子表字段模板(按子表page)
            FormFieldsExample formFieldsExample = new FormFieldsExample();
            FormFieldsExample.Criteria formFieldsExampleCriteria = formFieldsExample.createCriteria();

            formFieldsExampleCriteria.andClassIdEqualTo(classId);
            formFieldsExampleCriteria.andPageEqualTo(entry.getEntryIndex());

            formFieldsExample.setOrderByClause("`index`");

            List<FormFields> entryIndexFieldsByExample = formFieldsMapper.selectByExample(formFieldsExample);

            Map<String, FormFields> formFieldsEntryIndex = new LinkedHashMap<String, FormFields>();
            // 打包子表字段模板
            for (FormFields fields : entryIndexFieldsByExample) {
                formFieldsEntryIndex.put(fields.getKey(), fields);
            }

            // 第entryIndex个子表模板
            formFields.put(entryIndex, formFieldsEntryIndex);
        }

        retMap.put("formClass", formClass);
        retMap.put("formClassEntry", formEntry);
        retMap.put("formFields", formFields);

        return retMap;


    }

    /**
     * 获取基础资料/单据定义的功能操作列表
     *
     * @param classId 业务类型
     * @return 功能操作列表
     */
    @Override
    public List getFormAction(Integer classId) {

        FormActionMapper formActionMapper = sqlSession.getMapper(FormActionMapper.class);

        FormActionExample formActionExample = new FormActionExample();

        FormActionExample.Criteria criteria = formActionExample.createCriteria();

        criteria.andClassIdEqualTo(classId);
        // 功能按钮按照index排序
        formActionExample.setOrderByClause("`index`  ,`name` ");

        List<FormAction> formActions = formActionMapper.selectByExample(formActionExample);

        return formActions;
    }

    /**
     * 通过模板获取业务数据
     *
     * @param classId    业务类型
     * @param conditions 过滤条件（json结构化数据）
     * @param sorts      排序条件（json结构化数据）
     * @param pageSize   分页大小
     * @param pageNo     当前页码
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getItems(Integer classId, List<Condition> conditions, List<Sort> sorts, Integer pageSize, Integer pageNo) {

        // 返回结构
        Map<String, Object> ret = new HashMap<String, Object>(16);

        // 基础资料模板
        Map<String, Object> template = this.getFormTemplate(classId, 1);
        // 主表资料描述信息
        FormClass formClass = (FormClass) template.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formClassEntry");
        // 主表字段模板
        Map<String, Object> formFields0 = (Map<String, Object>) ((Map<String, Object>) template.get("formFields")).get("0");
        // 第一个子表字段模板(如果有)
        Map<String, Object> formFields1 = new HashMap<String, Object>();

        if (null == formClass || formFields0.isEmpty()) {
            throw new BusinessLogicRunTimeException("资料模板不存在");
        }

        // 插件事件 TODO---begin-------
        //  PlugInFactory factory = new PlugInFactory(classId);
        //  conditionString = factory.getConditions(classId, template, conditionString);
        // 插件事件 TODO---begin-------

        // 主表表名
        String primaryTableName = formClass.getTableName();
        // 主表主键key
        String primaryKey = formClass.getPrimaryKey();
        // 主表主键数据库字段名
        String primaryColumnName = ((FormFields) formFields0.get(primaryKey)).getSqlColumnName();
        // 指示是否存在子表，存在时主表需要关联第一个子表查询
        boolean isChildTableExist = false;

        if (!formEntries.isEmpty()) {
            // 存在关联字表-只关联第一个子表查询
            isChildTableExist = true;
            formFields1 = (Map<String, Object>) ((Map<String, Object>) template.get("formFields")).get("1");
        }

        // 动态构建select语句

        // 模板拼接的完整select语句
        String select = "";
        // 执行的select语句(用于有子表关联查询时进行两次查询，第一次查询出该页所有主表主键，第二次根据第一次获取的主键查询)
        String selectExec = "";
        // 查询语句----from部分
        String from = "";
        // 查询语句----where部分
        String where = "";
        // 查询语句----order by部分
        String sort = "";

        // 查询条件
        Map<String, Object> whereMap = new HashMap<String, Object>();
        // 查询条件格式化参数
        Map<String, Object> whereParams = new HashMap<String, Object>();
        // 构建查询脚本
        Map<String, Object> statement = getStatement(classId);

        // 获取客户端传入的查询条件
        if (conditions != null && !conditions.isEmpty()) {
            whereMap = getWhere(classId, -1, conditions);
        }

        // 获取客户端传入了排序规则
        if (sorts != null && !sorts.isEmpty()) {
            sort = getSort(classId, sorts);
        }

        select = (String) statement.get("select");
        from = (String) statement.get("from");

        if (!whereMap.isEmpty()) {
            where = whereMap.get("whereStr").toString();
            whereParams = (Map<String, Object>) whereMap.get("whereParams");
        }

        // 有子表的查询单据数跟实际记录数不一致(A INNER JOIN B 查询结果记录数是子表表B的记录数)，分页信息不准确
        // 这种情况进行二次查询，第一次查询出A表符合条件的记录，第二次查询出真实业务数据
        selectExec = select;
        if (isChildTableExist) {
            // 替换select部分，查询出该页主表的主键值
            selectExec = String.format("SELECT DISTINCT %s.%s AS id ", primaryTableName, primaryColumnName);
        }

        Map<String, Object> sqlMap = new HashMap<String, Object>();
        // 完整的sql(带格式化参数)
        String sql = selectExec + System.getProperty("line.separator") + from + System.getProperty("line.separator") + where + System.getProperty("line.separator")
                + sort;
        sqlMap.put("sql", sql);

        // 格式化参数
        for (Map.Entry<String, Object> entry : whereParams.entrySet()) {
            sqlMap.put(entry.getKey(), entry.getValue());
        }

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);

        if (pageNo == 1) {
            // 查询第一页数据是查询出总记录数
            PageHelper.startPage(pageNo, pageSize, true);
        } else {
            // 非第一页查询时不查询总记录数
            PageHelper.startPage(pageNo, pageSize, false);
        }

        List<Map<String, Object>> data = templateDaoMapper.getItems(sqlMap);

        if (pageNo == 1) {
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(data);
            ret.put("count", pageInfo.getTotal());
        }

        // 存在子表的查询需要进行第二次查询，获取真实数据字段
        if (isChildTableExist) {

            List<Long> idList = new ArrayList<>();

            // 获取本次查询的主表主键集合
            for (Map<String, Object> item : data) {
                idList.add((Long) item.get("id"));
            }

            List<Map<String, Object>> itemByIds = new ArrayList<Map<String, Object>>();

            if (idList.size() > 0) {
                itemByIds = getItemByIds(classId, idList, conditions, sorts);
            }

            ret.put("list", itemByIds);

            return ret;

        }

        // 将记录转换成返回接口的格式，将主表关联多行子表记录时，子表记录整合到返回结构"entry"中
        ret.put("list", data);

        return ret;

    }

    /**
     * 通过内码获取单个业务类型数据
     *
     * @param classId 业务类型
     * @param id      单据内码
     * @param orderBy 排序结构(json 结构化数据) 查询单据时，单据分录需要排序
     */
    @Override
    public Map<String, Object> getItemById(Integer classId, Long id, String orderBy) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getItemByIds(Integer classId, List<Long> idList, List<Condition> conditions, List<Sort> sorts) {

        if (idList == null || idList.size() == 0) {
            throw new BusinessLogicRunTimeException("请提交单据内码");
        }

        String ids = StringUtils.join(idList.toArray(), ",");

        // 基础资料模板
        Map<String, Object> template = getFormTemplate(classId, 1);

        // 主表字段模板
        Map<String, Object> formFields0 = (Map<String, Object>) ((Map<String, Object>) template.get("formFields")).get("0");
        // 第一个子表字段模板(如果有)
        Map<String, Object> formFields1 = new HashMap<String, Object>();
        // 主表资料描述信息
        FormClass formClass = (FormClass) template.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formClassEntry");

        // 查询条件
        Map<String, Object> whereMap = new HashMap<String, Object>();
        // 查询条件格式化参数
        Map<String, Object> whereParams = new HashMap<String, Object>();

        if (null == formClass) {
            throw new BusinessLogicRunTimeException("资料模板不存在");
        }

        // 主表表名
        String primaryTableName = formClass.getTableName();
        // 主表主键
        String primaryKey = formClass.getPrimaryKey();
        // 指示是否存在子表，存在时主表需要关联第一个子表查询
        boolean isChildTableExist = false;

        if (!formEntries.isEmpty()) {
            // 存在关联字表-只关联第一个子表查询
            isChildTableExist = true;
            formFields1 = (Map<String, Object>) ((Map<String, Object>) template.get("formFields")).get("1");
        }

        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        // 动态构建select语句

        // 查询字段
        String select = "";
        // 查询表
        String from = "";
        // 查询条件
        String where = "";
        //String orderBy = orderByStr == null ? "" : orderByStr;
        String orderBy = "";

        // 首先查询主表记录
        Map<String, Object> statement = getStatement(classId, 0);

        select = (String) statement.get("select");
        from = (String) statement.get("from");

        // 查询条件-直接用传入的主键做条件
        StringBuilder sbWhere = new StringBuilder();
        sbWhere.append("WHERE").append(System.getProperty("line.separator")).append(String.format("%s.%s%s%s IN (%s)", primaryTableName, bDelimiter, primaryKey, eDelimiter, ids));
        where = sbWhere.toString();

        Map<String, Object> sqlMap = new HashMap<String, Object>();
        // 完整的sql(带格式化参数)
        String sql = select + System.getProperty("line.separator") + from + System.getProperty("line.separator") + where + System.getProperty("line.separator") + orderBy
                + System.getProperty("line.separator");
        sqlMap.put("sql", sql);

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);
        // 主表记录
        List<Map<String, Object>> dataHead = templateDaoMapper.getItems(sqlMap);

        if (!formEntries.isEmpty()) {
            // 存在关联字表-只关联第一个子表查询

            FormClassEntry entry = (FormClassEntry) formEntries.get("1");
            String entryTableName = entry.getTableName();
            String foreignKey = entry.getForeignKey();

            statement = getStatement(classId, 1);
            // 清空原过滤条件
            where = " WHERE 1=1 ";
            // 获取客户端传入的查询条件
            if (conditions != null && !conditions.isEmpty()) {
                whereMap = getWhere(classId, -1, conditions);
            }
            if (!whereMap.isEmpty()) {
                where = whereMap.get("whereStr").toString();
                whereParams = (Map<String, Object>) whereMap.get("whereParams");
            }

            select = (String) statement.get("select");
            from = (String) statement.get("from");

            // 查询条件-直接用传入的主键做子表的外键条件
            sbWhere = new StringBuilder();
            sbWhere.append(where).append(System.getProperty("line.separator")).append(String.format(" AND %s.%s%s%s IN (%s)", entryTableName, bDelimiter, foreignKey, eDelimiter, ids));
            where = sbWhere.toString();

            sqlMap = new HashMap<String, Object>();
            // 完整的sql(带格式化参数)
            sql = select + System.getProperty("line.separator") + from + System.getProperty("line.separator") + where + System.getProperty("line.separator") + orderBy
                    + System.getProperty("line.separator");
            sqlMap.put("sql", sql);

            // 格式化参数
            for (Map.Entry<String, Object> whereEntry : whereParams.entrySet()) {
                sqlMap.put(whereEntry.getKey(), whereEntry.getValue());
            }

            // 子表记录
            List<Map<String, Object>> dataEntry = templateDaoMapper.getItems(sqlMap);

            // 迭代子表记录放入主表记录行entry中
            for (Map<String, Object> entryItem : dataEntry) {

                Long foreignKeyValue = (Long) entryItem.get(foreignKey);

                for (Map<String, Object> dataHeadItem : dataHead) {

                    if (!dataHeadItem.containsKey("entry")) {
                        // 没有entry先新增entry
                        Map<String, ArrayList<Map<String, Object>>> headEntry = new HashMap<String, ArrayList<Map<String, Object>>>();
                        headEntry.put("1", new ArrayList<Map<String, Object>>());
                        dataHeadItem.put("entry", headEntry);
                    }

                    Long primaryKeyValue = (Long) dataHeadItem.get(primaryKey);

                    ArrayList<Map<String, Object>> headEntry = ((Map<String, ArrayList<Map<String, Object>>>) dataHeadItem.get("entry")).get("1");

                    if (primaryKeyValue.equals(foreignKeyValue)) {
                        // 确认了该分录就是该主表分录-家该分录添加到对应主表分录中
                        headEntry.add(entryItem);
                        // 为了保证每条数据都有entry结构，此处不退出循环
                        //break;
                    }

                }
            }

        }

        return dataHead;
    }

    /**
     * 新增数据
     *
     * @param classId 业务类别
     * @param data    数据（严格按照单据模板匹配的数据）
     * @return 新增资料的id
     */
    @Override
    public Long addItem(Integer classId, String data) {
        return null;
    }

    /**
     * 修改业务数据
     *
     * @param classId 业务类型
     * @param id      内码
     * @param data    修改数据内容（严格按照单据模板匹配的数据）
     * @return 是否成功
     */
    @Override
    public Boolean editItem(Integer classId, Long id, String data) {
        return null;
    }

    /**
     * 删除基础资料
     *
     * @param classId 业务类型
     * @param ids     删除的内码集合
     * @return 是否成功
     */
    @Override
    public Boolean delItem(Integer classId, List<Long> ids) {
        return null;
    }

    /**
     * 审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */
    @Override
    public Boolean checkItem(Integer classId, List<Long> ids) {
        return null;
    }

    /**
     * 反审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */
    @Override
    public Boolean unCheckItem(Integer classId, List<Long> ids) {
        return null;
    }


    /**
     * 构建查询脚本
     *
     * @param classId 业务类别
     * @return 查询语句及参数
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getStatement(int classId) {

        // 指示是否存在子表，存在时主表需要关联第一个子表查询
        boolean isChildTableExist = false;

        // 基础资料模板
        Map<String, Object> template = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<String, Object> formFieldsAll = (Map<String, Object>) template.get("formFields");
        // 主表资料描述信息
        FormClass formClass = (FormClass) template.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formClassEntry");


        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        if (null == formClass) {
            throw new BusinessLogicRunTimeException("资料模板不存在");
        }

        String primaryTableName = formClass.getTableName();
        String primaryKey = formClass.getPrimaryKey();

        String childTableName = "";
        String foreignKey = "";
        String entryJoinType = "";

        if (!formEntries.isEmpty()) {

            // 存在关联字表-只关联第一个子表查询
            FormClassEntry formEntry = (FormClassEntry) formEntries.get("1");
            // 子表物理表名
            childTableName = formEntry.getTableName();
            // 子表与主表关联key
            foreignKey = formEntry.getForeignKey();
            // 子表与主表关联字段(eg:INNER JOIN)
            entryJoinType = formEntry.getJoinType();

            if ("".equals(childTableName) || "".equals(foreignKey)) {
                // 存在子表但子表FormEntries中配置错误
                throw new BusinessLogicRunTimeException("子表FormClassEntry中配置错误");
            }
            if ("".equals(entryJoinType)) {
                // 子表与主表关联关系默认 INNER JOIN
                entryJoinType = "INNER JOIN";
            }

            isChildTableExist = true;
        }

        if (formFieldsAll.isEmpty()) {
            throw new BusinessLogicRunTimeException(String.format("classId=%s 没有模板数据", classId));
        }

        StringBuilder sbSelect = new StringBuilder();
        StringBuilder sbFrom = new StringBuilder();

        String separator = System.getProperty("line.separator");

        sbSelect.append("SELECT").append(separator);
        sbFrom.append("FROM ").append(primaryTableName).append(separator);

        if (isChildTableExist) {
            // 存在子表先拼接子表
            sbFrom.append(String.format(" %s %s ON %s.%s%s%s = %s.%s%s%s", entryJoinType, childTableName, childTableName, bDelimiter, foreignKey, eDelimiter, primaryTableName, bDelimiter, primaryKey,
                    eDelimiter)).append(separator);
        }

        /*
            用户角色类别-用于字段权限控制(前端有控制，此处简单不做处理)
            1	系统管理员
            2	医院
            3	供应商
            */
        Integer userRoleType = SessionUtil.getUserRoleType();

        for (String pageIndex : formFieldsAll.keySet()) {

            // page=0/page=1的字段模板
            Map<String, Object> formFields = (Map<String, Object>) formFieldsAll.get(pageIndex);

            // 当前模板关联的物理表
            String formFieldLinkedTable = "";

            if ("0".equals(pageIndex)) {
                // 主表
                formFieldLinkedTable = primaryTableName;
            } else if ("1".equals(pageIndex)) {
                formFieldLinkedTable = childTableName;
            } else {
                // 主表不支持关联多个子表查询
                continue;
            }

            for (String fieldKey : formFields.keySet()) {

                FormFields formField = (FormFields) formFields.get(fieldKey);

                String joinType = formField.getJoinType();

                if (joinType == null || "".equals(joinType.trim())) {
                    // 默认 INNER JOIN
                    joinType = "INNER JOIN";
                }

                // 两边加一空格，防止模板配置时两边无空格，链接脚本错误
                joinType = " " + joinType.trim() + " ";
                // 过滤条件-用于关联表时的附加条件
                String filter = formField.getFilter();

                String name = formField.getName();
                String sqlColumnName = formField.getSqlColumnName();
                String key = formField.getKey();
                Integer dataType = formField.getDataType();
                Integer index = formField.getIndex();
                Integer display = formField.getDisplay();
                // 查找类型类别
                Integer lookUpType = formField.getLookUpType();
                // 具体查找类型
                Integer lookUpClassId = formField.getLookUpClassId();
                // 关联查询表
                String srcTable = formField.getSrcTable();
                // 关联表查询时，关联表可以给一个别名
                String srcTableAlisAs = formField.getSrcTableAlis();
                // 关联表字段
                String srcField = formField.getSrcField();
                // 查询时的显示字段(如订单关联物料查询，显示物料的名称)
                String disPlayField = formField.getDisplayField();
                // 扩展显示字段(如订单关联物料查询，还可显示物料的代码)
                String displayExt = formField.getDisplayExt();
                // 字段是否有物理表字段保存(如订单表中物料的规格型号不需要保存在订单表中，而是通过订单中的物料关联查询物料表获取)
                boolean needSave = formField.getNeedSave();

                // 设置了关联查询表别名时，查询字段应该用别名.字段名
                // eg: select A.colA, B.colB from table_a AS A inner join table_b AS B on A.id=B.id
                String srcTableAlis = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;

                if (lookUpType != null && (lookUpType == 1 || lookUpType == 2)) {

                    // eg: select order.number as number_key,item.name as name_DspName,item.number as number_NmbName from
                    // order inner join item on order.item_id=item.id

                    // 基础资料/辅助资料引用类型-主表中记录关联字段值一般是id
                    // 查询出主表中记录的关联字段值
                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                    // 查询出关联表该显示的值
                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key + "_DspName", eDelimiter)).append(separator);
                    // 如果增加配置了关联表扩展显示值，则一并查询出
                    if (displayExt != null && !"".equals(displayExt.trim())) {
                        // 代码显示字段
                        sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, displayExt, eDelimiter, bDelimiter, key + "_NmbName", eDelimiter)).append(separator);
                    }

                    // from 中同时增加关联表
                    sbFrom.append(joinType).append(srcTable);

                    // 关联表的别名
                    if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                        sbFrom.append(" AS ").append(srcTableAlisAs);
                    }
                    // 主表与关联表的关联条件
                    sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
                            .append(separator);

                    if (filter != null && !"".equals(filter.trim())) {
                        // 表链接有附加条件-附件条件程序不校验合法性
                        sbFrom.append(filter).append(separator);
                    }

                } else if (lookUpType != null && lookUpType == 3) {

                    // 引用基础资料的附加属性-依赖lookUpType == 1 || lookUpType == 2 存在,即模板中必须存在该记录lookUpClassId且lookUpType=1/2 的字段模板
                    // 即引用基础资料属性的模板中，disPlayField的配置统一认为是被引用基础资料模板中的key，需要二次验证引用资料模板确认查询字段

                    // 举例：目标，在车辆信息中显示其车辆类别的车辆付费类型
                    // 基础资料车辆信息中引用另一个基础资料车辆类别，还需要显示基础资料车辆类别的另一个属性"车辆付费类型"，而"车辆付费类型"在车辆类别模板中又是辅助资料引用类型，
                    // 此时在车辆信息的模板中配置携带车辆类别基础资料的附属属性"车辆付费类型"的模板时，模板中disPlayField应配置为"车辆类别"模板中"车辆付费类型"的key(payType),
                    // srcField应该配置为"车辆类别"表与辅助资料的关联字段(即车辆类别表t_CarType与辅助资料车辆付费类型的关联字段payType),
                    // srcTableAlisAs必须配置，可随意，但需保证在车辆信息模板中不重复。

                    FormFields lookUpTypeFormField = getFormField(lookUpClassId, disPlayField);

                    if (lookUpTypeFormField == null) {
                        throw new BusinessLogicRunTimeException(String.format("classId=%s, key=%s 的disPlayField配置错误，请联系管理员", classId, key));
                    }

                    String nameEx = lookUpTypeFormField.getName();
                    Integer lookUpTypeEx = lookUpTypeFormField.getLookUpType();
                    String joinTypeEx = lookUpTypeFormField.getJoinType();
                    String srcTableEx = lookUpTypeFormField.getSrcTable();
                    String srcFieldEx = lookUpTypeFormField.getSrcField();

                    if (needSave) {
                        // 需要保存的携带属性值直接从主表查询
                        sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                    } else {
                        // 不需要保存的引用字段关联查询
                        if (lookUpTypeEx != null && lookUpTypeEx > 0) {

                            // 附加属性又是引用类型的情况--取显示字段并关联表
                            sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, nameEx, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                            // from 中同时增加关联表
                            sbFrom.append(joinTypeEx).append(srcTableEx);

                            // 关联表的别名
                            sbFrom.append(" AS ").append(srcTableAlis);

                            sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", srcTableEx, bDelimiter, srcFieldEx, eDelimiter, srcTableAlis, bDelimiter, srcFieldEx, eDelimiter))
                                    .append(separator);
                        } else {
                            // 普通属性
                            sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                        }

                    }

                } else if (lookUpType != null && lookUpType == 4) {
                    // 普通引用-引用其他表数据

                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                    // from 中同时增加关联表
                    sbFrom.append(joinType).append(srcTable);

                    // 关联表的别名
                    if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                        sbFrom.append(" AS ").append(srcTableAlisAs);
                    }

                    sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
                            .append(separator);

                } else if (lookUpType != null && lookUpType == 5) {

                    // 普通引用其他表的其他字段-
                    // 主要为了避免为4即引用他表数据时，需引用多个字段时关联表重复问题。依附于=4时存在,即模板中肯定存在lookUpType=4的字段模板

                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                } else {
                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                }
            }
        }


        String select = sbSelect.toString().trim();
        select = select.substring(0, select.length() - 1);
        String from = sbFrom.toString().trim();

        Map<String, Object> statement = new HashMap<String, Object>(6);

        statement.put("select", select);
        statement.put("from", from);

        return statement;
    }

    /**
     * 构建指定classId，page的查询脚本
     *
     * @param classId 业务类别
     * @param page    0主表1第一个子表
     * @return 查询语句及参数
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getStatement(int classId, int page) {

        if (page < 0) {
            throw new BusinessLogicRunTimeException("page参数错误！");
        }

        // 基础资料模板
        Map<String, Object> template = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<String, Object> formFieldsAll = (Map<String, Object>) template.get("formFields");
        // 主表资料描述信息
        FormClass formClass = (FormClass) template.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formClassEntry");


        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        if (null == formClass) {
            // 单据必须有单据头-即使查询page=1
            throw new BusinessLogicRunTimeException("资料模板不存在");
        }

        if (formFieldsAll.isEmpty()) {
            throw new BusinessLogicRunTimeException(String.format("classId=%s 没有模板数据,请联系管理员！", classId));
        }

        // 查询的目标表
        String selTable = "";
        // 查询目标模板
        Map<String, FormFields> selFormFields = (Map<String, FormFields>) formFieldsAll.get(String.valueOf(page));

        if (selFormFields.isEmpty()) {
            throw new BusinessLogicRunTimeException(String.format("classId=%s page=%s 没有模板数据,请联系管理员！", classId, page));
        }

        if (page == 0) {
            // 主表
            selTable = formClass.getTableName();
        } else {
            // 子表
            selTable = ((FormClassEntry) formEntries.get(String.valueOf(page))).getTableName();
        }

        StringBuilder sbSelect = new StringBuilder();
        StringBuilder sbFrom = new StringBuilder();
        String separator = System.getProperty("line.separator");


        sbSelect.append("SELECT").append(separator);
        sbFrom.append("FROM ").append(selTable).append(separator);

        for (String fieldKey : selFormFields.keySet()) {

            FormFields formField = selFormFields.get(fieldKey);

            String joinType = formField.getJoinType();

            if (joinType == null || "".equals(joinType.trim())) {
                // 默认 INNER JOIN
                joinType = "INNER JOIN";
            }

            // 两边加一空格，防止模板配置时两边无空格，链接脚本错误
            joinType = " " + joinType.trim() + " ";
            // 过滤条件-用于关联表时的附加条件
            String filter = formField.getFilter();

            String name = formField.getName();
            String sqlColumnName = formField.getSqlColumnName();
            String key = formField.getKey();
            Integer dataType = formField.getDataType();
            Integer index = formField.getIndex();
            Integer display = formField.getDisplay();
            // 查找类型类别
            Integer lookUpType = formField.getLookUpType();
            // 具体查找类型
            Integer lookUpClassId = formField.getLookUpClassId();
            // 关联查询表
            String srcTable = formField.getSrcTable();
            // 关联表查询时，关联表可以给一个别名
            String srcTableAlisAs = formField.getSrcTableAlis();
            // 关联表字段
            String srcField = formField.getSrcField();
            // 查询时的显示字段(如订单关联物料查询，显示物料的名称)
            String disPlayField = formField.getDisplayField();
            // 扩展显示字段(如订单关联物料查询，还可显示物料的代码)
            String displayExt = formField.getDisplayExt();
            // 字段是否有物理表字段保存(如订单表中物料的规格型号不需要保存在订单表中，而是通过订单中的物料关联查询物料表获取)
            boolean needSave = formField.getNeedSave();

            // 设置了关联查询表别名时，查询字段应该用别名.字段名
            // eg: select A.colA, B.colB from table_a AS A inner join table_b AS B on A.id=B.id
            String srcTableAlis = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;

            if (lookUpType == 1 || lookUpType == 2) {

                // eg: select order.number as number_key,item.name as name_DspName,item.number as number_NmbName from
                // order inner join item on order.item_id=item.id

                // 基础资料/辅助资料引用类型-主表中记录关联字段值一般是id
                // 查询出主表中记录的关联字段值
                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", selTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                // 查询出关联表该显示的值
                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key + "_DspName", eDelimiter)).append(separator);
                // 如果增加配置了关联表扩展显示值，则一并查询出
                if (displayExt != null && !"".equals(displayExt.trim())) {
                    // 代码显示字段
                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, displayExt, eDelimiter, bDelimiter, key + "_NmbName", eDelimiter)).append(separator);
                }

                // from 中同时增加关联表
                sbFrom.append(joinType).append(srcTable);

                // 关联表的别名
                if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                    sbFrom.append(" AS ").append(srcTableAlisAs);
                }
                // 主表与关联表的关联条件
                sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", selTable, bDelimiter, sqlColumnName, eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
                        .append(separator);

                if (filter != null && !"".equals(filter.trim())) {
                    // 表链接有附加条件-附件条件程序不校验合法性
                    sbFrom.append(filter).append(separator);
                }

            } else if (lookUpType == 3) {

                // 引用基础资料的附加属性-依赖lookUpType == 1 || lookUpType == 2 存在,即模板中必须存在该记录lookUpClassId且lookUpType=1/2 的字段模板
                // 即引用基础资料属性的模板中，disPlayField的配置统一认为是被引用基础资料模板中的key，需要二次验证引用资料模板确认查询字段

                // 举例：目标，在车辆信息中显示其车辆类别的车辆付费类型
                // 基础资料车辆信息中引用另一个基础资料车辆类别，还需要显示基础资料车辆类别的另一个属性"车辆付费类型"，而"车辆付费类型"在车辆类别模板中又是辅助资料引用类型，
                // 此时在车辆信息的模板中配置携带车辆类别基础资料的附属属性"车辆付费类型"的模板时，模板中disPlayField应配置为"车辆类别"模板中"车辆付费类型"的key(payType),
                // srcField应该配置为"车辆类别"表与辅助资料的关联字段(即车辆类别表t_CarType与辅助资料车辆付费类型的关联字段payType),
                // srcTableAlisAs必须配置，可随意，但需保证在车辆信息模板中不重复。

                FormFields lookUpTypeFormField = getFormField(lookUpClassId, disPlayField);

                if (lookUpTypeFormField == null) {
                    throw new BusinessLogicRunTimeException(String.format("classId=%s, key=%s 的disPlayField配置错误，请联系管理员", classId, key));
                }

                String nameEx = lookUpTypeFormField.getName();
                Integer lookUpTypeEx = lookUpTypeFormField.getLookUpType();
                String joinTypeEx = lookUpTypeFormField.getJoinType();
                String srcTableEx = lookUpTypeFormField.getSrcTable();
                String srcFieldEx = lookUpTypeFormField.getSrcField();

                if (needSave) {
                    // 需要保存的携带属性值直接从主表查询
                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", selTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                } else {
                    // 不需要保存的引用字段关联查询
                    if (lookUpTypeEx != null && lookUpTypeEx > 0) {

                        // 附加属性又是引用类型的情况--取显示字段并关联表
                        sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, nameEx, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                        // from 中同时增加关联表
                        sbFrom.append(joinTypeEx).append(srcTableEx);

                        // 关联表的别名
                        sbFrom.append(" AS ").append(srcTableAlis);

                        sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", srcTableEx, bDelimiter, srcFieldEx, eDelimiter, srcTableAlis, bDelimiter, srcFieldEx, eDelimiter))
                                .append(separator);
                    } else {
                        // 普通属性
                        sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                    }

                }

            } else if (lookUpType == 4) {
                // 普通引用-引用其他表数据

                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                // from 中同时增加关联表
                sbFrom.append(joinType).append(srcTable);

                // 关联表的别名
                if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                    sbFrom.append(" AS ").append(srcTableAlisAs);
                }

                sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", selTable, bDelimiter, sqlColumnName, eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
                        .append(separator);

            } else if (lookUpType == 5) {

                // 普通引用其他表的其他字段-
                // 主要为了避免为4即引用他表数据时，需引用多个字段时关联表重复问题。依附于=4时存在,即模板中肯定存在lookUpType=4的字段模板

                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

            } else {
                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", selTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
            }
        }

/*        for (Iterator<String> it = selFormFields.keySet().iterator(); it.hasNext(); ) {

            String fieldKey = it.next();
            FormFields formField = selFormFields.get(fieldKey);

            String sqlColumnName = formField.getSqlColumnName();
            String key = formField.getKey();
            boolean needSave = formField.getNeedSave();

            if (needSave) {
                // 不需要保存的字段不需要查询
                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", sbSelect, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
            }

        }*/

        String select = sbSelect.toString().trim();
        select = select.substring(0, select.length() - 1);
        String from = sbFrom.toString().trim();

        Map<String, Object> statement = new HashMap<String, Object>(6);

        statement.put("select", select);
        statement.put("from", from);

        return statement;
    }

    /**
     * 构建查询时的where条件
     *
     * @param classId    业务类别
     * @param pageIndex  可以获取指定page的条件(-1获取所有page的条件,默认所有)
     * @param conditions 结构化条件信息
     * @return 查询条件
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getWhere(Integer classId, Integer pageIndex, List<Condition> conditions) {

        Map<String, Object> ret = new HashMap<String, Object>(16);

        pageIndex = pageIndex == null ? -1 : pageIndex > 1 ? 1 : pageIndex;

        StringBuilder sbWhere = new StringBuilder();
        // 保存sql格式化参数
        Map<String, Object> sqlParams = new HashMap<String, Object>();

        String separator = System.getProperty("line.separator");

        // 基础资料模板
        Map<String, Object> templateMap = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<String, FormFields> formFieldsAll = getFormFields(classId, -1);
        // 主表资料描述信息
        FormClass formClass = (FormClass) templateMap.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) templateMap.get("formClassEntry");

        if (null == formClass) {
            throw new BusinessLogicRunTimeException("没有模板数据");
        }

        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        // 主表物理表名
        String primaryTableName = formClass.getTableName();
        // 主表物理表主键
        String primaryKey = formClass.getPrimaryKey();
        // 第一个子表物理表名(如果有)
        String childTableName = "";
        // 第一个子表与主表的关联字段在模板中的key(外键)
        String foreignKey = "";

        if (!formEntries.isEmpty()) {

            // 存在关联字表-只关联第一个子表查询
            FormClassEntry formEntry = (FormClassEntry) formEntries.get("1");
            childTableName = formEntry.getTableName();
            foreignKey = formEntry.getForeignKey();

            if ("".equals(childTableName) || "".equals(foreignKey)) {
                // 存在子表但子表formClassEntry中配置错误
                throw new BusinessLogicRunTimeException(String.format("classId=%s formClassEntry模板数据配置错误", classId));
            }
        }

        // 将所有用户条件包含在AND()中，即所有过滤条件的结果是 WHERE 1=1 AND (用户过滤条件)
        String whereStructure = "WHERE 1=1 AND (%s)";

        for (int i = 0; i < conditions.size(); i++) {

            Condition condition = conditions.get(i);

            // 处理脚本参数格式化时参数值不符合TSQL规则BUG
            String preValue = "";
            // eg：select 1 where id IN (#{value})不能写成select 1 where id IN #{value}
            String sufValue = "";

            // 是否跳过格式化参数，eg IN比较符号时，不使用此系统动态查询方式
            boolean skip = false;

            // AND OR 条件链接符号-默认AND
            Condition.LinkTypeEnum linkType = condition.getLinkType();

            // 第一个条件忽略连接关系
            //andOr = i == 0 ? "" : andOr;


            // 左括号-可能有多个，如 "(("，甚至"((("等复杂查询,默认"("
            String leftParenTheses = condition.getLeftParenTheses();

            // 比较字段名
            String fieldKey = condition.getFieldKey();

            if (null == fieldKey || fieldKey.trim().isEmpty()) {
                throw new BusinessLogicRunTimeException("参数错误：condition必须包括fieldKey");
            }

            // 比较符号
            Condition.LogicOperatorEnum logicOperator = condition.getLogicOperator();

            if (logicOperator == Condition.LogicOperatorEnum.NOT_SUPPORT) {
                throw new BusinessLogicRunTimeException("参数错误：condition必须包含正确的logicOperator");
            }

            // 比较值
            Object value = condition.getValue();

            if (null == value) {
                throw new BusinessLogicRunTimeException("参数错误：condition必须包括比较值value");
            }

            // 右括号
            String rightParenTheses = condition.getRightParenTheses();

            // 是否需要转换条件字段，用于传入引用他表字段时过滤
            // 例如传入引用基础资料key是否需要转换为名称条件
            // 用户输入时通常需要转换成名称查询，而代码中调用不需要转换，直接匹配
            boolean needConvert = condition.getNeedConvert();

            // 没有定义模板-忽略
            if (!formFieldsAll.containsKey(fieldKey)) {
                continue;
            }
            // 不是指定page的过滤条件
            if (pageIndex > 0 && formFieldsAll.get(fieldKey).getPage().equals(pageIndex)) {
                continue;
            }

            FormFields formField = formFieldsAll.get(fieldKey);

            Integer page = formField.getPage();
            String sqlColumnName = formField.getSqlColumnName();
            Integer lookUpType = formField.getLookUpType();
            Integer lookUpClassId = formField.getLookUpClassId();
            String srcTable = formField.getSrcTable();
            String srcTableAlisAs = formField.getSrcTableAlis();
            String disPlayField = formField.getDisplayField();
            String srcField = formField.getSrcField();
            Integer dataType = formField.getDataType();

            // 确定当前字段是属于哪个表
            String formFieldLinkedTable = "";

            if (page == 0) {
                formFieldLinkedTable = primaryTableName;
            } else if (page == 1) {
                formFieldLinkedTable = childTableName;
            } else {
                // 主表不支持关联多个子表查询
                continue;
            }

            String tableName = formFieldLinkedTable;
            String fieldName = sqlColumnName;

            // 模板字段的数据类型(数字，本文，日期，布尔)
            DataTypeEnum dataTypeEnum = DataTypeEnum.getTypeEnum(dataType);

            if (needConvert && lookUpType > 0) {
                // 只有引用类型有转换与非转换情况
                // 需要转换为名称查询的引用类型的查询条件，dataType可能不是文本类型，但条件值是文本，需要文本格式化，此处修正值格式化类型
                dataTypeEnum = DataTypeEnum.TEXT;
            }

            switch (dataTypeEnum) {

                case NUMBER:
                    // 一般数字类型的不可能用like
                    break;
                case TEXT:

                    if (logicOperator == Condition.LogicOperatorEnum.LIKE) {
                        // 不处理，调用者控制左匹配%xxx或右匹配xxx%或全匹配%xxx%
                        //value = "%" + value + "%";
                    } else if (logicOperator == Condition.LogicOperatorEnum.IN) {
                        // 不适用此系统动态查询方式，对于IN，手工拼接脚本
                        preValue = "(";
                        sufValue = ")";
                        skip = true;
                    }
                    break;
                case TIME:
                    if (logicOperator == Condition.LogicOperatorEnum.LESS_OR_EQUAL) {
                        if (!Common.isLongDate(String.valueOf(value))) {
                            // 由于数据库中日期可能存储有时分秒，过滤天时过滤到当前23:59:59
                            value = value + " 23:59:59";
                        }
                    } else if (logicOperator == Condition.LogicOperatorEnum.GREATER_OR_EQUAL) {
                        if (!Common.isLongDate(String.valueOf(value))) {
                            value = value + " 00:00:00";
                        }
                    }
                    break;
                case BOOLEAN:
                    // boolean b = value.equals("是") ? true : false;
                    // 此类字段数据库中一般要求用bit类型,即非0即1
                    value = "是".equals(value) ? "1" : "否".equals(value) ? "0" : "2";
                    break;
                default:
                    break;
            }

            if (lookUpType == 1 || lookUpType == 2) {
                // 基础资料-辅助资料引用类型

                tableName = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;

                if (needConvert) {
                    // 需要转换则使用关联表显示字段作为条件
                    fieldName = disPlayField;
                } else {
                    // 不需要转换，直接使用关联字段匹配
                    fieldName = srcField;
                }

            } else if (lookUpType == 3) {

                // 引用字段携带属性查询-使用关联表显示字段作为条件
                tableName = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;

                // 携带基础资料属性的字段过滤
                // 即引用基础资料属性的模板中，disPlayField的配置统一认为是引用基础资料模板中的key，需要二次验证引用资料模板确认查询字段

                FormFields lookUpFormField = getFormField(lookUpClassId, disPlayField);

                String name = lookUpFormField.getName();
                Integer lookUpTypeEx = lookUpFormField.getLookUpType();
                String srcTableEx = lookUpFormField.getSrcTable();
                String srcFieldEx = lookUpFormField.getSrcField();

                if (lookUpTypeEx != null && lookUpTypeEx > 0) {
                    // 基础资料的附加属性又是引用类型的情况--取显示字段并关联表
                    if (needConvert) {
                        fieldName = name;
                    } else {
                        fieldName = srcFieldEx;
                    }
                } else {
                    if (needConvert) {
                        fieldName = disPlayField;
                    } else {
                        fieldName = srcFieldEx;
                    }
                }

            } else if (lookUpType == 4) {
                // 普通表关联查询
                if (dataType == 2) {
                    // 文本类的关联字段，未防止关联表中无记录，此处取主表字段值-如订单查询FCarNo字段取数
                    tableName = formFieldLinkedTable;
                } else {
                    // 引用字段查询-使用关联表显示字段作为条件
                    tableName = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;

                    if (needConvert) {
                        fieldName = disPlayField;
                    } else {
                        fieldName = srcField;
                    }
                }

            } else if (lookUpType > 4) {

                // 引用字段查询-使用关联表显示字段作为条件
                tableName = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;
                if (needConvert) {
                    fieldName = disPlayField;
                } else {
                    fieldName = srcField;
                }

            }

            if (skip) {
                // 手工脚本-主要是IN过滤类型MyBatis不好格式化参数
                sbWhere.append(separator).append(String.format("%s %s %s.%s%s%s %s %s %s %s %s ", linkType, leftParenTheses, tableName, bDelimiter, fieldName, eDelimiter, logicOperator, preValue, value,
                        sufValue, rightParenTheses));
            } else {
                // 动态脚本
                sbWhere.append(separator).append(String.format("%s %s %s.%s%s%s %s %s %s %s %s ", linkType, leftParenTheses, tableName, bDelimiter, fieldName, eDelimiter, logicOperator, preValue,
                        "#{" + fieldKey + i + "}", sufValue, rightParenTheses));
                // 格式化参数
                sqlParams.put(fieldKey + i, value);
            }

        }
        //将所有过滤条件用（）括起来

        String where = sbWhere.toString().trim();

        if (!"".equals(where)) {

            whereStructure = String.format(whereStructure, where);
            ret.put("whereStr", whereStructure);
            ret.put("whereParams", sqlParams);
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    private String getSort(Integer classId, List<Sort> sorts) {

        StringBuilder sbOrderBy = new StringBuilder();
        String separator = System.getProperty("line.separator");

        // 基础资料模板
        Map<String, Object> template = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<String, FormFields> formFieldsAll = getFormFields(classId, -1);
        // 主表资料描述信息
        FormClass itemClass = (FormClass) template.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formEntries");

        if (null == itemClass) {
            throw new BusinessLogicRunTimeException("没有模板数据");
        }

        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        String primaryTableName = itemClass.getTableName();
        String primaryKey = itemClass.getPrimaryKey();

        sbOrderBy.append("ORDER BY");

        for (Sort sort : sorts) {

            String fieldKey = sort.getFieldKey();

            if ("".equals(fieldKey)) {
                // 没有传递fieldKey,忽略
                continue;
            }
            // 默认ASC排序
            Sort.DirectionEnum orderDirection = sort.getDirection();

            if (!formFieldsAll.containsKey(fieldKey)) {
                // 没有定义模板-忽略
                continue;
            }

            FormFields formFields = formFieldsAll.get(fieldKey);

            Integer page = formFields.getPage();
            String sqlColumnName = formFields.getSqlColumnName();
            Integer lookUpType = formFields.getLookUpType();
            String srcTable = formFields.getSrcTable();
            String srcTableAlisAs = formFields.getSrcTableAlis();
            String disPlayField = formFields.getDisplayField();

            String tableName = primaryTableName;

            if (page > 0) {
                FormClassEntry entry = (FormClassEntry) formEntries.get(String.valueOf(page));
                tableName = entry.getTableName();
            }

            String fieldName = sqlColumnName;

            if (lookUpType > 0) {
                // 引用类型字段-找到真实的表，字段
                tableName = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;
                fieldName = disPlayField;
            }

            sbOrderBy.append(separator).append(String.format("%s.%s%s%s %s,", tableName, bDelimiter, fieldName, eDelimiter, orderDirection));

        }

        String orderBy = sbOrderBy.toString();
        if ("ORDER BY".equals(orderBy)) {
            return "";
        }

        return orderBy.substring(0, orderBy.length() - 1);
    }

    /**
     * 获取数据库字段关键字处理符号
     *
     * @return String
     * @Title getDBDelimiter
     * @date 2017-04-21 15:48:28 星期五
     */
    private Map<String, String> getDBDelimiter() {

        Map<String, String> ret = new HashMap<String, String>() {

            private static final long serialVersionUID = -2157281653097860908L;

            {
                put("bDelimiter", "`");
                put("eDelimiter", "`");
            }
        };

        // sqlSession.getConnection();
        //
        // Connection connection = null;
        //
        // SqlSessionTemplate st = (SqlSessionTemplate) sqlSession;
        //
        // connection = st.getSqlSessionFactory().openSession().getConnection();
        // try {
        // String dbName = connection.getMetaData().getDatabaseProductName();
        //
        // if (dbName.contains("Microsoft")) {
        // // Microsoft SQL Server
        // ret.put("bDelimiter", "[");
        // ret.put("eDelimiter", "]");
        //
        // } else if (dbName.contains("MySql")) {
        // // MySql
        // ret.put("bDelimiter", "`");
        // ret.put("eDelimiter", "`");
        //
        // }
        //
        // } catch (SQLException e) {
        //
        // e.printStackTrace();
        // }

        return ret;

    }

    /**
     * 根据classId,key查找特定单据指定模板信息
     */
    private FormFields getFormField(int classId, String key) {

        FormFieldsMapper fieldsMapper = sqlSession.getMapper(FormFieldsMapper.class);

        FormFieldsExample example = new FormFieldsExample();

        FormFieldsExample.Criteria criteria = example.createCriteria();

        criteria.andClassIdEqualTo(classId);
        criteria.andKeyEqualTo(key);

        List<FormFields> formFields = fieldsMapper.selectByExample(example);

        if (!formFields.isEmpty()) {
            return formFields.get(0);
        }

        return null;
    }

    /**
     * 获取指定classId中指定page的字段模板
     * 如果page=-1则获取所有
     *
     * @param classId 业务类别
     * @param page    page
     * @return 指定page的字段模板
     */
    private Map<String, FormFields> getFormFields(int classId, int page) {

        Map<String, FormFields> retMap = new LinkedHashMap<String, FormFields>();

        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        // 获取单据模板
        // page=0表头
        FormFieldsMapper fieldsMapper = sqlSession.getMapper(FormFieldsMapper.class);

        FormFieldsExample fieldsExample = new FormFieldsExample();
        FormFieldsExample.Criteria fieldsCriteria = fieldsExample.createCriteria();

        fieldsCriteria.andClassIdEqualTo(classId);

        if (page != -1) {
            fieldsCriteria.andPageEqualTo(page);
        }
        // 查询结果按照page index排序
        fieldsExample.setOrderByClause(String.format("page, %sindex%s", bDelimiter, eDelimiter));

        List<FormFields> fieldsByExample = fieldsMapper.selectByExample(fieldsExample);

        // 打包字段模板-打包成Map好操作
        for (FormFields item : fieldsByExample) {
            retMap.put(item.getKey(), item);
        }

        return retMap;

    }

    /**
     * 判断List<Map<String, Object>>中的Map中key为targetKey的元素的值是否为targetValue，是则并返回该元素，否则返回null
     *
     * @param list        List<Map<String, Object>>
     * @param targetKey
     * @param targetValue
     * @return
     */
    private Map<String, Object> isKeyInList(List<Map<String, Object>> list, String targetKey, Object targetValue) {

        for (Map<String, Object> map : list) {
            if (map.containsKey(targetKey) && map.get(targetKey).equals(targetValue)) {
                return map;
            }
        }
        return null;
    }
}
