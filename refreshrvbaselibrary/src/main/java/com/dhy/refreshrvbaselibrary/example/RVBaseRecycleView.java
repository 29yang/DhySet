package com.dhy.refreshrvbaselibrary.example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dhy.refreshrvbaselibrary.base.RVSimpleAdapter;
import com.dhy.refreshrvbaselibrary.cell.LoadRefreshCell;
import com.dhy.utilscorelibrary.DensityUtil;

/**
 * 使用三方下拉自定义的linearlayout可自定义头部和尾部 github搜索 UltimateRefreshView
 */
public class RVBaseRecycleView extends RecyclerView {

    /**
     * RecyclerView 最后可见Item在Adapter中的位置
     */
    private int mLastVisiblePosition = -1;
    private int mFirstVisiblePosition = -1;
    private RVSimpleAdapter mBaseAdapter;
    private boolean isLoadMore; //是否处于上拉加载更多中的状态
    private boolean isLoadRefresh; //是否处于下拉加载的状态
    private RvRcControl mRvRcControl;
    private int isLoadMode; //加载的模式 0默认下拉和上拉刷新模式 1只有下拉刷新加载模式  2只有上拉刷新加载模式 3都不能加载模式
    private float mDmping = 0.3f;  //阻尼系数
    private LayoutManager mLayoutManager;

    public void setIsLoadMode(int isLoadMode) {
        this.isLoadMode = isLoadMode;
    }

    public RVBaseRecycleView(@NonNull Context context) {
        super(context);
        init();
    }

