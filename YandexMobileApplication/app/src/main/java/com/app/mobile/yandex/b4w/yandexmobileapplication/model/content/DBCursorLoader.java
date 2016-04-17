package com.app.mobile.yandex.b4w.yandexmobileapplication.model.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

/**
 * Created by KonstantinSysoev on 17.04.16.
 */
public class DBCursorLoader extends CursorLoader {

//    AsyncTaskLoader<Cursor>

    private ContentResolver contentResolver;

    public DBCursorLoader(Context context, ContentResolver contentResolver) {
        super(context);
        this.contentResolver = contentResolver;
    }

    @Override
    public Cursor loadInBackground() {
        return contentResolver.query(YandexDBContentProvider.CONTENT_URI, null, null, null, null);
    }
}
