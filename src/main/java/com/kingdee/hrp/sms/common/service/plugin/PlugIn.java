package com.kingdee.hrp.sms.common.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.util.SessionUtil;

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
    public Set<Integer> getClassIdSet();

    /**
     * 基础资料新增前操作
     *
     * @param classId      业务类型
     * @param formTemplate 单据模板
     * @param data         业务数据
     * @return PlugInRet
     */
    public PlugInRet beforeSave(int classId, Map<String, Object> formTemplate, JsonNode data);

    /**
     * 基础资料新增后操作
     *
     * @param classId 业务类型
     * @param id      新增的资料内码
     * @param data    业务数据
     * @return
     */
    public PlugInRet afterSave(int classId, Long id, JsonNode data);

    /**
     * 单据修改前操作
     *
     * @param classId      业务类型
     * @param id           单据内码
     * @param formTemplate 模板
     * @param data         业务数据
     * @return PlugInRet
     */
    public PlugInRet beforeModify(int classId, Long id, Map<String, Object> formTemplate, JsonNode data);

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
    public PlugInRet beforeEntryModify(int classId, String primaryId, String entryId, Map<String, Object> formTemplate,
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
    public PlugInRet afterModify(int classId, Long id, Map<String, Object> formTemplate, JsonNode data);

    /**
     * 删除前操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    public PlugInRet beforeDelete(int classId, Map<String, Object> formTemplate, List<Long> ids);

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
    public PlugInRet beforeEntryDelete(int classId, String primaryId, String entryId, Map<String, Object> formTemplate);

    /**
     * 删除后操作
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    public PlugInRet afterDelete(int classId, Map<String, Object> formTemplate, List<Long> ids);

    /**
     * 基础资料查询前操作
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditons    原始过滤条件
     * @return
     */
    public PlugInRet beforeQuery(int classId, Map<String, Object> formTemplate, List<Condition> conditons);

    /**
     * 基础资料查询后操作
     *
     * @param classId 业务类型
     * @param list    查询结果集
     * @return
     */
    public PlugInRet afterQuery(int classId, List<Map<String, Object>> list);

    /**
     * 查询前插件查询条件
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditons    原始过滤条件
     * @return 插件过滤条件
     */
    public List<Condition> getConditions(int classId, Map<String, Object> formTemplate, List<Condition> conditons);

    /**
     * 禁用/反禁用前置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    PlugInRet beforeForbid(Integer classId, Map<String, Object> template, List<Long> ids, Integer operateType);

    /**
     * 禁用/反禁用后置事件（基础资料用）
     *
     * @param classId     业务类别
     * @param template    单据模板
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return PlugInRet
     */
    PlugInRet afterForbid(Integer classId, Map<String, Object> template, List<Long> ids, Integer operateType);

    /**
     * 获取当前用户关联的组织id(供应商/医院资料内码，系统用户返回0)
     *
     * @return Long
     */
    default Long getUserLinkOrg() {

        UserRoleType userRoleType = SessionUtil.getUserRoleType();
        if (userRoleType == UserRoleType.HOSPITAL) {
            //医院角色
            return SessionUtil.getUserLinkHospital();
        } else if (userRoleType == UserRoleType.SUPPLIER) {
            //供应商角色
            return SessionUtil.getUserLinkSupplier();
        }
        return 0L;
    }

    /**
     * 获取当前用户的角色类别（1: 系统角色 2: 医院角色 3: 供应商角色）
     *
     * @return Integer
     */
    default UserRoleType getUserRoleType() {
        return SessionUtil.getUserRoleType();
    }
}
