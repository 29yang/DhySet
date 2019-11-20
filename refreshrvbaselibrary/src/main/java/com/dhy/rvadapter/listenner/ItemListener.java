package com.dhy.rvadapter.listenner;

import android.view.View;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 13:28
 * describe:
 */
public interface ItemListener<T> {
    /**
     * item 点击事件监听
     */
    void onItemClick(View view, T entity, int position);

    //长按点击事件
    void onItemLongClick(View view, T entity, int position);
}
