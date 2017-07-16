package com.markchan.carrier.presenter.model;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class TextureModel {

    public static TextureModel createPureTexture(String name) {
        return new TextureModel(name, null);
    }

    private final String name;
    private final String url;

    public TextureModel(String name, String url) {
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
