package com.xg.w3.aptos.utils;

import com.xg.w3.aptos.utils.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardUtils {

    public static Map<Integer, String> cards = new HashMap<>();
    public static String[] mc = new String[]{"H", "D", "C", "S"};

    public static void main(String[] args) {
        List<Integer> ls = new ArrayList<>();
        for (int i = 0; i < 52; ++i) {
            ls.add(i);
        }
        List<String> lsString = convertCards(ls);
        System.out.println(JSONUtil.serialize(lsString));

        System.out.println(convertCard2Value("0H"));
        List<Integer> lsInt = convertCards2Value(lsString);
        System.out.println(JSONUtil.serialize(lsInt));
    }

    public static String convertCard(int c) {
        return ((int) (c / 4) + 1) + mc[c % 4];
    }

    public static List<String> convertCards(List<Integer> ls) {
        List<String> rs = new ArrayList<>();
        ls.forEach(i -> {
            rs.add(convertCard(i));
        });
        return rs;
    }

    public static int convertCard2Value(String strCard) {
        byte b = -1;
        int multi = 0;
        strCard = strCard.trim();
        if (strCard.length() == 0) {
            return -1;
        }
        int cardValue = Integer.parseInt(strCard.substring(0, strCard.length() - 1));
        String cardType = strCard.substring(strCard.length() - 1);
        switch (cardType) {
            case "J":
                multi = 4;
                if (cardValue == 16) {
                    return 52;
                }
                if (cardValue == 17) {
                    return 53;
                }
                break;
            case "H":
                multi = 0;
                break;
            case "D":
                multi = 1;
                break;
            case "C":
                multi = 2;
                break;
            case "S":
                multi = 3;
                break;
        }

        return (cardValue - 1) * 4 + multi;
    }
    
    public static List<Integer> convertCards2Value(List<String> ls) {
        List<Integer> rs = new ArrayList<>();
        ls.forEach(i -> {
            rs.add(convertCard2Value(i));
        });
        return rs;
    }
}
