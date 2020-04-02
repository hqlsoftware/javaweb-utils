package com.heqilin.util;

import com.heqilin.util.model.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * @author heqilin
 * date:  2019-01-18 ok
 **/
public class SystemUtil {

    private SystemUtil() {
        throw new AssertionError();
    }

    //region 系统属性的静态枚举

    /**
     * 系统属性的静态枚举
     **/
    public static enum TYPE {
        /**
         * 1.8.0_101
         **/
        JAVA_VERSION("java.version"),

        /**
         * 1.8.0_101
         **/
        JAVA_RUNTIME_VERSION("java.runtime.version"),

        /**
         * C:\javaweb\jdk1.8.0_101_64\jre
         **/
        JAVA_HOME("java.home"),
        /**
         * E:\7.ProjectCodeSource\jdbc\target\test-classes;
         **/
        JAVA_CLASS_PATH("java.class.path"),
        /**
         * Windows 10 sun.jnu.encoding=GBK
         **/
        OS_NAME("os.name"),
        /**
         * 10.0
         **/
        OS_VERSION("os.version"),
        /**
         * \
         **/
        FILE_SEPARATOR("file.separator"),
        /**
         * \n
         **/
        LINE_SEPARATOR("line.separator"),
        /**
         * C:\Users\Administrator
         **/
        USER_HOME("user.home"),
        /**
         * Administrator
         **/
        USER_NAME("user.name"),

        /**
         * mixed mode
         **/
        JAVA_VM_INFO("java.vm.info"),
        /**
         * E:\7.ProjectCodeSource\jdbc
         **/
        USER_DIR("user.dir");
        /**
         * 枚举描述
         **/
        private String description;

        TYPE(String description) {
            this.description = description;
        }
    }
    //endregion

    //region 获取系统属性

    /**
     * 是否是windows系统
     **/
    public static boolean isWindowsSystem() {
        String os = System.getProperty(TYPE.OS_NAME.description);
        if (os.toLowerCase().startsWith("win")) {
            return true;
        }
        return false;
    }

    /**
     * 获取换行符（windows/linux系统换行符不一样）
     **/
    public static String getLineSeparator() {
        return System.getProperty(TYPE.LINE_SEPARATOR.description);
    }


    /**
     * 获取系统属性
     **/
    public static String getSystemProperty(TYPE type) {
        return System.getProperty(type.description);
    }

    //endregion

    //region 获取jar包根目录 (编译后的classes文件夹下)

    /**
     * 获取jar包根目录 (编译后的classes文件夹下)
     **/
    public static String getProjectClassesPath() {
        String result = SystemUtil.class
                .getResource("/")
                .getPath();
        return fixFilepath(result);
    }

    /**
     * 修复路径-系统版本不同+测试环境区别
     * 暂时没有找到好的方法
     **/
    private static String fixFilepath(String filepath) {
        //fix windows系统，路径前面带/问题
        filepath = StringUtil.removePrefixString(filepath, "/");
        filepath = filepath.replace("target/test-classes", "target/classes");
        filepath = filepath.replace("%20", " ");
        try {
            filepath = URLDecoder.decode(filepath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return filepath;
    }
    //endregion

    //region  获取当前根目录

    public static String getProjectPath() {
        return getSystemProperty(TYPE.USER_DIR) + File.separator;
    }

    //endregion

    /**
     * 获取项目名称
     *
     * @return project_name
     */
    public static String getProjectName() {
        String classesPath = getProjectClassesPath();
        // java
        String rootPath = "";
        if (classesPath.endsWith("build/classes/")) {
            rootPath = classesPath.replace("build/classes/", "");
        } else if (classesPath.endsWith("WEB-INF/classes/")) {
            // java web
            rootPath = classesPath.replace("WEB-INF/classes/", "");
        }
        rootPath += "__";
        rootPath = rootPath.replace("/__", "");
        rootPath = rootPath.replaceAll("/", "/__");
        int index = rootPath.lastIndexOf("/__");
        if (index == -1) {
            return "";
        }
        return rootPath.substring(index + 3);
    }

    //region 获取my.utils配置文件路径

    /**
     * 获取 my.util jar包的配置文件路径
     * classpath下查找 my.util.properties
     **/
    public static String getMyUtilConfigPath() {
        String path = getProjectClassesPath() + Constants.MYUTILS_CONFIG_PROPERTIES_URL;
        boolean existFile = FileUtil.exist(path);
        if (existFile) {
            return path;
        } else {
            LogUtil.error("my.util.properties配置文件没有找到");
            return null;
        }
    }

    /**
     * 获取UEditor路径地址
     *
     * @return
     */
    public static String getUEditorConfigPath() {
        String path = StringUtil.format("{0}{1}{2}{1}config.json",
                getProjectClassesPath(), File.separator, "ueditor");
        boolean existFile = FileUtil.exist(path);
        if (existFile) {
            return path;
        } else {
            LogUtil.error("ueditor\\config.json配置文件没有找到");
            return null;
        }
    }
    //endregion

    //region 获取类名称和方法名称

    /**
     * 获取类名称（静态）
     **/
    public static String getClassName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return Thread.currentThread().getStackTrace()[elements.length - 1].getClassName();
    }

    /**
     * 获取方法名称（静态）
     **/
    public static String getMethodName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return Thread.currentThread().getStackTrace()[elements.length - 1].getMethodName();
    }
    //endregion

    /**
     * Returns the path to the system temporary directory
     *
     * @return
     */
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取默认的上传文件夹目录
     *
     * @return
     */
    public static String getDefaultUploadPath() {
        Properties prop = PropUtil.getProp(getMyUtilConfigPath());
        return prop.getProperty("my.util.plugin.storage.uploadPath");
    }

    /**
     * 获取默认的映射路径（存储到本地其它目录的时候）
     * 提供给 registry.addResourceHandler("/"+SystemUtil.getDefaultUploadMapperLocalDiskFolder()+"/**").addResourceLocations("file:"+ SystemUtil.getDefaultUploadPath());
     *
     * @return
     */
    public static String getDefaultUploadMapperLocalDiskFolder() {
        String uploadPath = getDefaultUploadPath();
        if (uploadPath.endsWith(File.separator)) {
            uploadPath = uploadPath.substring(0, uploadPath.length() - 1);
        }
        return uploadPath.substring(uploadPath.lastIndexOf(File.separator) + 1);
    }
}