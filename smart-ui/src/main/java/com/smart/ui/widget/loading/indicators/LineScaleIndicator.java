package com.smart.ui.widget.loading.indicators;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.smart.ui.widget.loading.Indicator;

import java.util.ArrayList;


public class LineScaleIndicator extends Indicator {

    private static final float SCALE = 1.0f;
    private int percent = -1;


    private float[] scaleYFloats = new float[]{0.4f,
            0.7f,
            1.0f,
            0.7f,
            0.4f,};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float translateX = getWidth() / 11f;
        float translateY = getHeight() / 2f;


        if (percent < 0) {
            for (int i = 0; i < 5; i++) {
                canvas.save();
                canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
                canvas.scale(SCALE, scaleYFloats[i]);
                RectF rectF = new RectF(-translateX / 2, -getHeight() / 2.5f, translateX / 2, getHeight() / 2.5f);
                canvas.drawRoundRect(rectF, 5, 5, paint);
                canvas.restore();
            }
        } else {

            int len = percent / 20;
            float scalePercent = (percent % (20) / 20f);
            for (int i = 0; i < len; i++) {
                canvas.save();
                canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
                float scale;
                if (i == len - 1) {
                    if (percent == 100) {
                        scalePercent = 1.0f;
                    }
                    scale = scaleYFloats[i] * scalePercent;

                } else {

                    scale = scaleYFloats[i];
                }
                canvas.scale(SCALE, scale);
                RectF rectF = new RectF(-translateX / 2, -getHeight() / 2.5f, translateX / 2, getHeight() / 2.5f);
                canvas.drawRoundRect(rectF, 5, 5, paint);
                canvas.restore();

            }
        }

    }


    public void setPercent(float percent) {
        this.percent = (int) (percent * 100f);
        if (this.percent >= 100) {
            this.percent = 100;
        }

        postInvalidate();

    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        long[] delays = new long[]{80, 160, 240, 300, 360};
//        long[] delays = new long[]{400,200,0,200,400};

        for (int i = 0; i < 5; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(0.3f, 1.0f, 0.3f);
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
