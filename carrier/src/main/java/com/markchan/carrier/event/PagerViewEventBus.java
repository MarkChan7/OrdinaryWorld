package com.markchan.carrier.event;

import com.markchan.carrier.core.PagerView.TextAlignment;

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

        public final int color;

        public TextColorEvent(int color) {
            this.color = color;
        }
    }

    public static class TextAlphaEvent {

        public final int alpha;

        public TextAlphaEvent(int alpha) {
            this.alpha = alpha;
        }
    }

    public static class TextAlignmentEvent {

        @TextAlignment
        public final int alignment;

        public TextAlignmentEvent(@TextAlignment int alignment) {
            this.alignment = alignment;
        }
    }

    public static class TextOffsetEvent {
        // no-op by default
    }
}
