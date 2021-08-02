package com.smart.ui.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.ui.demo.adapter.BaseRecyclerAdapter;
import com.smart.ui.demo.adapter.SmartViewHolder;
import com.smart.ui.demo.refresh.CommonRefreshLayout;
import com.smart.ui.demo.transition.DetailActivity;
import com.smart.ui.transition.IShareElements;
import com.smart.ui.transition.ShareElementInfo;
import com.smart.ui.transition.SmartShareElementHelper;
import com.smart.ui.transition.saver.TextViewStateSaver;
import com.smart.ui.widget.StatusLayout;

import java.util.Arrays;
import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import static android.R.layout.simple_list_item_2;

/**
 * @date : 2019-08-15 16:16
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class RefreshActivity extends Activity {

    private BaseRecyclerAdapter<Void> mAdapter;

    private static boolean isFirstEnter = true;
    CommonRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SmartShareElementHelper.enableContentTransition(getApplication());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);


        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(mAdapter = new BaseRecyclerAdapter<Void>(simple_list_item_2) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
                holder.text(android.R.id.text1, getString(R.string.item_example_number_title, position));
                holder.text(android.R.id.text2, getString(R.string.item_example_number_abstract, position));
                holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }
        });

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refresh(initData());
                        refreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter.getItemCount() > 30) {
                            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        } else {
                            mAdapter.loadMore(initData());
                            refreshLayout.finishLoadMore();
                        }
                    }
                }, 2000);

            }
        });


        final TextView tvMusic = findViewById(R.id.tvMusic);
        ViewCompat.setTransitionName(tvMusic, "name:" + "test");
        tvMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetailActivity(tvMusic);

            }
        });
//        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
//        refreshLayout.setHeaderHeight(60);

        tvMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
            }
        });

        //触发自动刷新
        if (isFirstEnter) {
            isFirstEnter = false;
            refreshLayout.autoRefresh();
        } else {
            mAdapter.refresh(initData());
        }

        //防止闪烁
//    (recycle_view.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
//    (recycle_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
//    recycle_view.itemAnimator?.changeDuration = 0

    }

    private Collection<Void> initData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }


    private void gotoDetailActivity(final View nameTxt) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = SmartShareElementHelper.buildOptionsBundle(this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{
                        new ShareElementInfo(nameTxt, new TextViewStateSaver())};
            }
        });
        ActivityCompat.startActivity(this, intent, bundle);
    }


    protected StatusLayout.Holder mHolder;

    /**
     * make a StatusLayout.Holder wrap with current activity by default
     */
    protected void initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            //bind status view to activity root view by default
            mHolder = StatusLayout.getInstance().wrap(this).withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
    }

    protected void onLoadRetry() {
        // override this method in subclass to do retry task
    }

    public void showLoading() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoading();
    }

    public void showLoadSuccess() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadSuccess();
    }

    public void showLoadFailed() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadFailed();
    }

    public void showEmpty() {
        initLoadingStatusViewIfNeed();
        mHolder.showEmpty();
    }

}
