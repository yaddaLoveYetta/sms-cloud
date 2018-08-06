package com.kingdee.hrp.sms.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 社会统一信用代码校验
 *
 * @author le.xiao
 */
public class CreditCodeRegex {

    private static String isCreditCode = "true";
    private static String error_CreditCode = "社会信用代码有误";
    private static String error_CreditCode_min = "社会信用代码不足18位，请核对后再输！";
    private static String error_CreditCode_max = "社会信用代码大于18位，请核对后再输！";
    private static String error_CreditCode_empty = "社会信用代码不能为空！";
    private static Map<String, Integer> data = null;
    private static char[] pre17s;
    private static int[] power = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };
    /**
     * 社会统一信用代码不含（I、O、S、V、Z） 等字母
     */
    static char[] code = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'T', 'U', 'W', 'X', 'Y' };

    /**
     * 判断是否是一个有效的社会信用代码
     *
     * @param creditCode 社会信用代码
     * @return String
     */
    static String isCreditCode(String creditCode) {
        if ("".equals(creditCode) || " ".equals(creditCode)) {
            System.out.println(error_CreditCode_empty);
            return error_CreditCode_empty;
        } else if (creditCode.length() < 18) {
            System.out.println(error_CreditCode_min);
            return error_CreditCode_min;
        } else if (creditCode.length() > 18) {
            System.out.println(error_CreditCode_max);
            return error_CreditCode_max;
        } else {
            int sum = sum(pre17s);
            int temp = sum % 31;
            //  谢谢 whhitli的帮助
            temp = temp == 0 ? 31 : temp;
            System.out.println(code[31 - temp] + " " +
                    (creditCode.substring(17, 18).equals(code[31 - temp] + "") ? isCreditCode : error_CreditCode));
            return creditCode.substring(17, 18).equals(code[31 - temp] + "") ? isCreditCode : error_CreditCode;
        }
    }

    /**
     * @param chars
     * @return
     */
    private static int sum(char[] chars) {
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            int code = data.get(chars[i] + "");
            sum += power[i] * code;
        }
        return sum;

    }

    /**
     * 获取前17位字符
     *
     * @param creditCode
     */
    static void pre17(String creditCode) {
        String pre17 = creditCode.substring(0, 17);
        pre17s = pre17.toCharArray();
    }

    /**
     * 初始化数据
     *
     * @param count
     */
    static void initDatas(int count) {
        data = new HashMap<String, Integer>(16);
        for (int i = 0; i < code.length; i++) {
            data.put(code[i] + "", i);
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // 测试
        String temp = "91350100M000100Y43";
        System.out.println(temp);
        initDatas(code.length);
        pre17(temp);
        isCreditCode(temp);
    }

}
