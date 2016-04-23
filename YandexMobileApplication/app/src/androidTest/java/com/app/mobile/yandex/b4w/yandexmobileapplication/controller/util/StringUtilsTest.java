package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by KonstantinSysoev on 23.04.16.
 * <p/>
 * JUnit test StringUtils.
 */
public class StringUtilsTest extends TestCase {

    private String[] albums;
    private String[] tracks;
    private String[] arrayOne;
    private String[] arrayTwo;

    @Before
    public void setUp() throws Exception {
        albums = new String[]{"альбом", "альбома", "альбомов"};
        tracks = new String[]{"песня", "песни", "песен"};
        arrayOne = new String[]{"1", "2", "3", "4", "5"};
        arrayTwo = new String[]{"123", "2341", "321", "543"};
    }

    @After
    public void tearDown() throws Exception {
        albums = null;
        tracks = null;
        arrayOne = null;
        arrayTwo = null;
    }

    @Test
    public void testGetStringFromStringArray() throws Exception {
        assertEquals(StringUtils.getStringFromStringArray(arrayOne), "1, 2, 3, 4, 5");
        assertEquals(StringUtils.getStringFromStringArray(arrayTwo), "123, 2341, 321, 543");
    }

    @Test
    public void testGetWordEnding() throws Exception {
        assertEquals(StringUtils.getWordEnding(1, albums), "альбом");
        assertEquals(StringUtils.getWordEnding(2, albums), "альбома");
        assertEquals(StringUtils.getWordEnding(5, albums), "альбомов");

        assertEquals(StringUtils.getWordEnding(1, tracks), "песня");
        assertEquals(StringUtils.getWordEnding(2, tracks), "песни");
        assertEquals(StringUtils.getWordEnding(5, tracks), "песен");
    }
}