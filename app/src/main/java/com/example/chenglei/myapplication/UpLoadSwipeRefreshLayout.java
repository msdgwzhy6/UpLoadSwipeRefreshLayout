package com.example.chenglei.myapplication;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by chenglei on 2016/4/20.
 * 重写下拉刷新组件，添加上拉加载的功能
 */
public class UpLoadSwipeRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    private ListView mListView;  //子view

    private boolean isCanLoading = false;  //是否正可以加载，默认不可用

    private OnLoadListener mOnLoadListener;

    private View mFooterView;  //上拉加载的视图

    private int firstY;
    private int lastY;

    private int mTouchSlop;  //最短的滑动距离

    private int footerResource;

    public UpLoadSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();  //获取系统默认的最短滑动距离
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取子view
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);  //获取第一个子view
            if (childView instanceof ListView) {
                /**
                 * 如果子view的类型是ListView
                 */
                mListView = (ListView) childView;
                //给子listview设置滑动监听
                mListView.setOnScrollListener(this);
            }
        }
    }

    /**
     * 触摸事件拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /**
                 * 获得触摸事件刚刚开始的时候，手指触摸的坐标
                 */
                firstY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 获得触摸事件结束的时候，手指离开时候的坐标
                 */
                lastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (isCanLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 判断是否可以进行加载
     */
    private boolean isCanLoad() {
        return isBottom() && isPushUp() && !isCanLoading;
    }

    /**
     * 判断listview是否到了底部
     */
    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            /**
             * 当前可加的最后一项就是listview的末尾项
             * 说明滑动到了最底部
             */
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 判断是否正在上拉
     */
    private boolean isPushUp() {
        /**
         * 往上滑动的距离大于系统默认的滑动距离
         */
        return firstY - lastY >= mTouchSlop;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if (isCanLoad()) {
            /**
             * 滑动过程中如果可以加载数据
             * 就执行加载数据的函数
             */
            loadData();
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            /**
             * 处理加载的逻辑
             * 具体逻辑在回调中完成
             */
            mOnLoadListener.onLoad();
        }
    }

    /**
     * 设置可加载
     */
    public void setLoading(boolean isCanLoad) {
        this.isCanLoading = isCanLoad;
        if (isCanLoad == true) {
            /**
             * 加载，添加底部的正在加载视图
             */
            mListView.addFooterView(mFooterView);
        } else {
            /**
             * 不在加载中，将底部的加载中视图移除
             */
            mListView.removeFooterView(mFooterView);
            firstY = 0;
            lastY = 0;
        }
    }

    /**
     * 设置上拉加载监听
     */
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.mOnLoadListener = onLoadListener;
    }

    /**
     * 设置上拉加载的布局
     */
    public void setFooterResource(int footerResource) {
        this.footerResource = footerResource;
        /**
         * 实例化布局view
         */
        this.mFooterView = LayoutInflater.from(getContext()).inflate(footerResource, null, false);
    }
}
