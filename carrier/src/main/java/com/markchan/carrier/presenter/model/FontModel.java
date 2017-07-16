package com.markchan.carrier.presenter.model;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontModel {

    private int id;
    private String displayName;
    private String postscriptName;
    private String thumbUrl;
    private String url;
    private boolean isDownloaded;
    private String filePath;

    public FontModel() {
    }

    public FontModel(int id, String displayName, String postscriptName, String thumbUrl,
            String url, boolean isDownloaded, String filePath) {
        this.id = id;
        this.displayName = displayName;
        this.postscriptName = postscriptName;
        this.thumbUrl = thumbUrl;
        this.url = url;
        this.isDownloaded = isDownloaded;
        this.filePath = filePath;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
