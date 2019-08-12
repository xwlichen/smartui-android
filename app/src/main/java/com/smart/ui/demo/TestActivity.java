package com.smart.ui.demo;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.SMUIButton;
import com.smart.ui.widget.SMUITopBar;
import com.smart.ui.widget.bottomnavigationView.SMUIBottomNavView;
import com.smart.ui.widget.dialog.SMUITipDialog;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

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
    }
}


