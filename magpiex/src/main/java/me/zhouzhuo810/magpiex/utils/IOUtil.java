package me.zhouzhuo810.magpiex.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/09
 *     desc  : 关闭相关工具类
 * </pre>
 */
public final class IOUtil {
    
    private IOUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 关闭 IO
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 安静关闭 IO
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
    
    /**
     * 复制IO
     */
    public static int copyStream(InputStream input, OutputStream output) throws IOException {
        long count = copyLargeStream(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }
    
    private static long copyLargeStream(InputStream input, OutputStream output) throws IOException {
        return copyLargeStream(input, output, new byte[4096]);
    }
    
    private static long copyLargeStream(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0L;
        int n;
        for (boolean var5 = false; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }
        return count;
    }
    
    public static byte[] inputStreamToByteArray(InputStream ins) {
        if (ins == null) {
            return null;
        }
        BufferedInputStream bis = new BufferedInputStream(ins);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[128];
            int n = -1;
            while ((n = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bos.toByteArray();
    }
}
