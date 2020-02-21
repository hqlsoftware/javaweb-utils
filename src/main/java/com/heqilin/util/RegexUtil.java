package com.heqilin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author heqilin
 * date:  2019-01-15
 **/
public class RegexUtil {

    //region 常量

    /**
     * 正则表达式：验证用户名 支持中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）,长度为4-20位,中文按二位计数
     */
    public static final String USERNAME = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";

    /**
     * 正则表达式：验证密码 4-20位  所有字符
     */
    public static final String PASSWORD = ".{4,20}";

    /**
     * 手机号码 前2位固定+9位 宽松校验
     */
    private final static String MOBILE_NUMBER = "(\\+\\d+)?1[23456789]\\d{9}$";

    /**
     * 邮箱
     */
    private final static String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 香港电话
     */
    private final static String HK_PHONE_NUMBER = "^(5|6|8|9)\\d{7}$";

    /**
     * 正整数
     */
    private final static String POSITIVE_INTEGER = "^\\d+$";

    /**
     * 金额
     */
    private final static String MONEY = "^(\\d+(?:\\.\\d+)?)$";

    /**
     * 身份证
     */
    private final static String ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}X)$";

    /**
     * 中文
     */
    private final static String CHINESE = "^[\u4E00-\u9FA5]+$";

    /**
     * 日期，格式：1992-09-03，或1992.09.03或1992/09/03
     */
    private final static String BIRTHDAY = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";

    /**
     * 验证URL地址 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     */
    private final static String URL = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";

    /**
     * 验证性别 男/女/保密
     */
    private final static String SEX = "男|女|保密";

    /**
     * 正负整数
     */
    private final static String INTEGER = "\\-?[1-9]\\d+";

    /**
     * 验证整数和浮点数(包含正负)
     */
    private final static String DECIMALS = "\\-?[1-9]\\d+(\\.\\d+)?";

    /**
     * 验证整数和浮点数(正的)
     */
    private final static String POSITIVE_DECIMALS = "\\[1-9]\\d+(\\.\\d+)?";

    /**
     * 中国邮编
     */
    private final static String POSTCODE = "[1-9]\\d{5}";

    /**
     * 空白字符 包括：空格、\t、\n、\r、\f、\x0B
     */
    private final static String BLANKSPACE = "\\s+";

    /**
     * IP 地址 (简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     */
    private final static String IP_ADDRESS = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";

    //endregion

    //region 私有方法
    /**
     *
     * @param regex
     * @param str
     * @return
     */
    private static boolean checkStr(String regex, String str){
        if(regex == null || str == null){
            return false;
        }
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    //endregion

    //region常用校验


    /**
     * 校验用户名 支持中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）,长度为4-20位,中文按二位计数
     * @param str
     * @return
     */
    public static boolean checkUserName(String str) {
        boolean rs = checkStr(USERNAME, str);
        if (rs) {
            int strLenth = StringUtil.getStrLength(str);
            if (strLenth < 4 || strLenth > 20) {
                rs = false;
            }
        }
        return rs;
    }

    public static boolean checkPassword(String str) {
        return checkStr(PASSWORD, str);
    }

    /**
     * 校验邮箱
     * @param str
     * @return
     */
    public static boolean checkEmail(String str){
        return checkStr(EMAIL, str);
    }

    /**
     * 校验身份证
     * @param str
     * @return
     */
    public static boolean checkIdCard(String str){
        return checkStr(ID_CARD, str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     *
     * @param str
     * @return
     */
    public static boolean checkMobile(String str){
        return checkStr(MOBILE_NUMBER, str);
    }

    /**
     * 校验香港
     * @param str
     * @return
     */
    public static boolean checkHKPhone(String str){
        return checkStr(HK_PHONE_NUMBER, str);
    }

    /**
     * 校验正整数
     * @param str
     * @return
     */
    public static boolean checkPlusInteger(String str){
        return checkStr(POSITIVE_INTEGER, str);
    }

    /**
     * 校验money
     * @param str
     * @return
     */
    public static boolean checkMoney(String str){
        return checkStr(MONEY, str);
    }

    /**
     * 校验中文
     * @param str
     * @return
     */
    public static boolean checkChinese(String str){
        return checkStr(CHINESE, str);
    }

    /**
     * 校验生日：1985-01-01 或 1985.01.01 1985/09/03
     * @param str
     * @return
     */
    public static boolean checkBirthday(String str){
        return checkStr(BIRTHDAY, str);
    }

    /**
     * 验证URL地址 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @param str
     * @return
     */
    public static boolean checkUrl(String str){
        return checkStr(URL, str);
    }

    /**
     * 验证性别 男/女/保密
     * @param str
     * @return
     */
    public static boolean checkSex(String str){
        return checkStr(SEX, str);
    }

    /**
     * 正负整数
     * @param str
     * @return
     */
    public static boolean checkInteger(String str){
        return checkStr(INTEGER, str);
    }

    /**
     * 验证整数和浮点数(包含正负)
     * @param str
     * @return
     */
    public static boolean checkDecimals(String str){
        return checkStr(DECIMALS, str);
    }

    /**
     *  验证整数和浮点数(正的)
     * @param str
     * @return
     */
    public static boolean checkPositiveDecimals(String str){
        return checkStr(POSITIVE_DECIMALS, str);
    }

    /**
     *  中国邮编
     * @param str
     * @return
     */
    public static boolean checkPostCode(String str){
        return checkStr(POSTCODE, str);
    }

    /**
     *  空白字符 包括：空格、\t、\n、\r、\f、\x0B
     * @param str
     * @return
     */
    public static boolean checkBlankSpace(String str){
        return checkStr(BLANKSPACE, str);
    }

    /**
     *  IP 地址 (简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param str
     * @return
     */
    public static boolean checkIpAddress(String str){
        return checkStr(IP_ADDRESS, str);
    }
    //endregion

    //region 取满足正则的结果

    /**
     * 根据网址，获取域名(二级域名)
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
    }

    /**
     * 根据网址，获取域名(顶级域名) 比如：baidu.com
     * @param url
     * @return
     */
    public static String getTopLevelDomain(String url) {
        url = url.replace("http://","https://");
        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
    }

    /**
     * 根据身份证获取性别
     * @param idCard
     * @return
     */
    public static String getSexByIdCard(String idCard) {
        String sex = "保密";
        if (18 == idCard.length()) {
            if (Integer.parseInt(idCard.substring(16, 17)) % 2 == 0) {
                sex = "女";
            } else {
                sex = "男";
            }
        } else if (15 == idCard.length()) {
            if (Integer.parseInt(idCard.substring(14, 15)) % 2 == 0) {
                sex = "女";
            } else {
                sex = "男";
            }
        }
        return sex;
    }

    //endregion

    public static void main(String[] args) {
        System.out.println(checkPassword("是中人国"));
    }
}
