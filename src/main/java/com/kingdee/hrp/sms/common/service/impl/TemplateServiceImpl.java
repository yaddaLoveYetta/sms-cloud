package com.kingdee.hrp.sms.common.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kingdee.hrp.sms.common.dao.customize.TemplateDaoMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassEntryMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormFieldMapper;
import com.kingdee.hrp.sms.common.enums.CtrlType;
import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.exception.PlugInRuntimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.*;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.TemplateService;
import com.kingdee.hrp.sms.common.service.plugin.PlugIn;
import com.kingdee.hrp.sms.common.service.plugin.PlugInRet;
import com.kingdee.hrp.sms.util.Common;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.ExcelUtil;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.sun.tools.corba.se.idl.StringGen;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author yadda
 */
@Service
public class TemplateServiceImpl extends BaseService implements TemplateService {

    @Resource(name = "plugInFactory")
    private PlugIn plugInFactory;

    private static Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    /**
     * 查询基础资料/单据模板数据
     *
     * @param classId 业务类别
     * @param type    查询方式（0:后端查询 1:前端获取）
     * @return Map<String, Object>
     */
    @Override
    public FormTemplate getFormTemplate(Integer classId, Integer type) {

        //  单据模板信息
        FormTemplate formTemplate = new FormTemplate();

        // 获取单据类别描述信息
        FormClassMapper classMapper = sqlSession.getMapper(FormClassMapper.class);

        // 主表单据类别描述
        FormClass formClass = classMapper.selectByPrimaryKey(classId);

        if (null == formClass) {
            throw new BusinessLogicRunTimeException("模板不存在或不唯一,请联系管理员!");
        }

        formTemplate.setFormClass(formClass);

        // 获取单据模板page=0表头
        FormFieldMapper formFieldsMapper = sqlSession.getMapper(FormFieldMapper.class);

        FormFieldExample fieldsExample = new FormFieldExample();
        FormFieldExample.Criteria fieldsCriteria = fieldsExample.createCriteria();

        fieldsCriteria.andClassIdEqualTo(classId);
        fieldsCriteria.andPageEqualTo(0);

        // 当前用户显示的模板
        //fieldsCriteria.andDisplayEqualTo(getCurrentDisplayMask(null));

        if (type == 1) {
            // 后端构建查询脚本时调用
            fieldsExample.orderBy(FormField.Column.ctrlIndex.asc());
        } else {
            // 前端处理显示顺序时调用
            fieldsExample.orderBy(FormField.Column.index.asc());
        }

        List<FormField> headFields = formFieldsMapper.selectByExample(fieldsExample);

        // 子表单据类别描述
        FormClassEntryMapper classEntryMapper = sqlSession.getMapper(FormClassEntryMapper.class);
        FormClassEntryExample classEntryExample = new FormClassEntryExample();
        FormClassEntryExample.Criteria entryCriteria = classEntryExample.createCriteria();

        entryCriteria.andClassIdEqualTo(classId);
        List<FormClassEntry> formClassEntries = classEntryMapper.selectByExample(classEntryExample);

        // 打包表头字段模板
        Map<String, FormField> formFields0 = new LinkedHashMap<>();
        for (FormField item : headFields) {
            formFields0.put(item.getKey(), item);
        }
        // 表头模板
        formTemplate.getFormFields().put(0, formFields0);

        // 循环打包子表字段模板
        for (FormClassEntry entry : formClassEntries) {

            Integer entryIndex = entry.getEntryIndex();

            formTemplate.getFormClassEntry().put(entry.getEntryIndex(), entry);

            // 查询子表字段模板(按子表page)
            FormFieldExample formFieldExample = new FormFieldExample();
            FormFieldExample.Criteria formFieldsExampleCriteria = formFieldExample.createCriteria();

            formFieldsExampleCriteria.andClassIdEqualTo(classId).andPageEqualTo(entry.getEntryIndex());

            formFieldExample.orderBy(FormField.Column.index.asc());

            List<FormField> entryIndexFieldsByExample = formFieldsMapper.selectByExample(formFieldExample);

            Map<String, FormField> formFieldsEntryIndex = new LinkedHashMap<String, FormField>();
            // 打包子表字段模板
            for (FormField fields : entryIndexFieldsByExample) {
                formFieldsEntryIndex.put(fields.getKey(), fields);
            }

            // 第entryIndex个子表模板
            formTemplate.getFormFields().put(entry.getEntryIndex(), formFieldsEntryIndex);
        }

        return formTemplate;

    }

