/*
 * Copyright (c) 2006-2008, Humpic Organization. All rights reserved.
 *
 * $HeadURL: http://humpic.googlecode.com/svn/trunk/humpic-framework/src/java/core/com/humpic/framework/helper/BytesHelper.java $
 * $Id: BytesHelper.java 24 2007-11-22 05:51:07Z subchen $
 *
 * This library is free. You can redistribute it and/or modify it.
 * Support by http://www.humpic.com/
 */
package com.figure.core.helper;

import com.figure.core.constant.Coder;
import org.springframework.util.Assert;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.CRC32;

public abstract class BytesHelper {
    static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将一个 long 按照字节顺序，转换成 byte[8]
     */
    public static byte[] getLongBytes(long val) {
        byte[] results = new byte[8];
        results[0] = (byte) (val >>> 56 & 0xff);
        results[1] = (byte) (val >>> 48 & 0xff);
        results[2] = (byte) (val >>> 40 & 0xff);
        results[3] = (byte) (val >>> 32 & 0xff);
        results[4] = (byte) (val >>> 24 & 0xff);
        results[5] = (byte) (val >>> 16 & 0xff);
        results[6] = (byte) (val >>> 8 & 0xff);
        results[7] = (byte) (val & 0xff);
        return results;
    }

    /**
     * 将一个 int 按照字节顺序，转换成 byte[4]
     */
    public static byte[] getIntBytes(int val) {
        byte[] results = new byte[4];
        results[0] = (byte) (val >>> 24 & 0xff);
        results[1] = (byte) (val >>> 16 & 0xff);
        results[2] = (byte) (val >>> 8 & 0xff);
        results[3] = (byte) (val & 0xff);
        return results;
    }

    /**
     * 将一个 short 按照字节顺序，转换成 byte[2]
     */
    public static byte[] getShortBytes(int val) {
        byte[] results = new byte[2];
        results[0] = (byte) (val >>> 8 & 0xff);
        results[1] = (byte) (val & 0xff);
        return results;
    }

    /**
     * int 转换成 byte
     */
    public static byte getByte(int val) {
        return (byte) (val & 0xff);
    }

    /**
     * 将一个 byte[8] 按照字节顺序，转换成 long
     */
    public static long bytesAsLong(byte[] bytes) {
        Assert.notNull(bytes);
        Assert.isTrue(bytes.length == 8);

        long val = 0;
        for (int i = 0; i < bytes.length; i++) {
            val = (val << 8) + (bytes[i] & 0xff);
        }
        return val;
    }

    /**
     * 将一个 byte[4] 按照字节顺序，转换成 int
     */
    public static int bytesAsInt(byte[] bytes) {
        Assert.notNull(bytes);
        Assert.isTrue(bytes.length == 4);

        int val = 0;
        for (int i = 0; i < bytes.length; i++) {
            val = (val << 8) + (bytes[i] & 0xff);
        }
        return val;
    }

    /**
     * 将一个 byte[2] 按照字节顺序，转换成 short
     */
    public static short bytesAsShort(byte[] bytes) {
        Assert.notNull(bytes);
        Assert.isTrue(bytes.length == 2);

        short val = 0;
        for (int i = 0; i < bytes.length; i++) {
            val = (short) ((val << 8) + (bytes[i] & 0xff));
        }
        return val;
    }

    /**
     * 将一个BOOLEA 按照字节转换
     */
    public static byte getBooleanByte(Boolean b) {
        return (byte) (b ? 0x01 : 0x00);
    }


    /**
     * 生成UUID值
     *
     * @return 返回UUID字符串
     */
    public static String genUUID() {
        String s = UUID.randomUUID().toString();
        s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
        return s;
    }

    /**
     * 将十六进制字符串转换成byte数组
     *
     * @param hex 十六进制字符串
     * @return byte数组
     */
    public static byte[] hexStringToByte(String hex) {
        int length = hex.length() / 2;
        byte[] bytes = new byte[length];
        byte[] source = hex.getBytes();

        for (int i = 0; i < bytes.length; ++i) {
            byte bh = Byte.decode(
                            "0x" + new String(new byte[]{source[i * 2]}))
                    .byteValue();
            bh = (byte) (bh << 4);
            byte bl = Byte.decode(
                            "0x" + new String(new byte[]{source[i * 2 + 1]}))
                    .byteValue();
            bytes[i] = (byte) (bh ^ bl);
        }

        return bytes;
    }

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     *
     * @param tmp 要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    public static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char[] str = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 读取一定长度字节转换成字符数组
     *
     * @throws IOException
     */
    public static char[] bytesToCharArray(int length, DataInputStream in)
            throws IOException {
        byte[] bs = new byte[length];
        in.read(bs);
        String str = new String(bs, Coder.CHARSET);
        return str.toCharArray();
    }

    /**
     * 读取一定长度字节转换成字符串
     */
    public static String bytesToString(int length, DataInputStream in)
            throws IOException {
        byte[] bs = new byte[length];
        in.read(bs);
        String str = new String(bs, Coder.CHARSET);
        return str;
    }

    public static String bytesToStringRemoveNullCh(int length, DataInputStream in)
            throws IOException {
        byte[] bs = new byte[length];
        in.read(bs);
        String str = new String(bs, Coder.CHARSET);
        return str.replace("\0", "");
    }

    /**
     * 将BYTE转换成CRC32码
     */
    public static int byteToCRC32(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        int crcCode = (int) crc32.getValue();
        return crcCode;
    }

    /**
     * 字符串后补0
     */
    public static String fillNullCh(String str, int length) {
        while (str.length() < length) {
            str += "\0";
        }
        return str;
    }

    /**
     * 字符前补0
     */
    public static String fillFrontNullCh(String str, int length) {
        StringBuffer sb = new StringBuffer();
        String nullCh = "";
        while (nullCh.length() < length - str.length()) {
            nullCh += "0";
        }
        sb.append(nullCh).append(str);
        return sb.toString();
    }

    /**
     * 说明：
     * 数组合并  ****注意byte的字节序问题，网络发送的顺序问题***
     *
     * @return
     */
    public static byte[] bytehe(byte[] byte1, byte[] byte2) {
        byte[] target = new byte[byte1.length + byte2.length];
        //	    int len = byte1.length + byte2.length;
        System.arraycopy(byte1, 0, target, 0, byte1.length);
        System.arraycopy(byte2, 0, target, byte1.length, byte2.length);
        return target;
    }


    /**
     * 说明： crc效验
     *
     * @return
     * @throws Exception
     */
    public static int mkCrc(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return (int) crc32.getValue();
    }

}