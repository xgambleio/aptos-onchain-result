package com.xg.w3.aptos.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

public class Utils {

    static final Random random = new Random(System.currentTimeMillis());

    public static String randomString(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public static byte[] convertStringListToByteArray(List<String> stringList) {
        List<Byte> byteList = new ArrayList<>();

        for (String str : stringList) {
            // Get the length of the string as a single byte
            byteList.add((byte) str.length());

            // Convert each character of the string to bytes and add to the byte list
            for (char c : str.toCharArray()) {
                byteList.add((byte) c);
            }
        }

        // Convert List<Byte> to byte[]
        byte[] byteArray = new byte[byteList.size() + 1];
        byteArray[0] = (byte) stringList.size();
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i + 1] = byteList.get(i);
        }

        return byteArray;
    }

    public static List<Integer> buildCards(int packNum) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < packNum; ++i) {
            for (int cardNo = 0; cardNo < 52; ++cardNo) {
                result.add(cardNo);
            }
        }
        return result;
    }

    public static List<Integer> getShuffleCards(int packNum) {
        return getShuffleCards(packNum, true);
    }

    public static List<Integer> getShuffleCards(int packNum, boolean shuffle) {
        List<Integer> result = buildCards(packNum);
        if (shuffle) {
            result = shuffle(result);
        } else {
            Collections.shuffle(result, random);
        }

        return result;
    }

    public static List<Integer> getRandomBet(int from, int to, int count) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            result.add(from + random.nextInt(to - from));
        }
        return result;
    }

    public static List<Integer> shuffle(List<Integer> card) {
        Integer[] array = (Integer[]) card.toArray(new Integer[card.size()]);
        for (int i = array.length - 1; i > 0; i--) {
            //Random for remaining positions.
            int index = random.nextInt(i + 1);

            //swapping the elements
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        card = Arrays.asList(array);
        return card;
    }
    
    public static String toMD5(final String text) {
        if (text == null) {
            return "";
        }
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            final byte[] resultByte = messageDigest.digest(text.getBytes());
            return Hex.hexFromByte(resultByte);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
