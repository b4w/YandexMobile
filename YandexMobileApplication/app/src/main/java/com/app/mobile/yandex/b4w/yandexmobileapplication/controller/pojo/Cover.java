package com.app.mobile.yandex.b4w.yandexmobileapplication.controller.pojo;

/**
 * Created by KonstantinSysoev on 10.04.16.
 * <p/>
 * Pojo class for Cover entity.
 */
public class Cover {

    private String small;
    private String big;

    public Cover() {
    }

    public Cover(String small, String big) {
        this.small = small;
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }
}
