package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

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
        mHelperPaint.setStrokeWidth(6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        // --------------------------------------------------
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(600);
        String text = "afgj";
        float baseLineX = 0;
        float baseLineY = 800;

        canvas.drawText(text, baseLineX, baseLineY, mPaint);

        baseLineY = baseLineY + 3;

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        Paint.FontMetricsInt fmInt = mPaint.getFontMetricsInt();

        // 以(baseLineX, baseLineY)作为坐标系, X轴向下为+, Y轴向右为+
        // top, ascent is "-"
        // descent, bottom is "+"
        Timber.d("FontMetrics: top=%f, ascent=%f, descent=%f, bottom=%f",
                fm.top, fm.ascent, fm.descent, fm.bottom);
        Timber.d("FontMetricsInt: top=%d, ascent=%d, descent=%d, bottom=%d",
                fmInt.top, fmInt.ascent, fmInt.descent, fmInt.bottom);

        // 转换为Android坐标系的坐标, 在X轴的坐标统一为baseLineX
        float topCoordY = baseLineY + fm.top;
        float ascentCoordY = baseLineY + fm.ascent;
        float descentCoordY = baseLineY + fm.descent;
        float bottomCoordY = baseLineY + fm.bottom;

        // base line
        canvas.drawLine(baseLineX, baseLineY, baseLineX + getWidth(), baseLineY,
                mHelperPaint);
        mHelperPaint.setColor(Color.CYAN);
        canvas.drawLine(baseLineX, topCoordY, baseLineX + getWidth(), topCoordY,
                mHelperPaint);
        mHelperPaint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, ascentCoordY, baseLineX + getWidth(), ascentCoordY,
                mHelperPaint);
        mHelperPaint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descentCoordY, baseLineX + getWidth(), descentCoordY,
                mHelperPaint);
        mHelperPaint.setColor(Color.MAGENTA);
        canvas.drawLine(baseLineX, bottomCoordY, baseLineX + getWidth(), bottomCoordY,
                mHelperPaint);
    }
}
