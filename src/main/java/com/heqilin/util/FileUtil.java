package com.heqilin.util;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18 ok
 **/
public class FileUtil {

    private FileUtil(){
        throw new AssertionError();
    }

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
            sb.append(str + System.lineSeparator());
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
        catch(IOException ex)
        {
            LogUtil.error(null,ex);
        }
    }
    //endregion

    //region commons-io-2.4.jar (copyURLToFile功能)
    public static void copyURLToFile(final URL source, final File destination) throws IOException {
        copyInputStreamToFile(source.openStream(), destination);
    }
    protected static void copyInputStreamToFile(final InputStream source, final File destination) throws IOException {
        try (InputStream in = source) {
            copyToFile(in, destination);
        }
    }
    protected static void copyToFile(final InputStream source, final File destination) throws IOException {
        try (InputStream in = source;
             OutputStream out = openOutputStream(destination)) {
            copy(in, out);
        }
    }
    protected static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }
    protected static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    public static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }
    protected static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, DEFAULT_BUFFER_SIZE);
    }
    protected static long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }
    protected static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    //endregion

    //region 网络文件下载

    /**
     * 下载文件---返回下载后的文件存储路径
     *
     * @param url 文件地址
     * @param dir 存储目录
     * @param fileName 存储文件名
     * @return 文件路径
     */
    public static String downloadHttpUrl(String url, String dir, String fileName) {
        try {
            if(StringUtil.isEmpty(dir)){
                dir = System.getProperty("user.dir");
                dir = dir + File.separator + "upload";
                dir =dir + File.separator + DateUtil.format(new Date(),"yyyyMMdd");
            }
            if(StringUtil.isEmpty(fileName)){
                fileName = url.split("/")[url.split("/").length-1];
            }
            URL httpurl = new URL(url);
            File dirfile = new File(dir);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
            copyURLToFile(httpurl, new File(dir+File.separator+fileName));
            return dir+File.separator+fileName;
        } catch (Exception ex) {
            LogUtil.error(null,ex);
            return "";
        }
    }
    /**
     * 下载文件---返回下载后的文件存储路径(默认保存在根目录下的/upload/yyyy-MM-dd/)
     *
     * @param url 文件地址
     * @param fileName 存储文件名
     * @return 文件路径
     */
    public static String downloadHttpUrl(String url, String fileName) {
        return downloadHttpUrl(url,null,fileName);
    }
    /**
     * 下载文件---返回下载后的文件存储路径(默认保存在根目录下的/upload/yyyy-MM-dd/)
     *
     * @param url 文件地址
     * @return 文件路径
     */
    public static String downloadHttpUrl(String url) {
        return downloadHttpUrl(url,null,null);
    }

    //endregion

    //region 读取txt文件内容

    /**
     * 读取txt内容
     * @param filePath
     * @return
     */
    public static String getFileContent(String filePath) {
        File file = new File(filePath);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result.append(s + System.lineSeparator());
            }
            br.close();
        } catch (Exception ex) {
            LogUtil.error(null,ex);
        }
        return result.toString();
    }
    //endregion

}
