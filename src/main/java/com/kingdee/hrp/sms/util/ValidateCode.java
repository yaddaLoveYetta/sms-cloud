package com.kingdee.hrp.sms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yadda
 */
public class ValidateCode {

    private static Logger logger = LoggerFactory.getLogger(ValidateCode.class);

    /**
     * 随机产生数字与字母组合的字符串
     */
    private String randomStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Random random = new Random();

    /**
     * 获得颜色
     *
     * @return
     */
    private Color getRandColor() {

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return new Color(r, g, b);
    }

    /**
     * 获取验证码数据
     * <p>
     * 使用数字与字母随机生成验证码
     *
     * @param width    图片宽度
     * @param height   图片高度
     * @param lineSize 干扰线数量
     * @param codeNum  随机产生的字符数量
     * @return 验证码及验证码生成的图片<br/>
     * code:验证码<br/>
     * image:验证码生成的图片
     */
    public Map<String, Object> getRandomCode(int width, int height, int lineSize, int codeNum) {

        Map<String, Object> ret = new HashMap<>(4);

        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics graphics = image.getGraphics();
        // 画布大小
        graphics.fillRect(0, 0, width, height);
        // 设置字体
        graphics.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));

        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            // 设置颜色
            graphics.setColor(getRandColor());
            drowLine(graphics, width, height);
        }

        // 绘制随机字符
        String code = "";
        for (int i = 1; i <= codeNum; i++) {

            String randomCode = getRandomCode();
            code += randomCode;

            graphics.setFont(new Font("Microsoft YaHei", Font.ITALIC + Font.BOLD, 22));
            graphics.setColor(getRandColor());
            graphics.translate(random.nextInt(3), random.nextInt(3));
            graphics.drawString(randomCode, 20 * i, 30);

        }

        graphics.dispose();

        ret.put("code", code);
        ret.put("image", image);

        return ret;
    }

    /**
     * 随机获取字符
     *
     * @return 一个字符String
     */
    private String getRandomCode() {
        return String.valueOf(randomStr.charAt(random.nextInt(randomStr.length())));
    }

    /**
     * 绘制干扰线
     *
     * @param g Graphics
     */
    private void drowLine(Graphics g, int width, int height) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(30);
        int yl = random.nextInt(20);
        g.drawLine(x, y, x + xl, y + yl);
    }

}
