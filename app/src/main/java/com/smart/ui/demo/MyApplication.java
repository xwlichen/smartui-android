package com.smart.ui.demo;

import android.app.Application;

import com.smart.ui.demo.adapter.GlobalAdapter;
import com.smart.ui.widget.StatusLayout;

/**
 * @author lichen
 * @date ：2019-08-31 23:15
 * @email : 196003945@qq.com
 * @description :
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StatusLayout.debug(BuildConfig.DEBUG);
        StatusLayout.init(new GlobalAdapter());
    }
}
