package com.dhy.refreshrvbaselibrary.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dhy.refreshrvbaselibrary.R;
import com.dhy.refreshrvbaselibrary.base.RVBaseCell;
import com.dhy.refreshrvbaselibrary.base.RVBaseViewHolder;


/**
 * author dhy
 * Created by test on 2018/4/16.
 */

public abstract class RVAbsStateCell extends RVBaseCell<Object> {
    protected View mView;
    protected float mHeight = 0;
    protected int mLoadState; //填充的布局高度属性 1：是MATCH_PARENT 0：WRAP_CONTENT

    public RVAbsStateCell(Object o) {
        super(o);
    }

    public void setView(View view) {
        mView = view;
    }

    public void setLoadState(int Loadstate) {
        mLoadState = Loadstate;
    }

    public void setHeight(float height) {
        mHeight = height;
    }

    public float getHeight() {
        return mHeight;
    }


    @Override
    public void releaseResource() {
        super.releaseResource();
        if (mView != null) {
            mView = null;
        }
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_state_layout, null);
        //如果调用者没有设置显示的View就用默认的View
        View defaultView = getDefaultView(parent.getContext());
        if (defaultView != null) {
            LinearLayout container = view.findViewById(R.id.rv_cell_state_root_layout);
            if (mLoadState == 1) {
                container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            container.removeAllViews();
            container.addView(defaultView);
        }
        return new RVBaseViewHolder(view);
    }

    /**
     * 子类提供的默认布局，当没有通过{@link #setView(View)}设置显示的View的时候，就显示默认的View
     *
     * @return 返回默认布局
     */
    public abstract View getDefaultView(Context context);
}
