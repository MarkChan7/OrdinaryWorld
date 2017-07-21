package com.markchan.carrier.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
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
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.markchan.carrier.R;
import com.markchan.carrier.util.TextHelper;
import com.markchan.carrier.util.TypefaceHelper;

import java.util.Vector;

import timber.log.Timber;

/**
 * Created by Mark on 2017/7/8.
 */
public class PagerView extends View {

    public static boolean DEBUG = false;

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
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public static final int TEXT_ALIGNMENT_LEFT = -1;
    public static final int TEXT_ALIGNMENT_CENTER = 0;
    public static final int TEXT_ALIGNMENT_RIGHT = 1;

    @IntDef(value = {TEXT_ALIGNMENT_LEFT, TEXT_ALIGNMENT_CENTER, TEXT_ALIGNMENT_RIGHT})
    public @interface TextAlignment {
        // no-op by default
    }

    public static String FAKE_DEFAULT_TYPEFACE_URI = "typeface://default";

    private static String DEFAULT_TEXT;

    private static float DEFAULT_TEXT_SIZE;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_ALPHA = 255;
    private static final int DEFAULT_TEXT_ALIGNMENT = TEXT_ALIGNMENT_CENTER;

    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private static int TEXT_BORDER_PADDING;
    private static int TEXT_PADDING_LEFT_AND_RIGHT;
    private static int TEXT_PADDING_TOP_AND_BOTTOM;

    private static int TEXT_RECT_MAX_HEIGHT;

    private static int TEXT_RECT_MAX_WIDTH;

    private static int MIN_TEXT_OFFSET;

    private static int MAX_TEXT_OFFSET;

    private static final int mWidth = ScreenUtils.getScreenWidth();
    private static final int mHeight = mWidth;

    /** 文字区域, 包括padding */
    private Rect mTextBorder;

    /** 文字绘制区域, 无padding */
    private Rect mTextRect;

    /** 文字 */
    private String mText;

    /** 字体路径 */
    private String mTypefaceUrl;
    private String mTempTypefaceUrl;

    /** 字体大小 */
    private float mTextSize;

    /** 字体颜色 */
    @ColorInt
    private int mTextColor;

    /** 字体透明度 */
    private int mTextAlpha;

    /** 对齐方式 */
    @TextAlignment
    private int mTextAlignment;

    /** 文字顶部相在Y轴上的坐标 */
    private int mTextOffset;

    /** 文字中心点在Y轴上的坐标 */
    private int mTextCenterY;

    /** 背景颜色 */
    @ColorInt
    private int mBackgroundColor;

    /** 纹理 */
    private Bitmap mTextureBitmap;

    /** 背景图片 */
    private Bitmap mBackgroundBitmap;

    /** 背景高斯模糊半径 */
    private float mBackgroundBlurRadius;

    /** 背景亮度 */
    private float mBackgroundLight;

    private Paint mBackgroundPaint;

    private TextPaint mTextPaint;

    private Paint mTextBorderPaint;

    private Paint mHelperPaint;

    private boolean mShowDashRect;

    private boolean mResetOffsetFlag;

    private float mTouchSlop;

    private float mLastTouchX;
    private float mLastTouchY;

    private boolean mDragging;

    private GestureDetector mGestureDetector;

    private OnTextTapListener mOnTextTapListener;

    public interface OnTextTapListener {

        void onTextTap(String text);
    }

    public OnTextTapListener getOnTextTapListener() {
        return mOnTextTapListener;
    }

    public void setOnTextTapListener(OnTextTapListener onTextTapListener) {
        mOnTextTapListener = onTextTapListener;
    }

    private OnDragTextListener mOnDragTextListener;

    public interface OnDragTextListener {

        void onDragText(@TextAlignment int textAlignment);
    }

    public OnDragTextListener getOnDragTextListener() {
        return mOnDragTextListener;
    }

    public void setOnDragTextListener(
            OnDragTextListener onDragTextListener) {
        mOnDragTextListener = onDragTextListener;
    }

