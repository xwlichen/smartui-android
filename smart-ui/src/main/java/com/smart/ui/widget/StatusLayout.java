package com.smart.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * @author lichen
 * @date ：2019-08-31 23:10
 * @email : 196003945@qq.com
 * @description : copy from https://github.com/luckybilly/Gloading/issues
 */
public class StatusLayout {

    public static final int STATUS_LOADING = 1;
    public static final int STATUS_LOAD_SUCCESS = 2;
    public static final int STATUS_LOAD_FAILED = 3;
    public static final int STATUS_EMPTY_DATA = 4;

    private static volatile StatusLayout instance;
    private Adapter adapter;
    private static boolean DEBUG = false;

    /**
     * Provides view to show current loading status
     */
    public interface Adapter {
        /**
         * get view for current status
         *
         * @param holder      Holder
         * @param convertView The old view to reuse, if possible.
         * @param status      current status
         * @return status view to show. Maybe convertView for reuse.
         * @see Holder
         */
        View getView(Holder holder, View convertView, int status);
    }

    /**
     * set debug mode or not
     *
     * @param debug true:debug mode, false:not debug mode
     */
    public static void debug(boolean debug) {
        DEBUG = debug;
    }

    private StatusLayout() {
    }

    /**
     * Create a new StatusLayout different from the default one
     *
     * @param adapter another adapter different from the default one
     * @return StatusLayout
     */
    public static StatusLayout from(Adapter adapter) {
        StatusLayout gloading = new StatusLayout();
        gloading.adapter = adapter;
        return gloading;
    }

    /**
     * get default StatusLayout object for global usage in whole app
     *
     * @return default StatusLayout object
     */
    public static StatusLayout getInstance() {
        if (instance == null) {
            synchronized (StatusLayout.class) {
                if (instance == null) {
                    instance = new StatusLayout();
                }
            }
        }
        return instance;
    }

    /**
     * init the default loading status view creator ({@link Adapter})
     *
     * @param adapter adapter to create all status views
     */
    public static void init(Adapter adapter) {
        getInstance().adapter = adapter;
    }

    /**
     * StatusLayout(loading status view) wrap the whole activity
     * wrapper is android.R.id.content
     *
     * @param activity current activity object
     * @return holder of StatusLayout
     */
    public Holder wrap(Activity activity) {
        ViewGroup wrapper = activity.findViewById(android.R.id.content);
        return new Holder(adapter, activity, wrapper);
    }

    /**
     * StatusLayout(loading status view) wrap the specific view.
     *
     * @param view view to be wrapped
     * @return Holder
     */
    public Holder wrap(View view) {
        FrameLayout wrapper = new FrameLayout(view.getContext());
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            wrapper.setLayoutParams(lp);
        }
        if (view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            parent.removeView(view);
            parent.addView(wrapper, index);
        }
        FrameLayout.LayoutParams newLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        wrapper.addView(view, newLp);
        return new Holder(adapter, view.getContext(), wrapper);
    }

    /**
     * loadingStatusView shows cover the view with the same LayoutParams object
     * this method is useful with RelativeLayout and ConstraintLayout
     *
     * @param view the view which needs show loading status
     * @return Holder
     */
    public Holder cover(View view) {
        ViewParent parent = view.getParent();
        if (parent == null) {
            throw new RuntimeException("view has no parent to show gloading as cover!");
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        FrameLayout wrapper = new FrameLayout(view.getContext());
        viewGroup.addView(wrapper, view.getLayoutParams());
        return new Holder(adapter, view.getContext(), wrapper);
    }

    /**
     * StatusLayout holder<br>
     * create by {@link StatusLayout#wrap(Activity)} or {@link StatusLayout#wrap(View)}<br>
     * the core API for showing all status view
     */
    public static class Holder {
        private Adapter adapter;
        private Context comtext;
        private Runnable retryTask;
        private View curStatusView;
        private ViewGroup wrapper;
        private int curState;
        private SparseArray<View> statusViews = new SparseArray<>(4);
        private Object data;

        private Holder(Adapter adapter, Context context, ViewGroup wrapper) {
            this.adapter = adapter;
            this.comtext = context;
            this.wrapper = wrapper;
        }

        /**
         * set retry task when user click the retry button in load failed page
         *
         * @param task when user click in load failed UI, run this task
         * @return this
         */
        public Holder withRetry(Runnable task) {
            retryTask = task;
            return this;
        }

        /**
         * set extension data
         *
         * @param data extension data
         * @return this
         */
        public Holder withData(Object data) {
            this.data = data;
            return this;
        }

        /**
         * show UI for status: {@link #STATUS_LOADING}
         */
        public void showLoading() {
            showLoadingStatus(STATUS_LOADING);
        }

        /**
         * show UI for status: {@link #STATUS_LOAD_SUCCESS}
         */
        public void showLoadSuccess() {
            showLoadingStatus(STATUS_LOAD_SUCCESS);
        }

        /**
         * show UI for status: {@link #STATUS_LOAD_FAILED}
         */
        public void showLoadFailed() {
            showLoadingStatus(STATUS_LOAD_FAILED);
        }

        /**
         * show UI for status: {@link #STATUS_EMPTY_DATA}
         */
        public void showEmpty() {
            showLoadingStatus(STATUS_EMPTY_DATA);
        }

        /**
         * Show specific status UI
         *
         * @param status status
         * @see #showLoading()
         * @see #showLoadFailed()
         * @see #showLoadSuccess()
         * @see #showEmpty()
         */
        public void showLoadingStatus(int status) {
            if (curState == status || !validate()) {
                return;
            }
            curState = status;
            //first try to reuse status view
            View convertView = statusViews.get(status);
            if (convertView == null) {
                //secondly try to reuse current status view
                convertView = curStatusView;
            }
            try {
                //call customer adapter to get UI for specific status. convertView can be reused
                View view = adapter.getView(this, convertView, status);
                if (view == null) {
                    printLog(adapter.getClass().getName() + ".getView returns null");
                    return;
                }
                if (view != curStatusView || wrapper.indexOfChild(view) < 0) {
                    if (curStatusView != null) {
                        wrapper.removeView(curStatusView);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.setElevation(Float.MAX_VALUE);
                    }
                    wrapper.addView(view);
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    if (lp != null) {
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    }
                } else if (wrapper.indexOfChild(view) != wrapper.getChildCount() - 1) {
                    // make sure loading status view at the front
                    view.bringToFront();
                }
                curStatusView = view;
                statusViews.put(status, view);
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        }

        private boolean validate() {
            if (adapter == null) {
                printLog("StatusLayout.Adapter is not specified.");
            }
            if (comtext == null) {
                printLog("Context is null.");
            }
            if (wrapper == null) {
                printLog("The wrapper of loading status view is null.");
            }
            return adapter != null && comtext != null && wrapper != null;
        }

        public Context getContext() {
            return comtext;
        }

        /**
         * get wrapper
         *
         * @return container of gloading
         */
        public ViewGroup getWrapper() {
            return wrapper;
        }

        /**
         * get retry task
         *
         * @return retry task
         */
        public Runnable getRetryTask() {
            return retryTask;
        }

        /**
         * get extension data
         *
         * @param <T> return type
         * @return data
         */
        public <T> T getData() {
            try {
                return (T) data;
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private static void printLog(String msg) {
        if (DEBUG) {
            Log.e("StatusLayout", msg);
        }
    }
}
