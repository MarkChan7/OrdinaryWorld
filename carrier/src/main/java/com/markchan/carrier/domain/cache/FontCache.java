package com.markchan.carrier.domain.cache;

import com.markchan.carrier.domain.Font;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/20
 */
public interface FontCache {

    boolean setFontDownloaded(Font font);

    boolean deleteDownloadedFont(Font font);

    void deleteAllDownloadedFonts();
}
