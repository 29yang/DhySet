package com.dhy.rvadapter;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 14:14
 * describe:
 */
public class SwipeRefreshHelper {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipRefreshListener mSwipRefreshListener;

    private SwipeRefreshHelper(SwipeRefreshLayout swipeRefreshLayout) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        init();
    }

    private void init() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark,android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mSwipRefreshListener != null){
                    mSwipRefreshListener.onRefresh();
                }
            }
        });
    }

    static SwipeRefreshHelper createSwipRefreshHelper(SwipeRefreshLayout swipeRefreshLayout){
        return new SwipeRefreshHelper(swipeRefreshLayout);
    }

    public void setSwipRefreshListener(SwipRefreshListener swipRefreshListener) {
        mSwipRefreshListener = swipRefreshListener;
    }

    public interface SwipRefreshListener{
        void onRefresh();
    }
}
