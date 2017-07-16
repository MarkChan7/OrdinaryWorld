package com.markchan.carrier.domain;

/**
 * Created by Mark on 2017/7/13.
 */
public class Font {

    private int id;
    private int order;
    private String displayName;
    private String postscriptName;
    private String thumbUrl;
    private String url;

    public Font() {
    }

    public Font(int id, int order, String displayName, String postscriptName,
            String thumbUrl, String url) {
        this.id = id;
        this.order = order;
        this.displayName = displayName;
        this.postscriptName = postscriptName;
        this.thumbUrl = thumbUrl;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
