package com.smart.ui.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.ui.utils.SMUIColorHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
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

        RelativeLayout testRl = findViewById(R.id.testRl);

        testRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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
