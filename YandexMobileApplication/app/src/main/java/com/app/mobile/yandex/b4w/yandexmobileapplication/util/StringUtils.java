package com.app.mobile.yandex.b4w.yandexmobileapplication.util;

import android.support.annotation.NonNull;

/**
 * Created by KonstantinSysoev on 10.04.16.
 */
public class StringUtils {

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
}
