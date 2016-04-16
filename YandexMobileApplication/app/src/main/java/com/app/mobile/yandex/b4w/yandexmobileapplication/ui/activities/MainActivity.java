package com.app.mobile.yandex.b4w.yandexmobileapplication.ui.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.data.db.IDBConstants;
import com.app.mobile.yandex.b4w.yandexmobileapplication.ui.fragments.ArtistFragment;
import com.app.mobile.yandex.b4w.yandexmobileapplication.ui.fragments.ArtistsFragment;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.pojo.Artist;

/**
 * Created by KonstantinSysoev on 29.03.16.
 * <p/>
 * ArtistsActivity - activity for display of the loaded list of actors.
 */
public class MainActivity extends AppCompatActivity implements ArtistsFragment.IOpenViewArtistCallback {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        updateToolbar();
        initArtistsFragment();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            updateToolbar();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update toolbar in artists_layout.
     */
    private void updateToolbar() {
        Log.d(TAG, "updateToolbar() started");
        if (toolbar == null) {
            // init toolbar if first run
            toolbar = (Toolbar) findViewById(R.id.app_toolbar);
            if (toolbar != null) {
                toolbar.setTitle(R.string.performers_capital);
                setSupportActionBar(toolbar);
            }
        } else {
            // update display toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbar.setTitle(R.string.performers_capital);
        }
        Log.d(TAG, "updateToolbar() done");
    }

    /**
     * First initialize ArtistsFragment for artists list.
     */
    private void initArtistsFragment() {
        Log.d(TAG, "initArtistsFragment() started");
        final FragmentManager fragmentManager = getFragmentManager();
        final ArtistsFragment fragment = ArtistsFragment.getInstance();
        fragmentManager.beginTransaction()
                .add(R.id.fragments_container, fragment)
                .addToBackStack(ArtistsFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "initArtistsFragment() done");
    }

    @Override
    public void openViewArtist(Artist artist) {
        final FragmentManager fragmentManager = getFragmentManager();
        final ArtistFragment fragment = ArtistFragment.getInstance();
        fragment.setArguments(getBundleForFragment(artist));
        fragmentManager.beginTransaction()
                .replace(R.id.fragments_container, fragment)
                .addToBackStack(ArtistFragment.class.getSimpleName())
                .commit();
    }

    /**
     * Fill bundle from artist.
     *
     * @param artist
     * @return fill Bundle();
     */
    private Bundle getBundleForFragment(Artist artist) {
        Log.d(TAG, "getBundleForFragment() started");
        Bundle bundle = new Bundle();
        bundle.putInt(IDBConstants.ID, artist.getId());
        bundle.putString(IDBConstants.NAME, artist.getName());
        bundle.putStringArray(IDBConstants.GENRES, artist.getGenres());
        bundle.putInt(IDBConstants.ALBUMS, artist.getAlbums());
        bundle.putInt(IDBConstants.TRACKS, artist.getTracks());
        bundle.putString(IDBConstants.LINK, artist.getLink());
        bundle.putString(IDBConstants.DESCRIPTION, artist.getDescription());
        bundle.putString(IDBConstants.COVER_SMALL, artist.getCover().getSmall());
        bundle.putString(IDBConstants.COVER_BIG, artist.getCover().getBig());
        Log.d(TAG, "getBundleForFragment() done");
        return bundle;
    }
}
