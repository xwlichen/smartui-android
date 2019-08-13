package com.smart.ui.demo;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.SMUIButton;
import com.smart.ui.widget.SMUITopBar;
import com.smart.ui.widget.bottomnav.SMUIBottomNavView;
import com.smart.ui.widget.bottomnav.lottie.ILottieBottomNavViewCallback;
import com.smart.ui.widget.bottomnav.lottie.NavItem;
import com.smart.ui.widget.bottomnav.lottie.NavItemBuilder;
import com.smart.ui.widget.bottomnav.lottie.SMUILottieBottomNavView;
import com.smart.ui.widget.dialog.SMUITipDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity implements ILottieBottomNavViewCallback {
    List<NavItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        SMUIStatusBarHelper.translucent(this);
        SMUIStatusBarHelper.setStatusBarLightMode(this);

        SMUITopBar topBar = findViewById(R.id.topBar);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SMUIButton smuiButton = findViewById(R.id.roundBtn);

        smuiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMUITipDialog tipDialog = new SMUITipDialog.Builder(TestActivity.this)
                        .setIconType(SMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在加载")
                        .create();

                tipDialog.show();
            }
        });
//        smuiButton.setTypeface();


        SMUIBottomNavView SMUIBottomNavView = findViewById(R.id.bottomNavView);
        SMUIBottomNavView.enableItemShiftingMode(false);
        SMUIBottomNavView.enableShiftingMode(false);
        SMUIBottomNavView.enableAnimation(true);
        SMUIBottomNavView.setCurrentItem(0);


//        Typeface typeface=Typeface.create()
//        smartBottomNavView.setTypeface(Typeface.MONOSPACE, R.style.font_2);
        Paint paint = new Paint();
        TextView textView = findViewById(R.id.text);

        Typeface typeface = Typeface.create("res/font/font_kai.otf", Typeface.BOLD);
//        textView.setTextAppearance(R.style.font_2);
        textView.setTypeface(typeface);


        SMUILottieBottomNavView bottomNav = findViewById(R.id.bottom_nav);

        NavItem item1 = NavItemBuilder.create("discovery", "lottie/discovery_tab_select_anim.json", NavItem.Source.Assets, null)
                .selectedTextColor(Color.BLACK)
                .unSelectedTextColor(Color.GRAY)
                .pausedProgress(100)
                .autoPlay(false)
                .loop(false)
                .build();

//        NavItem item2 = NavItemBuilder.createFrom(item1)
////                .navTitle("Gifts")
////                .selectedLottieName("lottie/home_tab_select_anim.json")
////                .unSelectedLottieName("lottie/home_tab_select_anim.json")
////                .build();
////
////        NavItem item3 = NavItemBuilder.createFrom(item1)
////                .navTitle("Mail")
////                .selectedLottieName("lottie/msg_tab_select_anim.json")
////                .unSelectedLottieName("lottie/msg_tab_select_anim.json")
////                .build();
////
////        NavItem item4 = NavItemBuilder.createFrom(item1)
////                .navTitle("Settings")
////                .selectedLottieName("lottie/profile_tab_select_anim.json")
////                .unSelectedLottieName("lottie/profile_tab_select_anim.json")
////                .build();


        NavItem item2 = NavItemBuilder.create("home", "lottie/home_tab_select_anim.json", NavItem.Source.Assets, null)
                .selectedTextColor(Color.BLACK)
                .unSelectedTextColor(Color.GRAY)
                .pausedProgress(100)
                .autoPlay(false)
                .loop(false)
                .build();


        NavItem item3 = NavItemBuilder.create("msg", "lottie/msg_tab_select_anim.json", NavItem.Source.Assets, null)
                .selectedTextColor(Color.BLACK)
                .unSelectedTextColor(Color.GRAY)
                .pausedProgress(100)
                .autoPlay(false)
                .loop(false)
                .build();

        list = new ArrayList<>(2);
        list.add(item1);
        list.add(item2);
//        list.add(item3);
//        list.add(item4);

//        bottomNav.setCallback(this);
        bottomNav.setCallback(this);
        bottomNav.setMenuItemList(list);
//        bottomNav.setSelectedIndex(1);
    }

    @Override
    public void onMenuSelected(int oldIndex, int newIndex, NavItem menuItem) {

    }

    @Override
    public void onAnimationStart(int index, NavItem menuItem) {

    }

    @Override
    public void onAnimationEnd(int index, NavItem menuItem) {

    }

    @Override
    public void onAnimationCancel(int index, NavItem menuItem) {

    }
}


