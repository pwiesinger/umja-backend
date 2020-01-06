package com.lab73.logic;

public class UppercaseUtil {
    public static boolean isUppercase(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) return false;
        }
        return true;
    }
}
