package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.network;

import com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo.Artist;

/**
 * Created by KonstantinSysoev on 09.04.16.
 * Interface for query to yandex json link.
 */
public interface IYandexArtistsService {

    @retrofit.http.GET("/artists.json")
    Artist.List artists();
}
