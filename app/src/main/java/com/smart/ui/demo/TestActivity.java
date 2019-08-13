package com.smart.ui.demo;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.SMUIButton;
import com.smart.ui.widget.SMUITopBar;
import com.smart.ui.widget.bottomnav.SMUIBottomNavView;
import com.smart.ui.widget.bottomnav.lottie.NavItem;
import com.smart.ui.widget.bottomnav.lottie.NavItemBuilder;
import com.smart.ui.widget.bottomnav.lottie.SMUILottieBottomNavView;
import com.smart.ui.widget.dialog.SMUITipDialog;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
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

        NavItem item1 = NavItemBuilder.create("发现", "lottie/discovery_tab_select_anim.json", R.mipmap.ic_tab_discovery_normal, NavItem.Source.Assets, null)
                .selectedTextColor(Color.BLACK)
                .unSelectedTextColor(Color.GRAY)
                .pausedProgress(100)
                .autoPlay(false)
                .loop(false)
                .build();

        NavItem item2 = NavItemBuilder.createFrom(item1)
                .navTitle("音视")
                .selectedLottieName("lottie/live_tab_select_anim.json")
                .unSelectedIcon(R.mipmap.ic_tab_live_normal)
                .build();

        NavItem item3 = NavItemBuilder.createFrom(item1)
                .navTitle("消息")
                .selectedLottieName("lottie/msg_tab_select_anim.json")
                .unSelectedIcon(R.mipmap.ic_tab_message_normal)
                .build();

        NavItem item4 = NavItemBuilder.createFrom(item1)
                .navTitle("我的")
                .selectedLottieName("lottie/profile_tab_select_anim.json")
                .unSelectedIcon(R.mipmap.ic_tab_me_normal)
                .build();


//        NavItem item2 = NavItemBuilder.create("home", "lottie/home_tab_select_anim.json", NavItem.Source.Assets, null)
//                .selectedTextColor(Color.BLACK)
//                .unSelectedTextColor(Color.GRAY)
//                .pausedProgress(100)
//                .autoPlay(false)
//                .loop(false)
//                .build();
//
//
//        NavItem item3 = NavItemBuilder.create("msg", "lottie/msg_tab_select_anim.json", NavItem.Source.Assets, null)
//                .selectedTextColor(Color.BLACK)
//                .unSelectedTextColor(Color.GRAY)
//                .pausedProgress(100)
//                .autoPlay(false)
//                .loop(false)
//                .build();

        list = new ArrayList<>(3);
        list.add(item1);
        list.add(item2);
//        list.add(item3);
        list.add(item4);

//        bottomNav.setCallback(this);
        bottomNav.setMenuItemList(list);
//        bottomNav.setSelectedIndex(1);
    }


}


