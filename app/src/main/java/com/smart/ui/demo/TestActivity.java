package com.smart.ui.demo;

import android.os.Bundle;
import android.view.View;

import com.smart.ui.widget.SMUITopBar;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SMUITopBar topBar = findViewById(R.id.topBar);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
