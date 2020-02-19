package com.heqilin.util;

import com.heqilin.util.model.Constants;
import com.heqilin.util.plugin.idGenerator.SnowflakeIdWorker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class RandomUtil {

    /**
     * 静态随机数
     */
    private static Random random = new Random();

    /**
     * 获取雪花id(唯一，可做主键，long类型)
     **/
    public static long createSnowflakeId(){
        SnowflakeIdWorker instance =  new SnowflakeIdWorker(0,0);
        return instance.nextId();
    }

    /**
     * 创建UUID
     **/
    public static String createUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 创建32位UUID，去掉中间的-符号
     **/
    public static String createUUID32(){
        return UUID.randomUUID().toString().replace(Constants.MINUS_STR, Constants.BLANK_STR);
    }

    /**
     * 创建16位易识别的唯一码(yyMMddHHmmss)最后4位是随机码
     **/
    public static String createDateTimeRandom(){
        return LocalDateUtil.format(LocalDateTime.now(),"yyMMddHHmmss")
                +createRandom(4,TYPE.NUMBER);
    }

    //region 随机类型枚举 + 私有方法
    /**
     * 随机类型枚举
     */
    public static enum TYPE {
        /**
         * 字符型
         */
        LETTER,
        /**
         * 大写字符型
         */
        CAPITAL,
        /**
         * 数字型
         */
        NUMBER,
        /**
         * 符号型
         */
        SIGN,
        /**
         * 大+小字符 型
         */
        LETTER_CAPITAL,
        /**
         * 小字符+数字 型
         */
        LETTER_NUMBER,
        /**
         * 大+小字符+数字 型
         */
        LETTER_CAPITAL_NUMBER,
        /**
         * 大+小字符+数字+符号 型
         */
        LETTER_CAPITAL_NUMBER_SIGN
    }

    private static String[] lowercase = {
            "a","b","c","d","e","f","g","h","j","k",
            "m","n","p","q","r","s","t","u","v","w","x","y","z"};

    private static String[] capital = {
            "A","B","C","D","E","F","G","H","J","K",
            "M","N","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private static String[] number = {
            "2","3","4","5","6","7","8","9"};

    private static String[] sign = {
            "~","!","@","#","$","%","^","&","*","(",")","_","+","`","-","=",
            "{","}","|",":","\"","<",">","?",
            "[","]","\\",";","'",",",".","/"};
    //endregion

    //region 生成随机数
    /**
     * 生成随机数
     * @param num 位数
     * @param type 类型
     * @return String
     * <p>字符型 LETTER,</p>
     * <p>大写字符型 CAPITAL,</p>
     * <p>数字型 NUMBER,</p>
     * <p>符号型 SIGN,</p>
     * <p>大+小字符型 LETTER_CAPITAL,</p>
     * <p>小字符+数字 型 LETTER_NUMBER,</p>
     * <p>大+小字符+数字 型 LETTER_CAPITAL_NUMBER,</p>
     * <p>大+小字符+数字+符号 型 LETTER_CAPITAL_NUMBER_SIGN</p>
     */
    public static String createRandom(int num,TYPE type){
        ArrayList<String> temp = new ArrayList<String>();
        StringBuffer code = new StringBuffer();
        if(type == TYPE.LETTER){
            temp.addAll(Arrays.asList(lowercase));
        }else if(type == TYPE.CAPITAL){
            temp.addAll(Arrays.asList(capital));
        }else if(type == TYPE.NUMBER){
            temp.addAll(Arrays.asList(number));
        }else if(type == TYPE.SIGN){
            temp.addAll(Arrays.asList(sign));
        }else if(type == TYPE.LETTER_CAPITAL){
            temp.addAll(Arrays.asList(lowercase));
            temp.addAll(Arrays.asList(capital));
        }else if(type == TYPE.LETTER_NUMBER){
            temp.addAll(Arrays.asList(lowercase));
            temp.addAll(Arrays.asList(number));
        }else if(type == TYPE.LETTER_CAPITAL_NUMBER){
            temp.addAll(Arrays.asList(lowercase));
            temp.addAll(Arrays.asList(capital));
            temp.addAll(Arrays.asList(number));
        }else if(type == TYPE.LETTER_CAPITAL_NUMBER_SIGN){
            temp.addAll(Arrays.asList(lowercase));
            temp.addAll(Arrays.asList(capital));
            temp.addAll(Arrays.asList(number));
            temp.addAll(Arrays.asList(sign));
        }
        for (int i = 0; i < num; i++) {
            code.append(temp.get(random.nextInt(temp.size())));
        }
        return code.toString();
    }
    //endregion

    public static void  main(String[] args){
        System.out.println(createSnowflakeId());
        System.out.println(createUUID());
        System.out.println(createRandom(6,TYPE.NUMBER));
        System.out.println(createDateTimeRandom());
        System.out.println(createUUID32());
    }


}
