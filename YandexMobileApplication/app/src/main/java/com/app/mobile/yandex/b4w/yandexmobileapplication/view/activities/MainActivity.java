package com.app.mobile.yandex.b4w.yandexmobileapplication.view.activities;

import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RelativeLayout;

import com.app.mobile.yandex.b4w.yandexmobileapplication.R;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.content.YandexDBContentProvider;
import com.app.mobile.yandex.b4w.yandexmobileapplication.model.db.IDBConstants;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.network.YandexRetrofitSpiceRequest;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.util.StringUtils;
import com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments.ArtistFragment;
import com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments.ArtistsFragment;
import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;
import com.app.mobile.yandex.b4w.yandexmobileapplication.view.fragments.OfficialSiteFragment;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by KonstantinSysoev on 29.03.16.
 * <p/>
 * ArtistsActivity - activity for display of the loaded list of actors.
 */
public class MainActivity extends BaseActivity implements ArtistsFragment.IOpenViewArtistCallback,
        ArtistFragment.IOpenOfficialSiteCallback {
    private static final String TAG = MainActivity.class.getSimpleName();

    private YandexRetrofitSpiceRequest yandexRetrofitSpiceRequest;
    private Toolbar toolbar;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);
        updateToolbar();
        initArtistsFragment();
        yandexRetrofitSpiceRequest = new YandexRetrofitSpiceRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSpiceManager().execute(yandexRetrofitSpiceRequest, "yandex", DurationInMillis.ONE_MINUTE,
                new YandexRequestListener());
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            super.onBackPressed();
        } else {
            // update toolbar only if transferred to artists list
            if (count == 2) updateToolbar();
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
                .add(R.id.fragments_container, fragment, ArtistsFragment.class.getSimpleName())
                .addToBackStack(ArtistsFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "initArtistsFragment() done");
    }

    /**
     * Open full view artist.
     *
     * @param artist for full view.
     */
    @Override
    public void openViewArtist(Artist artist) {
        final FragmentManager fragmentManager = getFragmentManager();
        final ArtistFragment fragment = ArtistFragment.getInstance();
        fragment.setArguments(getBundleForFragment(artist));
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_to_right, R.anim.slide_in_right, R.anim.slide_to_left)
                .replace(R.id.fragments_container, fragment, ArtistFragment.class.getSimpleName())
                .addToBackStack(ArtistFragment.class.getSimpleName())
                .commit();
    }

    /**
     * Open official site in browser.
     *
     * @param link to official page.
     */
    @Override
    public void openOfficialSiteLinkInBrowser(String link) {
        final FragmentManager fragmentManager = getFragmentManager();
        final OfficialSiteFragment fragment = OfficialSiteFragment.getInstance(link);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_to_right, R.anim.slide_in_right, R.anim.slide_to_left)
                .replace(R.id.fragments_container, fragment, OfficialSiteFragment.class.getSimpleName())
                .addToBackStack(OfficialSiteFragment.class.getSimpleName())
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

    /**
     * Async request listener for obtaining response.
     * Return List<Artist> or error.
     */
    public final class YandexRequestListener implements RequestListener<Artist.List> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(TAG, "onRequestFailure() started");
            Snackbar.make(relativeLayout, getString(R.string.robospice_error), Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "onRequestFailure() done");
        }

        @Override
        public void onRequestSuccess(Artist.List artists) {
            Log.d(TAG, "onRequestSuccess() started. Collect " + artists.size() + " elements.");
            ContentResolver contentResolver = getContentResolver();
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
                Uri urowUri = contentResolver.insert(YandexDBContentProvider.CONTENT_URI, contentValues);
//                getLoaderManager().getLoader(1).forceLoad();
                Log.i(TAG, "Insert into db artist name = " + artist.getName() + " id = " + artist.getId());
            }
            Snackbar.make(relativeLayout, getString(R.string.data_successfully_updated), Snackbar.LENGTH_LONG).show();
            Log.d(TAG, "onRequestSuccess() done");
        }
    }
}
