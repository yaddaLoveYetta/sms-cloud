package com.kingdee.hrp.sms.common.service;

import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;

import java.io.IOException;
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
     * @param type    获取按钮的场景 ( 0:查看(列表)1:(新增)2:(编辑)默认0)
     * @return 功能操作列表
     */

    List getFormAction(Integer classId, Integer type);

    /**
     * 通过模板获取业务数据
     *
     * @param classId    业务类型
     * @param conditions 过滤条件
     * @param sorts      排序条件
     * @param pageSize   分页大小
     * @param pageNo     当前页码
     */

    Map<String, Object> getItems(Integer classId, List<Condition> conditions, List<Sort> sorts, Integer pageSize, Integer pageNo);

    /**
     * 通过内码获取单个业务类型数据
     *
     * @param classId 业务类型
     * @param id      单据内码
     * @param sorts   排序结构 查询单据时，单据分录可能需要排序
     * @return 一个单据数据
     */
    Map<String, Object> getItemById(Integer classId, Long id, List<Sort> sorts);

    /**
     * 通过内码集合获取多个业务类型数据
     *
     * @param classId    业务类型
     * @param ids        单据内码集合
     * @param conditions 查询条件
     * @param sorts      排序结构
     * @return 单据集合
     */
    List<Map<String, Object>> getItemByIds(Integer classId, List<Long> ids, List<Condition> conditions, List<Sort> sorts);

    /**
     * 新增数据
     *
     * @param classId 业务类别
     * @param data    数据（严格按照单据模板匹配的数据）
     * @return 新增资料的id
     */

    Long addItem(Integer classId, String data) throws IOException;

    /**
     * 修改业务数据
     *
     * @param classId 业务类型
     * @param id      内码
     * @param data    修改数据内容（严格按照单据模板匹配的数据）
     * @return 是否成功
     */

    Boolean editItem(Integer classId, Long id, String data) throws IOException;

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

    Boolean check(Integer classId, List<Long> ids);

    /**
     * 反审核资料
     *
     * @param classId 业务类型
     * @param ids     内码集合
     * @return 是否成功
     */

    Boolean unCheck(Integer classId, List<Long> ids);

    /**
     * 禁用/反禁用(基础资料用)
     *
     * @param classId     业务类型
     * @param ids         内码集合
     * @param operateType 1：禁用 2：反禁用
     * @return 是否成功
     */
    Boolean forbid(Integer classId, List<Long> ids, Integer operateType);
}
