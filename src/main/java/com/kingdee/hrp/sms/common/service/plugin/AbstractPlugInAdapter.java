package com.kingdee.hrp.sms.common.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingdee.hrp.sms.common.controller.SystemSettingController;
import com.kingdee.hrp.sms.common.pojo.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 插件适配器
 *
 * @author yadda
 * @date 2018-02-27 17:32:12 星期四
 */
public abstract class AbstractPlugInAdapter implements PlugIn {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    PlugInRet result = new PlugInRet();

    /**
     * 获取当前插件支持的业务类型集合
     *
     * @return 插件支持的业务类型classId集合
     */
    @Override
    public Set<Integer> getClassIdSet() {
        return null;
    }

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
     * @return
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
     * @Title beforeEntryModify
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
     * @Title beforeentryDelete
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
     * @return
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
     * @return
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
     * @param conditons    原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, Map<String, Object> formTemplate, List<Condition> conditons) {
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
}
