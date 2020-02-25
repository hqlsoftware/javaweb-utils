package com.heqilin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密
 *
 * @author heqilin
 * date 2019/04/03
 */
public class Md5Util {

    private Md5Util(){
        throw new AssertionError();
    }

    /**
     * md5加密(全部小写)
     * @param text
     * @return
     */
    public static String md5(String text){
        return md5(text,null);
    }

    /**
     * md5加密(全部小写) 16位
     * @param text
     * @return
     */
    public static String md5By16(String text){
        String result =  md5(text,null);
        return null == result ? "" : result.substring(8, 24);
    }

    /**
     * md5加密(全部小写)
     * @param text
     * @param charset
     * @return
     */
    public static String md5(String text,String charset){
        String result = null;

        if (null != text && !"".equals(text)) {// 首先判断是否为空
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");// 首先进行实例化和初始化
                byte[] btInput = (null == charset) ? text.getBytes() : text.getBytes(charset);// 得到一个操作系统默认的字节编码格式的字节数组
                md.update(btInput);// 对得到的字节数组进行处理
                byte[] btResult = md.digest();// 进行哈希计算并返回结果
                StringBuffer sb = new StringBuffer();// 进行哈希计算后得到的数据的长度
                for (byte b : btResult) {
                    int bt = b & 0xff;
                    if (bt < 16) {
                        sb.append(0);
                    }
                    sb.append(Integer.toHexString(bt));
                }
                result = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            result = text;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(md5("15061805283hzcrm2019"));
    }

}
