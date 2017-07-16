package com.markchan.carrier.data.entity;

import com.markchan.carrier.domain.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontEntityDataMapper {

    public static Font transform(FontEntity fontData) {
        Font font = null;
        if (fontData != null) {
            font = new Font();
            font.setId(fontData.getId());
            font.setOrder(fontData.getOrder());
            font.setDisplayName(fontData.getDisplayName());
            font.setPostscriptName(fontData.getPostscriptName());
            font.setThumbUrl(fontData.getThumbUrl());
            font.setUrl(fontData.getUrl());
        }
        return font;
    }

    public List<Font> transform(Collection<FontEntity> fontEntityCollection) {
        final List<Font> fonts = new ArrayList<>();
        for (FontEntity fontEntity : fontEntityCollection) {
            final Font font = transform(fontEntity);
            if (font != null) {
                fonts.add(font);
            }
        }
        return fonts;
    }
}
