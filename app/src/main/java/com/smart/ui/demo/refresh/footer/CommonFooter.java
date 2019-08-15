package com.smart.ui.demo.refresh.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.smart.ui.demo.R;
import com.smart.ui.widget.loading.SMUILoadingIndicatorView;

/**
 * @author lichen
 * @date ：2019-08-15 21:40
 * @email : 196003945@qq.com
 * @description :
 */
public class CommonFooter extends InternalAbstract implements RefreshFooter {


    private TextView tvTip;
    private SMUILoadingIndicatorView loadingView;
    protected boolean mNoMoreData = false;


    public CommonFooter(Context context) {
        this(context, null);
    }

    protected CommonFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected CommonFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_refresh_common_footer, this);
        tvTip = view.findViewById(R.id.tvTip);
        loadingView = view.findViewById(R.id.loadingView);
    }


    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        loadingView.setVisibility(GONE);
//        tvTip.setVisibility(GONE);
        if (!mNoMoreData) {
            tvTip.setText(success ? "" : "加载失败");
            return super.onFinish(refreshLayout, success);
        }
        return 500;
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            if (noMoreData) {
                tvTip.setText("没有更多");
            } else {
                tvTip.setText("正在加载");
            }
        }
        return true;
    }


    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

        if (!mNoMoreData) {
            switch (newState) {
                case None:
                    loadingView.setVisibility(GONE);
                    break;
                case PullUpToLoad:
                    loadingView.setVisibility(VISIBLE);
                    tvTip.setText("正在加载");
                    break;
                case Loading:
                case LoadReleased:
                    loadingView.setVisibility(VISIBLE);
                    tvTip.setText("正在加载");
                    break;
                default:
                    break;
            }
        }
    }
}
