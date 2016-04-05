package com.app.mobile.yandex.b4w.yandexmobileapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.app.mobile.yandex.b4w.yandexmobileapplication.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.adapter.ArtistsAdapter;
import com.app.mobile.yandex.b4w.yandexmobileapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 29.03.16.
 * <p/>
 * ArtistsActivity - activity for display of the loaded list of actors.
 */
public class ArtistsActivity extends Activity {
    private static final String TAG = ArtistsActivity.class.getSimpleName();
    public static final int LAYOUT = R.layout.artists_layout;

    private Toolbar toolbar;
    private RecyclerView artists;
    private ArtistsAdapter artistsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        initToolbar();
        initListItemsView();
    }

    /**
     * Initialize toolbar in artists_layout.
     */
    private void initToolbar() {
        Log.d(TAG, "initToolbar() started");
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle(R.string.performers_capital);
        Log.d(TAG, "initToolbar() done");
    }

    /**
     * Initialize artists view list.
     */
    private void initListItemsView() {
        Log.d(TAG, "initListItemsView() started");
        artists = (RecyclerView) findViewById(R.id.artists);
        artistsAdapter = new ArtistsAdapter(getMockArtistList());
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        artists.setLayoutManager(manager);
        artists.setAdapter(artistsAdapter);
    }

    /**
     * Get temp data artists list.
     *
     * @return List<Artist>.
     */
    private List<Artist> getMockArtistList() {
        List<Artist> artists = new ArrayList<>();
        artists.add(new Artist(1080505, "Tove Lo", new String[]{"pop", "dance", "electronics"}, 81, 22,
                "http://www.tove-lo.com/", "description",
                "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300",
                "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000"));
        artists.add(new Artist(2915, "Ne-Yo", new String[]{"rnb", "pop", "rap"}, 256, 152,
                "http://www.neyothegentleman.com/", "description",
                "http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300",
                "http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000"));
        artists.add(new Artist(1080505, "Tove Lo", new String[]{"pop", "dance", "electronics"}, 81, 22,
                "http://www.tove-lo.com/", "description",
                "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300",
                "http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000"));
        artists.add(new Artist(2915, "Ne-Yo", new String[]{"rnb", "pop", "rap"}, 256, 152,
                "http://www.neyothegentleman.com/", "description",
                "http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300",
                "http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000"));
        return artists;
    }
}
