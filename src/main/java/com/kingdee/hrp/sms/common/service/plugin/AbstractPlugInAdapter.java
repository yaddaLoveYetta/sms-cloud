package com.kingdee.hrp.sms.common.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingdee.hrp.sms.common.model.FormClass;
import com.kingdee.hrp.sms.common.model.FormField;
import com.kingdee.hrp.sms.common.pojo.BillOperateType;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 插件适配器,不做业务处理
 * 具体业务插件只需继承此适配器覆盖感兴趣的方法即可
 *
 * @author yadda
 * @date 2018-02-27 17:32:12 星期四
 */
public abstract class AbstractPlugInAdapter extends BaseService implements PlugIn {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认返回对象
     */
    private PlugInRet result = new PlugInRet();

    /**
     * 基础资料新增前操作
     *
     * @param classId      业务类型
     * @param formTemplate 单据模板
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeSave(int classId, Map<String, Object> formTemplate, JsonNode data) {
        return result;
    }

    /**
     * 基础资料新增后操作
     *
     * @param classId 业务类型
     * @param id      新增的资料内码
     * @param data    业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterSave(int classId, Long id, JsonNode data) {
        return result;
    }

    /**
     * 基础资料修改前操作
     *
     * @param classId      业务类型
     * @param id           单据内码
     * @param formTemplate 模板
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeModify(int classId, Long id, Map<String, Object> formTemplate, JsonNode data) {
        return result;
    }

    /**
     * 单据/基础资料修改分录数据前事件
     *
     * @param classId      事务类型
     * @param primaryId    主表内码
     * @param entryId      子表内码
     * @param formTemplate 模板数据
     * @param data         修改数据内容
     * @return PlugInRet
     * @date 2017-07-12 09:05:42 星期三
     */
    @Override
    public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, Map<String, Object> formTemplate,
            JsonNode data) {
        return result;
    }

    /**
     * 修改后操作
     *
     * @param classId      业务类型
     * @param id           单据内码
     * @param formTemplate 模板
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterModify(int classId, Long id, Map<String, Object> formTemplate, JsonNode data) {
        return result;
    }

    /**
     * 删除前操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeDelete(int classId, Map<String, Object> formTemplate, List<Long> ids) {
        return result;
    }

    /**
     * 单据/基础资料分录删除前事件
     *
     * @param classId      事务类型
     * @param primaryId    主表内码
     * @param entryId      子表内码
     * @param formTemplate 模板数据
     * @return PlugInRet
     * @date 2017-07-12 09:10:46 星期三
     */
    @Override
    public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId,
            Map<String, Object> formTemplate) {
        return result;
    }

    /**
     * 删除后操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterDelete(int classId, Map<String, Object> formTemplate, List<Long> ids) {
        return result;
    }

    /**
     * 基础资料查询前操作
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditons    原始过滤条件
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeQuery(int classId, Map<String, Object> formTemplate, List<Condition> conditons) {
        return result;
    }

    /**
     * 基础资料查询后操作
     *
     * @param classId 业务类型
     * @param list    查询结果集
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterQuery(int classId, List<Map<String, Object>> list) {
        return result;
    }

    /**
     * 查询前插件查询条件
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditions   原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, Map<String, Object> formTemplate, List<Condition> conditions) {
        return null;
    }

    /**
     * 禁用/反禁用前置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeForbid(Integer classId, Map<String, Object> template, List<Long> ids, Integer operateType) {
        return result;
    }

    /**
     * 禁用/反禁用后置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    @Override
    public PlugInRet afterForbid(Integer classId, Map<String, Object> template, List<Long> ids, Integer operateType) {
        return result;
    }

    /**
     * 审核/反审核时获取审核状态的字段模板key
     * <p>
     * 当业务单据关联多个插件时以取index最大且非返回null的插件返回
     * <p>
     * 审核时系统设置该字段值为1，反审核时设置字段值为0
     *
     * @param classId 业务类型
     * @return 审核状态字段key
     */
    @Override
    public String checkFieldKey(Integer classId) {
        return null;
    }

    /**
     * 必录性校验
     * <p>
     * 单据新增时用
     *
     * @param template 单据模板
     * @param data     新增时按模板提交的单据数据
     * @return 校验结果
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> mustInputCheck(Map<String, Object> template, JsonNode data) {

        Map<String, Object> ret = new HashMap<>(4);

        // 单据头校验结果
        List<String> headCheckError = new ArrayList<>();
        // 单据体校验结果
        List<String> bodyCheckError = new ArrayList<>();

        // 按模板校验前端数据
        // 主表资料描述信息
        FormClass formClass = (FormClass) template.get("formClass");
        // 子表资料描述信息
        Map<String, Object> formEntries = (Map<String, Object>) template.get("formClassEntry");
        // 主表字段模板
        Map<String, Object> formFields0 = (Map<String, Object>) ((Map<String, Object>) template.get("formFields"))
                .get("0");

        // 新增时笔录性校验掩码
        int mustInputRoleMask = getCurrentMustInputMask(BillOperateType.ADD);

        String errMsg;

        // 单据头校验
        for (Map.Entry<String, Object> entry : formFields0.entrySet()) {

            FormField formField = (FormField) entry.getValue();

            if (isMustInputNoValue(formField, mustInputRoleMask, data)) {
                // 必录字段没提交值
                errMsg = String.format("[%s]不能为空", formField.getName());
                headCheckError.add(errMsg);
            }
        }

        //单据体校验
        if (!formEntries.isEmpty()) {
            // 只校验第一个子表（还没有存在一个以上子表的业务）
            Map<String, Object> formFields1 = (Map<String, Object>) ((Map<String, Object>) template.get("formFields"))
                    .get("1");

            // 第一个子表的数据
            List<JsonNode> elements = data.path("entry").findValues("1");

            for (int i = 0; i < elements.size(); i++) {
                // 迭代每一行数据
                JsonNode lineData = elements.get(i);

                for (Map.Entry<String, Object> entry : formFields1.entrySet()) {
                    FormField formField = (FormField) entry.getValue();
                    if (isMustInputNoValue(formField, mustInputRoleMask, lineData)) {
                        // 必录字段没提交值
                        errMsg = String.format("第[%s]行:[%s]不能为空", i + 1, formField.getName());
                        bodyCheckError.add(errMsg);
                    }
                }
            }

        }

        ret.put("headCheckError", headCheckError);
        ret.put("bodyCheckError", bodyCheckError);

        logger.info("mustInputCheck result:{}", ret);

        return ret;
    }

    /**
     * 判断当前用户类别新增单据时需保存的必录字段前端是否提交值
     *
     * @param formField         字段模板
     * @param mustInputRoleMask 当前用户角色字段必录性掩码
     * @param data              前端提交的数据结构
     * @return true:必录字段没提交值，false:其他(非需保存或必录的字段，必录字段有值)
     */
    private Boolean isMustInputNoValue(FormField formField, int mustInputRoleMask, JsonNode data) {

        String fieldKey = formField.getKey();
        Integer mustInputMask = formField.getMustInput();

        if (formField.getNeedSave() && (mustInputMask & mustInputRoleMask) == mustInputRoleMask) {
            // 当前用户类别新增单据时需保存的必录字段
            return data.path(fieldKey).isMissingNode() || data.findValue(fieldKey) == null ||
                    "".equals(data.path(fieldKey).asText());
        }

        return false;
    }
}
