package com.heqilin.util;

import java.io.File;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18 ok
 **/
public class FolderUtil {

    //region 文件夹常用操作(是否存在/创建/删掉/递归删掉/重命名/移动)

    /**
     * 判断是否存在文件夹
     **/
    public static boolean exist(String folderPath){
        File file = new File(folderPath);
        return file.exists() && file.isDirectory();
    }

    /**
     * 创建文件夹
     **/
    public static boolean create(String folderPath){
        File file=new File(folderPath);
        try {
            if (!file.exists()) {
                file.mkdir();
                return true;
            }
            else{
                if(!file.isDirectory()){
                    file.mkdir();
                    return true;
                }
            }
        } catch (Exception ex) {
            LogUtil.error(null,ex);
        }
        return false;
    }

    /**
     * 删除文件夹
     **/
    public static boolean delete(String folderPath){
        File file=new File(folderPath);
        try{
            deleteAllFile(folderPath);
            if(file.isDirectory()&&file.exists()) {
                return file.delete();
            }
        }
        catch (Exception ex){
            LogUtil.error(null,ex);
        }
        return false;
    }

    /**
     * 删除文件夹下的所有文件/及文件夹
     **/
    protected static boolean deleteAllFile(String folderPath) {
        boolean flag = false;
        File file = new File(folderPath);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (folderPath.endsWith(File.separator)) {
                temp = new File(folderPath + tempList[i]);
            } else {
                temp = new File(folderPath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteAllFile(folderPath + "/" + tempList[i]);//先删除文件夹里面的文件
                delete(folderPath + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    //endregion
}
