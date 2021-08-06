package com.smart.ui.demo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.SMUITopBar;
import com.smart.ui.widget.SMUITopBarLayout;

public class Test1Activity  extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMUIStatusBarHelper.translucent(this);
        SMUIStatusBarHelper.setStatusBarLightMode(this);
        setContentView(R.layout.activity_test1);

        SMUITopBarLayout smuiTopBarLayout =findViewById(R.id.title);
        smuiTopBarLayout.setTitle("test");
        smuiTopBarLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
    }
}