    private void setupDefaultValue(Context context) {
        DEFAULT_TEXT = getResources().getString(R.string.pager_view_default_text);

        DEFAULT_TEXT_SIZE = getResources().getDimension(R.dimen.pager_view_default_text_size);

        TEXT_BORDER_PADDING = (int) getResources()
                .getDimension(R.dimen.pager_view_text_border_padding);

        TEXT_PADDING_LEFT_AND_RIGHT = (int) getResources()
                .getDimension(R.dimen.pager_view_text_padding_left_and_right);
        TEXT_PADDING_TOP_AND_BOTTOM = (int) getResources()
                .getDimension(R.dimen.pager_view_text_padding_top_and_bottom);

        TEXT_RECT_MAX_HEIGHT = mHeight - TEXT_BORDER_PADDING * 2 - TEXT_PADDING_TOP_AND_BOTTOM * 2;

        TEXT_RECT_MAX_WIDTH = mWidth - TEXT_BORDER_PADDING * 2 - TEXT_PADDING_LEFT_AND_RIGHT * 2;

        MIN_TEXT_OFFSET = TEXT_BORDER_PADDING * 2;

        MAX_TEXT_OFFSET = MIN_TEXT_OFFSET + TEXT_RECT_MAX_HEIGHT;
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                      int defStyleRes) {
        setupDefaultValue(context);

        mText = DEFAULT_TEXT;
        mTextSize = DEFAULT_TEXT_SIZE;
        mTextColor = DEFAULT_TEXT_COLOR;
        mTextAlpha = DEFAULT_TEXT_ALPHA;
        mTextAlignment = DEFAULT_TEXT_ALIGNMENT;

        mBackgroundColor = DEFAULT_BACKGROUND_COLOR;

        mTextBorder = new Rect();
        mTextBorder.left = TEXT_BORDER_PADDING;
        mTextBorder.right = mWidth - TEXT_BORDER_PADDING;

        mTextRect = new Rect();
        mTextRect.left = mTextBorder.left + TEXT_PADDING_LEFT_AND_RIGHT;
        mTextRect.right = mTextBorder.right - TEXT_PADDING_LEFT_AND_RIGHT;

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAlpha(mTextAlpha);

        mTextBorderPaint = new Paint();
        mTextBorderPaint.setStyle(Style.STROKE);
        mTextBorderPaint.setStrokeWidth(SizeUtils.dp2px(1.0F));
        float interval = SizeUtils.dp2px(4.0F);
        DashPathEffect dashPathEffect = new DashPathEffect(
                new float[]{interval, interval, interval, interval}, 0);
        mTextBorderPaint.setPathEffect(dashPathEffect);

        mHelperPaint = new Paint();

        mTextCenterY = mHeight / 2;

        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();

        mGestureDetector = new GestureDetector(context, new SimpleOnGestureListener());
        mGestureDetector.setOnDoubleTapListener(new OnDoubleTapListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mTextRect.contains((int) e.getX(), (int) e.getY())
                        && mOnTextTapListener != null) {
                    mOnTextTapListener.onTextTap(mText);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();

                mDragging = false;

                if (mTextRect.contains((int) mLastTouchX, (int) mLastTouchY)) {
                    mShowDashRect = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float x = event.getX();
                final float y = event.getY();
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;
                if (!mDragging) {
                    mDragging = Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
                }

                if (mDragging) {
                    if (mOnDragTextListener != null) {
                        mOnDragTextListener.onDragText(mTextAlignment);
                    }

                    mTextCenterY += dy;

                    mLastTouchX = x;
                    mLastTouchY = y;

                    mShowDashRect = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mShowDashRect) {
                    mShowDashRect = false;
                    invalidate();
                }
                break;
        }

        mGestureDetector.onTouchEvent(event);

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(
                new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        if (mTextureBitmap != null && !mTextureBitmap.isRecycled()) {
            if (mBackgroundPaint == null) {
                mBackgroundPaint = new Paint();
            }
            int width = mTextureBitmap.getWidth();
            int height = mTextureBitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(getWidth() * 1.0F / width, getHeight() * 1.0F / height);
            canvas.drawBitmap(mTextureBitmap, matrix, mBackgroundPaint);
        } else if (mBackgroundBitmap != null && !mBackgroundBitmap.isRecycled()) {
            // TODO: 17/7/13 drawable photo as background
        } else {
            canvas.drawColor(mBackgroundColor);
        }

        boolean typefaceDefaultFlag = true;
        if (!TextUtils.isEmpty(mTypefaceUrl)) {
            if (mTypefaceUrl.equals(FAKE_DEFAULT_TYPEFACE_URI)) {
                mTextPaint.setTypeface(Typeface.DEFAULT);
            } else {
                Typeface typeface = TypefaceHelper.createTypeface(getContext(), mTypefaceUrl);
                if (typeface != null) {
                    mTextPaint.setTypeface(typeface);
                    mTempTypefaceUrl = null;
                    typefaceDefaultFlag = false;
                }
            }
        }
        if (typefaceDefaultFlag && !TextUtils.isEmpty(mTempTypefaceUrl)) {
            mTypefaceUrl = mTempTypefaceUrl;
        }

        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAlpha(mTextAlpha);

        FontMetricsInt fm = mTextPaint.getFontMetricsInt();

        int textHeight = fm.bottom - fm.top;

        Vector<String> textLinesVector = TextHelper
                .getTextLinesVector(mTextPaint, mText, TEXT_RECT_MAX_WIDTH, TEXT_RECT_MAX_HEIGHT);

        int lines = textLinesVector.size();

        mTextOffset = mTextCenterY - textHeight * lines / 2;

        if (mTextOffset < MIN_TEXT_OFFSET) {
            mTextOffset = MIN_TEXT_OFFSET;
            mTextCenterY = mTextOffset + textHeight * lines / 2;
        } else if (mTextOffset + lines * textHeight > MAX_TEXT_OFFSET) {
            mTextOffset = MAX_TEXT_OFFSET - lines * textHeight;
            mTextCenterY = mTextOffset + textHeight * lines / 2;
        }

        mTextBorder.top = mTextOffset - TEXT_PADDING_TOP_AND_BOTTOM;
        mTextBorder.bottom = mTextOffset + textHeight * lines + TEXT_PADDING_TOP_AND_BOTTOM;

        if (DEBUG) {
            mHelperPaint.setColor(Color.GRAY);
            canvas.drawRect(mTextBorder, mHelperPaint);
        }

        if (mShowDashRect) {
            Path textBorderPath = new Path();
            textBorderPath.moveTo(mTextBorder.left, mTextBorder.top);
            textBorderPath.lineTo(mTextBorder.right, mTextBorder.top);
            textBorderPath.lineTo(mTextBorder.right, mTextBorder.bottom);
            textBorderPath.lineTo(mTextBorder.left, mTextBorder.bottom);
            textBorderPath.close();
            canvas.drawPath(textBorderPath, mTextBorderPaint);
        }

        RandomColor randomColor = DEBUG ? new RandomColor() : null;
        for (int i = 0; i < textLinesVector.size(); i++) {
            mTextRect.top = mTextOffset + (i * textHeight);
            mTextRect.bottom = mTextRect.top + textHeight;

            if (randomColor != null) {
                mHelperPaint.setColor(randomColor.randomColor());
                canvas.drawRect(mTextRect, mHelperPaint);
            }

            float baseLineY = mTextRect.bottom - fm.bottom;
            float baseLineX;
            switch (mTextAlignment) {
                case TEXT_ALIGNMENT_LEFT:
                    mTextPaint.setTextAlign(Paint.Align.LEFT);
                    baseLineX = mTextRect.left;
                    break;
                case TEXT_ALIGNMENT_RIGHT:
                    mTextPaint.setTextAlign(Paint.Align.RIGHT);
                    baseLineX = mTextRect.right;
                    break;
                case TEXT_ALIGNMENT_CENTER:
                default:
                    mTextPaint.setTextAlign(Paint.Align.CENTER);
                    baseLineX = mWidth / 2;
                    break;
            }

            Timber.d("BaseLineX = %f, BaseLineY = %f", baseLineX, baseLineY);

            canvas.drawText(textLinesVector.elementAt(i), baseLineX, baseLineY, mTextPaint);
        }
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text) && !text.equals(mText)) {
            mText = text;
            invalidate();
        }
    }

