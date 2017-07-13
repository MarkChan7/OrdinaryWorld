package com.markchan.carrier.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/7
 */
public class ColorMatrixFilterHelper {


    /**
     * 这个矩阵定义的是一连串float数组, 其中不同的位置代表了不同的RGBA值, 它的范围在[0.0f, 2.0f]之间, 1为保持原图的RGBA值
     */
    public static void filterRGBA(Bitmap bitmap, float rMultiple, float gMultiple, float bMultiple,
                                  float aMultiple) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap can not be null");
        }

        float[] matrixArr = new float[]{
                rMultiple, 0.0F, 0.0F, 0.0F, 0.0F,
                0.0F, gMultiple, 0.0F, 0.0F, 0.0F,
                0.0F, 0.0F, bMultiple, 0.0F, 0.0F,
                0.0F, 0.0F, 0.0F, aMultiple, 0.0F,
        };

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixArr);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
    }
}
