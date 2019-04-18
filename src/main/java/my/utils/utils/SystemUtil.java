package my.utils.utils;

import my.utils.model.Constants;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18 ok
 **/
public class SystemUtil {

    //region 系统属性的静态枚举

    /**
     * 系统属性的静态枚举
     **/
    public static enum TYPE{
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
    public static boolean isWindowsSystem(){
        String os = System.getProperty(TYPE.OS_NAME.description);
        if(os.toLowerCase().startsWith("win")){
            return true;
        }
        return false;
    }

    /**
     * 获取换行符（windows/linux系统换行符不一样）
     **/
    public static String getLineSeparator(){
        return System.getProperty(TYPE.LINE_SEPARATOR.description);
    }


    /**
     * 获取系统属性
     **/
    public static String getSystemProperty(TYPE type){
        return System.getProperty(type.description);
    }

    //endregion

    //region 获取jar包根目录 (编译后的classes文件夹下)
    /**
     * 获取jar包根目录 (编译后的classes文件夹下)
     **/
    public static String getProjectClassesPath(){
        String result = SystemUtil.class
                .getResource("/")
                .getPath();
        return fixFilepath(result);
    }

    /**
     * 修复路径-系统版本不同+测试环境区别
     * 暂时没有找到好的方法
     **/
    private static String fixFilepath(String filepath)
    {
        //fix windows系统，路径前面带/问题
        filepath = StringUtil.removePrefixString(filepath,"/");
        filepath = filepath.replace("target/test-classes","target/classes");
        filepath = filepath.replace("%20"," ");
        return filepath;
    }
    //endregion

    //region 获取my.utils配置文件路径
    /**
     * 获取 my.utils jar包的配置文件路径
     * 开发环境：在resources文件根目录下 可以新建my.util.properties文件，若没有，则为java/my/utils/config/my.util.properties文件
     **/
    public static String getMyUtilConfigPath_EhCache(){
        String javaClassPath = getProjectClassesPath();
        boolean existFile = FileUtil.exist(javaClassPath +"ehcache-config.xml");
        if(existFile)
            return getProjectClassesPath()+"ehcache-config.xml";
        return javaClassPath  + Constants.MYUTILS_CONFIG_PROPERTIES_URL_EHCACHE;
    }
    //endregion

    //region 获取my.utils配置文件路径
    /**
     * 获取 my.utils jar包的配置文件路径
     * 开发环境：在resources文件根目录下 可以新建my.util.properties文件，若没有，则为java/my/utils/config/my.util.properties文件
     **/
    public static String getMyUtilConfigPath(){
        String javaClassPath = getProjectClassesPath();
        boolean existFile = FileUtil.exist(javaClassPath +"my.utils.properties");
        if(existFile)
            return javaClassPath+"my.utils.properties";
        return javaClassPath + Constants.MYUTILS_CONFIG_PROPERTIES_URL;
    }
    //endregion

    //region 获取类名称和方法名称
    /**
     * 获取类名称（静态）
     **/
    public static String getClassName(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return Thread.currentThread().getStackTrace()[elements.length-1].getClassName();
    }
    /**
     * 获取方法名称（静态）
     **/
    public static String getMethodName(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        return Thread.currentThread().getStackTrace()[elements.length-1].getMethodName();
    }
    //endregion

    public static void main(String[] args) {
        System.out.println(getProjectClassesPath());
        System.out.println(getMyUtilConfigPath());
        for (Object obj:System.getProperties().keySet()) {
            System.out.println("" + obj + "=" + System.getProperties().get(obj));
        }
    }
}