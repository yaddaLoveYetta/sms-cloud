package com.kingdee.hrp.sms.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 此工具类为service提供动态获取实例bean可能
 *
 * @author yadda
 * @date 2017-04-10 20:01:55
 */
@Service
public final class Environ implements ApplicationContextAware {

    private static ApplicationContext ctx;

    private static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * 获取bean
     *
     * @param name bean name
     * @return bean instance
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 获取指定类型对象
     *
     * @param clazz Class
     * @return Class<T> instance
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Environ.ctx = applicationContext;
    }
}
