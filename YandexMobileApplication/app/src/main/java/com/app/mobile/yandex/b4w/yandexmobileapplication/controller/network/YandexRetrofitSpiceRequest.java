package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.network;

import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by KonstantinSysoev on 16.04.16.
 * Class for async request by means of service.
 */
public class YandexRetrofitSpiceRequest extends RetrofitSpiceRequest<Artist.List, IYandexArtistsService> {

    public YandexRetrofitSpiceRequest() {
        super(Artist.List.class, IYandexArtistsService.class);
    }

    @Override
    public Artist.List loadDataFromNetwork() throws Exception {
        return getService().artists();
    }
}
