package com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        final View view = inflater.inflate(R.layout.fragment_artists, container, false);
        return view;
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
     * Class for callbacks DBCursorLoader after data loading.
     */
    class LoaderCallbackCursor implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new DBCursorLoader(getActivity(), getActivity().getContentResolver());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            artistsCursorAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            artistsCursorAdapter.swapCursor(null);
        }
    }
}
