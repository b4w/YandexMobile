package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by KonstantinSysoev on 16.04.16.
 * Service for query to yandex json link.
 */
public class YandexRetrofitSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://download.cdn.yandex.net/mobilization-2016";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(IYandexArtistsService.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
