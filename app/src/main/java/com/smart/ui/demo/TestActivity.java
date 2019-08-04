package com.smart.ui.demo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.SMUITopBar;

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
    }
}
