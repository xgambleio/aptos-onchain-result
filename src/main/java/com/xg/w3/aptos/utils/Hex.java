package com.xg.w3.aptos.utils;

public class Hex
{
    public static String hexFromByte(final byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; ++i) {
            sb.append(Integer.toHexString((data[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
    
    public static byte[] hexToByte(final String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }
        final byte[] number = new byte[data.length() / 2];
        for (int i = 0; i < data.length(); i += 2) {
            final int j = Integer.parseInt(data.substring(i, i + 2), 16);
            number[i / 2] = (byte)(j & 0xFF);
        }
        return number;
    }
    
    public static String hexFromAscii(final String value) {
        final int length = value.length();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            final int decimal = value.charAt(i);
            sb.append("&#x").append(Integer.toHexString(decimal)).append(";");
        }
        return sb.toString();
    }
    
    public static String hexToAscii(final String value) {
        final String[] valArray = value.split("&#x");
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valArray.length; ++i) {
            if (!"".equals(valArray[i])) {
                final int ch = Integer.parseInt(valArray[i].replaceAll(";$", ""), 16);
                sb.append((char)ch);
            }
        }
        return sb.toString();
    }
}
