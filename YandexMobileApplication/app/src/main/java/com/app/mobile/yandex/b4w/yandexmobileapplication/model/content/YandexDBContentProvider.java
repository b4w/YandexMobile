package com.app.mobile.yandex.b4w.yandexmobileapplication.model.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.SQLiteHelper;

import java.sql.SQLException;

/**
 * Created by KonstantinSysoev on 17.04.16.
 * ContentProvider for YandexDB data.
 */
public class YandexDBContentProvider extends ContentProvider {

    private static final String TAG = YandexDBContentProvider.class.getSimpleName();
    public static final Uri CONTENT_URI = Uri.parse("content://com.b4w.provider.yandexmobileapplication/elements");
    private static final int ALL_ROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final UriMatcher URI_MATCHER;

    private SQLiteHelper sqLiteHelper;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI("content://com.b4w.provider.yandexmobileapplication", "elements", ALL_ROWS);
        URI_MATCHER.addURI("content://com.b4w.provider.yandexmobileapplication", "elements/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate() started");
        sqLiteHelper = new SQLiteHelper(getContext());
        Log.d(TAG, "onCreate() done");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query() started");
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(IDBConstants.TABLE_ARTISTS);

        String groupBy = null;
        String having = null;

        switch (URI_MATCHER.match(uri)) {
            case ALL_ROWS:
                // TODO: добавить реализацию!
                break;
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                sqLiteQueryBuilder.appendWhere(IDBConstants.ID + "=" + rowID);
                break;
            default:
                Log.w(TAG, "YandexDBContentProvider. Not correct request to data base!");
                break;
        }
        Cursor cursor = sqLiteQueryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
//        cursor.setNotificationUri();
        Log.d(TAG, "query() done");
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType() started");
        String resultType;
        switch (URI_MATCHER.match(uri)) {
            case ALL_ROWS:
                resultType = "vnd.android.cursor.dir/vnd.b4w/elements";
                break;
            case SINGLE_ROW:
                resultType = "vnd.android.cursor.item/vnd.b4w/elements";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Log.d(TAG, "getType() done");
        return resultType;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert() started");
        String nullColumnHack = null;
        // при обновлении значения отдается тот же uri, который пришел или заменяется новым.
        Uri insertedId = uri;
        int numberOfRows = 0;
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

        try {
            long id = db.insertOrThrow(IDBConstants.TABLE_ARTISTS, nullColumnHack, values);
            if (id > -1) {
                insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
                // TODO: добавить сюда observer вместо null???
                if (getContext() != null) {
                    getContext().getContentResolver().notifyChange(insertedId, null);
                }
            }
            // TODO: добавить закрытие database?
            db.close();
        } catch (SQLiteConstraintException e) {
            numberOfRows = update(uri, values, null, null);
        }

        if (numberOfRows == 0) {
            Log.e(TAG, "It wasn't succeeded to update value in the database! Id = "
                    + values.getAsString(IDBConstants.ID));
        }
//        getContext().getContentResolver().notifyChange(uri, null, false);
        Log.d(TAG, "insert() done");
        return insertedId;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete() started");
        // TODO: Надо реализовывать?
        Log.d(TAG, "delete() done");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update() started");
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        return db.update(IDBConstants.TABLE_ARTISTS, values, IDBConstants.ID + "=?",
                new String[]{values.getAsString(IDBConstants.ID)});
    }
}
