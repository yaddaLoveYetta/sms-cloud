package com.kingdee.hrp.sms.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Common {

    /**
     * MD5加密算法，用于用户密码加密
     *
     * @param password
     * @return
     */
    public static String MD5(String password) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            // System.out.println("MD5(" + pwd + ",32) = " + result); //32位MD5加密
            // System.out.println("MD5(" + pwd + ",16) = " +
            // buf.toString().substring(8, 24));
            // //16位MD5加密
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(MD5("123"));
    }
}
