package com.smart.ui.widget.loading;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.smart.ui.LogUtils;
import com.smart.ui.R;
import com.smart.ui.widget.loading.indicators.LineScaleIndicator;

/**
 * @date : 2019-08-15 14:11
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUILoadingIndicatorView extends View {

    private static final String TAG = "SMUILoadingIndicator";

    private static final LineScaleIndicator DEFAULT_INDICATOR = new LineScaleIndicator();

    private static final int MIN_SHOW_TIME = 500; // ms
    private static final int MIN_DELAY = 500; // ms

    private long startTime = -1;

    private boolean postedHide = false;

    private boolean postedShow = false;

    private boolean dismissed = false;

    private final Runnable delayedHide = new Runnable() {

        @Override
        public void run() {
            postedHide = false;
            startTime = -1;
            setVisibility(View.GONE);
        }
    };

    private final Runnable delayedShow = new Runnable() {

        @Override
        public void run() {
            postedShow = false;
            if (!dismissed) {
                startTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
            }
        }
    };

    int minWidth;
    int minHeight;

    private Indicator indicator;
    private int indicatorColor;

    private boolean shouldStartAnimationDrawable;

    public SMUILoadingIndicatorView(Context context) {
        this(context, null);
    }

    public SMUILoadingIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMUILoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.SMUILoadingIndicatorStyle);
        init(context, attrs, R.attr.SMUILoadingIndicatorStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {


        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.SMUILoadingIndicatorView, defStyleAttr, 0);

        String indicatorName = a.getString(R.styleable.SMUILoadingIndicatorView_smui_indicatorName);
        indicatorColor = a.getColor(R.styleable.SMUILoadingIndicatorView_smui_indicatorColor, Color.WHITE);
        setIndicator(indicatorName);
        if (indicator == null) {
            setIndicator(DEFAULT_INDICATOR);
        }
        a.recycle();
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator d) {
        if (indicator != d) {
            if (indicator != null) {
                indicator.setCallback(null);
                unscheduleDrawable(indicator);
            }

            indicator = d;
            //need to set indicator color again if you didn't specified when you update the indicator .
            setIndicatorColor(indicatorColor);
            if (d != null) {
                d.setCallback(this);
            }
            postInvalidate();
        }
    }


    /**
     * setIndicatorColor(0xFF00FF00)
     * or
     * setIndicatorColor(Color.BLUE)
     * or
     * setIndicatorColor(Color.parseColor("#FF4081"))
     * or
     * setIndicatorColor(0xFF00FF00)
     * or
     * setIndicatorColor(getResources().getColor(android.R.color.black))
     *
     * @param color 颜色
     */
    public void setIndicatorColor(int color) {
        this.indicatorColor = color;
        indicator.setColor(color);
    }


    /**
     * You should pay attention to pass this parameter with two way:
     * for example:
     * 1. Only class Name,like "SimpleIndicator".(This way would use default package name with
     * "com.wang.avi.indicators")
     * 2. Class name with full package,like "com.my.android.indicators.SimpleIndicator".
     *
     * @param indicatorName the class must be extend Indicator .
     */
    public void setIndicator(String indicatorName) {
        if (TextUtils.isEmpty(indicatorName)) {
            return;
        }
        StringBuilder drawableClassName = new StringBuilder();
        if (!indicatorName.contains(".")) {
            String defaultPackageName = getClass().getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(indicatorName);
        try {
            Class<?> drawableClass = Class.forName(drawableClassName.toString());
            Indicator indicator = (Indicator) drawableClass.newInstance();
            setIndicator(indicator);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Didn't find your class , check the name again !");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void smoothToShow() {
        startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        setVisibility(VISIBLE);
    }

    public void smoothToHide() {
        startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        setVisibility(GONE);
    }

    public void hide() {
        dismissed = true;
        removeCallbacks(delayedShow);
        long diff = System.currentTimeMillis() - startTime;
        if (diff >= MIN_SHOW_TIME || startTime == -1) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            setVisibility(View.GONE);
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!postedHide) {
                postDelayed(delayedHide, MIN_SHOW_TIME - diff);
                postedHide = true;
            }
        }
    }

    public void show() {
        // Reset the start time.
        startTime = -1;
        dismissed = false;
        removeCallbacks(delayedHide);
        if (!postedShow) {
            postDelayed(delayedShow, MIN_DELAY);
            postedShow = true;
        }
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return who == indicator
                || super.verifyDrawable(who);
    }

    public void startAnimation() {
        if (getVisibility() != VISIBLE || indicator == null) {
            return;
        }

        shouldStartAnimationDrawable = true;

        postInvalidate();
    }

    public void stopAnimation() {
        indicator.stop();
        shouldStartAnimationDrawable = false;

        postInvalidate();
    }

    public boolean isRunning() {
        if (indicator == null) {
            return false;
        }

        return indicator.isRunning();

    }


    /**
     * 设置百分比 1.0f
     *
     * @param percent  LineScaleIndicator 的根据百分比进行显示不同程度的竖线
     */
    public void setPercent(float percent) {
        stopAnimation();
        if (indicator != null) {
            if (indicator instanceof LineScaleIndicator) {
                ((LineScaleIndicator) indicator).setPercent(percent);
            }
        }
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                stopAnimation();
            } else {
                startAnimation();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopAnimation();
            LogUtils.e("lc", "gone");
        } else {
            LogUtils.e("lc", "visble");

            startAnimation();
        }
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable drawable) {
        if (verifyDrawable(drawable)) {
            final Rect dirty = drawable.getBounds();
            final int scrollx = getScrollX() + getPaddingLeft();
            final int scrolly = getScrollY() + getPaddingTop();

            invalidate(dirty.left + scrollx, dirty.top + scrolly,
                    dirty.right + scrollx, dirty.bottom + scrolly);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateDrawableBounds(w, h);
    }

    private void updateDrawableBounds(int w, int h) {
        // onDraw will translate the canvas so we draw starting at 0,0.
        // Subtract out padding for the purposes of the calculations below.
        w -= getPaddingRight() + getPaddingLeft();
        h -= getPaddingTop() + getPaddingBottom();

        int right = w;
        int bottom = h;
        int top = 0;
        int left = 0;

        if (indicator != null) {
            // Maintain aspect ratio. Certain kinds of animated drawables
            // get very confused otherwise.
            final int intrinsicWidth = indicator.getIntrinsicWidth();
            final int intrinsicHeight = indicator.getIntrinsicHeight();
            final float intrinsicAspect = (float) intrinsicWidth / intrinsicHeight;
            final float boundAspect = (float) w / h;
            float diff = 1e-6f;
            if (Math.abs(intrinsicAspect - boundAspect) > diff) {
                if (boundAspect > intrinsicAspect) {
                    // New width is larger. Make it smaller to match height.
                    final int width = (int) (h * intrinsicAspect);
                    left = (w - width) / 2;
                    right = left + width;
                } else {
                    // New height is larger. Make it smaller to match width.
                    final int height = (int) (w * (1 / intrinsicAspect));
                    top = (h - height) / 2;
                    bottom = top + height;
                }
            }
            indicator.setBounds(left, top, right, bottom);
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
    }

    void drawTrack(Canvas canvas) {
        final Drawable d = indicator;
        if (d != null) {
            // Translate canvas so a indeterminate circular progress bar with padding
            // rotates properly in its animation
            final int saveCount = canvas.save();

            canvas.translate(getPaddingLeft(), getPaddingTop());

            d.draw(canvas);
            canvas.restoreToCount(saveCount);

            if (shouldStartAnimationDrawable) {
                ((Animatable) d).start();
                shouldStartAnimationDrawable = false;
            }
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = 0;
        int dh = 0;
        minWidth = getMinimumWidth();
        minHeight = getMinimumHeight();

//        final Drawable d = indicator;
//        if (d != null) {
//            dw = Math.max(minWidth, d.getIntrinsicWidth());
//            dh = Math.max(minHeight, d.getIntrinsicHeight());
//        }
//
//        updateDrawableState();
//
//        dw += getPaddingLeft() + getPaddingRight();
//        dh += getPaddingTop() + getPaddingBottom();
//
//        final int measuredWidth = resolveSizeAndState(dw, widthMeasureSpec, 0);
//        final int measuredHeight = resolveSizeAndState(dh, heightMeasureSpec, 0);
//        setMeasuredDimension(measuredWidth, measuredHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        final int[] state = getDrawableState();
        if (indicator != null && indicator.isStateful()) {
            indicator.setState(state);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);

        if (indicator != null) {
            indicator.setHotspot(x, y);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
        removeCallbacks();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimation();
        // This should come after stopAnimation(), otherwise an invalidate message remains in the
        // queue, which can prevent the entire view hierarchy from being GC'ed during a rotation
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(delayedHide);
        removeCallbacks(delayedShow);
    }

}
