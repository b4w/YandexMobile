package com.app.mobile.yandex.b4w.yandexmobileapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by KonstantinSysoev on 29.03.16.
 * <p/>
 * ArtistsActivity - activity for display of the loaded list of actors.
 */
public class ArtistsActivity extends Activity {
    private static final String TAG = ArtistsActivity.class.getSimpleName();
    public static final int LAYOUT = R.layout.artists_layout;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        initToolbar();
    }

    /**
     * Initialize toolbar in artists_layout.
     */
    private void initToolbar() {
        Log.d(TAG, "initToolbar() started");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.performers);
        Log.d(TAG, "initToolbar() done");
    }
}
