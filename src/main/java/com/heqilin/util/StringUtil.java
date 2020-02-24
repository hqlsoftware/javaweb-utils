package com.heqilin.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.MessageFormat;

/**
 * 字符串帮助类
 *
 * @author heqilin
 * date:  2018-12-21 ok
 **/
public class StringUtil {

    public static final String EMPTY = "";

    //region encode/decode

    /**
     * 编码
     **/
    public static String encodeUrl(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码
     **/
    public static String decodeUrl(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion

    /**
     * 是否不为空
     **/
    public static boolean isNotEmpty(Object str) {
        return str != null && !"".equals(str.toString().trim());
    }

    /**
     * 是否为空
     **/
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str.toString().trim());
    }

    /**
     * 是否全部不为空
     **/
    public static boolean isAllNotEmpty(String[] str) {
        if (str == null || str.length == 0)
            return false;
        for (String s : str) {
            if (!isNotEmpty(s))
                return false;
        }
        return true;
    }

    /**
     * 是否有一个为空
     **/
    public static boolean isOneEmpty(String[] str) {
        if (str == null || str.length == 0)
            return true;
        for (String s : str) {
            if (!isNotEmpty(s))
                return true;
        }
        return false;
    }

    /**
     * 元素是否在集合中
     **/
    public static <T> boolean isIn(T str, T[] result) {
        if (result == null || result.length == 0)
            return false;
        for (T s : result) {
            if (str.equals(s))
                return true;
        }
        return false;
    }

    /**
     * 重复N个字符
     **/
    public static String repeatNStr(int n, String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 替换N个字符
     **/
    public static String replaceNStar(String str, int startIndex, int length, String replaceChar) {
        String replaceStr = str.substring(startIndex, startIndex + length);
        return str.replace(replaceStr, repeatNStr(length, replaceChar));
    }

    //region 去掉首尾字符串前缀和后缀

    /**
     * 去掉前缀字符
     **/
    public static String removePrefixString(String val, String str) {
        String strRegex = "^(" + str + ")";
        return val.replaceAll(strRegex, "");
    }

    /**
     * 去掉后缀字符
     **/
    public static String removeSuffixString(String val, String str) {
        String strRegex = "(" + str + ")" + "$";
        return val.replaceAll(strRegex, "");
    }

    //endregion

    //region 补足前缀的0

    /**
     * 补足前缀的0
     * @param number 数值
     * @param digit 补足几位
     * @return
     */
    public static String addZeroPrefixString(long number, int digit) {
        DecimalFormat df = new DecimalFormat(repeatNStr(digit,"0"));
        return df.format(number);
    }

    //endregion

    //region 首字母大写和小写

    /**
     * 首字母大写
     **/
    public static String firstUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     **/
    public static String firstLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    //endregion

    //region 获取字节码
    public static byte[] getBytes(String str, Charset charset) {
        return str.getBytes(charset);
    }

    public static String byteToString(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }
    //endregion

    //region Join
    private static StringBuilder newStringBuilder(final int noOfItems) {
        return new StringBuilder(noOfItems * 16);
    }

    public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }

        final StringBuilder buf = newStringBuilder(noOfItems);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }


    //endregion

    //region 数字类型格式化为字符串

    /**
     * 超过1W数字转换为1.23万,保留两位小数点
     * @param number
     * @return
     */
    public static String formatNumberMoreThenTenThousand(long number) {
        if(number<10000){
            return String.valueOf(number);
        }
        double result = number*1.0/10000;
        DecimalFormat decimalFormat = new DecimalFormat("###################.##");
        return decimalFormat.format(result)+"万";
    }

    /**
     * 四舍五入 格式化小数点 （double long object decimal）
     * @param dicimal
     * @param dot
     * @param <T>
     * @return
     */
    public static <T> String formatDot (T dicimal,int dot){
        String dotChar = repeatNStr(dot,"#");
        if(isEmpty(dotChar)){
            dotChar="";
        }else{
            dotChar ="."+dotChar;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###################"+dotChar);
        return  decimalFormat.format(dicimal);
    }

    /**
     * 四舍五入 格式化小数点 （double long object decimal）金额格式
     * @param dicimal
     * @param dot
     * @param <T>
     * @return
     */
    public static <T> String formatMoney (T dicimal,int dot){
        String dotChar = repeatNStr(dot,"#");
        if(isEmpty(dotChar)){
            dotChar="";
        }else{
            dotChar ="."+dotChar;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###,###,###,###"+dotChar);
        return  decimalFormat.format(dicimal);
    }

    /**
     * 占位符 格式化字符串 形如：我的姓名：{0},我来自哪儿:{1}
     * @param str
     * @param arguments
     * @return
     */
    public static String format(String str,Object... arguments){
        if(isEmpty(str)){
            return str;
        }
        String placeholder = String.valueOf(RandomUtil.createSnowflakeId());
        str = str.replaceAll("\\{}",placeholder);
        return MessageFormat.format(str,arguments).replaceAll(placeholder,"{}");
    }
    //endregion

    //region 获取字符串的长度

    /**
     * 获取字符串的长度，对双字符（包括汉字）按两位计数
     * @param value
     * @return
     */
    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }
    //endregion

    public static void main(String[] args) {
        System.out.println(format("fafafas","fa"));
    }

}