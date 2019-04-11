package my.utils.utils;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class PropUtil {
    /**
     * 解析properties文件
     * @param filePath
     * 为空时，获取当前my.utils配置文件路径
     * @return
     */
    public static Properties getProp(String filePath){
        try {
            filePath = fixFilepath(filePath);
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            return getProp(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Properties();
    }

    /**
     * 解析properties文件
     * @param in
     * @return
     */
    public static Properties getProp(InputStream in){
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return props;
    }


    /**
     * 写入properties信息
     * @param filePath
     * @param map
     */
    public static void setProp(String filePath, Map<String, String> map){
        filePath = fixFilepath(filePath);
        Properties props = getProp(filePath);
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            map.forEach(props::setProperty);
            props.store(fos, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 暂时修复路径-自定义
     **/
    private static String fixFilepath(String filepath)
    {
        //fix windows系统，路径前面带/问题
        filepath = StringUtil.removePrefixString(filepath,"/");
        filepath = filepath.replace("target/test-classes","target/classes");
        return filepath;
    }
}
