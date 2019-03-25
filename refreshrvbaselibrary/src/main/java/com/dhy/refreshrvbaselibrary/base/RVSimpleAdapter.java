package com.dhy.refreshrvbaselibrary.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.dhy.refreshrvbaselibrary.cell.EmptyCell;
import com.dhy.refreshrvbaselibrary.cell.ErrorCell;
import com.dhy.refreshrvbaselibrary.cell.LoadMoreCell;
import com.dhy.refreshrvbaselibrary.cell.LoadRefreshCell;
import com.dhy.refreshrvbaselibrary.cell.LoadingCell;


/**
 * author dhy
 * Created by test on 2018/4/16.
 */

public class RVSimpleAdapter extends RVBaseAdapter {

    private EmptyCell mEmptyCell;
    private ErrorCell mErrorCell;
    private LoadingCell mLoadingCell;
    private LoadMoreCell mLoadMoreCell;
    private LoadRefreshCell mLoadRefreshCell;
    //LoadMore 是否已显示
    private boolean mIsShowLoadMore = false;
    private boolean mIsShowError = false;
    private boolean mIsShowLoading = false;
    private boolean mIsShowEmpty = false;
    private boolean mIsShowLoadRefresh = false;


    public RVSimpleAdapter() {
        mEmptyCell = new EmptyCell(null);
        mErrorCell = new ErrorCell(null);
        mLoadingCell = new LoadingCell(null);
        mLoadMoreCell = new LoadMoreCell(null);
        mLoadRefreshCell = new LoadRefreshCell(null);
    }

