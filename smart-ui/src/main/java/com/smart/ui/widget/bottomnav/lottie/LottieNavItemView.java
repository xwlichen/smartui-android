package com.smart.ui.widget.bottomnav.lottie;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.smart.ui.LogUtils;
import com.smart.ui.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @date : 2019-08-13 14:00
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LottieNavItemView extends LinearLayout {
    protected Context context;

    private LinearLayout navContainer;
    private LottieAnimationView navLottie;
    private TextView navText;


    public LinearLayout getNavContainer() {
        return navContainer;
    }

    public void setNavContainer(LinearLayout navContainer) {
        this.navContainer = navContainer;
    }

    public LottieAnimationView getNavLottie() {
        return navLottie;
    }

    public void setNavLottie(LottieAnimationView navLottie) {
        this.navLottie = navLottie;
    }

    public TextView getNavText() {
        return navText;
    }

    public void setNavText(TextView navText) {
        this.navText = navText;
    }

    public LottieNavItemView(Context context) {
        this(context, null);
    }

    public LottieNavItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LottieNavItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.smui_lottie_nav_item, this);
        navContainer = view.findViewById(R.id.navContainer);
        navLottie = view.findViewById(R.id.navLottie);
        navText = view.findViewById(R.id.navText);
    }


    public void init(@NonNull NavItem navItem,
                     boolean isSelected,
                     @NonNull NavConfig config) {
        if (navContainer == null) {
            LogUtils.e("LottieNavItemView is not initView()");
            return;
        }


        navText.setText(navItem.getNavTitle());
        navText.setTextColor(isSelected ? navItem.getNavTextSelectedColor() : navItem.getNavTextUnselectedColor());

        setLottieView(navLottie, navItem, isSelected);

        ViewGroup.LayoutParams params = navLottie.getLayoutParams();
        params.width = isSelected ? config.getSelectedMenuWidth() : config.getUnselectedMenuWidth();
        params.height = isSelected ? config.getSelectedMenuHeight() : config.getUnselectedMenuHeight();
        navLottie.setLayoutParams(params);

        if (!config.isShowTextOnUnselected()) {
            navText.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        }

    }


    private void setLottieView(LottieAnimationView view, NavItem navItem, boolean isSelected) {

        switch (navItem.getLottieSource()) {

            case Raw:
            case Assets:
                view.setAnimation(isSelected ? navItem.getSelectedLottieName() : navItem.getUnselectedLottieName());
                view.pauseAnimation();
                view.setProgress(navItem.getLottieProgress());
                break;
            default:
                break;
        }
    }


}