    public RVBaseRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RVBaseRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        this.mBaseAdapter = (RVSimpleAdapter) adapter;
    }

    public void setRvRcControl(RvRcControl rvRcControl) {
        mRvRcControl = rvRcControl;
    }

    private void init() {
        /**
         * android2.3以前用android:fadingEdge="none"
         * android2.3以后用setOverScrollMode(View.OVER_SCROLL_NEVER)
         * 这句话的作用 滑动到顶部和底部时出现的阴影消除方法(listview和recycleview通用)
         */
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                mLayoutManager = recyclerView.getLayoutManager();
//                if (mLayoutManager instanceof StaggeredGridLayoutManager)
//                    ((StaggeredGridLayoutManager) mLayoutManager).invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //这里将刷新条目的top设置0，否者在下拉达到一定值在向上滑动时会导致判断的不准备
                if (mBaseAdapter != null && mBaseAdapter.isShowLoadRefresh()
                        && mBaseAdapter.getLoadRefreshHeight() > 0 && !isLoadRefresh
                        && (mBaseAdapter.getData().get(0) instanceof LoadRefreshCell)) {
                    View childAt = getChildAt(0);
                    if (mBaseAdapter.getLoadRefreshHeight() > 0)
                        if (childAt != null && mFirstVisiblePosition == 0) {
                            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                                mLayoutManager.scrollToPosition(mFirstVisiblePosition);
                            } else {
                                childAt.setTop(0);
                            }
                        }
                }
                //在滚动的时候 获取最后一个item的position
                LayoutManager layoutManager = getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    mLastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    mFirstVisiblePosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    mLastVisiblePosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    mFirstVisiblePosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    mLastVisiblePosition = findMax(lastPositions);
                    staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions);
                    mFirstVisiblePosition = findMin(lastPositions);
                }
            }
        });
    }

    /**
     * 判断是否可以显示LoadMore
     *
     * @return
     */
    private boolean canShowLoadMore() {
        if (mBaseAdapter.isShowEmpty() || mBaseAdapter.isShowError() || mBaseAdapter.isShowLoading()
                || mBaseAdapter.isShowLoadRefresh()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否可以显示Loadrefresh
     *
     * @return
     */
    private boolean canShowRefreshMore() {
//        if (mBaseAdapter.isShowEmpty() || mBaseAdapter.isShowError() || mBaseAdapter.isShowLoading()
//                || mBaseAdapter.isShowLoadMore()) {
//            return false;
//        }
        //当前为空布局 或者 错误布局 允许下拉和上拉刷新
        if (mBaseAdapter.isShowLoading()
                || mBaseAdapter.isShowLoadMore()) {
            return false;
        }
        return true;
    }

    /**
     * show load more progress
     */
    private void showLoadMore(float height) {
        float oldHeight = 0;
        if (mBaseAdapter.isShowLoadMore()) {
            oldHeight = mBaseAdapter.getLoadMoreHeight();
        }
        mBaseAdapter.showLoadMore(oldHeight + height);
    }

    /**
     * show load more progress
     */
    private void showLoadRefresh(float height) {
        float oldHeight = 0;
        if (mBaseAdapter.isShowLoadRefresh()) {
            oldHeight = mBaseAdapter.getLoadRefreshHeight();
        }
        float heights = oldHeight + height;
        mBaseAdapter.showLoadRefresh(heights < 0 ? 0 : heights);
    }

    private float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下的时候记录值
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getRawY();
                //手指滑动的差值
                float distanceY = moveY - mLastY;
                mLastY = moveY;
                View firstView = getChildAt(0);
                if (firstView != null) {
                    int top = firstView.getTop();
                    int topEdge = getPaddingTop();
                    //判断RecyclerView 的ItemView是否满屏，如果不满一屏，上拉不会触发加载更多
                    boolean isFullScreen = top < topEdge;
                    LayoutManager manager = getLayoutManager();
                    int itemCount = manager.getItemCount();
                    //因为LoadMore View  是Adapter的一个Item,显示LoadMore 的时候，Item数量＋1了，导致 mLastVisibalePosition == itemCount-1
                    // 判断两次都成立，因此必须加一个判断条件 !mBaseAdapter.isShowLoadMore()
                    if (mLastVisiblePosition >= itemCount - 1 && isFullScreen
                            && canShowLoadMore() && (isLoadMode == 0 || isLoadMode == 2
                            && !isLoadMore)) {
                        //最后一个Item了
                        showLoadMore(-(distanceY > 0 ? distanceY : distanceY * mDmping));
                    }
                    //两个if均是判断到达顶部下拉刷新出现的条件  第一个是未添加下拉布局 第二个是已经存在下拉布局
                    if (mBaseAdapter.getData() != null && mBaseAdapter.getData().size() > 0
                            && !(mBaseAdapter.getData().get(0) instanceof LoadRefreshCell)
                            && mFirstVisiblePosition <= 1 && !isFullScreen && canShowRefreshMore()
                            && (isLoadMode == 0 || isLoadMode == 1) && distanceY >= 0 && !isLoadRefresh) {
                        showLoadRefresh((distanceY < 0 ? distanceY : distanceY * mDmping));
                    }
                    if (mBaseAdapter.getData() != null && mBaseAdapter.getData().size() > 0
                            && (mBaseAdapter.getData().get(0) instanceof LoadRefreshCell)
                            && mFirstVisiblePosition <= 1
                            && canShowRefreshMore() && (isLoadMode == 0 || isLoadMode == 1) && !isLoadRefresh) {
                        showLoadRefresh((distanceY < 0 ? distanceY : distanceY * mDmping));
                    }
                }
                break;
            default:
                //如果处于上拉状态中 并且没有处于正在加载中
                if (mBaseAdapter.isShowLoadMore() && !isLoadMore) {
                    //当加载更过状态存在时并且没有处于加载中的时候才会触发
                    float v1 = DensityUtil.px2dp(getContext(), mBaseAdapter.getLoadMoreHeight());
                    if (v1 >= 50) {
                        //当松手时达到了刷新标准时 打开加载更多
                        mBaseAdapter.showLoadMore(DensityUtil.dp2px(getContext(), 50));
                        mBaseAdapter.setShowLoadMoreState(1);
                        isLoadMore = true;
                        /**
                         *  在这里可通过接口回掉 加载更多的数据
                         */
                        if (mRvRcControl != null) {
                            mRvRcControl.loadMoreData();
                        }
                    } else {
                        //当松手时没有达到刷新标准时 关闭加载更多
                        mBaseAdapter.hideLoadMore();
                        isLoadMore = false;
                    }
                }
                //如果处于下拉状态中 并且没有处于 刷新中
                if (mBaseAdapter.isShowLoadRefresh() && !isLoadRefresh) {
                    float v2 = DensityUtil.px2dp(getContext(), mBaseAdapter.getLoadRefreshHeight());
                    if (v2 >= 50) {
                        //当松手时达到了刷新标准时 打开加载更多
                        mBaseAdapter.showLoadRefresh(DensityUtil.dp2px(getContext(), 50));
                        mBaseAdapter.setShowLoadRefreshState(1);
                        isLoadRefresh = true;
                        /**
                         *  在这里可通过接口回掉 加载更多的数据
                         */
                        if (mRvRcControl != null) {
                            mRvRcControl.refreshData();
                        }
                        mLayoutManager.scrollToPosition(0);
                    } else {
                        //当松手时没有达到刷新标准时 关闭加载更多
                        mBaseAdapter.hideLoadRefresh();
                        isLoadRefresh = false;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取组数最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 获取组数最小值
     *
     * @param lastPositions
     * @return
     */
    private int findMin(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value < max) {
                max = value;
            }
        }
        return max;
    }


    public interface RvRcControl {
        /**
         * 上拉加载更多数据回掉
         */
        void loadMoreData();

        /**
         * 下拉刷新页面回掉
         */
        void refreshData();
    }

    /**
     * @param state 0代表 关闭上拉加载更多  1：关闭下拉刷新加载更多
     */
    public void hideRvRcControlLoad(int state) {
        switch (state) {
            case 0:
                mBaseAdapter.hideLoadMore();
                isLoadMore = false;
                break;
            case 1: //待开发
                mBaseAdapter.hideLoadRefresh();
                isLoadRefresh = false;
                break;
        }
    }
}
