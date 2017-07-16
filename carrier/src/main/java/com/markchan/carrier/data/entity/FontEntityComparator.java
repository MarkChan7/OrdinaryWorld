package com.markchan.carrier.data.entity;

import java.util.Comparator;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontEntityComparator implements Comparator<FontEntity> {

    @Override
    public int compare(FontEntity o1, FontEntity o2) {
        return o1.getOrder() > o2.getOrder() ? -1 : o1.getOrder() == o2.getOrder() ? 0 : 1;
    }
}
