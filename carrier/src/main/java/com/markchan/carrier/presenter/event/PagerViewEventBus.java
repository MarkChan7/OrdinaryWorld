package com.markchan.carrier.presenter.event;

import com.markchan.carrier.presenter.core.PagerView.TextAlignment;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public final class PagerViewEventBus {

    public static class TypefaceEvent {

        public final String typefaceUrl;

        public TypefaceEvent(String typefaceUrl) {
            this.typefaceUrl = typefaceUrl;
        }
    }

    public static class TextSizeEvent {

        public final float textSize;

        public TextSizeEvent(float textSize) {
            this.textSize = textSize;
        }
    }

    public static class TextColorEvent {

        public final int textColor;

        public TextColorEvent(int textColor) {
            this.textColor = textColor;
        }
    }

    public static class TextAlphaEvent {

        public final int textAlpha;

        public TextAlphaEvent(int textAlpha) {
            this.textAlpha = textAlpha;
        }
    }

    public static class TextAlignmentEvent {

        @TextAlignment
        public final int textAlignment;

        public TextAlignmentEvent(@TextAlignment int textAlignment) {
            this.textAlignment = textAlignment;
        }
    }

    public static class TextOffsetEvent {
        // no-op by default
    }

    public static class TextureEvent {

        public final String textureUrl;

        public TextureEvent(String textureUrl) {
            this.textureUrl = textureUrl;
        }
    }

    public static class BackgroundColorEvent {

        public final int backgroundColor;

        public BackgroundColorEvent(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }
    }
}
