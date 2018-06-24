package com.kingdee.hrp.sms.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户注册参数模型
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/6/24 16:05
 */
@Setter
@Getter
@ToString
public class RegisterModel {

    /**
     * 用户类别 (1:系统管理员 2:医院 3:供应商)
     */
    private Integer userType;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 企业统一信用代码(供应商注册用户时提交)
     */
    private String creditCode;
    /**
     * 医疗机构登记号(医院注册用户时提交)
     */
    private String registrationNo;
    /**
     * 企业名称
     */
    private String orgName;
}
