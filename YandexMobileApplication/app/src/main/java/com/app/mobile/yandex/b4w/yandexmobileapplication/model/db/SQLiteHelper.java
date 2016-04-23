package com.app.mobile.yandex.b4w.yandexmobileapplication.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by KonstantinSysoev on 10.04.16.
 * <p/>
 * Help class for access to database.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String TAG = SQLiteHelper.class.getSimpleName();

    public SQLiteHelper(Context context) {
        super(context, IDBConstants.DATABASE_NAME, null, IDBConstants.DATABASE_VERSION);
    }

    /**
     * Create new database "artists".
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate() started");
        db.execSQL(IDBConstants.CREATE_TABLE_ARTIST);
        Log.d(TAG, "onCreate() done");
    }

    /**
     * Upgrade old database "artists".
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() started");
        db.execSQL(IDBConstants.DROP_TABLE + IDBConstants.TABLE_ARTISTS);
        onCreate(db);
        Log.d(TAG, "onUpgrade() done");
    }
}
