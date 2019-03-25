package com.dhy.refreshrvbaselibrary.cell;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.dhy.refreshrvbaselibrary.R;
import com.dhy.refreshrvbaselibrary.base.OftenViewType;
import com.dhy.refreshrvbaselibrary.base.RVBaseViewHolder;


/**
 * author dhy
 * Created by test on 2018/4/16.
 */

public class EmptyCell extends RVAbsStateCell {

    public EmptyCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return OftenViewType.EMPTY_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }

    @Override
    public View getDefaultView(Context context) {
        if (mView == null)
            mView = View.inflate(context, R.layout.rv_empty_layout, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mHeight > 0) {
            layoutParams.height = (int)mHeight;
            mView.setLayoutParams(layoutParams);
        }
        return mView;
    }
}
