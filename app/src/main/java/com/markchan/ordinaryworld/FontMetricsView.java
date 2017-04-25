package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.blankj.utilcode.utils.ScreenUtils;
import timber.log.Timber;

/**
 * Created by Mark on 17/1/19.
 */

public class FontMetricsView extends View {

    public FontMetricsView(Context context) {
        this(context, null);
    }

    public FontMetricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontMetricsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public FontMetricsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;

    private Paint mHelperPaint;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelperPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelperPaint.setColor(Color.RED);
        mHelperPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.CYAN);

        // --------------------------------------------------
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(200);
        String text = "asdfghjkl";
        float baseLineX = 0;
        float baseLineY = 400;

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        Paint.FontMetricsInt fmInt = mPaint.getFontMetricsInt();

        // top, ascent is "-"
        // descent, bottom is "+"
        Timber.d("FontMetrics: top=%f, ascent=%f, descent=%f, bottom=%f",
                fm.top, fm.ascent, fm.descent, fm.bottom);
        Timber.d("FontMetrics: top=%d, ascent=%d, descent=%d, bottom=%d",
                fmInt.top, fmInt.ascent, fmInt.descent, fmInt.bottom);

        // basic line
        canvas.drawLine(baseLineX, baseLineY, baseLineX + ScreenUtils.getScreenWidth(), baseLineY, mHelperPaint);

        float topCoordY = baseLineY + fm.top;
        float ascentCoordY = baseLineY + fm.ascent;
        float descentCoordY = baseLineY + fm.descent;
        float bottomCoordY = baseLineY + fm.bottom;

        // fm
        canvas.drawLine(baseLineX, topCoordY, baseLineX + ScreenUtils.getScreenWidth(), topCoordY, mHelperPaint);
        canvas.drawLine(baseLineX, ascentCoordY, baseLineX + ScreenUtils.getScreenWidth(),ascentCoordY, mHelperPaint);
        canvas.drawLine(baseLineX, descentCoordY, baseLineX + ScreenUtils.getScreenWidth(), descentCoordY, mHelperPaint);
        canvas.drawLine(baseLineX, bottomCoordY, baseLineX + ScreenUtils.getScreenWidth(), bottomCoordY, mHelperPaint);

        canvas.drawText(text, baseLineX, baseLineY, mPaint);
    }
}
