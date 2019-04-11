package my.utils.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18 ok
 **/
public class FileUtil {

    //region 文件的常用操作 （创建/删除/重命名/是否存在）
    /**
     * 创建文件
     **/
    public static boolean create(String filePath)
    {
        File file=new File(filePath);
        try {
            if (!file.exists()) {
                return file.createNewFile();
            }
        } catch (IOException ex) {
            LogUtil.error(null,ex);
        }
        return false;
    }

    /**
     * 删除文件
     **/
    public static boolean delete(String filePath)
    {
        File file=new File(filePath);
        try{
            //判断是否是文件 并且存在
            if(file.isFile()&&file.exists()) {
                return file.delete();
            }
        }
        catch (Exception ex){
            LogUtil.error(null,ex);
        }
        return false;
    }

    /**
     * 重命名文件
     **/
    public static boolean rename(String oldPath,String newPath)
    {
        File oldFile=new File(oldPath);
        return oldFile.renameTo(new File(newPath));
    }

    /**
     * 判断文件是否存在
     **/
    public static boolean exist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    //endregion

    //region 写入文件
    /**
     * 写入文件,末尾自动添加换行
     * utf-8  追加
     * @param path
     * @param str
     */
    public static void writeFile(String path, String str)
    {
        try
        {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file); //true表示追加
            StringBuffer sb = new StringBuffer();
            sb.append(str + SystemUtil.getSystemProperty(SystemUtil.TYPE.LINE_SEPARATOR));
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }
    //endregion

}
