package me.zhouzhuo810.magpiex.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * 文件操作工具类
 */
public class FileUtil {
    
    private FileUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 创建文件夹
     *
     * @param dirPath 文件夹路径
     * @return 是否成功
     */
    public static boolean mkDirs(String dirPath) {
        if (dirPath == null) {
            return false;
        }
        File dir = new File(dirPath);
        return dir.exists() || dir.mkdirs();
    }
    
    /**
     * 文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static boolean deleteFile(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        return deleteFile(file);
    }
    
    /**
     * 删除文件
     *
     * @param file 文件
     */
    public static boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File childFile : files) {
                    deleteFile(childFile);
                }
            }
            return false;
        } else {
            return file.exists() && file.delete();
        }
    }
    
    
    public static void saveFile(InputStream is, String dir, String fileName) {
        saveFile(is, dir + File.separator + fileName);
    }
    
    public static void saveFile(InputStream is, String filePath) {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }
    
    /**
     * 随机生产文件名
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }
    
    
    /**
     * 保存字符串为文件
     */
    public static String saveStringAsFile(String content, String path, String filename) throws Exception {
        // 创建目录
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 读取文件流并保持在指定路径
        String mPath = path + File.separator + filename;
        OutputStream outputStream = new FileOutputStream(mPath);
        
        byte[] buffer = ByteUtil.stringToBytes(content, 20 * 1000);
        outputStream.write(buffer);
        outputStream.flush();
        outputStream.close();
        return filename;
    }
    
    
    public static boolean copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null)
                return false;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }
    
    private static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copyLarge(input, output, new byte[4096]);
    }
    
    private static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0L;
        int n;
        for (boolean var5 = false; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }
        return count;
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    
    public static long getAllFileSize(File dir) {
        if (!dir.exists()) {
            return 0;
        }
        int count = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            count += file.length();
        }
        return count;
    }
    
    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }
    
    /**
     * Return the string in file.
     *
     * @param filePath The path of file.
     * @return the string in file
     */
    public static String readFile2String(final String filePath) {
        return readFile2String(getFileByPath(filePath), null);
    }
    
    /**
     * Return the string in file.
     *
     * @param filePath    The path of file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }
    
    /**
     * Return the string in file.
     *
     * @param file The file.
     * @return the string in file
     */
    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }
    
    
    public static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Return the name of file.
     *
     * @param filePath The path of file.
     * @return the name of file
     */
    public static String getFileName(final String filePath) {
        if (isSpace(filePath))
            return "";
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }
    
    /**
     * Return the string in file.
     *
     * @param file        The file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    public static String readFile2String(final File file, final String charsetName) {
        byte[] bytes = readFile2BytesByStream(file);
        if (bytes == null)
            return null;
        if (isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }
 
    /**
     * Return the bytes in file by stream.
     *
     * @param file The file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExists(file))
            return null;
        try {
            return is2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }
    
    
    /**
     * Return the bytes in file by channel.
     *
     * @param file The file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!isFileExists(file))
            return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0))
                    break;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }
    
    private static byte[] is2Bytes(final InputStream is) {
        if (is == null)
            return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] b = new byte[8192];
            int len;
            while ((len = is.read(b, 0, 8192)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
    
    public static boolean createDirIfNotExist(String path) {
        if (path == null) {
            return false;
        }
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }
    
    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null)
            return false;
        if (file.exists())
            return file.isFile();
        if (!createOrExistsDir(file.getParentFile()))
            return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static byte[] stringToBytes(String str, int strLen) {
        if (strLen == 0) {
            return null;
        }
        int length = str.length();
        int size = length / strLen;
        int left = length % strLen;
        int start = 0;
        byte[] newBytes = new byte[0];
        for (int i = 0; i < size; i++) {
            byte[] bytes = stringToBytes(str, start, strLen);
            newBytes = appendBytes(newBytes, bytes);
            start += strLen;
        }
        if (left > 0) {
            byte[] bytes = stringToBytes(str, start, left);
            newBytes = appendBytes(newBytes, bytes);
        }
        return newBytes;
    }
    
    public static byte[] stringToBytes(String str, int start, int length) {
        String substring = str.substring(start, start + length);
        return substring.getBytes(Charset.forName("utf-8"));
    }
    
    public static byte[] appendBytes(byte[] old, byte[] news) {
        byte[] bytes = new byte[old.length + news.length];
        if (old.length > 0) {
            System.arraycopy(old, 0, bytes, 0, old.length);
        }
        System.arraycopy(news, 0, bytes, old.length, news.length);
        return bytes;
    }
    
}
