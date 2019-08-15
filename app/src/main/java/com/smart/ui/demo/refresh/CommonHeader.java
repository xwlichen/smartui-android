package com.smart.ui.demo.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.smart.ui.demo.R;
import com.smart.ui.widget.loading.SMUILoadingIndicatorView;

import androidx.annotation.NonNull;

/**
 * @date : 2019-08-15 16:23
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class CommonHeader extends InternalAbstract {


    private TextView tvTip;
    private SMUILoadingIndicatorView loadingView;

    protected CommonHeader(Context context) {
        this(context, null);
    }

    protected CommonHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected CommonHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_refresh_header, this);
        tvTip = view.findViewById(R.id.tvTip);
        loadingView = view.findViewById(R.id.loadingView);
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
//        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        float progress = (percent * .8f);
        loadingView.setPercent(percent);
//        LogUtils.e("xw","common header:" + progress);
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
                tvTip.setText("下拉刷新");
                break;
            case ReleaseToRefresh: //松开刷新
                tvTip.setText("松开释放");
                break;
            case Refreshing: //loading中
                tvTip.setText("更新中");
                break;
            default:
                break;
        }
    }
}
