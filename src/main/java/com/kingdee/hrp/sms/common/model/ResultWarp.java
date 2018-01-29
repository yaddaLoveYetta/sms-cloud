package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;

/**
 * @author yadda
 */
public class ResultWarp implements Serializable {

    private static final long serialVersionUID = -6963503022738848863L;

    /**
     * 默认成功
     */
    private int code = 200;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (data != null) {
            return "ResultWarp [code=" + code + ", msg=" + msg + ", data=" + data.toString() + "]";
        }
        return "ResultWarp [code=" + code + ", msg=" + msg + ", data=" + data + "]";

    }
}
