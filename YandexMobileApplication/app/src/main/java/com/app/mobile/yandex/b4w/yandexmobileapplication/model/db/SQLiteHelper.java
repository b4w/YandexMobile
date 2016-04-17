package com.app.mobile.yandex.b4w.yandexmobileapplication.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util.StringUtils;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 10.04.16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String TAG = SQLiteHelper.class.getSimpleName();
    private Context context;

    public SQLiteHelper(Context context) {
        super(context, IDBConstants.DATABASE_NAME, null, IDBConstants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate() started");
        db.execSQL(IDBConstants.CREATE_TABLE_ARTIST);
        Log.d(TAG, "onCreate() done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() started");
        db.execSQL(IDBConstants.DROP_TABLE + IDBConstants.TABLE_ARTISTS);
        onCreate(db);
        Log.d(TAG, "onUpgrade() done");
    }

//    public void open() {
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//    }

    public void insertArtistsInDB(List<Artist> artists) {
        Log.d(TAG, "insertArtistsInDB() started");
        final SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Artist artist : artists) {
            contentValues.put(IDBConstants.ID, artist.getId());
            contentValues.put(IDBConstants.NAME, artist.getName());
            contentValues.put(IDBConstants.GENRES, artist.getGenres().length > 0 ?
                    StringUtils.getStringFromStringArray(artist.getGenres()) : "");
            contentValues.put(IDBConstants.ALBUMS, artist.getAlbums());
            contentValues.put(IDBConstants.TRACKS, artist.getTracks());
            contentValues.put(IDBConstants.LINK, artist.getLink() != null ? artist.getLink() : "");
            contentValues.put(IDBConstants.DESCRIPTION, artist.getDescription());
            contentValues.put(IDBConstants.COVER_SMALL, artist.getCover().getSmall());
            contentValues.put(IDBConstants.COVER_BIG, artist.getCover().getBig());
            contentValues.put(IDBConstants.COVER_SMALL_PATH, "");
            contentValues.put(IDBConstants.COVER_BIG_PATH, "");
            sqLiteDatabase.insert(IDBConstants.TABLE_ARTISTS, null, contentValues);
            Log.i(TAG, "Insert into db artist name = " + artist.getName() + " id = " + artist.getId());
        }
        Log.d(TAG, "insertArtistsInDB() done");
    }

    public List<Artist> getArtists() {
        Log.d(TAG, "getArtists() started");
        List<Artist> result = new ArrayList<>();
        final SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + IDBConstants.TABLE_ARTISTS, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
//            result.add(new Artist());
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        Log.d(TAG, "getArtists() done");
        return result;
    }
}
