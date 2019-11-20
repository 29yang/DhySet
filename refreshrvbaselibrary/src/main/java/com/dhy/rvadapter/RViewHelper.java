package com.dhy.rvadapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dhy.rvadapter.base.RviewAdapter;
import com.dhy.rvadapter.core.RViewCreate;

import java.util.List;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 13:08
 * describe:
 */
public class RViewHelper<T> {
    private Context context;
    private SwipeRefreshLayout swipRefresh; //下拉控件
    private SwipeRefreshHelper swipRefreshHelper; //下拉刷新的工具类
    private RecyclerView recycleView;
    private RviewAdapter<T> adpter;
    private int startPageNumber = 1; //开始页码
    private boolean isSupportPaging; //是否支持加载更多
    private SwipeRefreshHelper.SwipRefreshListener listener;
    private int currentPageNum; // 当前页数

    private RViewHelper(Builder<T> builder) {
        this.swipRefresh = builder.create.createSwipRefresh();
        this.recycleView = builder.create.createRecycleView();
        this.adpter = builder.create.createRecycleViewADpater();
        this.context = builder.create.context();
        this.isSupportPaging = builder.create.isSupportPaging();
        this.listener = builder.mListener;

        this.currentPageNum = this.startPageNumber;
        if (swipRefresh != null) {
            swipRefreshHelper = SwipeRefreshHelper.createSwipRefreshHelper(swipRefresh);
        }
        init();

    }

    private void init() {
        //初始化
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //下拉刷新
        if (swipRefreshHelper != null) {
            swipRefreshHelper.setSwipRefreshListener(new SwipeRefreshHelper.SwipRefreshListener() {
                @Override
                public void onRefresh() {
                    //如果正在刷新
                    if (swipRefresh != null && swipRefresh.isRefreshing()) {
                        swipRefresh.setRefreshing(false);
                    }
                    //重置页码
                    currentPageNum = startPageNumber;
                    if (listener != null) listener.onRefresh();
                }
            });
        }
    }

    public void notifyAdapterDataSetChanged(List<T> datas) {
        if (currentPageNum == startPageNumber) {
            adpter.updataDatas(datas);
        } else {
            adpter.addDatas(datas);
        }
        recycleView.setAdapter(adpter);

        if (isSupportPaging) {
            //分页带扩展
        }
    }

    public static class Builder<T> {
        private RViewCreate<T> create;
        private SwipeRefreshHelper.SwipRefreshListener mListener;

        public Builder(RViewCreate<T> create, SwipeRefreshHelper.SwipRefreshListener listener) {
            this.create = create;
            mListener = listener;
        }

        public RViewHelper build() {
            return new RViewHelper(this);
        }
    }
}
