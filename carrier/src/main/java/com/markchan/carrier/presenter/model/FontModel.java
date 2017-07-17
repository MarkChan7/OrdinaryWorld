package com.markchan.carrier.presenter.model;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontModel {

    private int id;
    private String displayName;
    private String postscriptName;
    private String thumbUrl;
    private String uri;

    public FontModel() {
    }

    public FontModel(int id, String displayName, String postscriptName, String thumbUrl,
                     String uri) {
        this.id = id;
        this.displayName = displayName;
        this.postscriptName = postscriptName;
        this.thumbUrl = thumbUrl;
        this.uri = uri;
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

    public String getPostscriptName() {
        return postscriptName;
    }

    public void setPostscriptName(String postscriptName) {
        this.postscriptName = postscriptName;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
