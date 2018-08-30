package com.kingdee.hrp.sms.util;

import com.baidu.aip.ocr.AipOcr;
import com.kingdee.hrp.sms.common.controller.TemplateController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 百度人工智能识别工具类
 *
 * @author yadda
 */
public class BaiduAiUtil {

    private static final String appId;
    private static final String appKey;
    private static final String secretKey;

    private static final AipOcr client;

    //private static Logger logger = LoggerFactory.getLogger(BaiduAiUtil.class);

    static {

        Properties props = new Properties();
        try {
            props.load(BaiduAiUtil.class.getClassLoader().getResourceAsStream("config/common.properties"));
        } catch (IOException e) {
            //logger.error("common.properties配置文件异常！");
            throw new ExceptionInInitializerError("common.properties配置文件异常！");
        }

        appId = props.getProperty("baidu.ai.appId");
        appKey = props.getProperty("baidu.ai.apiKey");
        secretKey = props.getProperty("baidu.ai.secretKey");

        if (StringUtils.isBlank(appId) || StringUtils.isBlank(appKey) || StringUtils.isBlank(secretKey)) {
            //logger.error("百度人工智能应用配置不正确！必须配置appId:{},appKey:{},secretKey:{}", appId, appKey, secretKey);
            throw new ExceptionInInitializerError("百度人工智能应用配置不正确！必须配置appId,appKey,secretKey");
        }

        client = new AipOcr(appId, appKey, secretKey);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // 设置http代理
        //client.setHttpProxy("proxy_host", proxy_port);
        // 设置socket代理
        //client.setSocketProxy("proxy_host", proxy_port);

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

    }

    private BaiduAiUtil() {
    }

    public static AipOcr getAipOcr() {
        return client;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getAppKey() {
        return appKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }
}
