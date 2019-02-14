package com.kingdee.hrp.sms.common.filter;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 重设allowBeanDefinitionOverriding为false，控制ioc中不能配置id或name一样的多个bean，规范bean定义
 *
 * @author le.xiao
 */
public class SpringApplicationContextInitializer implements ApplicationContextInitializer<XmlWebApplicationContext> {
    /**
     * Initialize the given application context.
     *
     * @param applicationContext the application to configure
     */
    @Override
    public void initialize(XmlWebApplicationContext applicationContext) {
        //在这里将XmlWebApplicationContext属性allowBeanDefinitionOverriding设置为false,这个属
        //性的值最终会传递给DefaultListableBeanFactory类的allowBeanDefinitionOverriding属性
        // 可以控制ioc中不能配置id或name一样的多个bean，重复id或name时抛出错误(规范bean定义)
        applicationContext.setAllowBeanDefinitionOverriding(false);
    }
}
