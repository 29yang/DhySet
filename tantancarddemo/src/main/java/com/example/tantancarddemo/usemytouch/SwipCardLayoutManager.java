package com.example.tantancarddemo.usemytouch;

/**
 * Created by dhy
 * Date: 2019/7/24
 * Time: 13:25
 * describe:
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义布局管理器
 */

public class SwipCardLayoutManager extends RecyclerView.LayoutManager {

    private RecyclerView mRecyclerView;
    private static String TAG = "======";
    private onFlingListener mOnFlingListener;
    private OnItemClickListener mOnItemClickListener;
    private float x;
    private float y;

    public SwipCardLayoutManager(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = checkIsNull(recyclerView);
    }

    public void setOnFlingListener(onFlingListener onFlingListener) {
        mOnFlingListener = onFlingListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(final RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        // 当数据源个数大于最大显示数时
        if (itemCount > SwipCardConfig.DEFAULT_SHOW_ITEM) {
            for (int position = SwipCardConfig.DEFAULT_SHOW_ITEM; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                //measure 时考虑把 margin 及  padding 也作为子视图大小的一部分
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局 然后layout带Margin的View，将View放置到对应的位置
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position == SwipCardConfig.DEFAULT_SHOW_ITEM) {
                    view.setScaleX(1 - (position - 1) * SwipCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * SwipCardConfig.DEFAULT_SCALE);
                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / SwipCardConfig.DEFAULT_TRANSLATE_Y);
                } else if (position > 0) {
                    view.setScaleX(1 - position * SwipCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * SwipCardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / SwipCardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    initTopView(view);
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    view.setScaleX(1 - position * SwipCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * SwipCardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / SwipCardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    initTopView(view);
                }
            }
        }
    }

    SwipeCardListener mlistener;

    /**
     * 设置监听
     *
     * @param view
     */
    private void initTopView(final View view) {
        //设置顶部view时候要重置初始属性值
        if (x == 0)
            x = view.getX();
        if (y == 0)
            y = view.getY();
        view.setX(x);
        view.setY(y);
        view.setRotation(0);
        view.setAlpha(1);
        final RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
        mlistener = new SwipeCardListener(view, 0, ((RecyclerView) view.getParent()).getWidth(), new SwipeCardListener.ItemSwipListener() {
            @Override
            public void onCardExit(float x, float y) {
                view.setOnTouchListener(null);
                mOnFlingListener.removeFirstObjectInAdapter(holder);
            }

            @Override
            public void onLeftExit(Object itemAtDataPosition) {
                mOnFlingListener.onLeftCardExit(itemAtDataPosition);
            }

            @Override
            public void onRightExit(Object itemAtDataPosition) {
                mOnFlingListener.onRightCardExit(itemAtDataPosition);
            }

            @Override
            public void onClick(Object itemAtDataPosition) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClicked(0, itemAtDataPosition);
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                mOnFlingListener.onScroll(scrollProgressPercent, holder);
                //获取可视的子条目
                int childCount = mRecyclerView.getChildCount();
                // 当数据源个数大于最大显示数时
                if (childCount > SwipCardConfig.DEFAULT_SHOW_ITEM) {
                    for (int position = 1; position < childCount - 1; position++) {
                        int index = childCount - position - 1;
                        View view = mRecyclerView.getChildAt(position);
                        view.setScaleX(1 - index * SwipCardConfig.DEFAULT_SCALE + Math.abs(scrollProgressPercent) * SwipCardConfig.DEFAULT_SCALE);
                        view.setScaleY(1 - index * SwipCardConfig.DEFAULT_SCALE + Math.abs(scrollProgressPercent) * SwipCardConfig.DEFAULT_SCALE);

                    /* view.setScaleX(1 - index * SwipCardConfig.DEFAULT_SCALE );
                    view.setScaleY(1 - index * SwipCardConfig.DEFAULT_SCALE);*/
                        view.setTranslationY((index - Math.abs(scrollProgressPercent)) * view.getMeasuredHeight() / SwipCardConfig.DEFAULT_TRANSLATE_Y);
                    }
                } else {
                    // 当数据源个数小于或等于最大显示数时
                    for (int position = 0; position < childCount - 1; position++) {
                        int index = childCount - position - 1;
                        View view = mRecyclerView.getChildAt(position);
                        view.setScaleX(1 - index * SwipCardConfig.DEFAULT_SCALE + Math.abs(scrollProgressPercent) * SwipCardConfig.DEFAULT_SCALE);
                        view.setScaleY(1 - index * SwipCardConfig.DEFAULT_SCALE + Math.abs(scrollProgressPercent) * SwipCardConfig.DEFAULT_SCALE);
                        view.setTranslationY((index - Math.abs(scrollProgressPercent)) * view.getMeasuredHeight() / SwipCardConfig.DEFAULT_TRANSLATE_Y);
                    }
                }
            }
        });
        view.setOnTouchListener(mlistener);
    }

    public SwipeCardListener getMlistener() {
        if (mlistener == null) {
            throw new NullPointerException();
        }
        return mlistener;
    }

    public interface OnItemClickListener {
        void onItemClicked(int itemPosition, Object dataObject);
    }

    public interface onFlingListener {
        void removeFirstObjectInAdapter(RecyclerView.ViewHolder holder);

        void onLeftCardExit(Object dataObject);

        void onRightCardExit(Object dataObject);

        void onScroll(float scrollProgressPercent, RecyclerView.ViewHolder viewHolder);
    }
}
