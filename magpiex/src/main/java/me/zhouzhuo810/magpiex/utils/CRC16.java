package me.zhouzhuo810.magpiex.utils;


/**
 * CRC-CCITT (XModem)
 */
public class CRC16 {

    public static byte[] CRC_XModem(byte[] bytes) {
        byte[] r = new byte[bytes.length + 2];
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;
        for (int index = 0; index < bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        byte[] crcByte = ByteUtil.shortToByteArray((short) crc);
        System.arraycopy(bytes, 0, r, 0, bytes.length);
        r[bytes.length] = crcByte[0];
        r[bytes.length + 1] = crcByte[1];
        return r;
    }

    // 测试
    public static void main(String[] args) {
        // 0x02 05 00 03 FF 00 , crc16=7C 09
        byte[] crc = CRC16.CRC_XModem(new byte[]{0x55, 0x33, 0x01, 0x3E});
        System.out.println(ByteUtil.byteArrayToString(crc));
    }


}

