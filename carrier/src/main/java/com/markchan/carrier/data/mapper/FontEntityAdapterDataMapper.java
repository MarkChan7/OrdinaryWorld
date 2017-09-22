package com.markchan.carrier.data.mapper;

import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.entity.adapter.FontEntityAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontEntityAdapterDataMapper {

    public FontEntity transform(FontEntityAdapter adapter) {
        FontEntity fontEntity = null;
        if (adapter != null) {
            fontEntity = new FontEntity();
            fontEntity.setId(adapter.getId());
            fontEntity.setDisplayName(adapter.getDisplayName());
            fontEntity.setPostscriptName(adapter.getPostscriptName());
            fontEntity.setThumbUrl(adapter.getThumbUrl());
            fontEntity.setUri(adapter.getUrl());
        }
        return fontEntity;
    }

    public List<FontEntity> transform(Collection<FontEntityAdapter> adapterCollection) {
        final List<FontEntity> fonts = new ArrayList<>();
        for (FontEntityAdapter adapter : adapterCollection) {
            final FontEntity fontEntity = transform(adapter);
            if (fontEntity != null) {
                fonts.add(fontEntity);
            }
        }
        return fonts;
    }
}
