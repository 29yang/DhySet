package com.example.tantancarddemo.userecyclyview;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by dhy
 * Date: 2019/7/24
 * Time: 13:24
 * describe:
 */
public class CardItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {

    private final RecyclerView.Adapter adapter;
    private List<T> dataList;
    private OnSwipeListener<T> mListener;

    public CardItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
    }

    public CardItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList, OnSwipeListener<T> listener) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
        this.mListener = listener;
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public void setOnSwipedListener(OnSwipeListener<T> mListener) {
        this.mListener = mListener;
    }


    /**
     * 设置滑动类型标记
     *
     * @param recyclerView
     * @param viewHolder
     * @return 返回一个整数类型的标识，用于判断Item那种移动行为是允许的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0; //拖拽
        int swipeFlags = 0; //滑动
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof CardLayoutManager) {
            //设置可推拽方向
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ;
            //dragflags 是拖拽的方向设置，如果想要拖拽的话 isLongPressDragEnabled()必须返回true
//            dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖拽切换Item的回调
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return 如果Item切换了位置，返回true；反之，返回false
     */

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * 划出时会执行
     *
     * @param viewHolder
     * @param direction:上1下2，左侧划出为:4,右侧划出为:8
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == 4 || direction == 8) {
            Log.d("mylog", "onSwiped: " + direction);
            // 移除 onTouchListener,否则触摸滑动会乱了
            viewHolder.itemView.setOnTouchListener(null);
            int layoutPosition = viewHolder.getLayoutPosition();
            T remove = dataList.remove(layoutPosition);
            adapter.notifyDataSetChanged();
            if (mListener != null) {
                mListener.onSwiped(viewHolder, remove, direction == ItemTouchHelper.LEFT ? CardConfig.SWIPED_LEFT : CardConfig.SWIPED_RIGHT);
            }
            // 当没有数据时回调 mListener
            if (adapter.getItemCount() == 0) {
                if (mListener != null) {
                    mListener.onSwipedClear();
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Item是否支持滑动 为了防止第二层和第三层卡片也能滑动，因此我们需要设置false
     *
     * @return true 支持滑动操作
     * false 不支持滑动操作
     */

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * item是否支持拖拽  如果要实现拖拽必须返回true，不需要放回false
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 拖动时会执行的方法
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        // Log.d("mylog", "onChildDraw: 拖动");
        View itemView = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            Log.d("mylog", "onChildDraw: " + ratio);
            itemView.setRotation(ratio * CardConfig.DEFAULT_ROTATE_DEGREE);
            //获取可视的子条目
            int childCount = recyclerView.getChildCount();
            // 当数据源个数大于最大显示数时
            if (childCount > CardConfig.DEFAULT_SHOW_ITEM) {
                for (int position = 1; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);

                    /* view.setScaleX(1 - index * CardConfig.DEFAULT_SCALE );
                    view.setScaleY(1 - index * CardConfig.DEFAULT_SCALE);*/
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                }
            }
            if (mListener != null) {
                if (ratio != 0) {
                    // Log.d("mylog", "onChildDraw: 不为零");
                    mListener.onSwiping(viewHolder, ratio, ratio < 0 ? CardConfig.SWIPING_LEFT : CardConfig.SWIPING_RIGHT);
                } else {
                    // Log.d("mylog", "onChildDraw: 为零");
                    mListener.onSwiping(viewHolder, ratio, CardConfig.SWIPING_NONE);
                }
            }
        }
    }

    /**
     * 第一层的卡片滑出去之后第二层的就莫名其妙地偏了。这正是因为 Item View 重用机制“捣鬼”。
     * 所以我们应该在 clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
     * 方法中重置一下
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }

    /**
     * 获取滑动的阀值距离，getWipeThreshold 系统默认是0.5f阀值，可以重写该方法就行修改
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

}
