package com.dhy.rvadapter.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 13:18
 * describe:
 */
public class RViewHolder extends RecyclerView.ViewHolder {

    //控件收集起来，作为缓存
    private SparseArray<View> mViews; //view控件的集合
    private View mConvertView; //提供当前的view

    private RViewHolder(@NonNull View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    //对外提供api，用来创建ViewHolder对象
    public static RViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RViewHolder(itemView);
    }

    //通过viewId获取控件
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
