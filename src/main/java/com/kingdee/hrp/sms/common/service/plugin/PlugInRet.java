package com.kingdee.hrp.sms.common.service.plugin;

import com.kingdee.hrp.sms.common.pojo.StatusCode;

import java.util.Map;

/**
 * 插件返回类型
 *
 * @author yadda
 * @date 2017-04-27 17:31:53 星期四
 */
public class PlugInRet {

    /**
     * 返回码:200标示成功，其他标示失败 默认成功
     */
    private int code = StatusCode.SUCCESS;

    /**
     * 返回信息-在不成功时通常需要附加错误提示信息，方便开发人员排查问题
     */
    private String msg = "success";

    /**
     * 复杂的数据容器，当code、msg不足以描述插件返回值时，将其他返回内容附加到此属性中
     */
    private Map<String, Object> data = null;

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public PlugInRet() {
        super();
    }

    public PlugInRet(int code) {
        super();
        this.code = code;
    }

    public PlugInRet(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public PlugInRet(int code, String msg, Map<String, Object> data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
