package com.markchan.carrier.data.entity;

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
            font.setOrder(fontEntity.getOrder());
            font.setDisplayName(fontEntity.getDisplayName());
            font.setPostscriptName(fontEntity.getPostscriptName());
            font.setThumbUrl(fontEntity.getThumbUrl());
            font.setUrl(fontEntity.getUrl());
            font.setDownloaded(fontEntity.isDownloaded());
            font.setFilePath(fontEntity.getFilePath());
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
