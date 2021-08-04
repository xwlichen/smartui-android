package com.smart.ui.widget.bottomnav.lottie;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.smart.ui.LogUtils;
import com.smart.ui.utils.SMUIDisplayHelper;

/**
 * @date : 2019-08-13 14:00
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LottieNavItemViewCreator {
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


    public TextView getNavText() {
        return navText;
    }

    public void setNavText(TextView navText) {
        this.navText = navText;
    }


    public void initView(Context context) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.smui_lottie_nav_item, this);
        this.context = context;
//        navContainer = view.findViewById(R.id.navContainer);
//        navLottie = (LottieAnimationView) navContainer.getChildAt(0);
//        navText = view.findViewById(R.id.navText);

//        navContainer = this;
//        navLottie = new LottieAnimationView(context);
//        navText = new TextView(context);
//
//        removeAllViews();
//        addView(navLottie);
//        addView(navText);


//        navContainer=this;
//        navLottie=new Lo


        navContainer = new LinearLayout(context);
        navLottie = new LottieAnimationView(context);
        navText = new TextView(context);

//        if (index == 0) {
//            navLottie.setId(R.id.smui_nav_lottie1);
//        } else if (index == 1) {
//            navLottie.setId(R.id.smui_nav_lottie2);
//        } else if (index == 2) {
//            navLottie.setId(R.id.smui_nav_lottie3);
//        }

        navContainer.removeAllViews();
        navContainer.setOrientation(LinearLayout.VERTICAL);
        navContainer.setGravity(Gravity.CENTER);
        navText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        navContainer.addView(navLottie);
        navContainer.addView(navText);


    }


    public void init(@NonNull NavItem navItem,
                     boolean isSelected,
                     @NonNull NavConfig config) {
        if (navContainer == null) {
            LogUtils.e("xw", "LottieNavItemViewCreator is not create");
            return;
        }


        navText.setText(navItem.getNavTitle());
        navText.setTextColor(isSelected ? navItem.getNavTextSelectedColor() : navItem.getNavTextUnselectedColor());
        navText.setTextSize(SMUIDisplayHelper.px2sp(context, config.getNavTextSize()));
        navText.setTypeface(config.getTypeface());


        ViewGroup.LayoutParams params = navLottie.getLayoutParams();
        params.width = isSelected ? config.getSelectedNavWidth() : config.getUnselectedNavWidth();
        params.height = isSelected ? config.getSelectedNavHeight() : config.getUnselectedNavHeight();
        navLottie.setLayoutParams(params);

        setLottieView(navLottie, navItem, isSelected);

        if (!config.isShowTextOnUnselected()) {
            navText.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        }


    }


    private void setLottieView(final LottieAnimationView view, NavItem navItem, boolean isSelected) {

        switch (navItem.getLottieSource()) {

            case Raw:
            case Assets:
                view.clearAnimation();
//                view.setImageResource(R.color.smui_config_color_transparent);


                if (isSelected) {
                    view.setAnimation(navItem.getSelectedLottieName());
                    view.pauseAnimation();
                    view.setProgress(navItem.getLottieProgress());
                } else {
//                    view.setAnimation(navItem.getSelectedLottieName());
                    view.pauseAnimation();
//                    view.setProgress(0);
                    view.setImageResource(navItem.getUnSelectedIcon());

                }
//
                break;
            default:
                break;
        }
    }


}
