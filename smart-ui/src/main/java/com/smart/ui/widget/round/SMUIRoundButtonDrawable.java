package com.smart.ui.widget.round;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;

import com.smart.ui.R;

import androidx.annotation.Nullable;

/**
 * @author lichen
 * @date ：2019-07-16 22:14
 * @email : 196003945@qq.com
 * @description :
 */
public class SMUIRoundButtonDrawable extends GradientDrawable {

    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private boolean radiusAdjustBounds = true;
    private ColorStateList fillColors;
    private int strokeWidth = 0;
    private ColorStateList strokeColors;

    /**
     * 设置按钮的背景色(只支持纯色,不支持 Bitmap 或 Drawable)
     */
    public void setBgData(@Nullable ColorStateList colors) {
        if (hasNativeStateListAPI()) {
            super.setColor(colors);
        } else {
            fillColors = colors;
            final int currentColor;
            if (colors == null) {
                currentColor = Color.TRANSPARENT;
            } else {
                currentColor = colors.getColorForState(getState(), 0);
            }
            setColor(currentColor);
        }
    }

    /**
     * 设置按钮的描边粗细和颜色
     */
    public void setStrokeData(int width, @Nullable ColorStateList colors) {
        if (hasNativeStateListAPI()) {
            super.setStroke(width, colors);
        } else {
            strokeWidth = width;
            strokeColors = colors;
            final int currentColor;
            if (colors == null) {
                currentColor = Color.TRANSPARENT;
            } else {
                currentColor = colors.getColorForState(getState(), 0);
            }
            setStroke(width, currentColor);
        }
    }

    private boolean hasNativeStateListAPI() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 设置圆角大小是否自动适应为 View 的高度的一半
     */
    public void setIsRadiusAdjustBounds(boolean isRadiusAdjustBounds) {
        radiusAdjustBounds = isRadiusAdjustBounds;
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean superRet = super.onStateChange(stateSet);
        if (fillColors != null) {
            int color = fillColors.getColorForState(stateSet, 0);
            setColor(color);
            superRet = true;
        }
        if (strokeColors != null) {
            int color = strokeColors.getColorForState(stateSet, 0);
            setStroke(strokeWidth, color);
            superRet = true;
        }
        return superRet;
    }

    @Override
    public boolean isStateful() {
        return (fillColors != null && fillColors.isStateful())
                || (strokeColors != null && strokeColors.isStateful())
                || super.isStateful();
    }

    @Override
    protected void onBoundsChange(Rect r) {
        super.onBoundsChange(r);
        if (radiusAdjustBounds) {
            // 修改圆角为短边的一半
            setCornerRadius(Math.min(r.width(), r.height()) / 2);
        }
    }

    public static SMUIRoundButtonDrawable[] fromAttributeSet(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SMUIRoundButton, defStyleAttr, 0);
        ColorStateList colorBg = typedArray.getColorStateList(R.styleable.SMUIRoundButton_smui_backgroundColor);
        ColorStateList colorBorder = typedArray.getColorStateList(R.styleable.SMUIRoundButton_smui_borderColor);
        int borderWidth = typedArray.getDimensionPixelSize(R.styleable.SMUIRoundButton_smui_borderWidth, 0);
        boolean isRadiusAdjustBounds = typedArray.getBoolean(R.styleable.SMUIRoundButton_smui_isRadiusAdjustBounds, false);
        int mRadius = typedArray.getDimensionPixelSize(R.styleable.SMUIRoundButton_smui_radius, 0);
        int mRadiusTopLeft = typedArray.getDimensionPixelSize(R.styleable.SMUIRoundButton_smui_radiusTopLeft, 0);
        int mRadiusTopRight = typedArray.getDimensionPixelSize(R.styleable.SMUIRoundButton_smui_radiusTopRight, 0);
        int mRadiusBottomLeft = typedArray.getDimensionPixelSize(R.styleable.SMUIRoundButton_smui_radiusBottomLeft, 0);
        int mRadiusBottomRight = typedArray.getDimensionPixelSize(R.styleable.SMUIRoundButton_smui_radiusBottomRight, 0);
        typedArray.recycle();

        SMUIRoundButtonDrawable bg = new SMUIRoundButtonDrawable();
        bg.setBgData(colorBg);
        bg.setStrokeData(borderWidth, colorBorder);
        if (mRadiusTopLeft > 0 || mRadiusTopRight > 0 || mRadiusBottomLeft > 0 || mRadiusBottomRight > 0) {
            float[] radii = new float[]{
                    mRadiusTopLeft, mRadiusTopLeft,
                    mRadiusTopRight, mRadiusTopRight,
                    mRadiusBottomRight, mRadiusBottomRight,
                    mRadiusBottomLeft, mRadiusBottomLeft
            };
            bg.setCornerRadii(radii);
            isRadiusAdjustBounds = false;
        } else {
            bg.setCornerRadius(mRadius);
            if (mRadius > 0) {
                isRadiusAdjustBounds = false;
            }
        }
        bg.setIsRadiusAdjustBounds(isRadiusAdjustBounds);

        SMUIRoundButtonDrawable bgPressed;
        bgPressed = new SMUIRoundButtonDrawable();
        bgPressed.setBgData(colorBg);
        bgPressed.setStrokeData(borderWidth, colorBorder);
        if (mRadiusTopLeft > 0 || mRadiusTopRight > 0 || mRadiusBottomLeft > 0 || mRadiusBottomRight > 0) {
            float[] radii = new float[]{
                    mRadiusTopLeft, mRadiusTopLeft,
                    mRadiusTopRight, mRadiusTopRight,
                    mRadiusBottomRight, mRadiusBottomRight,
                    mRadiusBottomLeft, mRadiusBottomLeft
            };
            bgPressed.setCornerRadii(radii);
            isRadiusAdjustBounds = false;
        } else {
            bgPressed.setCornerRadius(mRadius);
            if (mRadius > 0) {
                isRadiusAdjustBounds = false;
            }
        }

        bgPressed.setIsRadiusAdjustBounds(isRadiusAdjustBounds);

        SMUIRoundButtonDrawable[] bgs = new SMUIRoundButtonDrawable[]{bg, bgPressed};
        return bgs;
    }
}
