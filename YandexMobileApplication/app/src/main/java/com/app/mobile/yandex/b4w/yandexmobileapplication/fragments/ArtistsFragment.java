package com.app.mobile.yandex.b4w.yandexmobileapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.adapters.ArtistsAdapter;
import com.app.mobile.yandex.b4w.yandexmobileapplication.pojo.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 05.04.16.
 */
public class ArtistsFragment extends Fragment {

    private static final String TAG = ArtistsFragment.class.getSimpleName();

    private RecyclerView artists;
    private ArtistsAdapter artistsAdapter;
    private List<Artist> tmpMockArtistsList;

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
        loadData();
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
        initListItemsView();
    }

    /**
     * Load tmp data artists list.
     */
    private void loadData() {
        Log.d(TAG, "loadData() started");
        tmpMockArtistsList = new ArrayList<>();
        tmpMockArtistsList.add(new Artist(1080505, "Tove Lo", new String[]{"pop", "dance", "electronics"}, 81, 22,
                "http://www.tove-lo.com/", "description",
                "123",
                "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000"));
        tmpMockArtistsList.add(new Artist(2915, "Ne-Yo", new String[]{"rnb", "pop", "rap"}, 256, 152,
                "http://www.neyothegentleman.com/", "description",
                "",
                "http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000"));
        tmpMockArtistsList.add(new Artist(1080505, "Tove Lo", new String[]{"pop", "dance", "electronics"}, 81, 22,
                "http://www.tove-lo.com/", "description",
                null,
                "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000"));
        tmpMockArtistsList.add(new Artist(2915, "Ne-Yo", new String[]{"rnb", "pop", "rap"}, 256, 152,
                "http://www.neyothegentleman.com/", "description",
                "http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300",
                "http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000"));
        artistsAdapter = new ArtistsAdapter(tmpMockArtistsList);
        Log.d(TAG, "loadData() done");
    }

    /**
     * Initialize artists view list.
     */
    private void initListItemsView() {
        Log.d(TAG, "initListItemsView() started");
        artists = (RecyclerView) getActivity().findViewById(R.id.artists);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        artists.setLayoutManager(manager);
        artists.setAdapter(artistsAdapter);
        Log.d(TAG, "initListItemsView() done");
    }
}
