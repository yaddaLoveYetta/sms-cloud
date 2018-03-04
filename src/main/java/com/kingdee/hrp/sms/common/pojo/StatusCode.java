package com.kingdee.hrp.sms.common.pojo;

/**
 * 
 * @ClassName: StatusCode
 * @Description:
 * 
 * 				全局返回码说明：<br>
 *               200:请求成功. <br>
 *               500--600:为业务逻辑错误返回码，例如：入口参数错误,非法操作等. <br>
 *               40001--41000:为全局与业务无关错误返回码，例如登陆时用户名密码错误，手机号码不正确，验证码过期等.<br>
 *               返回码必须引用此类中的常量，方便返回码的统一管理.<br>
 *               当你认为这里的错误代码不足以表述当前错误时，请在此增加错误代码后使用.<br>
 * @author yadda
 * @date 2017年4月13日 下午2:37:40
 *
 */
public class StatusCode {

	/**
	 * 系统繁忙
	 */
	public static final int SYS_BUSY = -1;
	/**
	 * 请求成功
	 */
	public static final int SUCCESS = 200;

	/**
	 * 参数错误
	 */
	public static final int PARAMETER_ERROR = 501;
	/**
	 * 参数不存在
	 */
	public static final int PARAMETER_IS_NOT_EXIST = 502;
	/**
	 * 业务逻辑导致逻辑不能继续
	 */
	public static final int BUSINESS_LOGIC_ERROR = 503;

	/**
	 * 用户名已经存在
	 */
	public static final int USER_ALREADY_EXIST = 601;
	/**
	 * 插件错误
	 */
	public static final int PLUGIN_ERROR = 801;

	/**
	 * 不合法的access_token
	 */
	public static final int ACCESS_TOKEN_INVALID = 40001;

	/**
	 * 非法用戶
	 */
	public static final int ILLEGAL_USER = 40002;
	/**
	 * 用户名或密码错误
	 */
	public static final int MOBILE_OR_PWAD_ERROR = 40003;
	/**
	 * 手机验证码不正确
	 */
	public static final int MOBILE_CODE_ERROR = 40004;
	/**
	 * 手机验证码已过期
	 */
	public static final int MOBILE_CODE_EXPIRE = 40005;

	/**
	 * 获取验证码过于频繁，至少需要间隔1分钟后才能重发
	 */
	public static final int MOBILE_CODE_FREQUENTLY = 40006;

	/**
	 * 原密码不正确
	 */
	public static final int OLD_PASSWORD_ERROR = 40014;
	/**
	 * 图片格式不对
	 */
	public static final int PIC_FORMAT_ERROR = 40015;
	/**
	 * 图片过大
	 */
	public static final int PIC_SIZE_ERROR = 40016;

	/**
	 * 图片上传失败
	 */
	public static final int PIC_UPLOAD_FAIL = 40017;

	/**
	 * 手机号不存在
	 */
	public static final int MOBILE_NOT_EXIST = 40019;

	/**
	 * 用户不存在
	 */
	public static final int USER_NOT_ABSENT = 40025;

	/**
	 * 两次输入密码不一致
	 */
	public static final int PASSWORD_NOT_EQUAL = 40026;

	/**
	 * 未分配权限
	 */
	public static final int PERMISSION_INVALID = 40050;

	/**
	 * 回话结束
	 */
	public static final int SESSION_LOST = 10000;

}
