package com.markchan.carrier.data.mapper;

import com.markchan.carrier.data.entity.FontEntity;
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
            font.setUri(fontEntity.getUri());
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

    public FontEntity retransform(Font font) {
        FontEntity fontEntity = null;
        if (font != null) {
            fontEntity = new FontEntity();
            fontEntity.setId(font.getId());
            fontEntity.setDisplayName(font.getDisplayName());
            fontEntity.setPostscriptName(font.getPostscriptName());
            fontEntity.setThumbUrl(font.getThumbUrl());
            fontEntity.setUri(font.getUri());
        }
        return fontEntity;
    }

    public List<FontEntity> retransform(Collection<Font> fontCollection) {
        final List<FontEntity> fontEntities = new ArrayList<>();
        for (Font font : fontCollection) {
            final FontEntity fontEntity = retransform(font);
            if (fontEntity != null) {
                fontEntities.add(fontEntity);
            }
        }
        return fontEntities;
    }
}
