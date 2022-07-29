package com.goodsoft.infra.mediator.utils;

public class Utils
{
    public static String firstCharToLowerCase(String str) {

        if(str == null || str.length() == 0)
            return "";

        if(str.length() == 1)
            return str.toLowerCase();

        char[] chArr = str.toCharArray();
        chArr[0] = Character.toLowerCase(chArr[0]);

        return new String(chArr);
    }
}
