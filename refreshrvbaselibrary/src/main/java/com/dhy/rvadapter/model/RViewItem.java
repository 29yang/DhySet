package com.dhy.rvadapter.model;

import com.dhy.rvadapter.holder.RViewHolder;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 13:10
 * describe: 可以理解为JavaBean，记录了各式各样的条目
 */
public interface RViewItem<T> {
    //布局
    int getItemLayouut();

    //是否开启点击
    boolean openClick();

    //是否为当前需要的item布局
    boolean isItemView(T entity, int position);

    //将item的控件与数据绑定
    void convert(RViewHolder holder, T entity, int position);
}
