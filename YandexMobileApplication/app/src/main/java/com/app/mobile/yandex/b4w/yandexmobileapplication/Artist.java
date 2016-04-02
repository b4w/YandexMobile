package com.app.mobile.yandex.b4w.yandexmobileapplication;

/**
 * Created by KonstantinSysoev on 02.04.16.
 * <p/>
 * Pojo class for artist entity.
 */
public class Artist {

    private int id;
    private String name;
    private String[] genres;
    private int albums;
    private int tracks;
    private String link;
    private String description;
    private String coverSmallLink;
    private String coverBigLink;

    public Artist(int id, String name, String[] genres, int albums, int tracks, String link,
                  String description, String coverSmallLink, String coverBigLink) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.albums = albums;
        this.tracks = tracks;
        this.link = link;
        this.description = description;
        this.coverSmallLink = coverSmallLink;
        this.coverBigLink = coverBigLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverSmallLink() {
        return coverSmallLink;
    }

    public void setCoverSmallLink(String coverSmallLink) {
        this.coverSmallLink = coverSmallLink;
    }

    public String getCoverBigLink() {
        return coverBigLink;
    }

    public void setCoverBigLink(String coverBigLink) {
        this.coverBigLink = coverBigLink;
    }
}
