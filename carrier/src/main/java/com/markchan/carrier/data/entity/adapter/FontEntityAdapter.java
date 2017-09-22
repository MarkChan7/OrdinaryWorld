package com.markchan.carrier.data.entity.adapter;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2017/9/22
 */
public class FontEntityAdapter {

    @SerializedName("id")
    private long id;
    @SerializedName("order")
    private long order;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("postscript_name")
    private String postscriptName;
    @SerializedName("thumbnail_url")
    private String thumbUrl;
    @SerializedName("url")
    private String url;

    public FontEntityAdapter() {
    }

    public FontEntityAdapter(long id, long order, String displayName, String postscriptName,
            String thumbUrl, String url) {
        this.id = id;
        this.order = order;
        this.displayName = displayName;
        this.postscriptName = postscriptName;
        this.thumbUrl = thumbUrl;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
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