    @Override
    protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

    }

    /**
     * GridLayout
     * 我们解释一下这段代码，首先我们设置了一个SpanSizeLookup，这个类是一个抽象类，而且仅有一个抽象方法getSpanSize
     * ，这个方法的返回值决定了我们每个position上的item占据的单元格个数，假设是列数为2，而我们这段代码综合上面为GridLayoutManager
     * 设置的每行的个数来解释的话， 就是当前位置是header的位置，那么该item占据2个单元格，正常情况下占据1个单元格。
     * 那这段代码放哪呢？ 为了以后的封装，我们还是在Adapter中找方法放吧。我们在Adapter中再重写一个方法onAttachedToRecyclerView，
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        //处理GridView 布局
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return (viewType == OftenViewType.ERROR_TYPE || viewType == OftenViewType.EMPTY_TYPE || viewType == OftenViewType.LOADING_TYPE
                            || viewType == OftenViewType.LOAD_MORE_TYPE || viewType == OftenViewType.LOAD_REFRESH_TYPE) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 处理StaggeredGridLayoutManager 显示这个Span ，因为跟GridLayout不同，所有做的处理是如下处理
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        // 处理StaggeredGridLayoutManager 显示这个Span ，因为跟gridview不同，所有做的处理是如下处理
        int position = holder.getAdapterPosition();
        int viewType = getItemViewType(position);
        if (isStaggeredGridLayout(holder)) {
            if (viewType == OftenViewType.ERROR_TYPE || viewType == OftenViewType.EMPTY_TYPE || viewType == OftenViewType.LOADING_TYPE
                    || viewType == OftenViewType.LOAD_MORE_TYPE || viewType == OftenViewType.LOAD_REFRESH_TYPE) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                //设置显示整个span
                params.setFullSpan(true);
            }
        }
    }

    /**
     * 判断是否是瀑布流布局
     *
     * @param holder
     * @return
     */
    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    /**
     * 显示LoadingView
     * <p>请求数据时调用，数据请求完毕时调用{@link #hideLoading }</p>
     *
     * @see #showLoadingKeepCount(int)
     */
    public void showLoading() {
        clear();
        mIsShowLoading = true;
        mLoadingCell.setLoadState(1);
        add(mLoadingCell);
    }

    /**
     * 列表Loading 状态显示的View，默认全屏高度
     *
     * @param loadingView
     * @see {@link #showEmpty(View, int)}
     */
    public void showLoading(View loadingView) {
        showLoading(loadingView, 0);
    }

    /**
     * 指定列表Loading 状态显示的View，并指定View显示高度
     *
     * @param loadingView
     * @param viewHeight
     */
    public void showLoading(View loadingView, int viewHeight) {
        if (loadingView == null) {
            showLoading();
            return;
        }
        clear();
        mIsShowLoading = true;
        if (viewHeight <= 0)
            mLoadingCell.setLoadState(1);
        mLoadingCell.setView(loadingView);
        mLoadingCell.setHeight(viewHeight);
        add(mLoadingCell);
    }

    /**
     * 列表LoadingView 状态显示的View
     * <p>列表显示LoadingView并保留keepCount个Item</p>
     *
     * @param keepCount 保留的条目数量
     */
    public void showLoadingKeepCount(int keepCount) {
        showLoadingKeepCount(keepCount, 0);
    }

    /**
     * 列表Loading状态显示的View，保留keepCountg个Item，并指定高度
     *
     * @param keepCount 保留item的个数
     * @param height    View显示的高度
     */
    public void showLoadingKeepCount(int keepCount, int height) {
        showLoadingKeepCount(keepCount, height, null);
    }

    /**
     * 列表Loading状态显示的View，保留keepCountg个Item，并指定高度，指定显示的View
     *
     * @param keepCount   保留item的个数
     * @param height      View显示的高度
     * @param loadingView 显示的View
     */
    public void showLoadingKeepCount(int keepCount, int height, View loadingView) {
        if (keepCount < 0 || keepCount > mData.size()) {
            return;
        }
        remove(keepCount, mData.size() - keepCount);
        checkNotContainSpecailCell();
        mIsShowLoading = true;
        if (loadingView != null) {
            mLoadingCell.setView(loadingView);
        }
        if (height <= 0)
            mLoadingCell.setLoadState(1);
        mLoadingCell.setHeight(height);
        add(mLoadingCell);
    }

    /**
     * hide Loading view
     */
    public void hideLoading() {
        if (mData.contains(mLoadingCell)) {
            mData.remove(mLoadingCell);
            mLoadingCell.setLoadState(0);
            mIsShowLoading = false;
        }
    }

    /**
     * 显示错误提示View
     * <p>当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showError}</p>
     *
     * @see #showErrorKeepCount(int)
     */
    public void showError() {
        clear();
        mIsShowError = true;
        mErrorCell.setLoadState(1);
        add(mErrorCell);
    }

    /**
     * 显示错误提示View
     * <p>当网络请求发生错误，需要在界面给出错误提示时，调用{@link #showErrorKeepCount(int)},并保留keepCount 条Item</p>
     *
     * @param keepCount 保留Item数量
     */
    public void showErrorKeepCount(int keepCount) {
        showErrorKeepCount(keepCount, 0);
    }

    /**
     * 显示错误提示View，并指定保留的item数和View显示的高
     *
     * @param keepCount 保留的item数
     * @param height    view显示的高
     */
    public void showErrorKeepCount(int keepCount, int height) {
        showErrorKeepCount(keepCount, height, null);
    }

    /**
     * 显示错误提示View，并指定保留的item数和View显示的高
     *
     * @param keepCount 保留的item数
     * @param height    view显示的高
     * @param errorView 指定显示的View，null 则显示默认View
     */
    public void showErrorKeepCount(int keepCount, int height, View errorView) {
        if (keepCount < 0 || keepCount > mData.size()) {
            return;
        }
        remove(keepCount, mData.size() - keepCount);
        checkNotContainSpecailCell();
        mIsShowError = true;
        if (errorView != null) {
            mErrorCell.setView(errorView);
        }
        if (height <= 0)
            mErrorCell.setLoadState(1);
        mErrorCell.setHeight(height);
        add(mErrorCell);
    }

    /**
     * 指定列表发生错误时显示的View，默认为全屏高度
     *
     * @param errorView
     * @see {@link #showError(View, int)}
     */
    public void showError(View errorView) {
        showError(errorView, 0);
    }

    /**
     * 指定列表发生错误时显示的View，并指定View高度
     *
     * @param errorView
     * @param viewHeight
     */
    public void showError(View errorView, int viewHeight) {
        if (errorView == null) {
            showError();
            return;
        }
        clear();
        mIsShowError = true;
        if (viewHeight <= 0)
            mErrorCell.setLoadState(1);
        mErrorCell.setHeight(viewHeight);
        mErrorCell.setView(errorView);
        add(mErrorCell);
    }

    /**
     * 隐藏错误提示
     */
    public void hideErorr() {
        if (mData.contains(mErrorCell)) {
            remove(mErrorCell);
            mIsShowError = false;
            mErrorCell.setLoadState(0);
        }
    }

    /**
     * 显示LoadMoreView
     * <p>当列表滑动到底部时，调用 提示加载更多，加载完数据，调用{@link #hideLoadMore()}
     * 隐藏LoadMoreView,显示列表数据。</p>
     */
    public void showLoadMore(float height) {
        mLoadMoreCell.setHeight(height);
        if (!isShowLoadMore())
            add(mLoadMoreCell);
        mIsShowLoadMore = true;
    }

    /**
     * 上拉刷新的状态
     *
     * @param state 1处于刷新中
     */
    public void setShowLoadMoreState(int state) {
        mLoadMoreCell.setState(state);
    }

    public float getLoadMoreHeight() {
        return mLoadMoreCell.getHeight();
    }

    /**
     * 隐藏LoadMoreView
     * <p>调用之后，加载数据完成，调用{@link #hideLoadMore()}隐藏LoadMoreView</p>
     */
    public void hideLoadMore() {
        if (mData.contains(mLoadMoreCell)) {
            remove(mLoadMoreCell);
            mIsShowLoadMore = false;
            mLoadMoreCell.setState(0);
        }
    }

    /**
     * 显示loadrefreshview d当列表下拉的顶部时调用提示加载更多
     *
     * @param height
     */
    public void showLoadRefresh(float height) {
        mLoadRefreshCell.setHeight(height);
        if (!isShowLoadRefresh())
            add(0, mLoadRefreshCell);
        mIsShowLoadRefresh = true;
    }

    /**
     * 下拉刷新的状态
     *
     * @param state 1处于下拉刷新中
     */
    public void setShowLoadRefreshState(int state) {
        mLoadRefreshCell.setState(state);
    }

    public float getLoadRefreshHeight() {
        return mLoadRefreshCell.getHeight();
    }

    /**
     * 隐藏loadrefreshview
     * <p>调用之后，加载数据完成，调用{@link #hideLoadRefresh()}隐藏hideLoadRefresh</p>
     */
    public void hideLoadRefresh() {
        if (mData.contains(mLoadRefreshCell)) {
            remove(mLoadRefreshCell);
            mIsShowLoadRefresh = false;
            mLoadRefreshCell.setState(0);
        }
    }

    /**
     * 显示空状态View，并保留keepCount个Item
     *
     * @param keepCount
     */
    public void showEmptyKeepCount(int keepCount) {
        showEmptyKeepCount(keepCount, 0);
    }

    /**
     * 显示空状态View，保留keepCount个Item,并指定View显示的高
     *
     * @param keepCount 保留的Item个数
     * @param height    View 显示的高度
     * @see {@link #showEmptyKeepCount(int)}
     * @see {@link #showEmptyKeepCount(int, int, View)}
     */
    public void showEmptyKeepCount(int keepCount, int height) {
        showEmptyKeepCount(keepCount, height, null);
    }

    /**
     * 显示空状态View，保留keepCount个Item,并指定View和View显示的高
     *
     * @param keepCount 保留的Item个数
     * @param height    显示的View的高
     * @param view      显示的View，null 则显示默认View
     */
    public void showEmptyKeepCount(int keepCount, int height, View view) {
        if (keepCount < 0 || keepCount > mData.size()) {
            return;
        }
        remove(keepCount, mData.size() - keepCount);
        checkNotContainSpecailCell();
        mIsShowEmpty = true;
        if (view != null) {
            mEmptyCell.setView(view);
        }
        if (height <= 0)
            mEmptyCell.setLoadState(1);
        mEmptyCell.setHeight(height);
        add(mEmptyCell);
    }

    /**
     * 显示空view
     * <p>当页面没有数据的时候，调用{@link #showEmpty()}显示空View，给用户提示</p>
     */
    public void showEmpty() {
        clear();
        mIsShowEmpty = true;
        mEmptyCell.setLoadState(1);
        add(mEmptyCell);
    }

    /**
     * 显示指定的空状态View，并指定View显示的高度
     *
     * @param emptyView  页面为空状态显示的View
     * @param viewHeight view显示的高
     */
    public void showEmpty(View emptyView, int viewHeight) {
        if (emptyView == null) {
            showEmpty();
            return;
        }
        clear();
        mIsShowEmpty = true;
        if (viewHeight <= 0)
            mEmptyCell.setLoadState(1);
        mEmptyCell.setView(emptyView);
        mEmptyCell.setHeight(viewHeight);
        add(mEmptyCell);
    }

    /**
     * 显示指定的空状态View,默认显示屏幕高度
     *
     * @param emptyView
     * @see {@link #showEmpty(View, int)}
     */
    public void showEmpty(View emptyView) {
        showEmpty(emptyView, 0);
    }

    /**
     * 隐藏空View
     */
    public void hideEmpty() {
        if (mData.contains(mEmptyCell)) {
            remove(mEmptyCell);
            mIsShowEmpty = false;
            mEmptyCell.setLoadState(0);
        }
    }


    /**
     * 检查列表是否已经包含了这4种Cell
     */
    private void checkNotContainSpecailCell() {
        if (mData.contains(mEmptyCell)) {
            mData.remove(mEmptyCell);
        }
        if (mData.contains(mErrorCell)) {
            mData.remove(mErrorCell);
        }
        if (mData.contains(mLoadingCell)) {
            mData.remove(mLoadingCell);
        }
        if (mData.contains(mLoadMoreCell)) {
            mData.remove(mLoadMoreCell);
        }
        if (mData.contains(mLoadRefreshCell)) {
            mData.remove(mLoadRefreshCell);
        }
    }

    @Override
    public void clear() {
        mIsShowError = false;
        mIsShowLoading = false;
        mIsShowEmpty = false;
        mIsShowLoadMore = false;
        mIsShowLoadRefresh = false;
        super.clear();
    }

    /**
     * LoadMore View 是否已经显示
     *
     * @return
     */
    public boolean isShowLoadMore() {
        return mIsShowLoadMore;
    }

    public boolean isShowEmpty() {
        return mIsShowEmpty;
    }

    public boolean isShowLoading() {
        return mIsShowLoading;
    }

    public boolean isShowError() {
        return mIsShowError;
    }

    public boolean isShowLoadRefresh() {
        return mIsShowLoadRefresh;
    }
}
