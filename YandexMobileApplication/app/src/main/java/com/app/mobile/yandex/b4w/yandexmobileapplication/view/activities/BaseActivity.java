package com.app.mobile.yandex.b4w.yandexmobileapplication.view.activities;

import android.support.v7.app.AppCompatActivity;

import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.network.YandexRetrofitSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by KonstantinSysoev on 16.04.16.
 * Abstract class Base Activity for init RoboSpice methods.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private SpiceManager spiceManager = new SpiceManager(YandexRetrofitSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
