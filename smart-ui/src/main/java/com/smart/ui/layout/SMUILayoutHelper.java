package com.smart.ui.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.smart.ui.R;
import com.smart.ui.utils.SMUIResHelper;

import java.lang.ref.WeakReference;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

/**
 * @date : 2019-07-15 16:34
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUILayoutHelper implements ISMUILayout {

    private Context context;
    // size
    private int widthLimit = 0;
    private int heightLimit = 0;
    private int widthMini = 0;
    private int heightMini = 0;


    // divider
    private int topDividerHeight = 0;
    private int topDividerInsetLeft = 0;
    private int topDividerInsetRight = 0;
    private int topDividerColor;
    private int topDividerAlpha = 255;

    private int bottomDividerHeight = 0;
    private int bottomDividerInsetLeft = 0;
    private int bottomDividerInsetRight = 0;
    private int bottomDividerColor;
    private int bottomDividerAlpha = 255;

    private int leftDividerWidth = 0;
    private int leftDividerInsetTop = 0;
    private int leftDividerInsetBottom = 0;
    private int leftDividerColor;
    private int leftDividerAlpha = 255;

    private int rightDividerWidth = 0;
    private int rightDividerInsetTop = 0;
    private int rightDividerInsetBottom = 0;
    private int rightDividerColor;
    private int rightDividerAlpha = 255;
    private Paint dividerPaint;

    // round
    private Paint clipPaint;
    private PorterDuffXfermode mode;
    private int radiusValue;
    private @ISMUILayout.HideRadiusSide int mHideRadiusSide = HIDE_RADIUS_SIDE_NONE;
    private float[] radiusArray;
    private RectF borderRect;
    private int borderColor = 0;
    private int borderWidth = 1;
    private int outerNormalColor = 0;
    private WeakReference<View> owner;
    private boolean isOutlineExcludePadding = false;
    private Path path = new Path();

    // shadow
    private boolean isShowBorderOnlyBeforeL = true;
    private int shadowElevationValue = 0;
    private float shadowAlphaValue;
    private int shadowColorValue = Color.BLACK;

    // outline inset
    private int outlineInsetLeft = 0;
    private int outlineInsetRight = 0;
    private int outlineInsetTop = 0;
    private int outlineInsetBottom = 0;

    public SMUILayoutHelper(Context context, AttributeSet attrs, int defAttr, View owner) {
        context = context;
        this.owner = new WeakReference<>(owner);
        bottomDividerColor = topDividerColor =
                ContextCompat.getColor(context, R.color.smui_config_color_separator);
        mode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        clipPaint = new Paint();
        clipPaint.setAntiAlias(true);
        shadowAlphaValue = SMUIResHelper.getAttrFloatValue(context, R.attr.smui_general_shadow_alpha);
        borderRect = new RectF();

        int radius = 0, shadow = 0;
        boolean useThemeGeneralShadowElevation = false;
        if (null != attrs || defAttr != 0) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SMUILayout, defAttr, 0);
            int count = ta.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int index = ta.getIndex(i);
                if (index == R.styleable.SMUILayout_android_maxWidth) {
                    widthLimit = ta.getDimensionPixelSize(index, widthLimit);
                } else if (index == R.styleable.SMUILayout_android_maxHeight) {
                    heightLimit = ta.getDimensionPixelSize(index, heightLimit);
                } else if (index == R.styleable.SMUILayout_android_minWidth) {
                    widthMini = ta.getDimensionPixelSize(index, widthMini);
                } else if (index == R.styleable.SMUILayout_android_minHeight) {
                    heightMini = ta.getDimensionPixelSize(index, heightMini);
                } else if (index == R.styleable.SMUILayout_smui_topDividerColor) {
                    topDividerColor = ta.getColor(index, topDividerColor);
                } else if (index == R.styleable.SMUILayout_smui_topDividerHeight) {
                    topDividerHeight = ta.getDimensionPixelSize(index, topDividerHeight);
                } else if (index == R.styleable.SMUILayout_smui_topDividerInsetLeft) {
                    topDividerInsetLeft = ta.getDimensionPixelSize(index, topDividerInsetLeft);
                } else if (index == R.styleable.SMUILayout_smui_topDividerInsetRight) {
                    topDividerInsetRight = ta.getDimensionPixelSize(index, topDividerInsetRight);
                } else if (index == R.styleable.SMUILayout_smui_bottomDividerColor) {
                    bottomDividerColor = ta.getColor(index, bottomDividerColor);
                } else if (index == R.styleable.SMUILayout_smui_bottomDividerHeight) {
                    bottomDividerHeight = ta.getDimensionPixelSize(index, bottomDividerHeight);
                } else if (index == R.styleable.SMUILayout_smui_bottomDividerInsetLeft) {
                    bottomDividerInsetLeft = ta.getDimensionPixelSize(index, bottomDividerInsetLeft);
                } else if (index == R.styleable.SMUILayout_smui_bottomDividerInsetRight) {
                    bottomDividerInsetRight = ta.getDimensionPixelSize(index, bottomDividerInsetRight);
                } else if (index == R.styleable.SMUILayout_smui_leftDividerColor) {
                    leftDividerColor = ta.getColor(index, leftDividerColor);
                } else if (index == R.styleable.SMUILayout_smui_leftDividerWidth) {
                    leftDividerWidth = ta.getDimensionPixelSize(index, bottomDividerHeight);
                } else if (index == R.styleable.SMUILayout_smui_leftDividerInsetTop) {
                    leftDividerInsetTop = ta.getDimensionPixelSize(index, leftDividerInsetTop);
                } else if (index == R.styleable.SMUILayout_smui_leftDividerInsetBottom) {
                    leftDividerInsetBottom = ta.getDimensionPixelSize(index, leftDividerInsetBottom);
                } else if (index == R.styleable.SMUILayout_smui_rightDividerColor) {
                    rightDividerColor = ta.getColor(index, rightDividerColor);
                } else if (index == R.styleable.SMUILayout_smui_rightDividerWidth) {
                    rightDividerWidth = ta.getDimensionPixelSize(index, rightDividerWidth);
                } else if (index == R.styleable.SMUILayout_smui_rightDividerInsetTop) {
                    rightDividerInsetTop = ta.getDimensionPixelSize(index, rightDividerInsetTop);
                } else if (index == R.styleable.SMUILayout_smui_rightDividerInsetBottom) {
                    rightDividerInsetBottom = ta.getDimensionPixelSize(index, rightDividerInsetBottom);
                } else if (index == R.styleable.SMUILayout_smui_borderColor) {
                    borderColor = ta.getColor(index, borderColor);
                } else if (index == R.styleable.SMUILayout_smui_borderWidth) {
                    borderWidth = ta.getDimensionPixelSize(index, borderWidth);
                } else if (index == R.styleable.SMUILayout_smui_radius) {
                    radius = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SMUILayout_smui_outerNormalColor) {
                    outerNormalColor = ta.getColor(index, outerNormalColor);
                } else if (index == R.styleable.SMUILayout_smui_hideRadiusSide) {
                    mHideRadiusSide = ta.getColor(index, mHideRadiusSide);
                } else if (index == R.styleable.SMUILayout_smui_showBorderOnlyBeforeL) {
                    isShowBorderOnlyBeforeL = ta.getBoolean(index, isShowBorderOnlyBeforeL);
                } else if (index == R.styleable.SMUILayout_smui_shadowElevation) {
                    shadow = ta.getDimensionPixelSize(index, shadow);
                } else if (index == R.styleable.SMUILayout_smui_shadowAlpha) {
                    shadowAlphaValue = ta.getFloat(index, shadowAlphaValue);
                } else if (index == R.styleable.SMUILayout_smui_useThemeGeneralShadowElevation) {
                    useThemeGeneralShadowElevation = ta.getBoolean(index, false);
                } else if (index == R.styleable.SMUILayout_smui_outlineInsetLeft) {
                    outlineInsetLeft = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SMUILayout_smui_outlineInsetRight) {
                    outlineInsetRight = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SMUILayout_smui_outlineInsetTop) {
                    outlineInsetTop = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SMUILayout_smui_outlineInsetBottom) {
                    outlineInsetBottom = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SMUILayout_smui_outlineExcludePadding) {
                    isOutlineExcludePadding = ta.getBoolean(index, false);
                }
            }
            ta.recycle();
        }
        if (shadow == 0 && useThemeGeneralShadowElevation) {
            shadow = SMUIResHelper.getAttrDimen(context, R.attr.smui_general_shadow_elevation);

        }
        setRadiusAndShadow(radius, mHideRadiusSide, shadow, shadowAlphaValue);
    }

    @Override
    public void setUseThemeGeneralShadowElevation() {
        shadowElevationValue = SMUIResHelper.getAttrDimen(context, R.attr.smui_general_shadow_elevation);
        setRadiusAndShadow(radiusValue, mHideRadiusSide, shadowElevationValue, shadowAlphaValue);
    }

    @Override
    public void setOutlineExcludePadding(boolean outlineExcludePadding) {
        if (useFeature()) {
            View owner = this.owner.get();
            if (owner == null) {
                return;
            }
            isOutlineExcludePadding = outlineExcludePadding;
            owner.invalidateOutline();
        }

    }

    @Override
    public boolean setWidthLimit(int widthLimit) {
        if (widthLimit != widthLimit) {
            widthLimit = widthLimit;
            return true;
        }
        return false;
    }

    @Override
    public boolean setHeightLimit(int heightLimit) {
        if (heightLimit != heightLimit) {
            heightLimit = heightLimit;
            return true;
        }
        return false;
    }

    @Override
    public int getShadowElevation() {
        return shadowElevationValue;
    }

    @Override
    public float getShadowAlpha() {
        return shadowAlphaValue;
    }

    @Override
    public int getShadowColor() {
        return shadowColorValue;
    }

    @Override
    public void setOutlineInset(int left, int top, int right, int bottom) {
        if (useFeature()) {
            View owner = this.owner.get();
            if (owner == null) {
                return;
            }
            outlineInsetLeft = left;
            outlineInsetRight = right;
            outlineInsetTop = top;
            outlineInsetBottom = bottom;
            owner.invalidateOutline();
        }
    }


    @Override
    public void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL) {
        isShowBorderOnlyBeforeL = showBorderOnlyBeforeL;
        invalidate();
    }

    @Override
    public void setShadowElevation(int elevation) {
        if (shadowElevationValue == elevation) {
            return;
        }
        shadowElevationValue = elevation;
        invalidate();
    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {
        if (shadowAlphaValue == shadowAlpha) {
            return;
        }
        shadowAlphaValue = shadowAlpha;
        invalidate();
    }

    @Override
    public void setShadowColor(int shadowColor) {
        if (shadowColorValue == shadowColor) {
            return;
        }
        shadowColorValue = shadowColor;
        setShadowColorInner(shadowColorValue);
    }

    private void setShadowColorInner(int shadowColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View owner = this.owner.get();
            if (owner == null) {
                return;
            }
            owner.setOutlineAmbientShadowColor(shadowColor);
            owner.setOutlineSpotShadowColor(shadowColor);
        }
    }

    private void invalidate() {
        if (useFeature()) {
            View owner = this.owner.get();
            if (owner == null) {
                return;
            }
            if (shadowElevationValue == 0) {
                owner.setElevation(0);
            } else {
                owner.setElevation(shadowElevationValue);
            }
            owner.invalidateOutline();
        }
    }

    @Override
    public void setHideRadiusSide(@HideRadiusSide int hideRadiusSide) {
        if (mHideRadiusSide == hideRadiusSide) {
            return;
        }
        setRadiusAndShadow(radiusValue, hideRadiusSide, shadowElevationValue, shadowAlphaValue);
    }

    @Override
    public int getHideRadiusSide() {
        return mHideRadiusSide;
    }

    @Override
    public void setRadius(int radius) {
        if (radiusValue != radius) {
            setRadiusAndShadow(radius, shadowElevationValue, shadowAlphaValue);
        }
    }

    @Override
    public void setRadius(int radius, @ISMUILayout.HideRadiusSide int hideRadiusSide) {
        if (radiusValue == radius && hideRadiusSide == mHideRadiusSide) {
            return;
        }
        setRadiusAndShadow(radius, hideRadiusSide, shadowElevationValue, shadowAlphaValue);
    }

    @Override
    public int getRadius() {
        return radiusValue;
    }

    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, mHideRadiusSide, shadowElevation, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, @ISMUILayout.HideRadiusSide int hideRadiusSide, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowColorValue, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha) {
        final View owner = this.owner.get();
        if (owner == null) {
            return;
        }

        radiusValue = radius;
        mHideRadiusSide = hideRadiusSide;

        if (radiusValue > 0) {
            if (hideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                radiusArray = new float[]{0, 0, 0, 0, radiusValue, radiusValue, radiusValue, radiusValue};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                radiusArray = new float[]{radiusValue, radiusValue, 0, 0, 0, 0, radiusValue, radiusValue};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                radiusArray = new float[]{radiusValue, radiusValue, radiusValue, radiusValue, 0, 0, 0, 0};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                radiusArray = new float[]{0, 0, radiusValue, radiusValue, radiusValue, radiusValue, 0, 0};
            } else {
                radiusArray = null;
            }
        }

        shadowElevationValue = shadowElevation;
        shadowAlphaValue = shadowAlpha;
        shadowColorValue = shadowColor;
        if (useFeature()) {
            if (shadowElevationValue == 0 || isRadiusWithSideHidden()) {
                owner.setElevation(0);
            } else {
                owner.setElevation(shadowElevationValue);
            }

            setShadowColorInner(shadowColorValue);

            owner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    int w = view.getWidth(), h = view.getHeight();
                    if (w == 0 || h == 0) {
                        return;
                    }
                    if (isRadiusWithSideHidden()) {
                        int left = 0, top = 0, right = w, bottom = h;
                        if (mHideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                            left -= radiusValue;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                            top -= radiusValue;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                            right += radiusValue;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                            bottom += radiusValue;
                        }
                        outline.setRoundRect(left, top,
                                right, bottom, radiusValue);
                        return;
                    }

                    int top = outlineInsetTop, bottom = Math.max(top + 1, h - outlineInsetBottom),
                            left = outlineInsetLeft, right = w - outlineInsetRight;
                    if (isOutlineExcludePadding) {
                        left += view.getPaddingLeft();
                        top += view.getPaddingTop();
                        right = Math.max(left + 1, right - view.getPaddingRight());
                        bottom = Math.max(top + 1, bottom - view.getPaddingBottom());
                    }

                    float shadowAlpha = shadowAlphaValue;
                    if (shadowElevationValue == 0) {
                        // outline.setAlpha will work even if shadowElevation == 0
                        shadowAlpha = 1f;
                    }

                    outline.setAlpha(shadowAlpha);

                    if (radiusValue <= 0) {
                        outline.setRect(left, top,
                                right, bottom);
                    } else {
                        outline.setRoundRect(left, top,
                                right, bottom, radiusValue);
                    }
                }
            });
            owner.setClipToOutline(radiusValue > 0);

        }
        owner.invalidate();
    }

    /**
     * 有radius, 但是有一边不显示radius。
     *
     * @return
     */
    public boolean isRadiusWithSideHidden() {
        return radiusValue > 0 && mHideRadiusSide != HIDE_RADIUS_SIDE_NONE;
    }

    @Override
    public void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        topDividerInsetLeft = topInsetLeft;
        topDividerInsetRight = topInsetRight;
        topDividerHeight = topDividerHeight;
        topDividerColor = topDividerColor;
    }

    @Override
    public void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        bottomDividerInsetLeft = bottomInsetLeft;
        bottomDividerInsetRight = bottomInsetRight;
        bottomDividerColor = bottomDividerColor;
        bottomDividerHeight = bottomDividerHeight;
    }

    @Override
    public void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        leftDividerInsetTop = leftInsetTop;
        leftDividerInsetBottom = leftInsetBottom;
        leftDividerWidth = leftDividerWidth;
        leftDividerColor = leftDividerColor;
    }

    @Override
    public void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        rightDividerInsetTop = rightInsetTop;
        rightDividerInsetBottom = rightInsetBottom;
        rightDividerWidth = rightDividerWidth;
        rightDividerColor = rightDividerColor;
    }

    @Override
    public void onlyShowTopDivider(int topInsetLeft, int topInsetRight,
                                   int topDividerHeight, int topDividerColor) {
        updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        leftDividerWidth = 0;
        rightDividerWidth = 0;
        bottomDividerHeight = 0;
    }

    @Override
    public void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight,
                                      int bottomDividerHeight, int bottomDividerColor) {
        updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        leftDividerWidth = 0;
        rightDividerWidth = 0;
        topDividerHeight = 0;
    }

    @Override
    public void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        rightDividerWidth = 0;
        topDividerHeight = 0;
        bottomDividerHeight = 0;
    }

    @Override
    public void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        leftDividerWidth = 0;
        topDividerHeight = 0;
        bottomDividerHeight = 0;
    }

    @Override
    public void setTopDividerAlpha(int dividerAlpha) {
        topDividerAlpha = dividerAlpha;
    }

    @Override
    public void setBottomDividerAlpha(int dividerAlpha) {
        bottomDividerAlpha = dividerAlpha;
    }

    @Override
    public void setLeftDividerAlpha(int dividerAlpha) {
        leftDividerAlpha = dividerAlpha;
    }

    @Override
    public void setRightDividerAlpha(int dividerAlpha) {
        rightDividerAlpha = dividerAlpha;
    }


    public int handleMiniWidth(int widthMeasureSpec, int measuredWidth) {
        if (View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredWidth < widthMini) {
            return View.MeasureSpec.makeMeasureSpec(widthMini, View.MeasureSpec.EXACTLY);
        }
        return widthMeasureSpec;
    }

    public int handleMiniHeight(int heightMeasureSpec, int measuredHeight) {
        if (View.MeasureSpec.getMode(heightMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredHeight < heightMini) {
            return View.MeasureSpec.makeMeasureSpec(heightMini, View.MeasureSpec.EXACTLY);
        }
        return heightMeasureSpec;
    }

    public int getMeasuredWidthSpec(int widthMeasureSpec) {
        if (widthLimit > 0) {
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            if (size > widthLimit) {
                int mode = View.MeasureSpec.getMode(widthMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthLimit, View.MeasureSpec.EXACTLY);
                }

            }
        }
        return widthMeasureSpec;
    }

    public int getMeasuredHeightSpec(int heightMeasureSpec) {
        if (heightLimit > 0) {
            int size = View.MeasureSpec.getSize(heightMeasureSpec);
            if (size > heightLimit) {
                int mode = View.MeasureSpec.getMode(heightMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthLimit, View.MeasureSpec.EXACTLY);
                }
            }
        }
        return heightMeasureSpec;
    }

    @Override
    public void setBorderColor(@ColorInt int borderColor) {
        borderColor = borderColor;
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        borderWidth = borderWidth;
    }

    @Override
    public void setOuterNormalColor(int color) {
        outerNormalColor = color;
        View owner = this.owner.get();
        if (owner != null) {
            owner.invalidate();
        }
    }

    public void drawDividers(Canvas canvas, int w, int h) {
        if (dividerPaint == null &&
                (topDividerHeight > 0 || bottomDividerHeight > 0 || leftDividerWidth > 0 || rightDividerWidth > 0)) {
            dividerPaint = new Paint();
        }
        if (topDividerHeight > 0) {
            dividerPaint.setStrokeWidth(topDividerHeight);
            dividerPaint.setColor(topDividerColor);
            if (topDividerAlpha < 255) {
                dividerPaint.setAlpha(topDividerAlpha);
            }
            float y = topDividerHeight * 1f / 2;
            canvas.drawLine(topDividerInsetLeft, y, w - topDividerInsetRight, y, dividerPaint);
        }

        if (bottomDividerHeight > 0) {
            dividerPaint.setStrokeWidth(bottomDividerHeight);
            dividerPaint.setColor(bottomDividerColor);
            if (bottomDividerAlpha < 255) {
                dividerPaint.setAlpha(bottomDividerAlpha);
            }
            float y = (float) Math.floor(h - bottomDividerHeight * 1f / 2);
            canvas.drawLine(bottomDividerInsetLeft, y, w - bottomDividerInsetRight, y, dividerPaint);
        }

        if (leftDividerWidth > 0) {
            dividerPaint.setStrokeWidth(leftDividerWidth);
            dividerPaint.setColor(leftDividerColor);
            if (leftDividerAlpha < 255) {
                dividerPaint.setAlpha(leftDividerAlpha);
            }
            canvas.drawLine(0, leftDividerInsetTop, 0, h - leftDividerInsetBottom, dividerPaint);
        }

        if (rightDividerWidth > 0) {
            dividerPaint.setStrokeWidth(rightDividerWidth);
            dividerPaint.setColor(rightDividerColor);
            if (rightDividerAlpha < 255) {
                dividerPaint.setAlpha(rightDividerAlpha);
            }
            canvas.drawLine(w, rightDividerInsetTop, w, h - rightDividerInsetBottom, dividerPaint);
        }
    }


    public void dispatchRoundBorderDraw(Canvas canvas) {
        View owner = this.owner.get();
        if (owner == null) {
            return;
        }
        if (borderColor == 0 && (radiusValue == 0 || outerNormalColor == 0)) {
            return;
        }

        if (isShowBorderOnlyBeforeL && useFeature() && shadowElevationValue != 0) {
            return;
        }

        int width = canvas.getWidth(), height = canvas.getHeight();

        // react
        if (isOutlineExcludePadding) {
            borderRect.set(1 + owner.getPaddingLeft(), 1 + owner.getPaddingTop(),
                    width - 1 - owner.getPaddingRight(), height - 1 - owner.getPaddingBottom());
        } else {
            borderRect.set(1, 1, width - 1, height - 1);
        }

        if (radiusValue == 0 || (!useFeature() && outerNormalColor == 0)) {
            clipPaint.setStyle(Paint.Style.STROKE);
            clipPaint.setColor(borderColor);
            canvas.drawRect(borderRect, clipPaint);
            return;
        }

        // 圆角矩形
        if (!useFeature()) {
            int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawColor(outerNormalColor);
            clipPaint.setColor(outerNormalColor);
            clipPaint.setStyle(Paint.Style.FILL);
            clipPaint.setXfermode(mode);
            if (radiusArray == null) {
                canvas.drawRoundRect(borderRect, radiusValue, radiusValue, clipPaint);
            } else {
                drawRoundRect(canvas, borderRect, radiusArray, clipPaint);
            }
            clipPaint.setXfermode(null);
            canvas.restoreToCount(layerId);
        }

        clipPaint.setColor(borderColor);
        clipPaint.setStrokeWidth(borderWidth);
        clipPaint.setStyle(Paint.Style.STROKE);
        if (radiusArray == null) {
            canvas.drawRoundRect(borderRect, radiusValue, radiusValue, clipPaint);
        } else {
            drawRoundRect(canvas, borderRect, radiusArray, clipPaint);
        }
    }

    private void drawRoundRect(Canvas canvas, RectF rect, float[] radiusArray, Paint paint) {
        path.reset();
        path.addRoundRect(rect, radiusArray, Path.Direction.CW);
        canvas.drawPath(path, paint);

    }

    public static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }
}