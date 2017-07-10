package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.utils.SizeUtils;

/**
 * Created by Mark on 2017/7/8.
 */
public class PagerView extends View {

    public static final boolean DEBUG = true;

    public PagerView(Context context) {
        this(context, null);
    }

    public PagerView(Context context,
                     @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public PagerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private static final int ALIGNMENT_LEFT = -1;
    private static final int ALIGNMENT_CENTER = 0;
    private static final int ALIGNMENT_RIGHT = 1;

    @IntDef(value = {ALIGNMENT_LEFT, ALIGNMENT_CENTER, ALIGNMENT_RIGHT})
    public @interface ALIGNMENT {
        // no-op by default
    }

    private static final int DEFAULT_ALPHA = 255;

    private static final String DEFAULT_TEXT = "Like Sunday Like Raining";

    private Rect mTextBorder;
    private Rect mTextRect;

    /** 文字 */
    private String mText = DEFAULT_TEXT;

    /** 字体路径 */
    private String mTypefaceUri;

    /** 字体大小 */
    private float mTextSize;

    /** 字体颜色 */
    @ColorInt
    private int mTextColor;

    /** 字体透明度 */
    private int mTextAlpha = DEFAULT_ALPHA;

    /** 对齐方式 */
    @ALIGNMENT
    private int mAlignment;

    /** 文字相对顶部的偏移量 */
    private float mTextOffset;

    /** 纹理颜色 */
    @ColorInt
    private int mVeinColor;

    /** 纹理 */
    private Bitmap mVeinBitmap;

    /** 背景 */
    private Bitmap mBackgroundBitmap;

    /** 背景高斯模糊半径 */
    private float mBackgroundBlurRadius;

    /** 背景亮度 */
    private float mBackgroundLight;

    private TextPaint mTextPaint;

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                      int defStyleRes) {
        mTextRect = new Rect();
        mTextBorder = new Rect();

        mTextPaint = new TextPaint();

        mText = "Perfect";
        mTextSize = 120.0F;
        mTextColor = Color.BLACK;
        mTextOffset = 0.0F;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int area = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        setMeasuredDimension(area, area);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DEBUG) {
            canvas.drawARGB(64, 255, 0, 0);
        }

        if (TextUtils.isEmpty(mText)) {
            mText = DEFAULT_TEXT;
        }

        if (!TextUtils.isEmpty(mTypefaceUri)) {
            Scheme scheme = Scheme.ofUri(mTypefaceUri);
            if (scheme == Scheme.ASSETS || scheme == Scheme.FILE) {
                try {
                    String path = scheme.crop(mTypefaceUri);
                    Typeface typeface;
                    if (scheme == Scheme.ASSETS) {
                        typeface = Typeface.createFromAsset(getContext().getAssets(), path);
                    } else {
                        typeface = Typeface.createFromFile(path);
                    }
                    mTextPaint.setTypeface(typeface);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAlpha(mTextAlpha);

        FontMetricsInt fm = mTextPaint.getFontMetricsInt();
        int textHeight = fm.bottom - fm.top;

        int textRectPadding = SizeUtils.dp2px(8);
        int textPaddingTopAndBottom =  SizeUtils.dp2px(8);
        int textPaddingLeftAndRight =  SizeUtils.dp2px(12);

        mTextBorder.left = textRectPadding;
        mTextBorder.right = getWidth() - textRectPadding;
        mTextBorder.top = getHeight() / 2 - textHeight / 2 - textRectPadding;
        mTextBorder.bottom = mTextRect.top + textHeight + textRectPadding;

        float baseLineX = 0;
        float baseLineY = 0;

        canvas.drawText(mText, baseLineX, baseLineY, mTextPaint);
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private Typeface getTypeface(String uri) {
        boolean isAsset = true;
        if (uri.equals("xx")) {
        }
        return null;
    }
}
