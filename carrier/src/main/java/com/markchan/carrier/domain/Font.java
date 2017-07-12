package com.markchan.carrier.domain;

/**
 * Created by Mark on 2017/7/13.
 */
public class Font {

    private final String name;
    private final String url;

    public Font(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
