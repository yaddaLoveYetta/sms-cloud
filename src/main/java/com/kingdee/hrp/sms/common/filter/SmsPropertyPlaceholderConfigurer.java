package com.kingdee.hrp.sms.common.filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Property资源文件操作
 *
 * @author yadda<silenceisok@163.com>
 * @since 2017/12/11
 */
public class SmsPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, String> properties = new HashMap<String, String>(100);

    /**
     * Visit each bean definition in the given bean factory and attempt to replace ${...} property
     * placeholders with values from the given properties.
     *
     * @param beanFactoryToProcess
     * @param props
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        for (Object item : props.keySet()) {
            String key = item.toString();
            properties.put(key, props.getProperty(key));
        }
    }

    public static String getContextProperty(String name) {
        return properties.get(name);
    }
}
