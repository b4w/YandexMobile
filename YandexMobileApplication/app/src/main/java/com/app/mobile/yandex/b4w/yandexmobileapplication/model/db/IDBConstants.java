package com.app.mobile.yandex.b4w.yandexmobileapplication.model.db;

/**
 * Created by KonstantinSysoev on 09.04.16.
 * <p/>
 * Interface of constants for DB and pojo.
 */
public interface IDBConstants {

    // Artist fields name
    String ID = "_id";
    String NAME = "name";
    String GENRES = "genres";
    String ALBUMS = "albums";
    String TRACKS = "tracks";
    String LINK = "link";
    String DESCRIPTION = "description";
    String COVER_SMALL = "cover_small";
    String COVER_BIG = "cover_big";
    String COVER_SMALL_PATH = "cover_small_path";
    String COVER_BIG_PATH = "cover_big_path";

    // DB constants
    String DATABASE_NAME = "yandex_mobile_db";
    int DATABASE_VERSION = 1;
    String TABLE_ARTISTS = "artists";
    String DROP_TABLE = "drop table if exists ";
    String CREATE_TABLE_ARTIST =
            "create table " + TABLE_ARTISTS + " (" +
                    ID + " integer PRIMARY KEY, " +
                    NAME + " text NOT NULL, " +
                    GENRES + " text NOT NULL, " +
                    ALBUMS + " integer NOT NULL, " +
                    TRACKS + " integer NOT NULL, " +
                    LINK + " text, " +
                    DESCRIPTION + " text NOT NULL, " +
                    COVER_SMALL + " text NOT NULL, " +
                    COVER_BIG + " text NOT NULL, " +
                    COVER_SMALL_PATH + " text, " +
                    COVER_BIG_PATH + " text " +
                    ");";
}
