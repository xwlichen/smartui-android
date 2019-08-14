package com.smart.ui.widget.bottomnav.lottie;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.smart.ui.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @date : 2019-08-13 11:13
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUILottieBottomNavView extends LinearLayout {

    private List<NavItem> navItemList;
    private ArrayList<LottieNavItemViewCreator> lottieViews;
    private ILottieBottomNavViewCallback callback;
    private NavConfig config;

    private int selectedIndex;
    private final Context context;

    public SMUILottieBottomNavView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        selectedIndex = 0;
        callback = new LottieBottomNavViewCallbackImpl();
        extractProperties(attrs);
    }

    /**
     * Assign the Nav items that are to be displayed
     *
     * @param navItemList List of Nav items
     */
    public void setNavItemList(@NonNull List<NavItem> navItemList) {

        this.navItemList = navItemList;
        lottieViews = new ArrayList<>(navItemList.size());

        prepareNavItems();
    }

    public void updateNavItemFor(int index, @NonNull NavItem navItem) {

        if (navItemList == null || index < 0 || index > navItemList.size()) {
            return;
        }

        lottieViews.get(index).getNavLottie().pauseAnimation();
        navItemList.set(index, navItem);

        LottieNavItemViewCreator lottieNavItemViewCreator = new LottieNavItemViewCreator();
        lottieNavItemViewCreator.initView(getContext());
        lottieNavItemViewCreator.init(navItem, selectedIndex == index, config);
        lottieViews.set(index, lottieNavItemViewCreator);

        removeViewAt(index);
        ViewGroup.LayoutParams params = lottieNavItemViewCreator.getNavContainer().getLayoutParams();
        params.width = (getWidth() / navItemList.size());
        lottieNavItemViewCreator.getNavContainer().setLayoutParams(params);
        lottieNavItemViewCreator.getNavContainer().setTag(index);
        lottieNavItemViewCreator.getNavLottie().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSelectedNav((int) v.getTag());
            }
        });
//        lottieNavItemViewCreator.getNavLottie().addAnimatorListener(animatorListener);
        if (!config.isShowTextOnUnselected()) {
            lottieNavItemViewCreator.getNavText().setVisibility(isSelected(index) ? View.VISIBLE : View.INVISIBLE);
        }
        addView(lottieNavItemViewCreator.getNavContainer(), index);
        lottieNavItemViewCreator.getNavLottie().setProgress(0F);
        lottieNavItemViewCreator.getNavLottie().playAnimation();
    }

    /**
     * Set the index of the to be displayed as selected
     *
     * @param index Index
     */
    public void setSelectedIndex(int index) {

        if (lottieViews == null || lottieViews.size() == 0 || selectedIndex == index) {
            return;
        }

        //Boundary checks
        if (index < 0) {
            index = 0;
        } else if (index >= lottieViews.size()) {
            index = lottieViews.size() - 1;
        }

        switchSelectedNav(index);
    }

    /**
     * Returns the currently selected menu index
     *
     * @return Index
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Returns the Nav item associated with the index
     *
     * @param index Index
     * @return NavItem
     */
    @Nullable
    public NavItem getNavItemFor(int index) {

        if (navItemList == null || index < 0 || index > navItemList.size()) {
            return null;
        }

        return navItemList.get(index);
    }

    /**
     * Sets the callback to listen for the Nav changes
     *
     * @param callback Callback
     */
    public void setCallback(@Nullable ILottieBottomNavViewCallback callback) {
        this.callback = callback == null ? new LottieBottomNavViewCallbackImpl() : callback;
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(HORIZONTAL);
    }

    @Override
    public void setGravity(int gravity) {
        super.setGravity(Gravity.CENTER);
    }

    private void prepareNavItems() {

        int index = 0;
        lottieViews.clear();

        for (NavItem navItem : navItemList) {
//
            final LottieNavItemViewCreator lottieNavItemViewCreator = new LottieNavItemViewCreator();
            lottieNavItemViewCreator.initView(getContext());
            ;
            lottieNavItemViewCreator.init(navItem, selectedIndex == index, config);

            lottieNavItemViewCreator.getNavContainer().setTag(index);
            lottieNavItemViewCreator.getNavContainer().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchSelectedNav((int) v.getTag());
                }
            });
