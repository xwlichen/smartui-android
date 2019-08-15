package com.smart.ui.widget.loading.indicators;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.smart.ui.LogUtils;
import com.smart.ui.widget.loading.Indicator;

import java.util.ArrayList;


public class LineScaleIndicator extends Indicator {

    private static final float SCALE = 1.0f;
    private boolean isfirst = true;
    private int progress;


    private float[] scaleYFloats = new float[]{0.4f,
            0.7f,
            1.0f,
            0.7f,
            0.4f,};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float translateX = getWidth() / 11f;
        float translateY = getHeight() / 2f;
        canvas.save();
        if (animators != null && isRunning()) {
            for (int i = 0; i < 5; i++) {

                canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
                canvas.scale(SCALE, scaleYFloats[i]);
                RectF rectF = new RectF(-translateX / 2, -getHeight() / 2.5f, translateX / 2, getHeight() / 2.5f);
                canvas.drawRoundRect(rectF, 5, 5, paint);
            }
        } else {
            int len = progress / 20;
            LogUtils.e("xw", "progress:" + progress);
            float scalePercent = 0;
            if (len > 1) {
                LogUtils.e("xw", "scale %:" + progress % (20 * (len - 1)));

                scalePercent = ((progress % (20 * (len - 1))) / 20f);
            }
            LogUtils.e("xw", "scalePercent:" + scalePercent);
            for (int i = 0; i < len; i++) {
                canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
                if (i == len - 1) {
                    canvas.scale(SCALE, scaleYFloats[i]);

                } else {

                    float scale = scaleYFloats[i] * scalePercent;
                    LogUtils.e("xw", "scale:" + scale);

                    canvas.scale(SCALE, scale);
                }


            }
        }
        canvas.restore();

    }


    public void setPercent(float percent) {
        progress = (int) (percent * 100f);
        if (progress >= 100) {
            progress = 100;
        }
        postInvalidate();

    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        isfirst = true;
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        long[] delays = new long[]{80, 160, 240, 300, 360};
        for (int i = 0; i < 5; i++) {
            final int index = i;
            ValueAnimator scaleAnim;
//            if (isfirst) {
//                scaleAnim = ValueAnimator.ofFloat(scaleYFloats[index], 0.6f, 1f);
//            } else {
            scaleAnim = ValueAnimator.ofFloat(0.4f, 1.0f, 0.4f);

//            }
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleYFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
        }
        isfirst = false;
        return animators;
    }


    @Override
    public void stop() {
        super.stop();

        scaleYFloats = new float[]{0.6f,
                0.8f,
                1.0f,
                0.8f,
                0.6f,};
    }
}
