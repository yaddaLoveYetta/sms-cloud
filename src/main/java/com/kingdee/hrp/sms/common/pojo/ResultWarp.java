package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yadda
 */
@Getter
@Setter
@Accessors(chain = true)
public class ResultWarp implements Serializable {

    private static final long serialVersionUID = -6963503022738848863L;

    /**
     * 默认成功
     */
    private int code = StatusCode.SUCCESS;
    /**
     * 返回消息(不成功时必须要有消息)
     */
    private String msg = "success";
    /**
     * 返回数据
     */
    private Object data;

    public ResultWarp() {
        super();
    }

    public ResultWarp(int code, String message, Object data) {
        super();
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public ResultWarp(int code, String message) {
        super();
        this.code = code;
        this.msg = message;
    }

    public ResultWarp(int code, Object data) {
        super();
        this.code = code;
        this.data = data;
    }

    public ResultWarp(Object data) {
        super();
        this.data = data;
    }

}
