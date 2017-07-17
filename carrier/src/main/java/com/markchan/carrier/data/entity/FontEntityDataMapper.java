package com.markchan.carrier.data.entity;

import com.markchan.carrier.Scheme;
import com.markchan.carrier.domain.Font;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontEntityDataMapper {

    public Font transform(FontEntity fontEntity) {
        Font font = null;
        if (fontEntity != null) {
            font = new Font();
            font.setId(fontEntity.getId());
            font.setDisplayName(fontEntity.getDisplayName());
            font.setPostscriptName(fontEntity.getPostscriptName());
            font.setThumbUrl(fontEntity.getThumbUrl());
            String uri = fontEntity.getUri();
            if (Scheme.ofUri(uri) == Scheme.FILE) {
                font.setUri(fontEntity.getUri());
            } else {
                font.setUri(fontEntity.getUrl());
            }
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
