package com.app.mobile.yandex.b4w.yandexmobileapplication.model.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by KonstantinSysoev on 17.04.16.
 * <p/>
 * Cursor loader for loading data from database.
 */
public class DBCursorLoader extends CursorLoader {

    private ContentResolver contentResolver;

    public DBCursorLoader(Context context, ContentResolver contentResolver) {
        super(context);
        this.contentResolver = contentResolver;
    }

    /**
     * Get data in background thread.
     *
     * @return cursor on data.
     */
    @Override
    public Cursor loadInBackground() {
        return contentResolver.query(YandexDBContentProvider.CONTENT_URI, null, null, null, null);
    }
}
