package com.heqilin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

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


}