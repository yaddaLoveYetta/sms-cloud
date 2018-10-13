package com.kingdee.hrp.sms.common.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.model.FormClass;
import com.kingdee.hrp.sms.common.model.FormClassEntry;
import com.kingdee.hrp.sms.common.model.FormField;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.user.service.CurrentUserInfo;
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
public abstract class AbstractPlugInAdapter extends BaseService implements PlugIn,CurrentUserInfo {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认返回对象
     */
    private PlugInRet result = new PlugInRet();

    @Override
    public PlugInRet beforeSave(int classId, FormTemplate formTemplate, JsonNode data) {
        return result;
    }


    @Override
    public PlugInRet afterSave(int classId, Long id, JsonNode data) {
        return result;
    }

    @Override
    public PlugInRet beforeModify(int classId, Long id, FormTemplate formTemplate, JsonNode data) {
        return result;
    }


    @Override
    public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, FormTemplate formTemplate,
            JsonNode data) {
        return result;
    }


    @Override
    public PlugInRet afterModify(int classId, Long id, FormTemplate formTemplate, JsonNode data) {
        return result;
    }


    @Override
    public PlugInRet beforeDelete(int classId, FormTemplate formTemplate, List<Long> ids) {
        return result;
    }

    @Override
    public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId,
            FormTemplate formTemplate) {
        return result;
    }

    @Override
    public PlugInRet afterDelete(int classId, FormTemplate formTemplate, List<Long> ids) {
        return result;
    }


    @Override
    public PlugInRet beforeQuery(int classId, FormTemplate formTemplate, List<Condition> conditons) {
        return result;
    }

    @Override
    public PlugInRet afterQuery(int classId, List<Map<String, Object>> list) {
        return result;
    }


    @Override
    public List<Condition> getConditions(int classId, FormTemplate formTemplate, List<Condition> conditions) {
        return null;
    }


    @Override
    public PlugInRet beforeForbid(Integer classId, FormTemplate template, List<Long> ids, Integer operateType) {
        return result;
    }


    @Override
    public PlugInRet afterForbid(Integer classId, FormTemplate template, List<Long> ids, Integer operateType) {
        return result;
    }


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
    protected Map<String, Object> mustInputCheck(FormTemplate template, JsonNode data) {

        Map<String, Object> ret = new HashMap<>(4);

        // 单据头校验结果
        List<String> headCheckError = new ArrayList<>();
        // 单据体校验结果
        List<String> bodyCheckError = new ArrayList<>();

        // 按模板校验前端数据
        // 主表资料描述信息
        FormClass formClass = template.getFormClass();
        // 子表资料描述信息
        Map<Integer, FormClassEntry> formEntries = template.getFormClassEntry();
        // 主表字段模板
        Map<String, FormField> formFields0 = template.getFormFields().get(0);

        // 新增时笔录性校验掩码
        int mustInputRoleMask = getCurrentMustInputMask(Constants.BillOperateType.ADD);

        String errMsg;

        // 单据头校验
        for (Map.Entry<String, FormField> entry : formFields0.entrySet()) {

            FormField formField = entry.getValue();

            if (isMustInputNoValue(formField, mustInputRoleMask, data)) {
                // 必录字段没提交值
                errMsg = String.format("[%s]不能为空", formField.getName());
                headCheckError.add(errMsg);
            }
        }

        //单据体校验
        if (!formEntries.isEmpty()) {
            // 只校验第一个子表（还没有存在一个以上子表的业务）
            Map<String, FormField> formFields1 = template.getFormFields().get(1);

            // 第一个子表的数据
            List<JsonNode> elements = data.path("entry").findValues("1");

            for (int i = 0; i < elements.size(); i++) {
                // 迭代每一行数据
                JsonNode lineData = elements.get(i);

                for (Map.Entry<String, FormField> entry : formFields1.entrySet()) {
                    FormField formField = entry.getValue();
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
