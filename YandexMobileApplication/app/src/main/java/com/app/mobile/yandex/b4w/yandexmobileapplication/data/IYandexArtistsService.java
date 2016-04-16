package com.app.mobile.yandex.b4w.yandexmobileapplication.data;

import com.app.mobile.yandex.b4w.yandexmobileapplication.model.pojo.Artist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by KonstantinSysoev on 09.04.16.
 */
public interface IYandexArtistsService {

    String YA_ARTISTS_LINK = "http://download.cdn.yandex.net/mobilization-2016/";

    @GET("artists.json")
    Call<List<Artist>> loadArtists();
}
