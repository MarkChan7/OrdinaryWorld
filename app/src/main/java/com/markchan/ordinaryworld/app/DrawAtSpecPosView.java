package com.markchan.ordinaryworld.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mark on 17/1/19.
 */

public class DrawAtSpecPosView extends View {

    public DrawAtSpecPosView(Context context) {
        this(context, null);
    }

    public DrawAtSpecPosView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawAtSpecPosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public DrawAtSpecPosView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;
    private Paint mTopLinePaint;
    private Paint mBaseLinePaint;
    private Paint mBoundsPaint;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mTopLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTopLinePaint.setColor(Color.GREEN);
        mBaseLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBaseLinePaint.setColor(Color.RED);
        mBoundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundsPaint.setColor(Color.CYAN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        // --------------------------------------------------
        String text = "asdfghjkl";
        int top = 200;
        int x = 0;

        mPaint.setTextSize(200);
        mPaint.setTextAlign(Paint.Align.LEFT);

        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();

        // text bounds
        float width = (int) mPaint.measureText(text);
        RectF rectF = new RectF(x, top, x + width, top + fm.bottom - fm.top);
        canvas.drawRect(rectF, mBoundsPaint);

        // top line
        canvas.drawLine(x, top, getWidth(), top, mTopLinePaint);

        // base line
        int baseLineY = top - fm.top;
        canvas.drawLine(x, baseLineY, getWidth(), baseLineY, mBaseLinePaint);

        canvas.drawText(text, x, baseLineY, mPaint);
    }
}
