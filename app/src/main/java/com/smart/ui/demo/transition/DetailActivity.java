package com.smart.ui.demo.transition;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.smart.ui.demo.R;
import com.smart.ui.transition.IShareElements;
import com.smart.ui.transition.ShareElementInfo;
import com.smart.ui.transition.SmartShareElementHelper;
import com.smart.ui.transition.saver.TextViewStateSaver;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/**
 * @date : 2019-08-19 15:00
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class DetailActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView tvMusic = findViewById(R.id.tvMusic);
        ViewCompat.setTransitionName(tvMusic, "name:" + "test");


        SmartShareElementHelper.setEnterTransitions(this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{
                        new ShareElementInfo(tvMusic, new TextViewStateSaver())};
            }
        }, false);
    }
}
