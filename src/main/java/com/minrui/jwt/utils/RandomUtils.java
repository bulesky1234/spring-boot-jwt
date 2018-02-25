package com.minrui.jwt.utils;

import java.util.Random;

public class RandomUtils {
    private static char[] nchars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

   private static char[] numbs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String getStr6() {
     return getStr(6,nchars);
    }
    public static String getStr4() {
        return getStr(4,nchars);
    }

    public static String getNumbs4() {
        return getStr(4,numbs);
    }

    public static String getStr(int size,char[] chars) {
        Random rand = new Random();
        for (int i = chars.length; i > 0; i--) {
            int index = rand.nextInt(i);
            char tmp = chars[index];
            chars[index] = chars[i - 1];
            chars[i - 1] = tmp;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(chars[i]);
        }
        return result.toString();
    }
}
