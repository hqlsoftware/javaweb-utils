package com.heqilin.util.core;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Date;

/**
 * FileUtil类
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

    /**
     * 将InputStream写入本地文件
     * @param destination 写入本地目录
     * @param inputStream	输入流
     * @throws IOException
     */
    public static void writeFile(String destination, InputStream inputStream)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = inputStream.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        inputStream.close();
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

    /**
     * Moves a file.
     * <p>
     * When the destination file is on another file system, do a "copy and delete".
     *
     * @param srcFile  the file to be moved
     * @param destFile the destination file
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if the destination file exists
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs moving the file
     * @since 1.4
     */
    public static void moveFile(final File srcFile, final File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
        if (destFile.exists()) {
            throw new IOException("Destination '" + destFile + "' already exists");
        }
        if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }
        final boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                FileUtil.deleteQuietly(destFile);
                throw new IOException("Failed to delete original file '" + srcFile +
                        "' after copy to '" + destFile + "'");
            }
        }
    }

    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (final Exception ignored) {
        }

        try {
            return file.delete();
        } catch (final Exception ignored) {
            return false;
        }
    }
    public static void cleanDirectory(final File directory) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                forceDelete(file);
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }
    private static File[] verifiedListFiles(final File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            final boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }
    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        if (!isSymlink(directory)) {
            cleanDirectory(directory);
        }

        if (!directory.delete()) {
            final String message =
                    "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }
    public static boolean isSymlink(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        return Files.isSymbolicLink(file.toPath());
    }
    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }
    public static void copyFile(final File srcFile, final File destFile,
                                final boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        final File parentFile = destFile.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            }
        }
        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }
    private static void checkFileRequirements(final File src, final File dest) throws FileNotFoundException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
    }
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
            throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        try (FileInputStream fis = new FileInputStream(srcFile);
             FileChannel input = fis.getChannel();
             FileOutputStream fos = new FileOutputStream(destFile);
             FileChannel output = fos.getChannel()) {
            final long size = input.size(); // TODO See IO-386
            long pos = 0;
            long count = 0;
            while (pos < size) {
                final long remain = size - pos;
                count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
                final long bytesCopied = output.transferFrom(input, pos, count);
                if (bytesCopied == 0) { // IO-385 - can happen if file is truncated after caching the size
                    break; // ensure we don't loop forever
                }
                pos += bytesCopied;
            }
        }

        final long srcLen = srcFile.length(); // TODO See IO-386
        final long dstLen = destFile.length(); // TODO See IO-386
        if (srcLen != dstLen) {
            throw new IOException("Failed to copy full contents from '" +
                    srcFile + "' to '" + destFile + "' Expected length: " + srcLen + " Actual: " + dstLen);
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }
    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;
    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;
    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 30;
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
                dir = dir + File.separator + "storage";
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
     * 下载文件---返回下载后的文件存储路径(默认保存在根目录下的/storage/yyyy-MM-dd/)
     *
     * @param url 文件地址
     * @param fileName 存储文件名
     * @return 文件路径
     */
    public static String downloadHttpUrl(String url, String fileName) {
        return downloadHttpUrl(url,null,fileName);
    }
    /**
     * 下载文件---返回下载后的文件存储路径(默认保存在根目录下的/storage/yyyy-MM-dd/)
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
            // 构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            // 使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {
                result.append(s + System.lineSeparator());
            }
            br.close();
        } catch (Exception ex) {
            LogUtil.error(null,ex);
        }
        return StringUtil.removeSuffixString(result.toString(),System.lineSeparator());
    }
    //endregion

    /**
     * 获取Java临时文件目录
     * @return
     */
    public static File getTempDirectory() {
        return new File(SystemUtil.getTempDirectoryPath());
    }

    /**
     * 获取Java上传文件目录
     * @return
     */
    public static File getDefaultUploadPath() {
        return new File(SystemUtil.getDefaultUploadPath());
    }

}
