package com.smart.ui.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.smart.ui.utils.SMUIColorHelper;
import com.smart.ui.widget.image.SMUIImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Test1Activity.class));
            }
        });

        SMUIImageButton btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshActivity.class));
            }
        });


        int toColor = ContextCompat.getColor(this, R.color.tt_colorful);
//        toColor = Color.YELLOW;
        TextView test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        test.setBackgroundColor(toColor);

//        TextView testRl = findViewById(R.id.testRl);
//
//        testRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        ImageView ivArrow = findViewById(R.id.ivArrow);
        ObjectAnimator rotationAnimator = null;

        if (rotationAnimator == null) {
            rotationAnimator = ObjectAnimator.ofFloat(ivArrow, "rotation", 0, 360f);
        }

        rotationAnimator.setDuration(5000);
        rotationAnimator.setRepeatMode(ValueAnimator.RESTART);
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        rotationAnimator.start();


        RelativeLayout testRl1 = findViewById(R.id.testRl1);
        int color1 = SMUIColorHelper.computeColor(toColor, Color.BLACK, 0.1f);
        testRl1.setBackgroundColor(color1);

        testRl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
