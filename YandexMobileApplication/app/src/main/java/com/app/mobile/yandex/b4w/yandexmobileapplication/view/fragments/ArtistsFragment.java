package com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.adapters.ArtistsCursorAdapter;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.content.DBCursorLoader;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;


/**
 * Created by KonstantinSysoev on 05.04.16.
 * <p/>
 * Class fragment for display list artists.
 */
public class ArtistsFragment extends Fragment {

    /**
     * Interface for opening of the complete information on the artist.
     */
    public interface IOpenViewArtistCallback {
        void openViewArtist(Artist artist);
    }

    private static final String TAG = ArtistsFragment.class.getSimpleName();
    public static final int LOADER_ID = 1;

    private RecyclerView artists;
    private IOpenViewArtistCallback iOpenViewArtistCallback;
    private ArtistsCursorAdapter artistsCursorAdapter;

    /**
     * Return new instance ArtistsFragment.
     *
     * @return new ArtistsFragment();
     */
    public static ArtistsFragment getInstance() {
        return new ArtistsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistsCursorAdapter = new ArtistsCursorAdapter(new ArtistsCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Artist artist) {
                iOpenViewArtistCallback.openViewArtist(artist);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // restarting Loader for loading data from database
        getLoaderManager().restartLoader(LOADER_ID, null, new LoaderCallbackCursor());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            iOpenViewArtistCallback = (IOpenViewArtistCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IOpenViewArtistCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        artists = (RecyclerView) getActivity().findViewById(R.id.artists);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        artists.setLayoutManager(manager);
        artists.setAdapter(artistsCursorAdapter);
    }

    /**
     * Getter cursor adapter for swapCursor();
     *
     * @return
     */
    public ArtistsCursorAdapter getArtistsCursorAdapter() {
        return artistsCursorAdapter;
    }

    /**
     * Class for callbacks DBCursorLoader after data loading.
     */
    class LoaderCallbackCursor implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new DBCursorLoader(getActivity(), getActivity().getContentResolver());
        }

        /**
         * Loading new data from database is complete.
         * Update artists adapter.
         *
         * @param loader
         * @param data
         */
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "onLoadFinished() started");
            artistsCursorAdapter.swapCursor(data);
            Log.d(TAG, "onLoadFinished() done");
        }

        /**
         * At loading data from database end with error.
         *
         * @param loader
         */
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.d(TAG, "onLoaderReset() started");
            artistsCursorAdapter.swapCursor(null);
            Snackbar.make(getActivity().findViewById(R.id.main_relative_layout),
                    getString(R.string.error_for_loading_data), Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "onLoaderReset() done");
        }
    }
}
