package com.markchan.carrier.core;

import android.graphics.Rect;
import android.support.annotation.ColorInt;
import com.markchan.carrier.core.PagerView.TextAlignment;

/**
 * Created by Mark on 2017/7/15.
 */
public class Pager {

    /** 文字区域, 包括padding */
    private Rect textBorder;

    /** 文字绘制区域, 无padding */
    private Rect textRect;

    /** 文字 */
    private String text;

    /** 字体路径 */
    private String typefaceUrl;

    /** 文字大小 */
    private float textSize;

    /** 文字颜色 */
    @ColorInt
    private int textColor;

    /** 文字透明度 */
    private int textAlpha;

    /** 文字对齐方式 */
    @TextAlignment
    private int textAlignment;

    /** 背景颜色 */
    @ColorInt
    private int backgroundColor;

    /** 纹理Url */
    private String textureUrl;

    /** 背景图片Url */
    private String backgroundUrl;

    /** 背景图片高斯模糊半径 */
    private float backgroundBlurRadius;

    /** 背景图片亮度 */
    private float backgroundLight;

    public Pager() {
    }

    public Pager(Rect textRect, String text) {
        this.textRect = textRect;
        this.text = text;
    }

    public Pager(Rect textBorder, Rect textRect, String text, String typefaceUrl, float textSize,
            int textColor, int textAlpha, int textAlignment, int backgroundColor,
            String textureUrl, String backgroundUrl, float backgroundBlurRadius,
            float backgroundLight) {
        this.textBorder = textBorder;
        this.textRect = textRect;
        this.text = text;
        this.typefaceUrl = typefaceUrl;
        this.textSize = textSize;
        this.textColor = textColor;
        this.textAlpha = textAlpha;
        this.textAlignment = textAlignment;
        this.backgroundColor = backgroundColor;
        this.textureUrl = textureUrl;
        this.backgroundUrl = backgroundUrl;
        this.backgroundBlurRadius = backgroundBlurRadius;
        this.backgroundLight = backgroundLight;
    }

    public Rect getTextBorder() {
        return textBorder;
    }

    public void setTextBorder(Rect textBorder) {
        this.textBorder = textBorder;
    }

    public Rect getTextRect() {
        return textRect;
    }

    public void setTextRect(Rect textRect) {
        this.textRect = textRect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTypefaceUrl() {
        return typefaceUrl;
    }

    public void setTypefaceUrl(String typefaceUrl) {
        this.typefaceUrl = typefaceUrl;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextAlpha() {
        return textAlpha;
    }

    public void setTextAlpha(int textAlpha) {
        this.textAlpha = textAlpha;
    }

    public int getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(int textAlignment) {
        this.textAlignment = textAlignment;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextureUrl() {
        return textureUrl;
    }

    public void setTextureUrl(String textureUrl) {
        this.textureUrl = textureUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public float getBackgroundBlurRadius() {
        return backgroundBlurRadius;
    }

    public void setBackgroundBlurRadius(float backgroundBlurRadius) {
        this.backgroundBlurRadius = backgroundBlurRadius;
    }

    public float getBackgroundLight() {
        return backgroundLight;
    }

    public void setBackgroundLight(float backgroundLight) {
        this.backgroundLight = backgroundLight;
    }
}
