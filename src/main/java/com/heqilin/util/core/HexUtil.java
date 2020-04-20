package com.heqilin.util.core;

import java.nio.charset.Charset;

/**
 * 16进制转换
 *
 * @author heqilin
 * date 2019/04/07
 */
public class HexUtil {

    private HexUtil(){
        throw new AssertionError();
    }

    /**
     *  * 用于建立十六进制字符的输出的小写字符数组
     *  
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     *  * 用于建立十六进制字符的输出的大写字符数组
     *  
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//---------------------------------------------------------------------------------------------------- encode

    /**
     *  * 将字节数组转换为十六进制字符数组
     *  *
     *  * @param data byte[]
     *  * @return 十六进制char[]
     *  
     */
    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    /**
     *  * 将字节数组转换为十六进制字符数组
     *  *
     *  * @param str 字符串
     *  * @param charset 编码
     *  * @return 十六进制char[]
     *  
     */
    public static char[] encodeHex(String str, Charset charset) {
        return encodeHex(StringUtil.getBytes(str, charset), true);
    }

    /**
     *  * 将字节数组转换为十六进制字符数组
     *  *
     *  * @param data byte[]
     *  * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     *  * @return 十六进制char[]
     *  
     */
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     *  * 将字节数组转换为十六进制字符串
     *  *
     *  * @param data byte[]
     *  * @return 十六进制String
     *  
     */
    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    /**
     *  * 将字节数组转换为十六进制字符串
     *  *
     *  * @param data byte[]
     *  * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     *  * @return 十六进制String
     *  
     */
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }
//---------------------------------------------------------------------------------------------------- decode

    /**
     *  * 将十六进制字符数组转换为字符串
     *  *
     *  * @param hexStr 十六进制String
     *  * @param charset 编码
     *  * @return 字符串
     *  
     */
    public static String decodeHexStr(String hexStr, Charset charset) {
        if (StringUtil.isEmpty(hexStr)) {
            return hexStr;
        }
        return decodeHexStr(hexStr.toCharArray(), charset);
    }

    /**
     *  * 将十六进制字符数组转换为字符串
     *  *
     *  * @param hexData 十六进制char[]
     *  * @param charset 编码
     *  * @return 字符串
     *  
     */
    public static String decodeHexStr(char[] hexData, Charset charset) {
        return StringUtil.byteToString(decodeHex(hexData), charset);
    }

    /**
     *  * 将十六进制字符数组转换为字节数组
     *  *
     *  * @param hexData 十六进制char[]
     *  * @return byte[]
     *  * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     *  
     */
    public static byte[] decodeHex(char[] hexData) {
        int len = hexData.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] out = new byte[len >> 1];
// two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(hexData[j], j) << 4;
            j++;
            f = f | toDigit(hexData[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }
//---------------------------------------------------------------------------------------- Private method start

    /**
     *  * 将字节数组转换为十六进制字符串
     *  *
     *  * @param data byte[]
     *  * @param toDigits 用于控制输出的char[]
     *  * @return 十六进制String
     *  
     */
    private static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    /**
     *  * 将字节数组转换为十六进制字符数组
     *  *
     *  * @param data byte[]
     *  * @param toDigits 用于控制输出的char[]
     *  * @return 十六进制char[]
     *  
     */
    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
// two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     *  * 将十六进制字符转换成一个整数
     *  *
     *  * @param ch 十六进制char
     *  * @param index 十六进制字符在字符数组中的位置
     *  * @return 一个整数
     *  * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
     *  
     */
    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }
//---------------------------------------------------------------------------------------- Private method end

    /**
     *  * 2进制转16进制
     *  * @param bString 2进制字符串
     *  * @return
     *  
     */
    public static String binary2Hex(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     *  * 16进制转2进制
     *  * @param hexString
     *  * @return
     *  
     */
    public static String hex2Binary(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     *  * 将二进制转换成16进制
     *  * @param buf
     *  * @return
     *  
     */
    public static String binary2Hex(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     *  * 将16进制转换为二进制
     *  * @param hexStr
     *  * @return
     *  
     */
    public static byte[] hex2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {

        String head="! 0 200 200 300 1\r\n" +
                "CENTER\r\n" +
                "TEXT 4 0 0 0 违法停车告知单\r\n" +
                "RIGHT\r\n" +
                "TEXT 8 0 0 70 编号：  \r\n" +
                "LEFT\r\n" +
                "TEXT 8 0 0 120 车牌号码：苏123456    车辆型号：大型车\r\n" +
                "LEFT\r\n" +
                "TEXT 8 0 0 160 车牌颜色：黄色\r\n" +
                "LEFT\r\n" +
                "TEXT 8 0 0 200 违法停车时间：2019年04月09日22时24分\r\n" +
                "LEFT\r\n" +
                "TEXT 8 0 0 240 违法停车地点：其他类型电子监控-通江大道由北向南(G312至S342段)\r\n" +
                "PRINT";
        String body="! 0 200 200 200 1\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 46 0 该机动车在上述时间、地点停放，违反了道路\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 0 40 交通安全法律、法规关于机动车停放、临时停车的\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 0 80 规定。请在15日后，机动车年检前，到无锡市公安\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 0 120 局交通巡逻警察支队各大队违法处理窗口查询、处\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 0 160 理。（处理时请带好驾驶证、行驶证）\r\n" +
                "PRINT";
        String bottom="! 0 200 200 200 1\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 46 0 咨询电话：0510-88221013\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 46 40 办案人员：1、JSZF8001232\r\n" +
                "LEFT\r\n"+
                "TEXT 8 0 165 80 2、JSZF9812312\r\n" +

                "RIGHT\r\n"+
                "TEXT 8 0 0 120 无锡市锡山区综合行政执法局  \r\n" +
                "RIGHT\r\n"+
                "TEXT 8 0 0 160 2019年04月09号  \r\n" +
                "PRINT";

        byte[] bytes = StringUtil.getBytes(head,Charset.forName("GBK"));
        String hexStr = binary2Hex(bytes);
        System.out.println(hexStr);

        bytes = StringUtil.getBytes(body,Charset.forName("GBK"));
        hexStr = binary2Hex(bytes);
        System.out.println(hexStr);

         bytes = StringUtil.getBytes(bottom,Charset.forName("GBK"));
         hexStr = binary2Hex(bytes);
        System.out.println(hexStr);
    }
}
