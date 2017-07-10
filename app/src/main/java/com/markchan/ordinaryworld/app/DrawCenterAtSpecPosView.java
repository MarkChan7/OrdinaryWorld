package com.markchan.ordinaryworld.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mark on 17/1/19.
 */

public class DrawCenterAtSpecPosView extends View {

    public DrawCenterAtSpecPosView(Context context) {
        this(context, null);
    }

    public DrawCenterAtSpecPosView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCenterAtSpecPosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public DrawCenterAtSpecPosView(Context context, AttributeSet attrs, int defStyleAttr,
                                   int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;
    private Paint mCenterLinePaint;
    private Paint mBaseLinePaint;
    private Paint mBoundsPaint;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mCenterLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterLinePaint.setColor(Color.GREEN);
        mBaseLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBaseLinePaint.setColor(Color.RED);
        mBoundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundsPaint.setColor(Color.CYAN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        String text = "asdfghjkl";

        int centerY = 200;
        int baseLineX = 0;

        mPaint.setTextSize(120);
        mPaint.setTextAlign(Paint.Align.LEFT);

        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();

        int baseLineY = centerY + (fm.bottom - fm.top) / 2 - fm.bottom;
        canvas.drawText(text, baseLineX, baseLineY, mPaint);

        // center line
        canvas.drawLine(baseLineX, centerY, getWidth(), centerY, mCenterLinePaint);

        // base line
        canvas.drawLine(baseLineX, baseLineY, getWidth(), baseLineY, mBaseLinePaint);
    }
}