    /**
     * 获取基础资料/单据定义的功能操作列表
     *
     * @param classId 业务类型
     * @param type    获取按钮的场景 ( 0:查看(列表)1:(新增)2:(编辑)默认0)
     * @return 功能操作列表
     */
    @Override
    public List<FormAction> getFormAction(Integer classId, Integer type) {

        UserRoleType userRoleType = SessionUtil.getUserRoleType();

        // 按钮可用性-跟FormFields 模板中display字段配置规则一致
        int ownerType = userRoleType == UserRoleType.SYSTEM ? 1 : userRoleType == UserRoleType.HOSPITAL ? 2 : 4;
        // 按钮显示性
        int display = 0;

        if (type == 0) {
            // 查看
            switch (userRoleType) {
            case SYSTEM:
                //系统管理员
                display = 1;
                break;
            case HOSPITAL:
                //医院
                display = 64;
                break;
            case SUPPLIER:
                //供应商
                display = 8;
                break;
            default:
                break;
            }
        } else if (type == 1) {
            // 新增
            switch (userRoleType) {
            case SYSTEM:
                //系统管理员
                display = 2;
                break;
            case HOSPITAL:
                //医院
                display = 128;
                break;
            case SUPPLIER:
                //供应商
                display = 16;
                break;
            default:
                break;
            }
        } else if (type == 2) {
            // 编辑
            switch (userRoleType) {
            case SYSTEM:
                //系统管理员
                display = 4;
                break;
            case HOSPITAL:
                //医院
                display = 256;
                break;
            case SUPPLIER:
                //供应商
                display = 32;
                break;
            default:
                break;
            }
        }

        FormActionMapper formActionMapper = sqlSession.getMapper(FormActionMapper.class);

        FormActionExample formActionExample = new FormActionExample();

        FormActionExample.Criteria criteria = formActionExample.createCriteria();

        criteria.andClassIdEqualTo(classId);
        // 功能按钮按照index,name排序
        formActionExample.orderBy(FormAction.Column.index.asc(), FormAction.Column.name.asc());

        List<FormAction> formActions = formActionMapper.selectByExample(formActionExample);

        Iterator<FormAction> it = formActions.iterator();

        while (it.hasNext()) {
            FormAction formAction = it.next();
            Integer actionOwnerType = formAction.getOwnerType();
            Integer actionDisplay = formAction.getDisplay();
            // 权限验证 TODO
            if (!((actionOwnerType & ownerType) == ownerType && (actionDisplay & display) == display)) {
                // 不显示或非该角色类别拥有的功能
                it.remove();
            }
        }

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
    public Map<String, Object> getItems(Integer classId, List<Condition> conditions, List<Sort> sorts, Integer pageSize,
            Integer pageNo) {

        // 返回结构
        Map<String, Object> ret = new HashMap<String, Object>(16);

        // 基础资料模板
        FormTemplate template = this.getFormTemplate(classId, 1);
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();
        // 主表字段模板
        Map<String, FormField> formFields0 = template.getFormFields().get(0);

        if (null == formClass || formFields0.isEmpty()) {
            throw new BusinessLogicRunTimeException("资料模板不存在");
        }

        // 插件事件 begin-------
        List<Condition> pluginConditions = plugInFactory.getConditions(classId, template, conditions);
        if (pluginConditions != null && pluginConditions.size() > 0) {
            conditions.addAll(pluginConditions);
        }
        PlugInRet plugInRet = plugInFactory.beforeQuery(classId, template, conditions);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new BusinessLogicRunTimeException(plugInRet.getMsg());
        }

        // 插件事件 end-------

        // 主表表名
        String primaryTableName = formClass.getTableName();
        // 主表主键key
        String primaryKey = formClass.getPrimaryKey();
        // 主表主键数据库字段名
        String primaryColumnName = ((FormField) formFields0.get(primaryKey)).getSqlColumnName();
        // 指示是否存在子表，存在时主表需要关联第一个子表查询
        boolean isChildTableExist = false;

        if (!formEntries.isEmpty()) {
            // 存在关联字表-只关联第一个子表查询
            isChildTableExist = true;
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
        String sql = selectExec + System.getProperty("line.separator") + from + System.getProperty("line.separator") +
                where + System.getProperty("line.separator")
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
     * @param sorts   排序结构 查询单据时，单据分录需要排序
     */
    @Override
    public Map<String, Object> getItemById(Integer classId, Long id, List<Sort> sorts) {

        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        List<Map<String, Object>> list = getItemByIds(classId, ids, null, sorts);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        throw new BusinessLogicRunTimeException(String.format("找不到id为%s的单据", id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getItemByIds(Integer classId, List<Long> idList, List<Condition> conditions,
            List<Sort> sorts) {

        if (idList == null || idList.size() == 0) {
            throw new BusinessLogicRunTimeException("请提交单据内码");
        }

        String ids = StringUtils.join(idList.toArray(), ",");

        // 基础资料模板
        FormTemplate template = getFormTemplate(classId, 1);
        // 主表字段模板
        Map<String, FormField> formFields0 = template.getFormFields().get(0);
        // 第一个子表字段模板(如果有)
        Map<String, FormField> formFields1 = new HashMap<>(32);
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

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
            formFields1 = template.getFormFields().get(1);
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
        sbWhere.append("WHERE").append(System.getProperty("line.separator"))
                .append(String.format("%s.%s%s%s IN (%s)", primaryTableName, bDelimiter, primaryKey, eDelimiter, ids));
        where = sbWhere.toString();

        Map<String, Object> sqlMap = new HashMap<String, Object>();
        // 完整的sql(带格式化参数)
        String sql =
                select + System.getProperty("line.separator") + from + System.getProperty("line.separator") + where +
                        System.getProperty("line.separator") + orderBy
                        + System.getProperty("line.separator");
        sqlMap.put("sql", sql);

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);
        // 主表记录
        List<Map<String, Object>> dataHead = templateDaoMapper.getItems(sqlMap);

        if (!formEntries.isEmpty()) {
            // 存在关联字表-只关联第一个子表查询

            FormClassEntry entry = formEntries.get(1);
            String entryTableName = entry.getTableName();
            String foreignKey = entry.getForeignKey();

            statement = getStatement(classId, 1);
            // 清空原过滤条件
            where = " WHERE 1=1 ";
            // 获取客户端传入的查询条件
            if (conditions != null && !conditions.isEmpty()) {
                whereMap = getWhere(classId, 1, conditions);
            }
            if (!whereMap.isEmpty()) {
                where = whereMap.get("whereStr").toString();
                whereParams = (Map<String, Object>) whereMap.get("whereParams");
            }

            select = (String) statement.get("select");
            from = (String) statement.get("from");

            // 查询条件-直接用传入的主键做子表的外键条件
            sbWhere = new StringBuilder();
            sbWhere.append(where).append(System.getProperty("line.separator")).append(String
                    .format(" AND %s.%s%s%s IN (%s)", entryTableName, bDelimiter, foreignKey, eDelimiter, ids));
            where = sbWhere.toString();

            sqlMap = new HashMap<String, Object>();
            // 完整的sql(带格式化参数)
            sql = select + System.getProperty("line.separator") + from + System.getProperty("line.separator") + where +
                    System.getProperty("line.separator") + orderBy
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

                    ArrayList<Map<String, Object>> headEntry = ((Map<String, ArrayList<Map<String, Object>>>) dataHeadItem
                            .get("entry")).get("1");

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
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public Long addItem(Integer classId, String data) throws IOException {

        // 基础资料模板
        FormTemplate template = getFormTemplate(classId, 1);
        // 主表字段模板
        Map<String, FormField> formFields = template.getFormFields().get(0);
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

        // 转成json便于操作
        ObjectMapper mapper = Environ.getBean(ObjectMapper.class);
        JsonNode jsonNode = mapper.readTree(data);

        // 保存前插件事件
        PlugInRet plugInRet = plugInFactory.beforeSave(classId, template, jsonNode);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }

        // 主表主键key
        String primaryKey = formClass.getPrimaryKey();
        // 生成主表主键值
        Long id = getId();

        // 准备单据头保存模板
        Map<String, Object> statement = prepareAddMap(jsonNode, formFields, formClass.getTableName(),
                formClass.getPrimaryKey(), id);

        Map<String, Object> sqlMap = new HashMap<String, Object>();

        String tableName = statement.get("tableName").toString();
        String fieldStr = statement.get("fieldStr").toString();
        String valueStr = statement.get("valueStr").toString();
        Map<String, Object> sqlParams = (Map<String, Object>) statement.get("sqlParams");

        String sql = "insert into " + tableName + " ( " + fieldStr + " ) values ( " + valueStr + " )";
        // 完整带参数的sql
        sqlMap.put("sql", sql);
        // --格式化参数
        sqlMap.putAll(sqlParams);

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);
        // 插入主表数据
        templateDaoMapper.add(sqlMap);

        // 处理分录数据
        handleEntryData(classId, id, jsonNode);

        // 新增后事件
        plugInRet = plugInFactory.afterSave(classId, id, jsonNode);
        if (plugInRet != null && plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }

        return id;

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
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public Boolean editItem(Integer classId, Long id, String data) throws IOException {

        // 基础资料模板
        FormTemplate template = getFormTemplate(classId, 1);

        // 主表的字段模板
        Map<String, FormField> formFields = template.getFormFields().get(0);

        // 主表资料描述信息
        FormClass formClass = template.getFormClass();

        // 转成json便于操作
        ObjectMapper mapper = Environ.getBean(ObjectMapper.class);
        JsonNode jsonNode = mapper.readTree(data);

        // 修改前插件事件
        PlugInRet plugInRet = plugInFactory.beforeModify(classId, id, template, jsonNode);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }

        String primaryTableName = formClass.getTableName();
        String primaryKey = formClass.getPrimaryKey();
        String primaryColumnName = formFields.get(primaryKey).getSqlColumnName();

        // 准备保存模板
        Map<String, Object> statement = prepareEditMap(jsonNode, formFields, primaryTableName, primaryKey, id);

        // 修改基础资料
        if (!"".equals(statement.get("kvStr")) && (statement.get("kvStr") != null)) {

            String tableName = statement.get("tableName").toString();
            String kvStr = statement.get("kvStr").toString();

            Map<String, Object> sqlParams = (Map<String, Object>) statement.get("sqlParams");

            Map<String, Object> sqlMap = new HashMap<String, Object>();

            String sql =
                    "update " + tableName + " set " + kvStr + " where " + primaryColumnName + "= #{" + primaryKey + "}";
            // 完整带参数的sql
            sqlMap.put("sql", sql);
            // --格式化参数列表
            sqlMap.putAll(sqlParams);

            TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);
            templateDaoMapper.edit(sqlMap);
        }

        // 处理分录数据
        handleEntryData(classId, id, jsonNode);

        // 修改后 插件事件
        plugInRet = plugInFactory.afterModify(classId, id, template, jsonNode);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }

        return true;
    }

    /**
     * 删除基础资料
     *
     * @param classId 业务类型
     * @param ids     删除的内码集合
     * @return 是否成功
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public Boolean delItem(Integer classId, List<Long> ids) {

        if (ids.isEmpty()) {
            throw new BusinessLogicRunTimeException("没有待删除的数据!");
        }

        // 基础资料模板
        FormTemplate template = getFormTemplate(classId, 1);
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 主表字段模板
        Map<String, FormField> formFields = template.getFormFields().get(0);

        String primaryTableName = formClass.getTableName();
        String primaryKey = formClass.getPrimaryKey();
        FormField ff = formFields.get(primaryKey);

        // 删除前插件事件
        PlugInRet plugInRet = plugInFactory.beforeDelete(classId, template, ids);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

        // 先删除分录数据（子表）
        delEntryData(formEntries, ids);
        // 再删除基础资料（主表）
        // 准备删除模板
        Map<String, Object> statement = prepareStatement(ids, primaryTableName, primaryKey);

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);

        if (!statement.isEmpty()) {
            templateDaoMapper.del(statement);
        }
        // 删除后插件事件
        plugInRet = plugInFactory.afterDelete(classId, template, ids);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }

        return true;
    }

    /**
     * 禁用/反禁用(基础资料用)
     *
     * @param classId     业务类型
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return 是否成功
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public Boolean forbid(Integer classId, List<Long> ids, Integer operateType) {

        if (ids.isEmpty()) {
            throw new BusinessLogicRunTimeException("没有待操作的数据!");
        }

        // 基础资料模板
        FormTemplate template = getFormTemplate(classId, 1);
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 主表字段模板
        Map<String, FormField> formFields = template.getFormFields().get(0);

        String primaryTableName = formClass.getTableName();
        String primaryKey = formClass.getPrimaryKey();

        // 禁用/反禁用前插件事件
        PlugInRet plugInRet = plugInFactory.beforeForbid(classId, template, ids, operateType);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }

        // 准备模板
        Map<String, Object> statement = prepareStatement(ids, primaryTableName, primaryKey);

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);

        if (!statement.isEmpty()) {
            statement.put("operateType", operateType == 1);
            templateDaoMapper.forbid(statement);
        }
        // 禁用/反禁用后插件事件
        plugInRet = plugInFactory.afterForbid(classId, template, ids, operateType);
        if (plugInRet.getCode() != StatusCode.SUCCESS) {
            throw new PlugInRuntimeException(plugInRet.getMsg());
        }

        return true;

    }

    /**
     * 按条件导出列表
     *
     * @param classId    业务类型
     * @param conditions 查询条件集合
     * @param sorts      排序条件集合
     */
    @Override
    public HSSFWorkbook export(Integer classId, List<Condition> conditions, List<Sort> sorts) {

        FormTemplate formTemplate = getFormTemplate(classId, 1);

        List<FormField> disPlayFieldList = getDisPlayFieldList(formTemplate, null);

        String[] title = getExportTitle(disPlayFieldList);

        Map<Integer, Map<String, Object>> values = getExportValue(classId, conditions, sorts, disPlayFieldList,
                formTemplate);

        return ExcelUtil.getHSSFWorkbook(formTemplate.getFormClass().getName(), disPlayFieldList, values, null);

    }

    /**
     * 导出指定记录
     *
     * @param classId 业务类型
     * @param ids     内码集合
     */
    @Override
    public HSSFWorkbook export(Integer classId, List<Long> ids) {
        return null;
    }

    /**
     * 根据模板构建导出excel标题行
     *
     * @param disPlayFieldList 需显示的字段模板
     * @return 标题行数组
     */
    private String[] getExportTitle(List<FormField> disPlayFieldList) {

        List<String> titles = new ArrayList<>(16);

        disPlayFieldList.forEach(formField -> {
            titles.add(formField.getName());
        });

        String[] ret = new String[titles.size()];

        return titles.toArray(ret);
    }

    /**
     * 根据模板构建导出excel数据
     *
     * @param classId          业务类型
     * @param conditions       查询条件集合
     * @param sorts            排序条件集合
     * @param disPlayFieldList 需导出的字段模板信息
     * @return 导出excel数据
     */
    @SuppressWarnings("unchecked")
    private Map<Integer, Map<String, Object>> getExportValue(Integer classId, List<Condition> conditions,
            List<Sort> sorts, List<FormField> disPlayFieldList, FormTemplate formTemplate) {

        // 导出数据，类似二维数组，第一维行，第二维列(行数不固定，数组结构不合适)
        Map<Integer, Map<String, Object>> values = Maps.newLinkedHashMap();

        Map<String, Object> items = getItems(classId, conditions, sorts, Integer.MAX_VALUE, 1);

        // 导出的单据数量
        int count = Integer.valueOf(items.get("count").toString());
        // 导出数据
        List<Map<String, Object>> list = (List<Map<String, Object>>) items.get("list");
        // 列数(导出的字段数量)
        int column = disPlayFieldList.size();

        // (ret中)已经打包好的数据行数
        int dataIndex = 1;
        for (int i = 0; i < list.size(); i++) {

            // excel一行数据
            Map<String, Object> lineData = Maps.newLinkedHashMap();

            // 迭代每一条单据数据

            Map<String, Object> item = list.get(i);

            disPlayFieldList.forEach(formField -> {

                if (0 == formField.getPage()) {
                    // 先把单据头字段打包成一行
                    lineData.put(formField.getKey(), item.get(formField.getKey()));
                }
            });

            // 用于迭代分录
            int k = 0;

            if (!formTemplate.getFormClassEntry().isEmpty()) {
                // 存在子表，导出第一个子表数据
                List<Map<String, Object>> entryItems = ((Map<String, ArrayList<Map<String, Object>>>) item.get("entry"))
                        .get("1");

                for (; k < entryItems.size(); k++) {

                    // 一条子表数据
                    Map<String, Object> entryItem = entryItems.get(k);

                    if (k == 0) {
                        //子表记录第一行与表头共用
                        disPlayFieldList.forEach((FormField formField) -> {

                            if (1 == formField.getPage()) {
                                lineData.put(formField.getKey(), entryItem.get(formField.getKey()));
                            }
                        });

                    } else {

                        // 第二行开始要新增一行，该行无表头值
                        int newEntryLine = dataIndex + k;
                        Map<String, Object> newExportLineData = new HashMap<>(8);

                        disPlayFieldList.forEach((FormField formField) -> {

                            if (1 == formField.getPage()) {
                                newExportLineData.put(formField.getKey(), entryItem.get(formField.getKey()));
                            }
                        });
                        // 不带主表信息的分录数据
                        values.put(newEntryLine, newExportLineData);

                    }
                }

            }

            // 带主表信息的行数据
            values.put(dataIndex, lineData);

            // 重新定位下一张单据数据导出时excel起始行
            dataIndex += k;

        }

        return values;
    }

    /**
     * 处理分录数据
     *
     * @param formEntries Entry集合
     * @param items       主表id值
     */
    private void delEntryData(Map<Integer, FormClassEntry> formEntries, List<Long> items) {

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);

        for (Map.Entry<Integer, FormClassEntry> formClassEntryEntry : formEntries.entrySet()) {

            FormClassEntry formEntry = formClassEntryEntry.getValue();

            String tableName = formEntry.getTableName();
            String foreignKey = formEntry.getForeignKey();

            Map<String, Object> statement = prepareStatement(items, tableName, foreignKey);

            templateDaoMapper.del(statement);
        }
    }

    private Map<String, Object> prepareStatement(List<Long> ids, String primaryTableName, String primaryKey) {

        Map<String, Object> map = new HashMap<String, Object>();

        StringBuilder sb = new StringBuilder();

        for (Long id : ids) {
            sb.append(id).append(",");
        }

        if (sb.length() != 0) {

            sb.deleteCharAt(sb.length() - 1);
            map.put("tableName", primaryTableName);
            map.put("primaryKey", primaryKey);
            map.put("items", sb);

        }

        return map;
    }

    /**
     * 审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean check(Integer classId, List<Long> ids) {
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean unCheck(Integer classId, List<Long> ids) {
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
        FormTemplate template = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<Integer, Map<String, FormField>> formFieldsAll = template.getFormFields();
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

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
            FormClassEntry formEntry = (FormClassEntry) formEntries.get(1);
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
            sbFrom.append(
                    String.format(" %s %s ON %s.%s%s%s = %s.%s%s%s", entryJoinType, childTableName, childTableName,
                            bDelimiter, foreignKey, eDelimiter, primaryTableName, bDelimiter, primaryKey,
                            eDelimiter)).append(separator);
        }

        for (Integer pageIndex : formFieldsAll.keySet()) {

            // page=0/page=1的字段模板
            Map<String, FormField> formFields = formFieldsAll.get(pageIndex);

            // 当前模板关联的物理表
            String formFieldLinkedTable = "";

            if (0 == pageIndex) {
                // 主表
                formFieldLinkedTable = primaryTableName;
            } else if (1 == pageIndex) {
                formFieldLinkedTable = childTableName;
            } else {
                // 主表不支持关联多个子表查询
                continue;
            }

            for (String fieldKey : formFields.keySet()) {

                FormField formField = formFields.get(fieldKey);

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
                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName,
                                    eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                    // 查询出关联表该显示的值
                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter,
                                    bDelimiter, key + "_DspName", eDelimiter)).append(separator);
                    // 如果增加配置了关联表扩展显示值，则一并查询出
                    if (displayExt != null && !"".equals(displayExt.trim())) {
                        // 代码显示字段
                        sbSelect.append(
                                String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, displayExt, eDelimiter,
                                        bDelimiter, key + "_NmbName", eDelimiter)).append(separator);
                    }

                    // from 中同时增加关联表
                    sbFrom.append(joinType).append(srcTable);

                    // 关联表的别名
                    if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                        sbFrom.append(" AS ").append(srcTableAlisAs);
                    }
                    // 主表与关联表的关联条件
                    sbFrom.append(
                            String.format(" ON %s.%s%s%s = %s.%s%s%s ", formFieldLinkedTable, bDelimiter, sqlColumnName,
                                    eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
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

                    FormField lookUpTypeFormField = getFormField(lookUpClassId, disPlayField);

                    if (lookUpTypeFormField == null) {
                        throw new BusinessLogicRunTimeException(
                                String.format("classId=%s, key=%s 的disPlayField配置错误，请联系管理员", classId, key));
                    }

                    String nameEx = lookUpTypeFormField.getName();
                    Integer lookUpTypeEx = lookUpTypeFormField.getLookUpType();
                    String joinTypeEx = lookUpTypeFormField.getJoinType();
                    String srcTableEx = lookUpTypeFormField.getSrcTable();
                    String srcFieldEx = lookUpTypeFormField.getSrcField();

                    if (needSave) {
                        // 需要保存的携带属性值直接从主表查询
                        sbSelect.append(
                                String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName,
                                        eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                    } else {
                        // 不需要保存的引用字段关联查询
                        if (lookUpTypeEx != null && lookUpTypeEx > 0) {

                            // 附加属性又是引用类型的情况--取显示字段并关联表
                            sbSelect.append(
                                    String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, nameEx, eDelimiter,
                                            bDelimiter, key, eDelimiter)).append(separator);

                            // from 中同时增加关联表
                            sbFrom.append(joinTypeEx).append(srcTableEx);

                            // 关联表的别名
                            sbFrom.append(" AS ").append(srcTableAlis);

                            sbFrom.append(
                                    String.format(" ON %s.%s%s%s = %s.%s%s%s ", srcTableEx, bDelimiter, srcFieldEx,
                                            eDelimiter, srcTableAlis, bDelimiter, srcFieldEx, eDelimiter))
                                    .append(separator);
                        } else {
                            // 普通属性
                            sbSelect.append(
                                    String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField,
                                            eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                        }

                    }

                } else if (lookUpType != null && lookUpType == 4) {

                    // 普通引用-引用其他表数据
                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName,
                                    eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter,
                                    bDelimiter, key, eDelimiter)).append(separator);

                    // from 中同时增加关联表
                    sbFrom.append(joinType).append(srcTable);

                    // 关联表的别名
                    if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                        sbFrom.append(" AS ").append(srcTableAlisAs);
                    }

                    sbFrom.append(
                            String.format(" ON %s.%s%s%s = %s.%s%s%s ", formFieldLinkedTable, bDelimiter, sqlColumnName,
                                    eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
                            .append(separator);

                } else if (lookUpType != null && lookUpType == 5) {

                    // 普通引用其他表的其他字段-
                    // 主要为了避免为4即引用他表数据时，需引用多个字段时关联表重复问题。依附于=4时存在,即模板中肯定存在lookUpType=4的字段模板

                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter,
                                    bDelimiter, key, eDelimiter)).append(separator);

                } else {
                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName,
                                    eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                }
            }
        }

        String select = sbSelect.toString().trim();
        select = select.substring(0, select.length() - 1);
        String from = sbFrom.toString().trim();

        Map<String, Object> statement = new HashMap<String, Object>(8);

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
        FormTemplate template = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<Integer, Map<String, FormField>> formFieldsAll = template.getFormFields();
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

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
        Map<String, FormField> selFormFields = formFieldsAll.get(page);

        if (selFormFields.isEmpty()) {
            throw new BusinessLogicRunTimeException(String.format("classId=%s page=%s 没有模板数据,请联系管理员！", classId, page));
        }

        if (page == 0) {
            // 主表
            selTable = formClass.getTableName();
        } else {
            // 子表
            selTable = formEntries.get(page).getTableName();
        }

        StringBuilder sbSelect = new StringBuilder();
        StringBuilder sbFrom = new StringBuilder();
        String separator = System.getProperty("line.separator");

        sbSelect.append("SELECT").append(separator);
        sbFrom.append("FROM ").append(selTable).append(separator);

        for (String fieldKey : selFormFields.keySet()) {

            FormField formField = selFormFields.get(fieldKey);

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
                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", selTable, bDelimiter, sqlColumnName, eDelimiter,
                        bDelimiter, key, eDelimiter)).append(separator);
                // 查询出关联表该显示的值
                sbSelect.append(
                        String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter,
                                bDelimiter, key + "_DspName", eDelimiter)).append(separator);
                // 如果增加配置了关联表扩展显示值，则一并查询出
                if (displayExt != null && !"".equals(displayExt.trim())) {
                    // 代码显示字段
                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, displayExt, eDelimiter,
                                    bDelimiter, key + "_NmbName", eDelimiter)).append(separator);
                }

                // from 中同时增加关联表
                sbFrom.append(joinType).append(srcTable);

                // 关联表的别名
                if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                    sbFrom.append(" AS ").append(srcTableAlisAs);
                }
                // 主表与关联表的关联条件
                sbFrom.append(
                        String.format(" ON %s.%s%s%s = %s.%s%s%s ", selTable, bDelimiter, sqlColumnName, eDelimiter,
                                srcTableAlis, bDelimiter, srcField, eDelimiter))
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

                FormField lookUpTypeFormField = getFormField(lookUpClassId, disPlayField);

                if (lookUpTypeFormField == null) {
                    throw new BusinessLogicRunTimeException(
                            String.format("classId=%s, key=%s 的disPlayField配置错误，请联系管理员", classId, key));
                }

                String nameEx = lookUpTypeFormField.getName();
                Integer lookUpTypeEx = lookUpTypeFormField.getLookUpType();
                String joinTypeEx = lookUpTypeFormField.getJoinType();
                String srcTableEx = lookUpTypeFormField.getSrcTable();
                String srcFieldEx = lookUpTypeFormField.getSrcField();

                if (needSave) {
                    // 需要保存的携带属性值直接从主表查询
                    sbSelect.append(
                            String.format("%s.%s%s%s AS %s%s%s,", selTable, bDelimiter, sqlColumnName, eDelimiter,
                                    bDelimiter, key, eDelimiter)).append(separator);

                } else {
                    // 不需要保存的引用字段关联查询
                    if (lookUpTypeEx != null && lookUpTypeEx > 0) {

                        // 附加属性又是引用类型的情况--取显示字段并关联表
                        sbSelect.append(
                                String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, nameEx, eDelimiter,
                                        bDelimiter, key, eDelimiter)).append(separator);

                        // from 中同时增加关联表
                        sbFrom.append(joinTypeEx).append(srcTableEx);

                        // 关联表的别名
                        sbFrom.append(" AS ").append(srcTableAlis);

                        sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", srcTableEx, bDelimiter, srcFieldEx,
                                eDelimiter, srcTableAlis, bDelimiter, srcFieldEx, eDelimiter))
                                .append(separator);
                    } else {
                        // 普通属性
                        sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField,
                                eDelimiter, bDelimiter, key, eDelimiter)).append(separator);
                    }

                }

            } else if (lookUpType == 4) {
                // 普通引用-引用其他表数据
                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", selTable, bDelimiter, sqlColumnName, eDelimiter,
                        bDelimiter, key, eDelimiter)).append(separator);
                sbSelect.append(
                        String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter,
                                bDelimiter, key + "_DspName", eDelimiter)).append(separator);

                // from 中同时增加关联表
                sbFrom.append(joinType).append(srcTable);

                // 关联表的别名
                if (srcTableAlisAs != null && !"".equals(srcTableAlisAs)) {
                    sbFrom.append(" AS ").append(srcTableAlisAs);
                }

                sbFrom.append(
                        String.format(" ON %s.%s%s%s = %s.%s%s%s ", selTable, bDelimiter, sqlColumnName, eDelimiter,
                                srcTableAlis, bDelimiter, srcField, eDelimiter))
                        .append(separator);

            } else if (lookUpType == 5) {

                // 普通引用其他表的其他字段-
                // 主要为了避免为4即引用他表数据时，需引用多个字段时关联表重复问题。依附于=4时存在,即模板中肯定存在lookUpType=4的字段模板

                sbSelect.append(
                        String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter,
                                bDelimiter, key, eDelimiter)).append(separator);

            } else {
                sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", selTable, bDelimiter, sqlColumnName, eDelimiter,
                        bDelimiter, key, eDelimiter)).append(separator);
            }
        }

        String select = sbSelect.toString().trim();
        select = select.substring(0, select.length() - 1);
        String from = sbFrom.toString().trim();

        Map<String, Object> statement = new HashMap<String, Object>(8);

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

        Map<String, Object> ret = new HashMap<String, Object>(8);

        pageIndex = pageIndex == null ? -1 : pageIndex > 1 ? 1 : pageIndex;

        StringBuilder sbWhere = new StringBuilder();
        // 保存sql格式化参数
        Map<String, Object> sqlParams = new HashMap<String, Object>();

        String separator = System.getProperty("line.separator");

        // 基础资料模板
        FormTemplate template = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<String, FormField> formFieldsAll = getFormFields(classId, -1);
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

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
            FormClassEntry formEntry = formEntries.get(1);
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
            Condition.LinkType linkType = condition.getLinkType();

            // 第一个条件忽略连接关系
            linkType = i == 0 ? Condition.LinkType.NULL : linkType;

            // 左括号-可能有多个，如 "(("，甚至"((("等复杂查询,默认"("
            String leftParenTheses = condition.getLeftParenTheses();

            // 比较字段名
            String fieldKey = condition.getFieldKey();

            if (null == fieldKey || fieldKey.trim().isEmpty()) {
                throw new BusinessLogicRunTimeException("参数错误：condition必须包括fieldKey");
            }

            // 比较符号
            Condition.LogicOperator logicOperator = condition.getLogicOperator();

            if (logicOperator == Condition.LogicOperator.NOT_SUPPORT) {
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
            if (pageIndex > 0 && !formFieldsAll.get(fieldKey).getPage().equals(pageIndex)) {
                continue;
            }

            FormField formField = formFieldsAll.get(fieldKey);

            Integer page = formField.getPage();
            String sqlColumnName = formField.getSqlColumnName();
            Integer lookUpType = formField.getLookUpType();
            Integer lookUpClassId = formField.getLookUpClassId();
            String srcTable = formField.getSrcTable();
            String srcTableAlisAs = formField.getSrcTableAlis();
            String disPlayField = formField.getDisplayField();
            String srcField = formField.getSrcField();
            Integer ctrlType = formField.getCtrlType();
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
            CtrlType ctrlTypeEnum = CtrlType.getType(ctrlType);

            if (needConvert && lookUpType > 0) {
                // 只有引用类型有转换与非转换情况
                // 需要转换为名称查询的引用类型的查询条件，dataType可能不是文本类型，但条件值是文本，需要文本格式化，此处修正值格式化类型
                ctrlTypeEnum = CtrlType.TEXT;
            }

            switch (ctrlTypeEnum) {

            case INTEGER:
                break;
            case DECIMAL:
                break;
            case CHECKBOX:
                break;
            case SELECT:
                break;
            case F7:
                break;
            case CASCADE:
                break;
            case MOBILE:
                break;
            case PHONE:
                break;
            case TEXT:
            case TEXTAREA:
                if (logicOperator == Condition.LogicOperator.LIKE) {
                    // 不处理，调用者控制左匹配%xxx或右匹配xxx%或全匹配%xxx%
                    //value = "%" + value + "%";
                } else if (logicOperator == Condition.LogicOperator.IN) {
                    // 不适用此系统动态查询方式，对于IN，手工拼接脚本
                    preValue = "(";
                    sufValue = ")";
                    skip = true;
                }
                break;
            case DATETIME:
                if (logicOperator == Condition.LogicOperator.LESS_OR_EQUAL) {
                    if (!Common.isLongDate(String.valueOf(value))) {
                        // 由于数据库中日期可能存储有时分秒，过滤天时过滤到当前23:59:59
                        value = value + " 23:59:59";
                    }
                } else if (logicOperator == Condition.LogicOperator.GREATER_OR_EQUAL) {
                    if (!Common.isLongDate(String.valueOf(value))) {
                        value = value + " 00:00:00";
                    }
                }
                break;
            case SEX:
                break;
            case PASSWORD:
                break;
            case WHETHER:
                // boolean b = value.equals("是") ? true : false;
                // 此类字段数据库中一般要求用bit类型,即非0即1
                value = "是".equals(value) ? "1" : "否".equals(value) ? "0" : "2";
                break;
            case MONEY:
                break;
            default:
                break;
            }
            /*
            switch (ctrlTypeEnum) {

               case NUMBER:
                    // 一般数字类型的不可能用like
                    break;
                case TEXT:

                    if (logicOperator == Condition.LogicOperator.LIKE) {
                        // 不处理，调用者控制左匹配%xxx或右匹配xxx%或全匹配%xxx%
                        //value = "%" + value + "%";
                    } else if (logicOperator == Condition.LogicOperator.IN) {
                        // 不适用此系统动态查询方式，对于IN，手工拼接脚本
                        preValue = "(";
                        sufValue = ")";
                        skip = true;
                    }
                    break;
                case TIME:
                    if (logicOperator == Condition.LogicOperator.LESS_OR_EQUAL) {
                        if (!Common.isLongDate(String.valueOf(value))) {
                            // 由于数据库中日期可能存储有时分秒，过滤天时过滤到当前23:59:59
                            value = value + " 23:59:59";
                        }
                    } else if (logicOperator == Condition.LogicOperator.GREATER_OR_EQUAL) {
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
*/
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

                FormField lookUpFormField = getFormField(lookUpClassId, disPlayField);

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
                sbWhere.append(separator).append(String
                        .format("%s %s %s.%s%s%s %s %s %s %s %s ", linkType.getName(), leftParenTheses, tableName,
                                bDelimiter, fieldName, eDelimiter, logicOperator.getOperator(), preValue, value,
                                sufValue, rightParenTheses));
            } else {
                // 动态脚本

                if (logicOperator == Condition.LogicOperator.NULL ||
                        logicOperator == Condition.LogicOperator.NOT_NULL) {
                    // is null || is not null 不需要比较值
                    sbWhere.append(separator).append(String
                            .format("%s %s %s.%s%s%s %s %s", linkType.getName(), leftParenTheses, tableName, bDelimiter,
                                    fieldName, eDelimiter, logicOperator.getOperator(), rightParenTheses));
                } else {
                    sbWhere.append(separator).append(String
                            .format("%s %s %s.%s%s%s %s %s %s %s %s ", linkType.getName(), leftParenTheses, tableName,
                                    bDelimiter, fieldName, eDelimiter, logicOperator.getOperator(), preValue,
                                    "#{" + fieldKey + i + "}", sufValue, rightParenTheses));
                    // 格式化参数
                    sqlParams.put(fieldKey + i, value);
                }
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
        FormTemplate template = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<String, FormField> formFieldsAll = getFormFields(classId, -1);
        // 主表资料描述信息
        FormClass itemClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

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

            FormField formField = formFieldsAll.get(fieldKey);

            Integer page = formField.getPage();
            String sqlColumnName = formField.getSqlColumnName();
            Integer lookUpType = formField.getLookUpType();
            String srcTable = formField.getSrcTable();
            String srcTableAlisAs = formField.getSrcTableAlis();
            String disPlayField = formField.getDisplayField();

            String tableName = primaryTableName;

            if (page > 0) {
                FormClassEntry entry = formEntries.get(page);
                tableName = entry.getTableName();
            }

            String fieldName = sqlColumnName;

            if (lookUpType > 0) {
                // 引用类型字段-找到真实的表，字段
                tableName = srcTableAlisAs == null || "".equals(srcTableAlisAs) ? srcTable : srcTableAlisAs;
                fieldName = disPlayField;
            }

            sbOrderBy.append(separator).append(String
                    .format("%s.%s%s%s %s,", tableName, bDelimiter, fieldName, eDelimiter, orderDirection));

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
    private FormField getFormField(int classId, String key) {

        FormFieldMapper fieldsMapper = sqlSession.getMapper(FormFieldMapper.class);

        FormFieldExample example = new FormFieldExample();

        FormFieldExample.Criteria criteria = example.createCriteria();

        criteria.andClassIdEqualTo(classId);
        criteria.andKeyEqualTo(key);

        List<FormField> formFields = fieldsMapper.selectByExample(example);

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
     * @param page    page -1获取所有字段模板
     * @return 指定page的字段模板
     */
    private Map<String, FormField> getFormFields(int classId, int page) {

        Map<String, FormField> retMap = new LinkedHashMap<String, FormField>();

        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        // 获取单据模板
        // page=0表头
        FormFieldMapper fieldsMapper = sqlSession.getMapper(FormFieldMapper.class);

        FormFieldExample fieldsExample = new FormFieldExample();
        FormFieldExample.Criteria fieldsCriteria = fieldsExample.createCriteria();

        fieldsCriteria.andClassIdEqualTo(classId);

        if (page != -1) {
            fieldsCriteria.andPageEqualTo(page);
        }
        //fieldsCriteria.andClassIdEqualTo(classId).andIf(page != -1, criteria -> criteria.andPageEqualTo(page));

        // 查询结果按照page index排序
        fieldsExample.orderBy(FormField.Column.page.asc(), FormField.Column.index.asc());

        List<FormField> fieldsByExample = fieldsMapper.selectByExample(fieldsExample);

        // 打包字段模板-打包成Map好操作
        for (FormField item : fieldsByExample) {
            retMap.put(item.getKey(), item);
        }

        return retMap;

    }

    /**
     * 获取单据主表插入脚本结构
     *
     * @param data         数据包
     * @param formFields   模板
     * @param tableName    表名
     * @param primaryKey   表主键
     * @param primaryValue 主键值
     * @return Map<String, Object>
     */
    private Map<String, Object> prepareAddMap(JsonNode data, Map<String, FormField> formFields, String tableName,
            String primaryKey, Long primaryValue) {

        return prepareAddMap(data, formFields, tableName, primaryKey, primaryValue, null, null);

    }

    /**
     * 获取单据子表插入脚本结构
     *
     * @param data         数据包
     * @param formFields   模板
     * @param tableName    表名
     * @param primaryKey   表主键
     * @param primaryValue 主键值
     * @param foreignKey   子表外键key
     * @param foreignValue 子表外键value(主表主键值)
     * @return Map<String, Object>
     */
    private Map<String, Object> prepareAddMap(JsonNode data, Map<String, FormField> formFields, String tableName,
            String primaryKey, Long primaryValue, String foreignKey, Long foreignValue) {

        Map<String, Object> ret = new HashMap<String, Object>(8);
        Map<String, Object> fieldValuesParams = new HashMap<String, Object>(16);

        StringBuilder fieldNames = new StringBuilder("");
        StringBuilder fieldValues = new StringBuilder("");

        // 数据库字段-关键字处理
        Map<String, String> dbDelimiter = getDBDelimiter();
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        for (Iterator<String> iterator = data.fieldNames(); iterator.hasNext(); ) {

            String key = iterator.next();

            if ("entry".equals(key)) {
                // 子表数据不在此处理
                continue;
            }

            if (key.equalsIgnoreCase(primaryKey)) {
                // 新增时因为主键在后端生成，前端不需要提交主键key
                continue;
            }

            FormField formField = formFields.get(key);

            if (formField == null) {
                // 不存在的字段-忽略
                continue;
            }

            Integer lookUpType = formField.getLookUpType();
            Boolean needSave = formField.getNeedSave();

            if (!needSave) {
                // 无需保存字段不处理
                continue;
            }

            String fieldName = formField.getSqlColumnName();

            // 值
            JsonNode dataNode = data.findValue(key);
            // 目标转义值(默认String)
            Object value = transformValue(dataNode, CtrlType.getType(formField.getCtrlType()));

            // 子表的外键值为主表主键值，有后台生成
            if (key.equalsIgnoreCase(foreignKey)) {
                value = foreignValue;
            }

            // insert into 字段列表
            fieldNames.append(",").append(String.format("%s%s%s", bDelimiter, fieldName, eDelimiter));
            // mybatis格式化参数占位符 #{value}
            fieldValues.append(",").append("#{").append(fieldName).append("}");
            // 参数
            fieldValuesParams.put(fieldName, value);
        }

        // 加上主键值-同样格式化参数形式
        fieldNames.append(",").append(formFields.get(primaryKey).getSqlColumnName());
        fieldValues.append(",").append("#{").append(primaryKey).append("}");
        fieldValuesParams.put(primaryKey, primaryValue);

        String fieldStr = fieldNames.length() > 0 ? fieldNames.toString().substring(1) : "";
        String valueStr = fieldValues.length() > 0 ? fieldValues.toString().substring(1) : "";

        ret.put("tableName", tableName);
        ret.put("fieldStr", fieldStr);
        ret.put("valueStr", valueStr);
        ret.put("sqlParams", fieldValuesParams);

        return ret;
    }

    /**
     * 保存或修改表体数据
     *
     * @param classId 业务类型
     * @param id      主键
     * @param data    单据数据
     * @date 2018-02-27 14:51:05 星期四
     */
    @SuppressWarnings("unchecked")
    private void handleEntryData(int classId, Long id, JsonNode data) {

        if (!data.has("entry") || data.path("entry").size() == 0) {
            // 没有子表数据
            return;
        }

        // 基础资料模板
        FormTemplate template = getFormTemplate(classId, 1);

        JsonNode jsonEntry = data.path("entry");

        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();

        for (Map.Entry<Integer, FormClassEntry> formClassEntryEntry : formEntries.entrySet()) {

            Integer key = formClassEntryEntry.getKey();

            FormClassEntry formEntry = formClassEntryEntry.getValue();

            // key 等于1或2或3...
            Map<String, FormField> formFields = template.getFormFields().get(key);

            JsonNode entryData = jsonEntry.path(key.toString());

            // 保存或删除分录数据
            saveEntry(entryData, formEntry, formFields, id);

        }

    }

    /**
     * 保存或删除分录数据
     *
     * @param entryData  分录数据 如：[{ 'data':{parkID:3},'flag':'1'},{'data':{entryID:4,parkID:11} ,'flag':'2'},{'data':{entryID:3
     *                   , parkId : 1 } , ' flag ' : ' 0 ' } ]
     * @param formEntry  分录表描述 { "entryIndex": 1, "primaryKey": "entryID", "foreignKey": "FID", "classId": 13001, "tableName":
     *                   "t_PropertyCompanyEntry" }
     * @param formFields 分录表配置的字段
     * @param id         分录表外键值，关联主表的主键
     */
    @SuppressWarnings("unchecked")
    private void saveEntry(JsonNode entryData, FormClassEntry formEntry, Map<String, FormField> formFields, Long id) {

        String entryTableName = formEntry.getTableName();
        String primaryKey = formEntry.getPrimaryKey();
        String foreignKey = formEntry.getForeignKey();

        // 记录删除id，一次性删除
        String delItems = "-1";

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);

        for (int i = 0; i < entryData.size(); i++) {

            // 一条待操作分录数据
            JsonNode entry = entryData.path(i);
            //分录真实数据
            JsonNode data = entry.path("data");
            // 该分录操作标识flag: 0：删除1：新增 2：修改
            int flag = entry.path("flag").asInt();

            if (flag == 1) {
                // 新增分录

                // 检查字段
                // checkFields(formFields, data, primaryKey, flag, userType);

                // 产生记录主键
                Long entryId = getId();

                // 准备保存模板
                Map<String, Object> statement = prepareAddMap(data, formFields, entryTableName, primaryKey, entryId,
                        foreignKey, id);

                Map<String, Object> sqlMap = new HashMap<String, Object>();

                String tableName = statement.get("tableName").toString();
                String fieldStr = statement.get("fieldStr").toString();
                String valueStr = statement.get("valueStr").toString();
                Map<String, Object> sqlParams = (Map<String, Object>) statement.get("sqlParams");

                if (!"".equals(fieldStr)) {

                    if (!fieldStr.contains(foreignKey)) {
                        // 外键：模板配置中不需要配置该外键，因为该外键的值在前端时无法获取，只有主表保存后才能在后台获取
                        // 模板中配置的外键key mustInput不能设置为true
                        fieldStr += "," + foreignKey;
                        valueStr += ",#{" + foreignKey + "}";
                        sqlMap.put(foreignKey, id);
                    }

                    String sql = "insert into " + tableName + " ( " + fieldStr + " ) values ( " + valueStr + " )";
                    // 完整带参数的sql
                    sqlMap.put("sql", sql);
                    // 格式化参数
                    sqlMap.putAll(sqlParams);

                    // 插入分录-循环中执行脚本不优雅
                    templateDaoMapper.add(sqlMap);
                }
            } else if (flag == 2) {
                // 修改

                // 检查字段
                // checkFields(formFields, data, primaryKey, flag, userType);
                // 模板参数

                // 主键值
                Long entryId = data.path(primaryKey).asLong();

                String primaryColumnName = formFields.get(primaryKey).getSqlColumnName();

                // 准备保存模板
                Map<String, Object> statement = prepareEditMap(data, formFields, entryTableName, primaryKey, entryId);

                String tableName = statement.get("tableName").toString();
                String kvStr = statement.get("kvStr").toString();
                // String primaryKey=statement.get("primaryKey").toString();
                // String id=statement.get("id").toString();
                Map<String, Object> sqlParams = (Map<String, Object>) statement.get("sqlParams");

                Map<String, Object> sqlMap = new HashMap<String, Object>();

                String sql =
                        "update " + tableName + " set " + kvStr + " where " + primaryColumnName + "= #{" + primaryKey +
                                "}";

                // 完整带参数的sql
                sqlMap.put("sql", sql);
                // 格式化参数
                sqlMap.putAll(sqlParams);

                // 修改基础资料分录
                templateDaoMapper.edit(sqlMap);

            } else if (flag == 0) {
                // 删除，组装items

                Long delId = data.path(primaryKey).asLong();

                delItems += String.format(",%s", delId);

            }
        }

        if (!"-1".equals(delItems)) {
            // items不为空，则执行删除
            Map<String, Object> statement = new HashMap<String, Object>();
            statement.put("tableName", entryTableName);
            statement.put("primaryKey", formFields.get(primaryKey).getSqlColumnName());
            // 去掉delItems最开头的 -1,
            statement.put("items", delItems.substring(3));
            templateDaoMapper.del(statement);
        }
    }

    /**
     * 根据模板构建更新表数据脚本
     *
     * @param data             数据包
     * @param formFields       模板
     * @param primaryTableName 表名
     * @param primaryKey       表主键
     * @param id               主键值
     * @return Map<String, Object>
     */
    private Map<String, Object> prepareEditMap(JsonNode data, Map<String, FormField> formFields,
            String primaryTableName, String primaryKey, Long id) {

        Map<String, Object> sqlParams = new HashMap<String, Object>();

        StringBuilder sb = new StringBuilder("");

        for (Iterator<String> iterator = data.fieldNames(); iterator.hasNext(); ) {

            String key = iterator.next();
            if ("entry".equals(key)) {
                continue;
            }

            FormField formField = formFields.get(key);
            if (formField == null) {
                continue;
            }

            if (primaryKey.equalsIgnoreCase(key)) {
                // 主键更新忽略
                continue;
            }

            Integer lookUpType = formField.getLookUpType();
            Boolean needSave = formField.getNeedSave();

            if (!needSave) {
                // 引用基础资料的附加属性OR关联普通表携带字段，无需保存
                continue;
            }

            String fieldName = formField.getSqlColumnName();

            // 值
            JsonNode dataNode = data.findValue(key);
            // 目标转义值(默认String)
            Object value = transformValue(dataNode, CtrlType.getType(formField.getCtrlType()));

            if (!StringUtils.isNotBlank(fieldName)) {
                // 理论上不应该出现，执行到此可能是模板配置错误
                continue;
            }

            sb.append(",").append(fieldName).append("=").append("#{").append(key).append("}");

            sqlParams.put(key, value);
        }

        String kvStr = sb.length() > 0 ? sb.toString().substring(1) : "";

        Map<String, Object> ret = new HashMap<String, Object>(6);

        ret.put("tableName", primaryTableName);
        ret.put("kvStr", kvStr);
        sqlParams.put(primaryKey, id);
        ret.put("sqlParams", sqlParams);

        return ret;
    }

    /**
     * 根据模板配置的字段类型，将前端传入的值转换成对应的类型
     *
     * @param dataNode 原始值
     * @param ctrlType 字段类型
     * @return 转换后的类型
     */
    private Object transformValue(JsonNode dataNode, CtrlType ctrlType) {

        Object value;

        switch (ctrlType) {

        case INTEGER:
            value = dataNode.asLong();
            break;
        case DECIMAL:
            value = dataNode.asDouble(0.00D);
            break;
        case CHECKBOX:
            value = dataNode.asInt(0);
            break;
        case SELECT:
            value = dataNode.asInt(0);
            break;
        case F7:
            value = dataNode.asLong();
            break;
        case CASCADE:
            value = dataNode.asLong();
            break;
        case MOBILE:
            value = dataNode.asText("");
            break;
        case PHONE:
            value = dataNode.asText("");
            break;
        case TEXT:
            value = dataNode.asText("");
            break;
        case TEXTAREA:
            value = dataNode.asText("");
            break;
        case DATETIME:
            value = dataNode.asText("");
            break;
        case SEX:
            value = dataNode.asInt(0);
            break;
        case PASSWORD:
            value = dataNode.asText("");
            break;
        case WHETHER:
            value = dataNode.asInt(0);
            break;
        case MONEY:
            value = dataNode.asDouble(0.00D);
            break;
        default:
            // 默认文本
            value = dataNode.asText("");
            break;
        }

        return value;
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"name\":\"yadda\",\"age\":22,\"like\":[\"pingPon\",\"ball\"]}");

        System.out.println(jsonNode.toString());
        System.out.println(jsonNode.get("name").asText());
        System.out.println(jsonNode.get("age").asInt());
        System.out.println(jsonNode.path("like").size());
        System.out.println(jsonNode.path("like"));

        // 遍历 info 内的 array
        if (jsonNode.path("like").isArray()) {
            for (JsonNode objNode : jsonNode.path("like")) {
                System.out.println(objNode);
            }
        }

    }
}
