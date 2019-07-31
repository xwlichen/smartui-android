package com.smart.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.smart.ui.layout.ISMUILayout;
import com.smart.ui.layout.SMUILayoutHelper;

/**
 * @author lichen
 * @date ：2019-07-31 21:25
 * @email : 196003945@qq.com
 * @description :
 */
public class SMUIFrameLayout extends FrameLayout implements ISMUILayout {
    private SMUILayoutHelper smuiLayoutHelper;

    public SMUIFrameLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SMUIFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SMUIFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        smuiLayoutHelper = new SMUILayoutHelper();
        smuiLayoutHelper.initAttrs(context, attrs);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        widthMeasureSpec = smuiLayoutHelper.getMeasuredWidthSpec(widthMeasureSpec);
//        heightMeasureSpec = smuiLayoutHelper.getMeasuredHeightSpec(heightMeasureSpec);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int minW = smuiLayoutHelper.handleMiniWidth(widthMeasureSpec, getMeasuredWidth());
//        int minH = smuiLayoutHelper.handleMiniHeight(heightMeasureSpec, getMeasuredHeight());
//        if (widthMeasureSpec != minW || heightMeasureSpec != minH) {
//            super.onMeasure(minW, minH);
//        }
//    }


    @Override
    public void setClipBg(boolean clipBackground) {

    }

    @Override
    public void setIsCircle(boolean roundAsCircle) {

    }

    @Override
    public void setRadius(int radius) {

    }

    @Override
    public void setTopLeftRadius(int topLeftRadius) {

    }

    @Override
    public void setTopRightRadius(int topRightRadius) {

    }

    @Override
    public void setBottomLeftRadius(int bottomLeftRadius) {

    }

    @Override
    public void setBottomRightRadius(int bottomRightRadius) {

    }

    @Override
    public void setBorderWidth(int strokeWidth) {

    }

    @Override
    public void setBorderColor(int strokeColor) {

    }

    @Override
    public boolean isClipBg() {
        return false;
    }

    @Override
    public boolean isCircle() {
        return false;
    }

    @Override
    public float getTopLeftRadius() {
        return 0;
    }

    @Override
    public float getTopRightRadius() {
        return 0;
    }

    @Override
    public float getBottomLeftRadius() {
        return 0;
    }

    @Override
    public float getBottomRightRadius() {
        return 0;
    }

    @Override
    public int getBorderWidth() {
        return 0;
    }

    @Override
    public int getBorderColor() {
        return 0;
    }
}
