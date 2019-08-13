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

    private List<NavItem> menuItemList;
    private ArrayList<LottieNavItemView> lottieViews;
    private ILottieBottomNavViewCallback callback;
    private NavConfig config;

    private int selectedIndex;

    public SMUILottieBottomNavView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        selectedIndex = 0;
        callback = new LottieBottomNavViewCallbackImpl();
        extractProperties(attrs);
    }

    /**
     * Assign the menu items that are to be displayed
     *
     * @param menuItemList List of menu items
     */
    public void setMenuItemList(@NonNull List<NavItem> menuItemList) {

        this.menuItemList = menuItemList;
        lottieViews = new ArrayList<>(menuItemList.size());

        prepareMenuItems();
    }

    public void updateMenuItemFor(int index, @NonNull NavItem menuItem) {

        if (menuItemList == null || index < 0 || index > menuItemList.size()) {
            return;
        }

        lottieViews.get(index).getNavLottie().pauseAnimation();
        menuItemList.set(index, menuItem);

        LottieNavItemView lottieNavItemView = new LottieNavItemView(getContext());
        lottieNavItemView.init(menuItem, selectedIndex == index, config);
        lottieViews.set(index, lottieNavItemView);

        removeViewAt(index);
        ViewGroup.LayoutParams params = lottieNavItemView.getNavContainer().getLayoutParams();
        params.width = (getWidth() / menuItemList.size());
        lottieNavItemView.getNavContainer().setLayoutParams(params);
        lottieNavItemView.setTag(index);
        lottieNavItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSelectedMenu((int) v.getTag());
            }
        });
        lottieNavItemView.getNavLottie().addAnimatorListener(animatorListener);
        if (!config.isShowTextOnUnselected()) {
            lottieNavItemView.getNavText().setVisibility(isSelected(index) ? View.VISIBLE : View.INVISIBLE);
        }
        addView(lottieNavItemView, index);
        lottieNavItemView.getNavLottie().setProgress(0F);
        lottieNavItemView.getNavLottie().playAnimation();
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

        switchSelectedMenu(index);
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
     * Returns the menu item associated with the index
     *
     * @param index Index
     * @return NavItem
     */
    @Nullable
    public NavItem getMenuItemFor(int index) {

        if (menuItemList == null || index < 0 || index > menuItemList.size()) {
            return null;
        }

        return menuItemList.get(index);
    }

    /**
     * Sets the callback to listen for the menu changes
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

    private void prepareMenuItems() {

        int index = 0;
        lottieViews.clear();

        for (NavItem menuItem : menuItemList) {
            LottieNavItemView lottieNavItemView = new LottieNavItemView(getContext());
            lottieNavItemView.init(menuItem, selectedIndex == index, config);

            lottieNavItemView.setTag(index);
            lottieNavItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchSelectedMenu((int) v.getTag());
                }
            });
            lottieNavItemView.getNavLottie().addAnimatorListener(animatorListener);

            if (index == selectedIndex) {
                lottieNavItemView.getNavLottie().setProgress(0F);
                lottieNavItemView.getNavLottie().playAnimation();
            }

            lottieViews.add(lottieNavItemView);
            index++;
        }

        if (getWidth() == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    updateUI();
                }
            });
        } else {
            updateUI();
        }
    }

    private void switchSelectedMenu(int newIndex) {

        if (newIndex == selectedIndex) {
            return;
        }

        //Pause any existing, or else it might impact
        lottieViews.get(selectedIndex).getNavLottie().pauseAnimation();

        LottieNavItemView lottieNavItemView = lottieViews.get(newIndex);
        NavItem menuItem = menuItemList.get(newIndex);

        lottieNavItemView.getNavLottie().setAnimation(menuItem.getSelectedLottieName());
        lottieNavItemView.getNavText().setTextColor(menuItem.getNavTextSelectedColor());

        lottieNavItemView.getNavLottie().playAnimation();

        ViewGroup.LayoutParams params = lottieNavItemView.getNavLottie().getLayoutParams();
        params.width = config.getSelectedMenuWidth();
        params.height = config.getSelectedMenuHeight();
        lottieNavItemView.getNavLottie().setLayoutParams(params);

        lottieNavItemView.getNavText().setVisibility(View.VISIBLE);

        callback.onMenuSelected(selectedIndex, newIndex, menuItem);

        //Set the unselected menu item properties

        lottieNavItemView = lottieViews.get(selectedIndex);
        menuItem = menuItemList.get(selectedIndex);

        lottieNavItemView.getNavLottie().setAnimation(menuItem.getUnselectedLottieName());
        lottieNavItemView.getNavText().setTextColor(menuItem.getNavTextUnselectedColor());

        lottieNavItemView.getNavLottie().pauseAnimation();
        lottieNavItemView.getNavLottie().setProgress(menuItem.getLottieProgress());

        params = lottieNavItemView.getNavLottie().getLayoutParams();
        params.width = config.getUnselectedMenuWidth();
        params.height = config.getUnselectedMenuHeight();
        lottieNavItemView.getNavLottie().setLayoutParams(params);

        if (!config.isShowTextOnUnselected()) {
            lottieNavItemView.getNavText().setVisibility(View.GONE);
        }

        selectedIndex = newIndex;
    }

    private void updateUI() {

        removeAllViews();

        int menuItemWidth = getWidth() / menuItemList.size();

        for (LottieNavItemView lottieNavItemView : lottieViews) {

            ViewGroup.LayoutParams params = lottieNavItemView.getNavContainer().getLayoutParams();
            params.width = menuItemWidth;
            lottieNavItemView.getNavContainer().setLayoutParams(params);

            addView(lottieNavItemView);
        }

        invalidate();
    }

    private void extractProperties(@Nullable AttributeSet attributeSet) {

        config = new NavConfig(getContext());
        if (attributeSet == null) {
            return;
        }

        TypedArray properties = getContext().obtainStyledAttributes(attributeSet, R.styleable.SMUILottieBottomNavView);
        config.setSelectedMenuWidth(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_menu_selected_width, -1));
        config.setSelectedMenuHeight(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_menu_selected_height, -1));
        config.setUnselectedMenuWidth(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_menu_unselected_width, -1));
        config.setUnselectedMenuHeight(properties.getDimensionPixelSize(R.styleable.SMUILottieBottomNavView_menu_unselected_height, -1));
        config.setShowTextOnUnselected(properties.getBoolean(R.styleable.SMUILottieBottomNavView_menu_text_show_on_unselected, true));
        properties.recycle();
    }

    private boolean isSelected(int index) {
        return selectedIndex == index;
    }

    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animator) {
            callback.onAnimationStart(selectedIndex, menuItemList.get(selectedIndex));
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            callback.onAnimationEnd(selectedIndex, menuItemList.get(selectedIndex));
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            callback.onAnimationCancel(selectedIndex, menuItemList.get(selectedIndex));
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
            //
        }
    };

}
