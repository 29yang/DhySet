package com.dhy.rvadapter.core;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.dhy.rvadapter.base.RviewAdapter;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 14:08
 * describe:
 */
public interface RViewCreate<T> {
    Context context();

    RecyclerView createRecycleView();
    //下拉刷新
    SwipeRefreshLayout createSwipRefresh();

    RviewAdapter<T> createRecycleViewADpater();

    boolean isSupportPaging();
}
