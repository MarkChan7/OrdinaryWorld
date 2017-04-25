package com.markchan.ordinaryworld.layout;

import static com.markchan.ordinaryworld.layout.TextHelper.vectorToString;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import java.util.Vector;

/**
 * Created by Mark on 17/1/19.
 */

public class CenterStaticLayoutView extends View {

    public CenterStaticLayoutView(Context context) {
        this(context, null);
    }

    public CenterStaticLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterStaticLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public CenterStaticLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private TextPaint mPaint;
    private Paint mBoundsPaint;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mBoundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundsPaint.setColor(Color.CYAN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        // --------------------------------------------------
        mPaint.setTextSize(30);

        Rect rect = new Rect(200, 200, 400, 800);
        canvas.drawRect(rect, mBoundsPaint);

        String text = "这是一串需要进行换行并居中显示的文字。";

        Vector vector = TextHelper.getTextLinesVector(mPaint, text, rect.height(), rect.width());
        text = vectorToString(vector);

        StaticLayout layout = new StaticLayout(text, mPaint, rect.width(), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F,
                true);
        mPaint.setTextAlign(Paint.Align.CENTER);

        canvas.save();
        canvas.translate(rect.left + rect.width() / 2,
                rect.top + (rect.height() - TextHelper.getFontHeight(mPaint) * vector.size()) / 2);
        layout.draw(canvas);
        canvas.restore();
    }
}
