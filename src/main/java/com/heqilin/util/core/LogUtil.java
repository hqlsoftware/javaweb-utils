package com.heqilin.util.core;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.util.function.BiConsumer;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18
 **/

@Slf4j
public class LogUtil {

    private LogUtil(){
        throw new AssertionError();
    }

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

    private static void handleArguments(BiConsumer<String,Throwable> throwableConsumer,String message, Object... args){
        if(args!=null && args.length>0){
            message = StringUtil.format(message,args);
            if(args[args.length-1] instanceof Throwable){
                throwableConsumer.accept(message,(Throwable) args[args.length-1]);
            }else{
                throwableConsumer.accept(message,null);
            }
        }else{
            throwableConsumer.accept(message,null);
        }
    }

    /**
     * trace 占位符号 {0},{1} 要这种风格才可以被识别，注意最后一个参数如果是 throwable ,则会输出异常堆栈
     **/
    public static void trace(String message,Object... args){
        if(log.isTraceEnabled()){
            handleArguments((msg,throwable)->{
                log.trace(msg,throwable);
            },message,args);
        }
    }

    /**
     * debug 占位符号 {0},{1} 要这种风格才可以被识别，注意最后一个参数如果是 throwable ,则会输出异常堆栈
     **/
    public static void debug(String message,Object... args){
        if(log.isDebugEnabled()){
            handleArguments((msg,throwable)->{
                log.debug(msg,throwable);
            },message,args);
        }
    }

    /**
     * info 占位符号 {0},{1} 要这种风格才可以被识别，注意最后一个参数如果是 throwable ,则会输出异常堆栈
     **/
    public static void info(String message,Object... args){
        if(log.isInfoEnabled()){
            handleArguments((msg,throwable)->{
                log.info(msg,throwable);
            },message,args);
        }
    }

    /**
     * warm 占位符号 {0},{1} 要这种风格才可以被识别，注意最后一个参数如果是 throwable ,则会输出异常堆栈
     **/
    public static void warn(String message,Object... args){
        if(log.isWarnEnabled()){
            handleArguments((msg,throwable)->{
                log.warn(msg,throwable);
            },message,args);
        }
    }

    /**
     * error 占位符号 {0},{1} 要这种风格才可以被识别，注意最后一个参数如果是 throwable ,则会输出异常堆栈
     **/
    public static void error(String message,Object... args){
        if(log.isErrorEnabled()){
            handleArguments((msg,throwable)->{
                log.error(msg,throwable);
            },message,args);
        }
    }

    //endregion

}
