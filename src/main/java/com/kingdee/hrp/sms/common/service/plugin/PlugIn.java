package com.kingdee.hrp.sms.common.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 后台系统-基础资料业务插件接口<br>
 * <p>
 * 用于处理个性化业务需求<br>
 * <p>
 * 例如：基础资料操作前的数据校验，操作前的关联操作<br>
 * <p>
 * 操作后的数据校验，关联操作等等跟业务相关但又缺乏共性的逻辑<br>
 *
 * @author yadda
 * @date 2017-04-27 17:40:40 星期四
 */
public interface PlugIn {

    /**
     * 插件名称
     *
     * @return 插件名称
     */
    String name();

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    String description();

    /**
     * 插件序号-同一个业务上绑定多插件时确定插件的执行顺序
     *
     * @return 插件序号，值越小越先执行
     */
    Integer index();

    /**
     * 获取当前插件支持的业务类型集合
     *
     * @return 插件支持的业务类型classId集合
     */
    Set<Integer> getSupports();

    /**
     * 插件是否支持指定的业务单据
     *
     * @param classId 业务单据类型
     * @return true if support , false not support
     */
    boolean support(Integer classId);

    /**
     * 基础资料新增前操作<br/>
     * 通常可以在此方法中补充那些不需要前端输入但又是业务必须的字段数据，如新增资料的归属组织
     *
     * @param classId      业务类型
     * @param formTemplate 单据模板
     * @param data         业务数据
     * @return PlugInRet
     */
    PlugInRet beforeSave(int classId, FormTemplate formTemplate, JsonNode data);

    /**
     * 基础资料新增后操作
     *
     * @param classId 业务类型
     * @param id      新增的资料内码
     * @param data    业务数据
     * @return
     */
    PlugInRet afterSave(int classId, Long id, JsonNode data);

    /**
     * 单据修改前操作
     *
     * @param classId      业务类型
     * @param id           单据内码
     * @param formTemplate 模板
     * @param data         业务数据
     * @return PlugInRet
     */
    PlugInRet beforeModify(int classId, Long id, FormTemplate formTemplate, JsonNode data);

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
    PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, FormTemplate formTemplate,
                                JsonNode data);

    /**
     * 单据修改后操作
     *
     * @param classId      业务类型
     * @param id           单据内码
     * @param formTemplate 模板
     * @param data         业务数据
     * @return PlugInRet
     */
    PlugInRet afterModify(int classId, Long id, FormTemplate formTemplate, JsonNode data);

    /**
     * 删除前操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    PlugInRet beforeDelete(int classId, FormTemplate formTemplate, List<Long> ids);

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
    PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId, FormTemplate formTemplate);

    /**
     * 删除后操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    PlugInRet afterDelete(int classId, FormTemplate formTemplate, List<Long> ids);

    /**
     * 基础资料查询前操作
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditons    原始过滤条件
     * @return
     */
    PlugInRet beforeQuery(int classId, FormTemplate formTemplate, List<Condition> conditons);

    /**
     * 基础资料查询后操作
     *
     * @param classId 业务类型
     * @param list    查询结果集
     * @return
     */
    PlugInRet afterQuery(int classId, List<Map<String, Object>> list);

    /**
     * 查询前插件查询条件处理
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditions   原始过滤条件
     * @return 插件过滤条件
     */
    List<Condition> getConditions(int classId, FormTemplate formTemplate, List<Condition> conditions);

    /**
     * 禁用/反禁用前置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    PlugInRet beforeForbid(Integer classId, FormTemplate template, List<Long> ids, Integer operateType);

    /**
     * 禁用/反禁用后置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    PlugInRet afterForbid(Integer classId, FormTemplate template, List<Long> ids, Integer operateType);

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
    String checkFieldKey(Integer classId);

}
