package com.kingdee.hrp.sms.common.service;

import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sorts;

import java.util.List;
import java.util.Map;

public interface ITemplateService {

    /**
     * 查询基础资料/单据模板数据
     *
     * @param classId 业务类别
     * @param type    查询方式（0:后端查询 1:前端获取）
     * @return
     */
    Map<String, Object> getFormTemplate(Integer classId, Integer type);

    /**
     * 获取基础资料/单据定义的功能操作列表
     *
     * @param classId 业务类型
     * @return 功能操作列表
     */

    List getFormAction(Integer classId);

    /**
     * 通过模板获取业务数据
     *
     * @param classId   业务类型
     * @param conditions 过滤条件（json结构化数据）
     * @param sorts   排序条件（json结构化数据）
     * @param pageSize  分页大小
     * @param pageNo    当前页码
     */

    Map<String, Object> getItems(Integer classId, String conditions, Sorts sorts, Integer pageSize, Integer pageNo);

    /**
     * 通过内码获取单个业务类型数据
     *
     * @param classId 业务类型
     * @param id      单据内码
     * @param orderBy 排序结构(json 结构化数据) 查询单据时，单据分录需要排序
     */

    Map<String, Object> getItemById(Integer classId, Long id, String orderBy);

    /**
     * 新增数据
     *
     * @param classId 业务类别
     * @param data    数据（严格按照单据模板匹配的数据）
     * @return 新增资料的id
     */

    Long addItem(Integer classId, String data);

    /**
     * 修改业务数据
     *
     * @param classId 业务类型
     * @param id      内码
     * @param data    修改数据内容（严格按照单据模板匹配的数据）
     * @return 是否成功
     */

    Boolean editItem(Integer classId, Long id, String data);

    /**
     * 删除基础资料
     *
     * @param classId 业务类型
     * @param ids     删除的内码集合
     * @return 是否成功
     */

    Boolean delItem(Integer classId, List<Long> ids);

    /**
     * 审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */

    Boolean checkItem(Integer classId, List<Long> ids);

    /**
     * 反审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */

    Boolean unCheckItem(Integer classId, List<Long> ids);
}