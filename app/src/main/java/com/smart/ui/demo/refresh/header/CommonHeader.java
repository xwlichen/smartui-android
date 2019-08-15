package com.smart.ui.demo.refresh.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.smart.ui.demo.R;
import com.smart.ui.widget.loading.SMUILoadingIndicatorView;

/**
 * @date : 2019-08-15 16:23
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class CommonHeader extends InternalAbstract implements RefreshHeader {


    private TextView tvTip;
    private SMUILoadingIndicatorView loadingView;

    public CommonHeader(Context context) {
        this(context, null);
    }

    protected CommonHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected CommonHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_refresh_common_header, this);
        tvTip = view.findViewById(R.id.tvTip);
        loadingView = view.findViewById(R.id.loadingView);
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (isDragging) {
            float progress = (percent * 0.7f);
            loadingView.setPercent(progress);
        }

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        loadingView.setVisibility(GONE);
        tvTip.setVisibility(VISIBLE);
        if (success) {
            tvTip.setText("已为你更新个性化内容");
        } else {
            tvTip.setText("更新失败");

        }
        super.onFinish(refreshLayout, success);
        return 500;
    }


    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh: //下拉过程
                loadingView.setVisibility(VISIBLE);
                tvTip.setVisibility(GONE);
                break;
            case ReleaseToRefresh: //松开刷新
                break;
            case Refreshing: //loading中
                tvTip.setText("更新中");
                loadingView.setPercent(-1);
                loadingView.startAnimation();
                break;
            default:
                break;
        }
    }
}
