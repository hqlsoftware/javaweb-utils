package my.utils.model;

import java.io.File;

/**
 * 常量
 *
 * @author heqilin
 * date:  2019-01-15
 **/
public class Constants {

    //region 分隔符常量

    public static final String POINT_STR = ".";

    public static final String BLANK_STR = "";

    public static final String SPACE_STR = " ";

    public static final String NEWLINE_STR = "\n";

    public static final String SYS_SEPARATOR = File.separator;

    public static final String FILE_SEPARATOR = "/";

    public static final String BRACKET_LEFT = "[";

    public static final String BRACKET_RIGHT = "]";

    public static final String UNDERLINE = "_";

    public static final String MINUS_STR = "-";

    //endregion

    //region 编码格式

    public static final String UTF8 = "UTF-8";

    //endregion

    //region 文件后缀

    public static final String EXCEL_XLS = ".xls";

    public static final String EXCEL_XLSX = ".xlsx";

    public static final String IMAGE_PNG = "png";

    public static final String IMAGE_JPG = "jpg";

    public static final String FILE_ZIP = ".zip";

    public static final String FILE_GZ = ".gz";

    //endregion

    //region io流

    public static final int BUFFER_1024 = 1024;

    public static final int BUFFER_512 = 512;

    public static final String USER_DIR = "user.dir";

    //endregion

    //region tesseract for java语言字库

    public static final String ENG = "eng";

    public static final String CHI_SIM = "chi_sim";

    //endregion

    //region opencv

    public static final String OPENCV_LIB_NAME_246 = "libs/x64/opencv_java246";

    public static final String OPENCV_LIB_NAME_330 = "libs/x64/opencv_java330";

    //endregion

    //region my.utils相关
    public  static  final  String  MYUTILS_CONFIG_PROPERTIES_URL = "my/utils/config/my.utils.properties";
    public  static  final  String  MYUTILS_CONFIG_PROPERTIES_URL_EHCACHE = "my/utils/config/ehcache-config.xml";

    //endregion
    
    //region cms相关
    public static final String CACHE_SHOPPING_CART="my_cache_shoppingcart";
    public static final String CACHE_SMS_CODE="my_cache_smscode";
    public static final String CACHE_USER_INFO="my_cache_userinfo";
    public static final String CACHE_MANAGER_INFO="my_cache_managerinfo";
    
    //endregion
}
