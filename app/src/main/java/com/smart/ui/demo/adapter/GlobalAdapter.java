package com.smart.ui.demo.adapter;

import android.view.View;

import com.smart.ui.demo.view.GlobalLoadingStatusView;
import com.smart.ui.widget.StatusLayout;


public class GlobalAdapter implements StatusLayout.Adapter {

    @Override
    public View getView(StatusLayout.Holder holder, View convertView, int status) {
        GlobalLoadingStatusView loadingStatusView = null;
        //reuse the old view, if possible
        if (convertView != null && convertView instanceof GlobalLoadingStatusView) {
            loadingStatusView = (GlobalLoadingStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new GlobalLoadingStatusView(holder.getContext(), holder.getRetryTask());
        }
        loadingStatusView.setStatus(status);
        Object data = holder.getData();
        //show or not show msg view
        boolean hideMsgView = "hide_loading_status_msg".equals(data);
        loadingStatusView.setMsgViewVisibility(!hideMsgView);
        return loadingStatusView;
    }

}