    public String getTypefaceUrl() {
        return mTypefaceUrl;
    }

    public void setTypefaceUrl(String typefaceUrl) {
        if (!typefaceUrl.equals(mTypefaceUrl)) {
            mTempTypefaceUrl = mTypefaceUrl;
            mTypefaceUrl = typefaceUrl.trim();
            invalidate();
        }
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        if (textSize != mTextSize) {
            mTextSize = textSize;
            invalidate();
        }
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        if (textColor != mTextColor) {
            mTextColor = textColor;
            invalidate();
        }
    }

    public int getTextAlpha() {
        return mTextAlpha;
    }

    public void setTextAlpha(int textAlpha) {
        if (textAlpha != mTextAlpha) {
            mTextAlpha = textAlpha;
            invalidate();
        }
    }

    @Override
    @TextAlignment
    public int getTextAlignment() {
        return mTextAlignment;
    }

    @Override
    public void setTextAlignment(@TextAlignment int textAlignment) {
        if (textAlignment != mTextAlignment) {
            mTextAlignment = textAlignment;
            invalidate();
        }
    }

    public void resetTextLocation() {
        mTextCenterY = mHeight / 2;
        invalidate();
    }

    public Bitmap getTextureBitmap() {
        return mTextureBitmap;
    }

    public void setTextureBitmap(Bitmap textureBitmap) {
        if (textureBitmap != null && !textureBitmap.isRecycled()
                && textureBitmap != mTextureBitmap) {
            mTextureBitmap = textureBitmap;
            invalidate();
        }
    }

    public int getPagerBackgroundColor() {
        return mBackgroundColor;
    }

    public void setPagerBackgroundColor(int backgroundColor) {
        if (backgroundColor != mBackgroundColor) {
            mBackgroundColor = backgroundColor;
            invalidate();
        }
    }
}
