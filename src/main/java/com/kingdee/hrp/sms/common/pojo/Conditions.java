package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 通用查询条件
 *
 * @author le.xiao
 * @date 2018/07/06 09:24
 */
@Getter
@Setter
@Accessors(chain = true)
public class Conditions implements Serializable {

    /**
     * 查询条件集合
     */
    private List<Condition> conditionList;
}
