package com.smart.ui.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.smart.ui.layout.SMUILayoutHelper;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @date : 2019-07-30 15:27
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUIImageView extends AppCompatImageView {

    private SMUILayoutHelper smuiLayoutHelper;
    private boolean isPressed;

    public SMUIImageView(Context context) {
        this(context, null);
    }

    public SMUIImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMUIImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        smuiLayoutHelper = new SMUILayoutHelper();
        smuiLayoutHelper.initAttrs(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        smuiLayoutHelper.onSizeChanged(this, w, h);

    }

    @Override
    public void draw(Canvas canvas) {
        if (smuiLayoutHelper.isClipBg()) {
            canvas.save();
            canvas.clipPath(smuiLayoutHelper.getClipPath());
            super.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(smuiLayoutHelper.getCanvasRectF(), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        smuiLayoutHelper.onClipDraw(canvas, this.isPressed);
        canvas.restore();
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        this.isPressed = pressed;
        invalidate();
    }
}
