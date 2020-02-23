package com.heqilin.util;

import lombok.extern.slf4j.Slf4j;
import java.io.File;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18
 **/

@Slf4j
public class LogUtil {
    //region 往文件中写入日志
    /**
     * 往文件中写入日志
     **/
    public static void writeLog(String content, String logName,String filePath)
    {
        if (StringUtil.isEmpty(filePath)) {
            filePath = RequestUtil.getWebRoot(null)+ File.separator + "logs" + File.separator;
        }else{
        filePath = filePath +File.separator;
    }
        if(!FolderUtil.exist(filePath))
            FolderUtil.create(filePath);
        FileUtil.writeFile(filePath+logName+".txt",content);
    }
    public static void writeLog(String content, String logName)
    {
        writeLog(content,logName,null);
    }
    //endregion

    //region log 内置方法

    /**
     * trace 占位符号 {0},{1} 要这种风格才可以被识别
     **/
    public static void trace(String message,Object... args){
        if(log.isTraceEnabled()){
            log.trace(StringUtil.format(message,args));
        }
    }

    /**
     * debug 占位符号 {0},{1} 要这种风格才可以被识别
     **/
    public static void debug(String message,Object... args){
        if(log.isDebugEnabled()){
            log.debug(StringUtil.format(message,args));
        }
    }

    /**
     * info 占位符号 {0},{1} 要这种风格才可以被识别
     **/
    public static void info(String message,Object... args){
        if(log.isInfoEnabled()){
            log.info(StringUtil.format(message,args));
        }
    }

    /**
     * warm 占位符号 {0},{1} 要这种风格才可以被识别
     **/
    public static void warm(String message,Object... args){
        if(log.isWarnEnabled()){
            log.warn(StringUtil.format(message,args));
        }
    }

    /**
     * error 占位符号 {0},{1} 要这种风格才可以被识别
     **/
    public static void error(String message,Object... args){
        if(log.isErrorEnabled()){
            log.error(StringUtil.format(message,args));
        }
    }

    //endregion

    public static void main(String[] args) {
        System.out.println(SystemUtil.getProjectClassesPath());
        System.out.println(SystemUtil.getMyUtilConfigPath());
        System.out.println(SystemUtil.getProjectPath());
        info("aaaa:{0},affafds:{1}","heqilin","china");
    }

}
