package com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util.StringUtils;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Cover;
import com.squareup.picasso.Picasso;

/**
 * Created by KonstantinSysoev on 06.04.16.
 */
public class ArtistFragment extends Fragment {

    public interface IOpenOfficialSiteCallback {
        void openOfficialSiteLinkInBrowser(String link);
    }

    private static final String TAG = ArtistFragment.class.getSimpleName();

    private ImageView coverBig;
    private TextView genres;
    private TextView albums;
    private TextView tracks;
    private TextView description;
    private TextView link;
    private Artist artist;
    private IOpenOfficialSiteCallback iOpenOfficialSiteCallback;

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            iOpenOfficialSiteCallback = (IOpenOfficialSiteCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IOpenOfficialSiteCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_artist, container, false);
        if (getArguments() != null) {
            setArgumentValues(getArguments());
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        setArtistValues();
        initToolbar();
        initListeners();
    }

    /**
     * Initialize xml fields on view.
     */
    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() started");
        coverBig = (ImageView) getActivity().findViewById(R.id.artist_cover_big);
        genres = (TextView) getActivity().findViewById(R.id.artist_genres);
        albums = (TextView) getActivity().findViewById(R.id.artist_albums);
        tracks = (TextView) getActivity().findViewById(R.id.artist_tracks);
        description = (TextView) getActivity().findViewById(R.id.artist_description);
        link = (TextView) getActivity().findViewById(R.id.artist_link);
        Log.d(TAG, "initXmlFields() done");
    }

    /**
     * Set values xml fields from artist.
     */
    private void setArtistValues() {
        Log.d(TAG, "setArtistValues() started");
        // TODO: заменить picasso
        final Resources resources = getActivity().getResources();
        Picasso.with(getActivity().getApplicationContext())
                .load(artist.getCover().getBig())
                .placeholder(R.drawable.load_holder)
                .fit()
                .centerCrop()
                .into(coverBig);
        genres.setText(StringUtils.getStringFromStringArray(artist.getGenres()));
        albums.setText(String.format(resources.getString(R.string.album_message_artist), artist.getAlbums(),
                StringUtils.getWordEnding(artist.getAlbums(), StringUtils.ALBUMS)));
        tracks.setText(String.format(resources.getString(R.string.track_message), artist.getTracks(),
                StringUtils.getWordEnding(artist.getTracks(), StringUtils.TRACKS)));
        description.setText(artist.getDescription());
        link.setText((artist.getLink() != null && !artist.getLink().isEmpty())
                ? getString(R.string.official_site) + "\n" + artist.getLink()
                : "");
        Log.d(TAG, "setArtistValues() done");
    }

    /**
     * Loading data from input arguments, return new artist.
     *
     * @param bundle
     */
    private void setArgumentValues(Bundle bundle) {
        Log.d(TAG, "setArgumentValues() started");
        artist = new Artist();
        artist.setId(bundle.getInt(IDBConstants.ID));
        artist.setName(bundle.getString(IDBConstants.NAME));
        artist.setGenres(bundle.getStringArray(IDBConstants.GENRES));
        artist.setAlbums(bundle.getInt(IDBConstants.ALBUMS));
        artist.setTracks(bundle.getInt(IDBConstants.TRACKS));
        artist.setLink(bundle.getString(IDBConstants.LINK));
        artist.setDescription(bundle.getString(IDBConstants.DESCRIPTION));
        Cover cover = new Cover();
        cover.setSmall(bundle.getString(IDBConstants.COVER_SMALL));
        cover.setBig(bundle.getString(IDBConstants.COVER_BIG));
        artist.setCover(cover);
        Log.d(TAG, "setArgumentValues() done");
    }

    /**
     * Initialize toolbar. Set title and back arrow.
     */
    private void initToolbar() {
        Log.d(TAG, "initToolbar() started");
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(getActivity(), R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(ContextCompat.getColor(getActivity(), R.color.textToolbarHeading), PorterDuff.Mode.SRC_ATOP);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(artist.getName());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Log.d(TAG, "initToolbar() done");
    }

    /**
     * Initialize listeners for ArtistFragment.
     */
    private void initListeners() {
        Log.d(TAG, "initListeners() started");
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOpenOfficialSiteCallback.openOfficialSiteLinkInBrowser(artist.getLink());
            }
        });
        Log.d(TAG, "initListeners() done");
    }
}
