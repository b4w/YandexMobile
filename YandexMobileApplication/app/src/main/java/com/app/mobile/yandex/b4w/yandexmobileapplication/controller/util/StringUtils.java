package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util;

import android.support.annotation.NonNull;

/**
 * Created by KonstantinSysoev on 10.04.16.
 */
public class StringUtils {

    public static final String[] ALBUMS = {"альбом", "альбома", "альбомов"};
    public static final String[] TRACKS = {"песня", "песни", "песен"};

    @NonNull
    public static String getStringFromStringArray(String[] inputArray) {
        StringBuilder sb = new StringBuilder();
        sb.append(inputArray[0]);
        if (inputArray.length > 1) {
            for (int i = 1; i < inputArray.length; i++) {
                sb.append(", ");
                sb.append(inputArray[i]);
            }
        }
        return sb.toString();
    }

    @NonNull
    public static String getWordEnding(int number, String... inputArray) {
        String result;
        number %= 100;
        if (number >= 11 && number <= 19) {
            result = inputArray[2];
        } else {
            number %= 10;
            switch (number) {
                case 1:
                    result = inputArray[0];
                    break;
                case 2:
                case 3:
                case 4:
                    result = inputArray[1];
                    break;
                default:
                    result = inputArray[2];
            }
        }
        return result;
    }
}
