package com.app.mobile.yandex.b4w.yandexmobileapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;

/**
 * Created by KonstantinSysoev on 06.04.16.
 */
public class ArtistFragment extends Fragment {

    private static final String TAG = ArtistFragment.class.getSimpleName();

    private ImageView coverBig;
    private TextView genres;
    private TextView albums;
    private TextView tracks;
    private TextView description;

    /**
     * Return new instance ArtistFragment.
     *
     * @return new ArtistFragment();
     */
    public static ArtistFragment getInstance() {
        return new ArtistFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_artist, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() started");
        coverBig = (ImageView) getActivity().findViewById(R.id.artist_cover_big);
        genres = (TextView) getActivity().findViewById(R.id.artist_genres);
        albums = (TextView) getActivity().findViewById(R.id.artist_albums);
        tracks = (TextView) getActivity().findViewById(R.id.artist_tracks);
        description = (TextView) getActivity().findViewById(R.id.artist_description);
        Log.d(TAG, "initXmlFields() done");
    }
}
