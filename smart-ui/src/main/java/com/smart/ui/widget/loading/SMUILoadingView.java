package com.smart.ui.widget.loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.smart.ui.R;
import com.smart.ui.utils.SMUIDisplayHelper;

import androidx.annotation.NonNull;

/**
 * @date : 2019-08-12 15:24
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUILoadingView extends View {
    private int size;
    private int paintColor;
    private int aimateValue = 0;
    private ValueAnimator valueAnimator;
    private Paint paint;
    private static final int LINE_COUNT = 12;
    private static final int DEGREE_PER_LINE = 360 / LINE_COUNT;

    public SMUILoadingView(Context context) {
        this(context, null);
    }

    public SMUILoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SMUILoadingStyle);
    }

    public SMUILoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SMUILoadingView, defStyleAttr, 0);
        size = array.getDimensionPixelSize(R.styleable.SMUILoadingView_smui_loading_view_size, SMUIDisplayHelper.dp2px(context, 32));
        paintColor = array.getInt(R.styleable.SMUILoadingView_android_color, Color.WHITE);
        array.recycle();
        initPaint();
    }

    public SMUILoadingView(Context context, int size, int color) {
        super(context);
        this.size = size;
        paintColor = color;
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setColor(int color) {
        paintColor = color;
        paint.setColor(color);
        invalidate();
    }

    public void setSize(int size) {
        this.size = size;
        requestLayout();
    }

    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            aimateValue = (int) animation.getAnimatedValue();
            invalidate();
        }
    };

    public void start() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1);
            valueAnimator.addUpdateListener(mUpdateListener);
            valueAnimator.setDuration(600);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.start();
        } else if (!valueAnimator.isStarted()) {
            valueAnimator.start();
        }
    }

    public void stop() {
        if (valueAnimator != null) {
            valueAnimator.removeUpdateListener(mUpdateListener);
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
            valueAnimator = null;
        }
    }

    private void drawLoading(Canvas canvas, int rotateDegrees) {
        int width = size / 12, height = size / 6;
        paint.setStrokeWidth(width);

        canvas.rotate(rotateDegrees, size / 2, size / 2);
        canvas.translate(size / 2, size / 2);

        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.rotate(DEGREE_PER_LINE);
            paint.setAlpha((int) (255f * (i + 1) / LINE_COUNT));
            canvas.translate(0, -size / 2 + width / 2);
            canvas.drawLine(0, 0, 0, height, paint);
            canvas.translate(0, size / 2 - width / 2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        drawLoading(canvas, aimateValue * DEGREE_PER_LINE);
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }

}
