package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 通用排序条件
 *
 * @author le.xiao
 * @date 2018/07/06 09:24
 */
@Getter
@Setter
@Accessors(chain = true)
public class Sorts implements Serializable {

    /**
     * 排序条件集合
     */
    private List<Sort> sortList;
}