//            lottieNavItemViewCreator.getNavLottie().addAnimatorListener(animatorListener);


            lottieViews.add(lottieNavItemViewCreator);
            index++;
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                updateUI();
            }
        });
    }

    private void switchSelectedNav(int newIndex) {

        if (newIndex == selectedIndex) {
            return;
        }

        //Pause any existing, or else it might impact
        lottieViews.get(selectedIndex).getNavLottie().pauseAnimation();

        LottieNavItemViewCreator lottieNavItemViewCreator = lottieViews.get(newIndex);

        NavItem navItem = navItemList.get(newIndex);
        lottieNavItemViewCreator.getNavLottie().setAnimation(navItem.getSelectedLottieName());
        lottieNavItemViewCreator.getNavText().setTextColor(navItem.getNavTextSelectedColor());

        lottieNavItemViewCreator.getNavLottie().playAnimation();

        ViewGroup.LayoutParams params = lottieNavItemViewCreator.getNavLottie().getLayoutParams();
        params.width = config.getSelectedNavWidth();
        params.height = config.getSelectedNavHeight();
        lottieNavItemViewCreator.getNavLottie().setLayoutParams(params);

        lottieNavItemViewCreator.getNavText().setVisibility(View.VISIBLE);

        callback.onNavSelected(selectedIndex, newIndex, navItem);

        //Set the unselected Nav item properties

        lottieNavItemViewCreator = lottieViews.get(selectedIndex);


        navItem = navItemList.get(selectedIndex);

        lottieNavItemViewCreator.getNavLottie().setImageResource(navItem.getUnSelectedIcon());
//        lottieNavItemViewCreator.getNavLottie().setAnimation(navItem.getSelectedLottieName());

        lottieNavItemViewCreator.getNavText().setTextColor(navItem.getNavTextUnselectedColor());

        lottieNavItemViewCreator.getNavLottie().pauseAnimation();
        lottieNavItemViewCreator.getNavLottie().setProgress(0);

        params = lottieNavItemViewCreator.getNavLottie().getLayoutParams();
        params.width = config.getSelectedNavWidth();
        params.height = config.getUnselectedNavHeight();
        lottieNavItemViewCreator.getNavLottie().setLayoutParams(params);

        if (!config.isShowTextOnUnselected()) {
            lottieNavItemViewCreator.getNavText().setVisibility(View.GONE);
        }

        selectedIndex = newIndex;
    }


    private void updateUI() {

        removeAllViews();

        int navItemWidth = getWidth() / navItemList.size();
//
        for (LottieNavItemViewCreator lottieNavItemViewCreator : lottieViews) {

            ViewGroup.LayoutParams params = lottieNavItemViewCreator.getNavContainer().getLayoutParams();
            if (params != null) {
                params.width = navItemWidth;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lottieNavItemViewCreator.getNavContainer().setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(navItemWidth, ViewGroup.LayoutParams.MATCH_PARENT);

                lottieNavItemViewCreator.getNavContainer().setLayoutParams(layoutParams);
            }

            addView(lottieNavItemViewCreator.getNavContainer());
        }
        invalidate();
    }

    private void extractProperties(@Nullable AttributeSet attributeSet) {

        config = new NavConfig(getContext());
        if (attributeSet == null) {
            return;
        }

        TypedArray properties = getContext().obtainStyledAttributes(attributeSet, R.styleable.SMUILottieBottomNavView);
        config.setSelectedNavWidth(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_nav_selected_width, -1));
        config.setSelectedNavHeight(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_nav_selected_height, -1));
        config.setUnselectedNavWidth(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_nav_unselected_width, -1));
        config.setUnselectedNavHeight(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_nav_unselected_height, -1));
        config.setNavTextSize(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_nav_text_size, -1));

        config.setShowTextOnUnselected(properties.getBoolean(R.styleable.SMUILottieBottomNavView_nav_text_show_on_unselected, true));
        properties.recycle();
    }

    private boolean isSelected(int index) {
        return selectedIndex == index;
    }

    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animator) {
            callback.onAnimationStart(selectedIndex, navItemList.get(selectedIndex));
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            callback.onAnimationEnd(selectedIndex, navItemList.get(selectedIndex));
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            callback.onAnimationCancel(selectedIndex, navItemList.get(selectedIndex));
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
            //
        }
    };


}
