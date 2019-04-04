package com.kingdee.hrp.sms.util;

import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.Util;
import com.google.common.primitives.Booleans;
import com.kingdee.hrp.sms.common.model.AccessControl;
import lombok.*;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BaiduAiUtilTest {

    /**
     * 身份证识别
     *
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
     *
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

    @Test
    public void bitMove() {
        Integer COUNT_BITS = Integer.SIZE - 3;
        System.out.println(COUNT_BITS);
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(-1 << COUNT_BITS);
        System.out.println(Integer.toBinaryString(0));
        System.out.println(0 << COUNT_BITS);
        System.out.println(Integer.toBinaryString(1));
        System.out.println(1 << COUNT_BITS);
        System.out.println(Integer.toBinaryString(2));
        System.out.println(2 << COUNT_BITS);
        System.out.println(Integer.toBinaryString(3));
        System.out.println(3 << COUNT_BITS);
    }

    @Test
    public void threadTest() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1));

        for (int i = 1; i <= 21; i++) {
            executor.execute(new Task(i));
        }

        executor.shutdown();

    }

    @Test
    public void toStringTest() {
        AccessControl accessControl = new AccessControl();

        System.out.println(accessControl.toString());
    }

    private class Task implements Runnable {

        private int index;

        public Task(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "========index=" + this.index);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void lombokLoadTest() {

        try {
            Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass("lombok.Lombok");
            System.out.println(aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bTest(){
        Boolean b =null;

/*        System.out.println(true==b);
        System.out.println(false==b);*/
        //Boolean.valueOf(b);
        System.out.println(Boolean.valueOf(b));


    }
    @Test
    public void streamTest() {

        A a = new A();

        List<String> items = new ArrayList<>();
        items.add("yadda");
        items.add("yetta");
        items.add("kitty");
        items.add("marry");

        a.setItems(items);

        System.out.println(System.identityHashCode(a.getItems()));

        List<String> aItems = a.getItems();

        System.out.println(System.identityHashCode(aItems));

        aItems = aItems.stream().filter(item -> !"yadda".equals(item)).collect(Collectors.toList());

        System.out.println(System.identityHashCode(aItems));
        System.out.println(aItems);

        System.out.println(System.identityHashCode(a.getItems()));
        System.out.println("=================> after filter==========================>");
        System.out.println(a);

    }

    @Getter
    @Setter
    @ToString
    private class A {

        private List<String> items;
    }
}