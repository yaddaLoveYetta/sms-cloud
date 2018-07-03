package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 非json格式数据包装成可json序列化对象,如int,char,String,boolean 等
 *
 * @author yadda
 */
@Getter
@Setter
@Accessors(chain = true)
public class NoJsonWarp implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object value;


    public NoJsonWarp(Object data) {
        super();
        this.value = data;
    }
}
