package com.smart.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smart.ui.utils.SMUIWindowInsetHelper;

public class SMUITopBarLayout  extends  SMUIFrameLayout{
    private SMUITopBar mTopBar;
    public SMUITopBarLayout(Context context) {
        super(context);
    }

    public SMUITopBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SMUITopBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTopBar = new SMUITopBar(context, attrs, defStyleAttr);
        mTopBar.setBackground(null);
        mTopBar.setVisibility(View.VISIBLE);
        // reset these field because mTopBar will set same value with SMUITopBarLayout from attrs
        mTopBar.setFitsSystemWindows(false);
        mTopBar.setId(View.generateViewId());
        mTopBar.updateBottomDivider(0, 0, 0, 0);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mTopBar.getTopBarHeight());
        addView(mTopBar, lp);
        SMUIWindowInsetHelper.handleWindowInsets(this,
                WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout(), true);
    }


    public SMUITopBar getTopBar() {
        return mTopBar;
    }

    public void setCenterView(View view) {
        mTopBar.setCenterView(view);
    }

    public TextView setTitle(int resId) {
        return mTopBar.setTitle(resId);
    }

    public TextView setTitle(String title) {
        return mTopBar.setTitle(title);
    }

    public void showTitleView(boolean toShow) {
        mTopBar.showTitleView(toShow);
    }

    public void  setSubTitle(int resId) {
         mTopBar.setSubTitle(resId);
    }


    @Nullable
    public TextView getTitleView(){
        return mTopBar.getTitleView();
    }

    @Nullable
    public TextView getSubTitleView(){
        return mTopBar.getSubTitleView();
    }

    public void setTitleGravity(int gravity) {
        mTopBar.setTitleGravity(gravity);
    }

    public void addLeftView(View view, int viewId) {
        mTopBar.addLeftView(view, viewId);
    }

    public void addLeftView(View view, int viewId, RelativeLayout.LayoutParams layoutParams) {
        mTopBar.addLeftView(view, viewId, layoutParams);
    }

    public void addRightView(View view, int viewId) {
        mTopBar.addRightView(view, viewId);
    }

    public void addRightView(View view, int viewId, RelativeLayout.LayoutParams layoutParams) {
        mTopBar.addRightView(view, viewId, layoutParams);
    }

    public ImageView addRightImageButton(int drawableResId, int viewId) {
        return mTopBar.addRightImageButton(drawableResId, viewId);
    }


    public ImageView addLeftImageButton(int drawableResId, int viewId) {
        return mTopBar.addLeftImageButton(drawableResId, viewId);
    }


    public Button addLeftTextButton(int stringResId, int viewId) {
        return mTopBar.addLeftTextButton(stringResId, viewId);
    }

    public Button addLeftTextButton(String buttonText, int viewId) {
        return mTopBar.addLeftTextButton(buttonText, viewId);
    }

    public Button addRightTextButton(int stringResId, int viewId) {
        return mTopBar.addRightTextButton(stringResId, viewId);
    }

    public Button addRightTextButton(String buttonText, int viewId) {
        return mTopBar.addRightTextButton(buttonText, viewId);
    }

    public ImageView addLeftBackImageButton() {
        return mTopBar.addLeftBackImageButton();
    }

    public void removeAllLeftViews() {
        mTopBar.removeAllLeftViews();
    }

    public void removeAllRightViews() {
        mTopBar.removeAllRightViews();
    }

    public void removeCenterViewAndTitleView() {
        mTopBar.removeCenterViewAndTitleView();
    }

    /**
     * 设置 TopBar 背景的透明度
     *
     * @param alpha 取值范围：[0, 255]，255表示不透明
     */
    public void setBackgroundAlpha(int alpha) {
        this.getBackground().mutate().setAlpha(alpha);
    }

    /**
     * 根据当前 offset、透明度变化的初始 offset 和目标 offset，计算并设置 Topbar 的透明度
     *
     * @param currentOffset     当前 offset
     * @param alphaBeginOffset  透明度开始变化的offset，即当 currentOffset == alphaBeginOffset 时，透明度为0
     * @param alphaTargetOffset 透明度变化的目标offset，即当 currentOffset == alphaTargetOffset 时，透明度为1
     */
    public int computeAndSetBackgroundAlpha(int currentOffset, int alphaBeginOffset, int alphaTargetOffset) {
        double alpha = (float) (currentOffset - alphaBeginOffset) / (alphaTargetOffset - alphaBeginOffset);
        alpha = Math.max(0, Math.min(alpha, 1)); // from 0 to 1
        int alphaInt = (int) (alpha * 255);
        this.setBackgroundAlpha(alphaInt);
        return alphaInt;
    }




}
