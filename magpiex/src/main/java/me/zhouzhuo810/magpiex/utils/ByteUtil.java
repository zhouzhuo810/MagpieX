package me.zhouzhuo810.magpiex.utils;

import java.nio.charset.Charset;

/**
 *
 * Created by zz on 2016/11/10.
 */
public class ByteUtil {

    /**
     * byte[] to string,  for print byte[].
     *
     * @param bytes byte[]
     * @return string str
     */
    public static String byteArrayToString(byte[] bytes) {
        String a = "";
        for (byte aByte : bytes) {
            a = a + " " + byteToHexString(aByte);
        }
        return a;
    }

    /**
     * byte to decimal string
     *
     * @param b byte , like 0x0A
     * @return decimal stirng , like 10
     */
    public static String byteToDecimalString(byte b) {
        return Integer.parseInt(byteToHexString(b), 16) + "";
    }

    /**
     * decimal string to byte
     *
     * @param decimalStr decimalStr , like 10
     * @return byte, like 0x0A
     */
    public static byte decStringToByte(String decimalStr) {
        int value = Integer.parseInt(decimalStr);
        return (byte)value;
    }

    /**
     * byte to hexString, 0x2B to 2B
     *
     * @param b byte
     * @return hexString
     */
    public static String byteToHexString(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
    }

    /**
     * byte[] to int
     *
     * @param b byte[] length = 4
     * @return int
     */
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    /**
     * int to byte[]
     *
     * @param value int
     * @return byte[] length = 4
     */
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * short to int
     *
     * @param s short
     * @return byte[] length = 2
     */
    public static byte[] shortToByteArray(short s) {
        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
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
    
    private static byte[] stringToBytes(String str, int start, int length) {
        String substring = str.substring(start, start + length);
        return substring.getBytes(Charset.forName("utf-8"));
    }
    
    private static byte[] appendBytes(byte[] old, byte[] news) {
        byte[] bytes = new byte[old.length + news.length];
        if (old.length > 0) {
            System.arraycopy(old, 0, bytes, 0, old.length);
        }
        System.arraycopy(news, 0, bytes, old.length, news.length);
        return bytes;
    }
}
