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
    public DrawAtSpecPosView(Context context, AttributeSet attrs, int defStyleAttr,
                             int defStyleRes) {
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

        mPaint.setTextSize(200);
        mPaint.setTextAlign(Paint.Align.LEFT);

        String text = "asdfghjkl";

        int topCoordX = 0;
        int topCoordY = 200;

        // top line
        canvas.drawLine(topCoordX, topCoordY, getWidth(), topCoordY, mTopLinePaint);

        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();

        // text bounds
        float width = (int) mPaint.measureText(text);
        int textHeight = fm.bottom - fm.top;
        RectF rectF = new RectF(topCoordX, topCoordY, topCoordX + width, topCoordY + textHeight);
        canvas.drawRect(rectF, mBoundsPaint);

        int baseLineY = topCoordY - fm.top;

        canvas.drawText(text, topCoordX, baseLineY, mPaint);

        canvas.drawLine(topCoordX, baseLineY, getWidth(), baseLineY, mBaseLinePaint);
    }
}
