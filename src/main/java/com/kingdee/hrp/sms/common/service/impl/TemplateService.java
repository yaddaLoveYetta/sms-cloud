package com.kingdee.hrp.sms.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingdee.hrp.sms.common.dao.generate.FormActionMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassEntryMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormClassMapper;
import com.kingdee.hrp.sms.common.dao.generate.FormFieldsMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sorts;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.ITemplateService;
import com.kingdee.hrp.sms.util.Common;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TemplateService extends BaseService implements ITemplateService {
    /**
     * 查询基础资料/单据模板数据
     *
     * @param classId
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
            fieldsExample.setOrderByClause("ctlIndex");

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
    public Map<String, Object> getItems(Integer classId, List<Condition> conditions, List<Sorts> sorts, Integer pageSize, Integer pageNo) {

        Map<String, Object> ret = new HashMap<String, Object>(16);

        // 基础资料模板
        Map<String, Object> template = this.getFormTemplate(classId, 1);

        // 主表资料描述信息
        FormClass formClass = (FormClass) template.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formEntries");

        // 主表字段模板
        Map<String, Object> formFields0 = (Map<String, Object>) ((Map<String, Object>) template.get("formFields")).get("0");
        // 第一个子表字段模板(如果有)
        Map<String, Object> formFields1 = new HashMap<String, Object>();

        if (null == formClass || formFields0.isEmpty()) {
            throw new BusinessLogicRunTimeException("资料模板不存在");
        }

        // 插件事件---begin-------
        //  PlugInFactory factory = new PlugInFactory(classId);
        //  conditionString = factory.getConditions(classId, template, conditionString);
        // 插件事件---begin-------

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

        // 模板拼接的select语句
        String select = "";
        // 执行的select语句
        String selectExec = "";
        // 查询语句----from部分
        String from = "";
        // 查询语句----where部分
        String whereStr = "";
        // 查询语句----order by部分
        String orderByStr = "";

        // 查询条件
        Map<String, Object> where = new HashMap<String, Object>();

        Map<String, Object> whereParams = new HashMap<String, Object>();

        Map<String, Object> statement = getStatement(classId);

        if (conditions != null && !conditions.isEmpty()) {
            // 获取客户端传入的查询条件
            where = getWhereStr(classId, conditions);

        }

        if (orderByString != null && !orderByString.equals("")) {

            // 客户端传入了排序字段
            JSONArray orderByArray = JSONArray.parseArray(orderByString);

            orderByStr = getOrderByStr(classId, orderByArray); // 获取where条件

        }

        select = (String) statement.get("select");
        from = (String) statement.get("from");

        if (!where.isEmpty()) {
            whereStr = where.get("whereStr").toString();
            whereParams = (Map<String, Object>) where.get("whereParams");
        }

        // 有子表的查询单据数跟实际记录数不一致(A INNER JOIN B 单据数值得是主表A的记录数)，分页信息不准确
        // 这种情况进行二次查询，第一次查询出A表符合条件的记录，第二次查询出真实业务数据
        selectExec = select;
        if (isChildTableExist) {
            selectExec = String.format("SELECT DISTINCT %s.%s AS id ", primaryTableName, primaryColumnName);
        }

        Map<String, Object> sqlMap = new HashMap<String, Object>();
        // 完整的sql(带格式化参数)
        String sql = selectExec.toString() + System.getProperty("line.separator") + from.toString() + System.getProperty("line.separator") + whereStr + System.getProperty("line.separator")
                + orderByStr;
        sqlMap.put("sql", sql);

        // --参数列表
        for (Iterator<Map.Entry<String, Object>> it = whereParams.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> item = it.next();
            sqlMap.put(item.getKey(), item.getValue());
        }

        TemplateDaoMapper templateDaoMapper = sqlSession.getMapper(TemplateDaoMapper.class);

        if (pageNo == 1) {
            PageHelper.startPage(pageNo, pageSize, true);
        } else {
            PageHelper.startPage(pageNo, pageSize, false);
        }

        List<Map<String, Object>> data = templateDaoMapper.getItems(sqlMap);

        if (pageNo == 1) {

            PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(data);

            ret.put("count", pageInfo.getTotal());
        }

        // 存在子表的查询需要进行第二次查询，获取真实数据字段
        if (isChildTableExist) {

            List<String> idList = new ArrayList<>();

            // 获取本次查询的主表内码集合
            for (Map<String, Object> item : data) {
                idList.add("'" + item.get("id").toString() + "'");
            }

            List<Map<String, Object>> itemByIds = new ArrayList<>();

            if (idList.size() > 0) {
                itemByIds = getItemByIds(classId, idList, orderByStr);
            }

            ret.put("list", itemByIds);

            return ret;

        }

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        // 将记录转换成返回接口的格式，将主表关联多行子表记录时，子表记录整合到返回结构"entry"中
        for (Map<String, Object> item : data) {
            // 循环每一行
            Map<String, Object> head = new HashMap<String, Object>();// 主表所有字段
            Map<String, Object> entries = new HashMap<String, Object>(); // 所有子表entry

            List<Map<String, Object>> formEntryRows = new ArrayList<Map<String, Object>>(); // 第一个子表所有行
            Map<String, Object> formEntryRow = new HashMap<String, Object>();// 子表每一行的元素

            for (Iterator<Map.Entry<String, Object>> it = item.entrySet().iterator(); it.hasNext(); ) {
                // 循环行中的列
                Map.Entry<String, Object> column = it.next();

                String cName = column.getKey();
                Object cValue = column.getValue();

                String cNameTrueKey = cName; // 真实的模板key

                if (cName.contains("_")) {
                    // 关联查询字段时携带的_DspName,_NmbName等模板之外的key
                    cNameTrueKey = cName.substring(0, cName.indexOf("_"));
                }

                if (formFields0.containsKey(cNameTrueKey)) {
                    // formClass即主表字段
                    // formClass.put(cNameTrueKey, cValue);

                    // if (!cNameTrueKey.equals(cName)) {
                    // 关联查询字段时携带的_DspName,_NmbName等模板之外的key
                    head.put(cName, item.get(cName));
                    // }

                } else if (isChildTableExist && formFields1.containsKey(cNameTrueKey)) {
                    // formEntries1即第一个子表字段
                    // formEntryRow.put(cNameTrueKey, cValue);

                    // if (!cNameTrueKey.equals(cName)) {
                    // 关联查询字段时携带的_DspName,_NmbName等模板之外的key
                    formEntryRow.put(cName, item.get(cName));
                    // }
                }
            }

            if (isChildTableExist) {
                formEntryRows.add(formEntryRow); // 这里formEntryRows也就一行，为了构造{[]}的结构
                entries.put("1", formEntryRows); // 第一个子表数据
            }

            head.put("entry", entries);// 将子表已关键字"entry"作为key插入到formClass中

            Map<String, Object> keyInList = isKeyInList(returnList, primaryKey, head.get(primaryKey));

            if (keyInList != null) {
                // 已存在该条记录，增加子表行
                ((List<Map<String, Object>>) ((Map<String, Object>) keyInList.get("entry")).get("1")).add(formEntryRow);
            } else {
                // 不存在该行记录，新增行
                returnList.add(head);
            }
        }

        ret.put("list", returnList);

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
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formEntries");
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
        sbFrom.append("FROM " + primaryTableName).append(separator);

        if (isChildTableExist) {
            sbFrom.append(String.format(" %s %s ON %s.%s%s%s = %s.%s%s%s", entryJoinType, childTableName, childTableName, bDelimiter, foreignKey, eDelimiter, primaryTableName, bDelimiter, primaryKey,
                    eDelimiter)).append(separator);
        }

        /*
            用户角色类别
            1	系统管理员
            2	医院
            3	供应商
            */
        Integer userRoleType = SessionUtil.getUserRole().getType();
        // 供应商用户
        int displayTypeList = 0;
        int displayTypeAdd = 0;
        int displayTypeEdit = 0;

        if (userRoleType == 1) {
            //系统管理员
            displayTypeList = 1;
            displayTypeAdd = 4;
            displayTypeEdit = 16;
        } else if (userRoleType == 2) {
            //医院
            displayTypeList = 2;
            displayTypeAdd = 8;
            displayTypeEdit = 32;
        } else if (userRoleType == 3) {
            //供应商
            displayTypeList = 2;
            displayTypeAdd = 8;
            displayTypeEdit = 32;
        }

        for (Iterator<String> iterator = formFieldsAll.keySet().iterator(); iterator.hasNext(); ) {

            String page = iterator.next();

            Map<String, Object> formFields = (Map<String, Object>) formFieldsAll.get(page);

            // 当前模板关联的物理表
            String formFieldLinkedTable = "";

            if ("0".equals(page)) {
                // 主表
                formFieldLinkedTable = primaryTableName;
            } else if ("1".equals(page)) {
                formFieldLinkedTable = childTableName;
            } else {
                // 主表不支持关联多个子表查询
                continue;
            }

            for (Iterator<String> it = formFields.keySet().iterator(); it.hasNext(); ) {

                String fieldKey = it.next();
                FormFields formField = (FormFields) formFields.get(fieldKey);
                String joinType = formField.getJoinType();

                if (joinType == null || joinType.trim().equals("")) {
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
                Integer lookUpType = formField.getLookUpType();
                Integer lookUpClassId = formField.getLookUpClassId();
                String srcTable = formField.getSrcTable();
                String srcTableAlisAs = formField.getSrcTableAlis();
                String srcField = formField.getSrcField();
                String disPlayField = formField.getDisplayField();
                // String disPlayFieldAlisAs = formField.getdis
                String disPlayNum = formField.getDisplayExt();
                boolean needSave = formField.getNeedSave();

                String srcTableAlis = srcTableAlisAs == null || srcTableAlisAs.equals("") ? srcTable : srcTableAlisAs;

                if (display > 0 && ((display & displayTypeList) != displayTypeList && (display & displayTypeAdd) != displayTypeAdd && (display & displayTypeEdit) != displayTypeEdit)) {
                    // 因为该函数需要兼容列表及编辑时查询，此处权限放开： 具有列表显示OR新增时显示OR编辑时显示的任意权限即可
                    // 当前用户类别无权限查看该字段
                    continue;
                }

                if (lookUpType != null && (lookUpType == 1 || lookUpType == 2)) {
                    // 基础资料/辅助资料引用类型
                    // 强制显示关联字段名称
                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                    sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayField, eDelimiter, bDelimiter, key + "_DspName", eDelimiter)).append(separator);

                    if (disPlayNum != null && !disPlayNum.trim().equals("")) {
                        // 代码显示字段
                        sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, disPlayNum, eDelimiter, bDelimiter, key + "_NmbName", eDelimiter)).append(separator);
                    }

                    // from 中同时增加关联表
                    sbFrom.append(joinType).append(srcTable);

                    // 关联表的别名
                    if (srcTableAlisAs != null && !srcTableAlisAs.equals("")) {
                        sbFrom.append(" as " + srcTableAlisAs);
                    }

                    sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
                            .append(separator);

                    if (filter != null && !filter.trim().equals("")) {
                        // 表链接有附加条件
                        sbFrom.append(filter).append(separator);
                    }

                } else if (lookUpType != null && lookUpType == 3) {

                    // 引用基础资料的附加属性
                    // lookUpType == 3
                    // 即引用基础资料属性的模板中，disPlayField的配置统一认为是被引用基础资料模板中的key，需要二次验证引用资料模板确认查询字段

                    // 举例：目标，在车辆信息中显示其车辆类别的车辆付费类型
                    // 基础资料车辆信息中引用另一个基础资料车辆类别，还需要显示基础资料车辆类别的另一个属性"车辆付费类型"，而"车辆付费类型"在车辆类别模板中又是辅助资料引用类型，
                    // 此时在车辆信息的模板中配置携带车辆类别基础资料的附属属性"车辆付费类型"的模板时，模板中disPlayField应配置为"车辆类别"模板中"车辆付费类型"的key(payType),
                    // srcField应该配置为"车辆类别"表与辅助资料的关联字段(即车辆类别表t_CarType与辅助资料车辆付费类型的关联字段payType),
                    // srcTableAlisAs必须配置，可随意，但需保证在车辆信息模板中不重复。

                    FormFields lookUpTypeFormField = getFormField(lookUpClassId, disPlayField);

                    String nameEx = lookUpTypeFormField.getName();
                    Integer lookUpTypeEx = lookUpTypeFormField.getLookUpType();
                    String joinTypeEx = lookUpTypeFormField.getJoinType();
                    String srcTableEx = lookUpTypeFormField.getSrcTable();
                    String srcFieldEx = lookUpTypeFormField.getSrcField();

                    if (needSave) {
                        // needSave 不需要保存的引用字段关联查询，需要保存的属性值直接查询
                        sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                    } else {

                        if (lookUpTypeEx != null && lookUpTypeEx > 0) {

                            // 附加属性又是引用类型的情况--取显示字段并关联表

                            sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,", srcTableAlis, bDelimiter, nameEx, eDelimiter, bDelimiter, key, eDelimiter)).append(separator);

                            // from 中同时增加关联表
                            sbFrom.append(joinTypeEx).append(srcTableEx);

                            // 关联表的别名
                            sbFrom.append(" as " + srcTableAlis);

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

                    // if (dataType != null && dataType == 2) {
                    // // 文本类的关联字段，未防止关联表中无记录，此处取主表字段值-如订单查询CarNo字段取数
                    // sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,",
                    // formFieldLinkedTable, bDelimiter,
                    // sqlColumnName, eDelimiter, bDelimiter, key,
                    // eDelimiter)).append(separator);
                    // } else {
                    // sbSelect.append(String.format("%s.%s%s%s AS %s%s%s,",
                    // srcTableAlis, bDelimiter, disPlayField,
                    // eDelimiter, bDelimiter, key,
                    // eDelimiter)).append(separator);
                    // }

                    // from 中同时增加关联表
                    sbFrom.append(joinType).append(srcTable);

                    // 关联表的别名
                    if (srcTableAlisAs != null && !srcTableAlisAs.equals("")) {
                        sbFrom.append(" as " + srcTableAlisAs);
                    }

                    sbFrom.append(String.format(" ON %s.%s%s%s = %s.%s%s%s ", formFieldLinkedTable, bDelimiter, sqlColumnName, eDelimiter, srcTableAlis, bDelimiter, srcField, eDelimiter))
                            .append(separator);
                } else if (lookUpType != null && lookUpType == 5) {

                    // 普通引用其他表的其他字段-主要为了避免为4即引用他表数据时，需引用多个字段时关联表重复问题。依附于=4时存在,即模板中肯定存在lookUpType=4的字段模板

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

    @SuppressWarnings("unchecked")
    private Map<String, Object> getWhereStr(Integer classId, List<Condition> conditions) {

        Map<String, Object> ret = new HashMap<String, Object>(16);

        StringBuilder sbWhere = new StringBuilder();
        // 保存sql格式化参数
        Map<String, Object> sqlParams = new HashMap<String, Object>();

        String separator = System.getProperty("line.separator");

        // 基础资料模板
        Map<String, Object> templateMap = getFormTemplate(classId, 1);
        // 所有字段模板
        Map<String, FormFields> itemClassTemplateMap = getFormFields(classId, -1);
        // 主表资料描述信息
        FormClass formClass = (FormClass) templateMap.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) templateMap.get("formEntries");

        if (null == formClass) {
            throw new BusinessLogicRunTimeException("没有模板数据");
        }

        Map<String, String> dbDelimiter = getDBDelimiter();
        // 数据库字段-关键字处理
        String bDelimiter = dbDelimiter.get("bDelimiter");
        String eDelimiter = dbDelimiter.get("eDelimiter");

        String primaryTableName = formClass.getTableName();
        String primaryKey = formClass.getPrimaryKey();
        String childTableName = "";
        String foreignKey = "";

        if (!formEntries.isEmpty()) {

            // 存在关联字表-只关联第一个子表查询
            FormClassEntry formEntry = (FormClassEntry) formEntries.get("1");
            childTableName = formEntry.getTableName();
            foreignKey = formEntry.getForeignKey();

            if ("".equals(childTableName) || "".equals(foreignKey)) {
                // 存在子表但子表FormEntries中配置错误
                throw new BusinessLogicRunTimeException("没有模板数据");
            }

        }

        sbWhere.append("WHERE 1=1 AND (");

        for (Condition condition : conditions) {

            // 处理脚本参数格式化时参数值不符合TSQL规则BUG
            String preValue = "";
            // eg：select 1 where id IN (#{value})不能写成select 1 where id IN #{value}
            String sufValue = "";

            // 是否跳过格式化参数，eg IN比较符号时，不使用此系统动态查询方式
            boolean skip = false;

            // AND OR 条件链接符号-默认AND
            String andOr = condition.getLinkType().getName();

            if (condition.containsKey("andOr")) {
                andOr = condition.getString("andOr");
            }
            // 第一个条件忽略连接关系
            andOr = i == 0 ? "" : andOr;

            String leftParenTheses = "("; // 左括号-可能有多个，如 "(("，甚至"((("等复杂查询,默认"("

            if (condition.containsKey("leftParenTheses")) {
                leftParenTheses = condition.getString("leftParenTheses");
            }

            String fieldKey = "";// 比较字段名

            if (condition.containsKey("fieldKey")) {
                fieldKey = condition.getString("fieldKey");
            } else {
                throw new BusinessLogicRunTimeException("参数错误：condition必须包括fieldKey");
            }

            String logicOperator = "="; // 比较符号

            if (condition.containsKey("logicOperator")) {
                logicOperator = condition.getString("logicOperator");
            } else {
                throw new BusinessLogicRunTimeException("参数错误：condition必须包括logicOperator");
            }

            /**
             * 有些公司防火墙会禁止>= <= 等比较符号参数，被认为可能存在sql注入，此处做兼容
             *
             * equal 等于 =
             * lessThan 等于 <=
             * greaterThan 等于 >=
             */
            String logicOperatorCopy = logicOperator.toLowerCase();
            switch (logicOperatorCopy) {
                case "equal":
                    logicOperator = "=";
                    break;
                case "lessthan":
                    logicOperator = "<=";
                    break;
                case "greaterthan":
                    logicOperator = ">=";
                    break;
                default:
                    break;
            }

            String value = ""; // 比较值

            if (condition.containsKey("value")) {
                value = condition.getString("value");
                // value = handleSqlInjection(value);
            } else {
                throw new BusinessLogicRunTimeException("参数错误：condition必须包括value");
            }

            String rightParenTheses = ")"; // 右括号

            if (condition.containsKey("rightParenTheses")) {
                rightParenTheses = condition.getString("rightParenTheses");
            }

            boolean needConvert = true;// 是否需要转换条件字段，用于传入引用他表字段时过滤，例如传入引用基础资料key是否需要转换为名称条件，用户输入时通常需要转换成名称查询，而代码中调用不需要转换，直接用ID匹配

            if (condition.containsKey("needConvert")) {
                needConvert = condition.getBoolean("needConvert");
            }

            if (!itemClassTemplateMap.containsKey(fieldKey)) {
                // 没有定义模板-忽略
                continue;
            }

            FormFields formfield = itemClassTemplateMap.get(fieldKey);

            Integer page = (Integer) formfield.getPage();
            String sqlColumnName = formfield.getSqlColumnName();
            Integer lookUpType = formfield.getLookUpType();
            Integer lookUpClassID = formfield.getLookUpClassID();
            String srcTable = formfield.getSrcTable();
            String srcTableAlisAs = formfield.getSrcTableAlisAs();
            String disPlayField = formfield.getDisPlayField();
            String srcField = formfield.getSrcField();
            Integer dataType = formfield.getDataType();

            String formFieldLinkedTable = ""; // 确定当前字段是属于哪个表

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

            DataTypeeEnum dataTypeeEnum = DataTypeeEnum.getTypeEnum(dataType);

            if (needConvert && lookUpType > 0) {
                // 需要转换为名称查询的引用类型的查询条件，dataType可能不是文本类型，但条件值是文本，需要文本格式化，此处修正值格式化类型
                dataTypeeEnum = DataTypeeEnum.TEXT;
            }

            switch (dataTypeeEnum) {

                case NUMBER:
                    // 一般数字类型的不可能用like
                    break;
                case TEXT:
                    if (logicOperator.equalsIgnoreCase("like")) {
                        value = "%" + value + "%";
                    }
                    if (logicOperator.equalsIgnoreCase("IN")) {
                        preValue = "(";
                        sufValue = ")";
                        skip = true;// 不适用此系统动态查询方式，对于IN，手工拼接脚本
                    }
                    break;
                case TIME:
                    if (logicOperator.equalsIgnoreCase("<=")) {
                        if (!Common.isLongDate(value)) {
                            // 由于数据库中日期可能存储有时分秒，过滤天时过滤到当前23:59:59
                            value = value + " 23:59:59"; // 小于等于结束时间
                        }
                    }
                    break;
                case BOOLEAN:
                    // boolean b = value.equals("是") ? true : false;
                    value = value.equals("是") ? "1" : value.equals("否") ? "0" : "2";
                    break;
                default:
                    break;
            }

            if (lookUpType != null && (lookUpType == 1 || lookUpType == 2)) {
                // 基础资料-辅助资料引用类型

                // 引用字段查询-使用关联表显示字段作为条件
                tableName = srcTableAlisAs == null || srcTableAlisAs.equals("") ? srcTable : srcTableAlisAs;

                if (needConvert) {
                    fieldName = disPlayField;
                } else {
                    fieldName = srcField;
                }

            } else if (lookUpType > 0 && lookUpType == 3) {

                // 引用字段查询-使用关联表显示字段作为条件
                tableName = srcTableAlisAs == null || srcTableAlisAs.equals("") ? srcTable : srcTableAlisAs;

                // 携带基础资料属性的字段过滤
                // FLookUpType == 3
                // 即引用基础资料属性的模板中，FDisPlayField的配置统一认为是引用基础资料模板中的key，需要二次验证引用资料模板确认查询字段

                FormFields formField = getFormField(lookUpClassID, disPlayField);

                String name = (String) formField.getName();
                Integer lookUpTypeEx = formField.getLookUpType();
                String srcTableEx = formField.getSrcTable();
                String srcFieldEx = formField.getSrcField();

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

            } else if (lookUpType != null && lookUpType == 4) {

                if (dataType != null && dataType == 2) {
                    // 文本类的关联字段，未防止关联表中无记录，此处取主表字段值-如订单查询FCarNo字段取数
                    tableName = formFieldLinkedTable;
                } else {
                    // 引用字段查询-使用关联表显示字段作为条件
                    tableName = srcTableAlisAs == null || srcTableAlisAs.equals("") ? srcTable : srcTableAlisAs;

                    if (needConvert) {
                        fieldName = disPlayField;
                    } else {
                        fieldName = srcField;
                    }
                }

            } else if (lookUpType > 0 && lookUpType > 4) {

                // 引用字段查询-使用关联表显示字段作为条件
                tableName = srcTableAlisAs == null || srcTableAlisAs.equals("") ? srcTable : srcTableAlisAs;
                if (needConvert) {
                    fieldName = disPlayField;
                } else {
                    fieldName = srcField;
                }

            }

            if (skip) {
                // 手工脚本
                sbWhere.append(separator).append(String.format("%s %s %s.%s%s%s %s %s %s %s %s ", andOr, leftParenTheses, tableName, bDelimiter, fieldName, eDelimiter, logicOperator, preValue, value,
                        sufValue, rightParenTheses));
            } else {
                // 动态脚本
                sbWhere.append(separator).append(String.format("%s %s %s.%s%s%s %s %s %s %s %s ", andOr, leftParenTheses, tableName, bDelimiter, fieldName, eDelimiter, logicOperator, preValue,
                        "#{" + fieldKey + i + "}", sufValue, rightParenTheses));

                sqlParams.put(fieldKey + i, value);
            }

        }
        //将所有过滤条件用（）括起来
        sbWhere.append(")");

        String whereStr = sbWhere.toString();

        if (!whereStr.equals("WHERE 1=1 AND ()")) {
            ret.put("whereStr", whereStr);
            ret.put("whereParams", sqlParams);
        }

        return ret;
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
                put("bDelimiter", "[");
                put("eDelimiter", "]");
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

    private Map<String, FormFields> getFormFields(int classId, int page) {

        Map<String, FormFields> retMap = new LinkedHashMap<String, FormFields>();

        // 获取单据模板page=0表头

        FormFieldsMapper fieldsMapper = sqlSession.getMapper(FormFieldsMapper.class);

        FormFieldsExample fieldsExample = new FormFieldsExample();
        FormFieldsExample.Criteria fieldsCriteria = fieldsExample.createCriteria();

        fieldsCriteria.andClassIdEqualTo(classId);

        if (page != -1) {
            fieldsCriteria.andPageEqualTo(page);
        }

        fieldsExample.setOrderByClause("page, [index]");

        List<FormFields> fieldsByExample = fieldsMapper.selectByExample(fieldsExample);

        // 打包字段模板
        for (FormFields item : fieldsByExample) {
            retMap.put(item.getKey(), item);
        }

        return retMap;

    }
}
