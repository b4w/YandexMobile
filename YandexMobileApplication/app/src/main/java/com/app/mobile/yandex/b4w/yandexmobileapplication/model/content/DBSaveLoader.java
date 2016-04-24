package com.app.mobile.yandex.b4w.yandexmobileapplication.model.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.content.Loader;

import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util.StringUtils;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;

import java.util.List;

/**
 * Created by KonstantinSysoev on 24.04.16.
 */
public class DBSaveLoader extends Loader<Cursor> {

    private DBSaveTask saveTask;
    private List<Artist> artists;
    private Context context;

    public DBSaveLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
        if (artists != null && !artists.isEmpty()) {
            saveTask = new DBSaveTask(context.getContentResolver());
            saveTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, artists);
        }
    }

    @Override
    public void stopLoading() {
        super.stopLoading();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    private void getResultFromTask (Cursor cursor) {
        deliverResult(cursor);
    }

    class DBSaveTask extends AsyncTask<List<Artist>, Void, Cursor> {

        private ContentResolver contentResolver;

        public DBSaveTask(ContentResolver contentResolver) {
            this.contentResolver = contentResolver;
        }

        @Override
        protected Cursor doInBackground(List<Artist>... params) {
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
                contentResolver.insert(YandexDBContentProvider.CONTENT_URI, contentValues);
            }
            return contentResolver.query(YandexDBContentProvider.CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            getResultFromTask(cursor);
        }
    }
}
