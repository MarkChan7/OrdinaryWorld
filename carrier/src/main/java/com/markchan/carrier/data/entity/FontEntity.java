package com.markchan.carrier.data.entity;

import com.google.gson.annotations.SerializedName;
import com.markchan.carrier.data.dao.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Mark on 2017/7/16.
 */
@Table(database = AppDatabase.class)
public class FontEntity extends BaseModel {

    @PrimaryKey
    private int id;
    @Column
    private int order;
    @Column
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("postscript_name")
    @Column
    private String postscriptName;
    @Column
    @SerializedName("thumbnail_url")
    private String thumbUrl;
    @Column
    private String url;
    @Column
    private String uri;

    public FontEntity() {
    }

    public FontEntity(int id, int order, String displayName, String postscriptName,
                      String thumbUrl, String url, String uri) {
        this.id = id;
        this.order = order;
        this.displayName = displayName;
        this.postscriptName = postscriptName;
        this.thumbUrl = thumbUrl;
        this.url = url;
        this.uri = uri;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
