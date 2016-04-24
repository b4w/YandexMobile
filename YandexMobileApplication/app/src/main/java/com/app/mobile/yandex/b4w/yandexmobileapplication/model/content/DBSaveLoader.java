package com.app.mobile.yandex.b4w.yandexmobileapplication.model.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ProgressBar;

import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util.StringUtils;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;

import java.util.List;

/**
 * Created by KonstantinSysoev on 24.04.16.
 * <p/>
 * Class Loader for save data from yandex json link in background thread.
 */
public class DBSaveLoader extends Loader<Cursor> {

    private DBSaveTask saveTask;
    private List<Artist> artists;
    private Context context;
    private ProgressBar progressBar;

    public DBSaveLoader(Context context, ProgressBar progressBar) {
        super(context);
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
        if (artists != null && !artists.isEmpty()) {
            saveTask = new DBSaveTask(context.getContentResolver());
            saveTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, artists);
        }
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    private void getResultFromTask(Cursor cursor) {
        deliverResult(cursor);
    }

    class DBSaveTask extends AsyncTask<List<Artist>, Integer, Cursor> {

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
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            progressBar.setVisibility(View.INVISIBLE);
            getResultFromTask(cursor);
        }
    }
}
