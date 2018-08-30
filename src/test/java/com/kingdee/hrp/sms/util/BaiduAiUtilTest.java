package com.kingdee.hrp.sms.util;

import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.Util;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BaiduAiUtilTest {

    /**
     * 身份证识别
     * @throws IOException
     */
    @Test
    public void idcard() throws IOException {

        AipOcr client = BaiduAiUtil.getAipOcr();

        // 身份证图片路径
        String path = client.getClass().getClassLoader().getResource("files/test.jpg").getFile();

        // 附加可选识别参数
        HashMap<String, String> params = new HashMap<String, String>(4) {{
            // 检测旋转角度并矫正识别
            put("detect_direction", "true");
            // 开启身份证风险类型
            put("detect_risk", "true");
        }};
        ;
        // 调用百度api获取身份证识别数据
        JSONObject res = client.idcard(Util.readFileByBytes(path), "front", params);

        System.out.println(res.toString(2));

    }

    /**
     * 营业执照识别
     * @throws IOException
     */
    @Test
    public void businessLicense() throws IOException {

        AipOcr client = BaiduAiUtil.getAipOcr();

        // 身份证图片路径
        String path = client.getClass().getClassLoader().getResource("files/business_license.jpg").getFile();

        // 附加可选识别参数
        HashMap<String, String> params = new HashMap<String, String>(4) {{
            // 检测旋转角度并矫正识别
            put("detect_direction", "true");
            // normal,high参数选normal或为空使用快速服务；选择high使用高精度服务，但是时延会根据具体图片有相应的增加
            put("accuracy", "high");
        }};
        ;
        // 调用百度api获取身份证识别数据
        JSONObject res = client.businessLicense(path, params);

        System.out.println(res.toString(2));
    }
}