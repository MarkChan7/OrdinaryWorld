package com.markchan.carrier.mapper;

import com.markchan.carrier.domain.Font;
import com.markchan.carrier.model.FontModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontModelDataMapper {

    public static FontModel transform(Font font) {
        FontModel fontModel = null;
        if (font != null) {
            fontModel = new FontModel();
            fontModel.setId(font.getId());
            fontModel.setDisplayName(font.getDisplayName());
            fontModel.setThumbUrl(font.getThumbUrl());
            fontModel.setUrl(font.getUrl());
        }
        return fontModel;
    }

    public List<FontModel> transform(Collection<Font> fontCollection) {
        final List<FontModel> fontModels = new ArrayList<>();
        for (Font font : fontCollection) {
            final FontModel fontModel = transform(font);
            if (fontModel != null) {
                fontModels.add(fontModel);
            }
        }
        return fontModels;
    }
}
