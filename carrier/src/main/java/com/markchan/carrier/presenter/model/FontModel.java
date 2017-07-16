package com.markchan.carrier.presenter.model;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontModel {

    private int id;
    private String displayName;
    private String thumbUrl;
    private String url;

    public FontModel() {
    }

    public FontModel(int id, String displayName, String thumbUrl, String url) {
        this.id = id;
        this.displayName = displayName;
        this.thumbUrl = thumbUrl;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
