package com.app.mobile.yandex.b4w.yandexmobileapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by KonstantinSysoev on 29.03.16.
 * <p/>
 * ArtistsActivity - activity for display of the loaded list of actors.
 */
public class ArtistsActivity extends Activity {

    public static final int LAYOUT = R.layout.artists_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }
}
