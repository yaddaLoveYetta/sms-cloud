package com.kingdee.hrp.sms;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * 初始化请求相关设置
 */
public interface InitRequest {

    /**
     * 设置请求参数
     *
     * @param requestBuilder MockHttpServletRequestBuilder
     */
    void paramsSet(MockHttpServletRequestBuilder requestBuilder);

    /**
     * 参数提交方式
     *
     * @return application/x-www-form-urlencoded
     */
    default MediaType getRequestMediaType() {
        return MediaType.APPLICATION_FORM_URLENCODED;
    }

}
