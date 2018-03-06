package com.kingdee.hrp.sms.common.filter;

import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.User;
import com.kingdee.hrp.sms.common.pojo.ResultWarp;
import com.kingdee.hrp.sms.common.pojo.StatusCode;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 拦截请求-校验请求合法性
 *
 * @author yadda
 * @date 2018-03-06
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private MappingJackson2HttpMessageConverter converter;
    /**
     * web不拦截的资源
     */
    public Map<String, String> allowUrls;

    /**
     * app请求不拦截的资源
     */
    public Map<String, String> clientUrls;

    public void setClientUrls(Map<String, String> clientUrls) {
        this.clientUrls = clientUrls;
    }

    public void setAllowUrls(Map<String, String> allowUrls) {
        this.allowUrls = allowUrls;
    }

    /**
     * 判断用户是否登陆，未登陆用户不允许访问
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");

        if (null != allowUrls && allowUrls.containsKey(requestUrl)) {
            // 不拦截接口可能也会用到用户信息
            User user = (User) request.getSession().getAttribute("user");
            List<Role> roles = (List<Role>) request.getSession().getAttribute("roles");

            if (user != null) {
                request.getSession(true).setAttribute("user", user);
            }
            if (roles != null) {
                request.getSession(true).setAttribute("roles", roles);
            }

            // 不拦截的请求
            return super.preHandle(request, response, handler);
        }

        if (null != clientUrls && clientUrls.containsKey(requestUrl)) {
            // HRP调用的业务接口--验证token合法性

            return super.preHandle(request, response, handler);
        }

        User user = (User) request.getSession().getAttribute("user");
        List<Role> roles = (List<Role>) request.getSession().getAttribute("roles");

        if (user == null) {
            // ResponseWriteUtil.output(response, StatusCode.SESSION_LOST, "会话结束请重新登陆!");
            //return false;
            //throw new SessionLostRuntimeException("会话结束请重新登陆!");

            ResultWarp result = new ResultWarp();
            result.setCode(StatusCode.SESSION_LOST);
            result.setMsg("会话结束请重新登陆!");

            HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

            converter.write(result, MediaType.APPLICATION_JSON, outputMessage);
            response.getOutputStream().close();

            return false;
        }

        // 重新设值session--触发监听器将user放入ThreadLocal
        request.getSession(true).setAttribute("user", user);
        request.getSession(true).setAttribute("roles", roles);

        return super.preHandle(request, response, handler);

    }
}
