package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import timber.log.Timber;

/**
 * Created by Mark on 17/1/19.
 */
public class TextBoundsView extends View {

    public TextBoundsView(Context context) {
        this(context, null);
    }

    public TextBoundsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextBoundsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public TextBoundsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;

    private Paint mHelperPaint;

    private Paint mBoundsPaint;
    private Paint mWrapPaint;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mHelperPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelperPaint.setColor(Color.RED);
        mHelperPaint.setStrokeWidth(1);

        mBoundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundsPaint.setColor(Color.YELLOW);

        mWrapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWrapPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.CYAN);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(120);
        String text = "asdfghjkl";
        float baseLineX = 0;
        float baseLineY = 200;

        Paint.FontMetrics fm = mPaint.getFontMetrics();

        float topCoordY = baseLineY + fm.top;
        float ascentCoordY = baseLineY + fm.ascent;
        float descentCoordY = baseLineY + fm.descent;
        float bottomCoordY = baseLineY + fm.bottom;

        // text bounds
        float width = (int) mPaint.measureText(text);
        RectF rectF = new RectF(baseLineX, topCoordY, baseLineX + width, bottomCoordY);
        canvas.drawRect(rectF, mBoundsPaint);

        // wrap bounds
        Rect wrapBounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), wrapBounds);
        // 以(baseLineX, baseLineY)作为坐标系, X轴向下为+, Y轴向右为+
        // [6,-92][479,26]
        Timber.d("TextBounds is " + wrapBounds.toShortString());

        // 转换为Android坐标系的坐标, 在X轴的坐标统一为baseLineX
        wrapBounds.top = (int) (baseLineY + wrapBounds.top);
        wrapBounds.bottom = (int) (baseLineY + wrapBounds.bottom);

        mPaint.setColor(Color.RED);
        canvas.drawRect(wrapBounds, mWrapPaint);

        canvas.drawText(text, baseLineX, baseLineY, mPaint);

        // base line
        canvas.drawLine(baseLineX, baseLineY, baseLineX + getWidth(), baseLineY, mHelperPaint);
    }
}
