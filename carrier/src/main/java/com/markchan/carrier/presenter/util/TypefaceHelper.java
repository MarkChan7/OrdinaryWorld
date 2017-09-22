package com.markchan.carrier.presenter.util;

import android.content.Context;
import android.graphics.Typeface;

import com.markchan.carrier.domain.Scheme;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/21
 */
public class TypefaceHelper {

    public static Typeface createTypeface(Context context, String uri) {
        Typeface typeface = null;

        Scheme scheme = Scheme.ofUri(uri);
        if (scheme == Scheme.ASSETS || scheme == Scheme.FILE) {
            try {
                String path = scheme.crop(uri);
                if (scheme == Scheme.ASSETS) {
                    typeface = Typeface.createFromAsset(context.getAssets(), path);
                } else {
                    typeface = Typeface.createFromFile(path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return typeface;
    }
}
