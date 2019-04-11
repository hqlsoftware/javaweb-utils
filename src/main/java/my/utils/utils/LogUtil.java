package my.utils.utils;

import ch.qos.logback.core.joran.spi.JoranException;
import lombok.extern.slf4j.Slf4j;
import my.utils.plugin.log.LogBackConfigLoader;

import java.io.File;
import java.io.IOException;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18
 **/

@Slf4j
public class LogUtil {

    static {
//        try {
//            LogBackConfigLoader.load(LogUtil.class.getClassLoader().getResource("logback-spring.xml").getPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JoranException e) {
//            e.printStackTrace();
//        }
    }

    //region 往文件中写入日志
    /**
     * 往文件中写入日志
     **/
    public static void writeLog(String content, String logName,String filePath)
    {
        if (StringUtil.isEmpty(filePath)) {
            filePath = RequestUtil.getWebRoot(null) + "logs" + File.separator;
        }
        filePath = filePath +File.separator;
        if(!FolderUtil.exist(filePath))
            FolderUtil.create(filePath);
        FileUtil.writeFile(filePath+logName+".txt",content);
    }
    //endregion

    //region log 内置方法

    /**
     * info
     **/
    public static void info(String message,Object... args){
        log.info(message,args);
    }

    /**
     * warm
     **/
    public static void warm(String message,Object... args){
        log.warn(message,args);
    }

    /**
     * error
     **/
    public static void error(String message,Object... args){
        log.error(message,args);
    }

    /**
     * debug
     **/
    public static void debug(String message,Object... args){
        log.debug(message,args);
    }

    /**
     * trace
     **/
    public static void trace(String message,Object... args){
        log.trace(message,args);
    }
    //endregion

}
