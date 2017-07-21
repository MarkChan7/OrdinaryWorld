package com.markchan.carrier.presenter.mapper;

import com.markchan.carrier.domain.Font;
import com.markchan.carrier.presenter.model.FontModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontModelDataMapper {

    public FontModel transform(Font font) {
        FontModel fontModel = null;
        if (font != null) {
            fontModel = new FontModel();
            fontModel.setId(font.getId());
            fontModel.setDisplayName(font.getDisplayName());
            fontModel.setPostscriptName(font.getPostscriptName());
            fontModel.setThumbUrl(font.getThumbUrl());
            fontModel.setUri(font.getUri());
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

    public Font retransform(FontModel fontModel) {
        Font font = null;
        if (fontModel != null) {
            font = new Font();
            font.setId(fontModel.getId());
            font.setDisplayName(fontModel.getDisplayName());
            font.setPostscriptName(fontModel.getPostscriptName());
            font.setThumbUrl(fontModel.getThumbUrl());
            font.setUri(fontModel.getUri());
        }
        return font;
    }

    public List<Font> retransform(Collection<FontModel> fontModelCollection) {
        final List<Font> fonts = new ArrayList<>();
        for (FontModel fontModel : fontModelCollection) {
            final Font font = retransform(fontModel);
            if (font != null) {
                fonts.add(font);
            }
        }
        return fonts;
    }
}
