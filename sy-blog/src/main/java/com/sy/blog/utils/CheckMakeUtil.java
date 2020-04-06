package com.sy.blog.utils;

public class CheckMakeUtil {
    public static String RoNum(int num) {
        if (num <= 0) {
            return "FuckYou";
        }
        String result = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 26 + 97);
            result = result + (char) intVal;
        }
        System.out.println(result);
        return result;
    }
}
