package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util;

import android.support.annotation.NonNull;

/**
 * Created by KonstantinSysoev on 10.04.16.
 * <p/>
 * Class for supplementary methods.
 */
public class StringUtils {

    public static final String[] ALBUMS = {"альбом", "альбома", "альбомов"};
    public static final String[] TRACKS = {"песня", "песни", "песен"};

    /**
     * Get string partitioned by commas from string array for storage in database.
     *
     * @param inputArray
     * @return new String();
     */
    @NonNull
    public static String getStringFromStringArray(String... inputArray) {
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

    /**
     * Get the correct termination of the word.
     *
     * @param number
     * @param inputArray
     * @return String();
     */
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
