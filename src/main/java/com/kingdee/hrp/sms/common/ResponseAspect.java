package com.kingdee.hrp.sms.common;

import com.kingdee.hrp.sms.common.exception.BaseRuntimeException;
import com.kingdee.hrp.sms.common.domain.NoJsonWarp;
import com.kingdee.hrp.sms.common.domain.ResultWarp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 负责将返回转换成统一消息格式
 * *序列化为的格式如下:
 * {
 * code: 200,
 * msg:msg,
 * data: data
 * }
 *
 * @author yadda<silenceisok@163.com>
 * @version 0.0.1
 * @date 2015-5-18
 */
@Aspect
@Component
@Order(value = 2)
public class ResponseAspect {

    @Resource
    private MappingJackson2HttpMessageConverter converter;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 拦截Controller中所有@ResponseBody
     */
    @Pointcut("execution(* com.kingdee.hrp.sms..*.*Controller..*(..)) && @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void responseBodyPointCut() {
    }


    @Around(value = "responseBodyPointCut()")
    public void formatResult2JSON(ProceedingJoinPoint pjp) throws Throwable {

        Object ret = pjp.proceed(pjp.getArgs());

        // ret统一格式化成json,如果接口返回基本类型或者不能序列化成json类型eg:String,int,boolean...用data做key格式化成json形式
        ret = warpResult(ret);

        // 将业务返回值包装成固定的返回类型格式ResultWarp
        ResultWarp result = new ResultWarp();
        result.setData(ret);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // test
        response.addHeader("Access-Control-Allow-Origin", "*");

        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

        converter.write(result, MediaType.APPLICATION_JSON, outputMessage);
        shutdownResponse(response);
    }

    @AfterThrowing(pointcut = "responseBodyPointCut()", throwing = "throwable")
    public void handleForException(JoinPoint jp, Throwable throwable) throws Throwable {

        ResultWarp result = new ResultWarp();

        result.setCode(-1);
        result.setMsg(throwable.getMessage());

        if (throwable instanceof BaseRuntimeException) {

            BaseRuntimeException baseRuntimeException = (BaseRuntimeException) throwable;

            result.setCode(baseRuntimeException.getErrCode());

        }

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

        logger.error(jp.getSignature().getName() + "-throwable!", throwable);
        converter.write(result, MediaType.APPLICATION_JSON, outputMessage);
        shutdownResponse(response);
    }

    /**
     * 在已返回json的情况下，将Response关掉，不然可能会在这里输出json后还会再次输出一些内容
     */
    private void shutdownResponse(HttpServletResponse response) throws IOException {
        response.getOutputStream().close();
    }

    /**
     * 对result进行包装，将不能格式化成json{}|[]的对象包装成NoJsonWarp对象
     *
     * @param result
     * @return Object
     * @Title warpResult
     * @date 2017-09-06 11:55:39 星期三
     */
    private Object warpResult(Object result) {

        if (result == null || Integer.class.equals(result.getClass()) || Long.class.equals(result.getClass()) || Float.class.equals(result.getClass()) || Double.class.equals(result.getClass())
                || Date.class.equals(result.getClass()) || Character.class.equals(result.getClass()) || Byte.class.equals(result.getClass())
                || Short.class.equals(result.getClass()) || String.class.equals(result.getClass())) {

            result = new NoJsonWarp(result);
        }

        return result;
    }
}
