package com.smart.ui.demo.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.smart.ui.demo.refresh.footer.CommonFooter;
import com.smart.ui.demo.refresh.header.CommonHeader;

/**
 * @date : 2019-08-15 16:41
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class CommonRefreshLayout extends SmartRefreshLayout {

    private CommonHeader header;
    private CommonFooter footer;

    public CommonRefreshLayout(Context context) {
        this(context, null);
    }

    public CommonRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header = new CommonHeader(context);
        header.setLayoutParams(layoutParams);

        footer = new CommonFooter(context);
        footer.setLayoutParams(layoutParams);

//        addView(header, 0);

        setRefreshHeader(header);

        setRefreshFooter(footer);
    }


}
