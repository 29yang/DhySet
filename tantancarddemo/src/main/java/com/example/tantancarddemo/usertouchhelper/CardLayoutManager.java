package com.example.tantancarddemo.usertouchhelper;

/**
 * Created by dhy
 * Date: 2019/7/24
 * Time: 13:25
 * describe:
 */

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义布局管理器
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private static String TAG = "======";
    public CardLayoutManager(@NonNull RecyclerView recyclerView, @NonNull ItemTouchHelper itemTouchHelper) {
        this.mRecyclerView = checkIsNull(recyclerView);
        this.mItemTouchHelper = checkIsNull(itemTouchHelper);
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
        if (itemCount > CardConfig.DEFAULT_SHOW_ITEM) {
            for (int position = CardConfig.DEFAULT_SHOW_ITEM; position >= 0; position--) {
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

                if (position == CardConfig.DEFAULT_SHOW_ITEM) {
                    view.setScaleX(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else if (position > 0) {
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
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
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                //因为在自定义itemtouchhelper.callback中每次滑动后都取消了点击事件，
                // 并且在isItemSwipeEnabled返回false禁止了item滑动操作，所以
                // 在每次重新onLayoutChildren时对最上面的item添加点击事件开启滑动
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };

}
