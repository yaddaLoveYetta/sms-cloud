package com.kingdee.hrp.sms.common.bill.operator;

import com.kingdee.hrp.sms.common.pojo.Sort;

import java.util.List;

/**
 * 单据的基本操作
 * 所谓一个单据具备的最基础的可用操作
 *
 * @author yadda
 * @param <V> 单据的类型
 */
public interface Base<V> {

    /**
     * 查询一个单据内容
     *
     * @param id 单据内码
     * @return 一个单据的完整数据，单据体分录按照主键排序
     */
    V getItemById(Long id);

    /**
     * 通过内码获取单个业务类型数据
     *
     * @param id    单据内码
     * @param sorts 排序结构 查询单据时，单据分录可能需要排序
     * @return 一个单据的完整数据
     */
    V getItemById(Long id, List<Sort> sorts);

    /**
     * 新增一个单据
     *
     * @param v 单据内容
     * @return 新增的单据主键
     */
    Long addItem(V v);
}
