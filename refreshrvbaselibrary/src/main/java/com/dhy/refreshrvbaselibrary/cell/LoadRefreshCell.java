package com.dhy.refreshrvbaselibrary.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhy.refreshrvbaselibrary.R;
import com.dhy.refreshrvbaselibrary.base.OftenViewType;
import com.dhy.refreshrvbaselibrary.base.RVBaseViewHolder;
import com.dhy.utilscorelibrary.DensityUtil;


public class LoadRefreshCell extends RVAbsStateCell {
    private View mInflate;
    private TextView mTextview;
    private int mState; //当前状态 0是未加载状态 1是加载状态

    public LoadRefreshCell(Object o) {
        super(o);
    }

    public void setState(int state) {
        //改变当前下拉刷新的状态
        mState = state;
        if (mTextview != null && state == 1)
            mTextview.setText("正在刷新");
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        if (mInflate != null && mState == 0 && height >= 0) {
            /**
             * 这里可以根据需求进行动画的展示的效果
             */
            //当mState没有处于加载中的时候才可以实现阻尼效果
            ViewGroup.LayoutParams layoutParams = mInflate.getLayoutParams();
            //上拉 和释放加载更多状态的一个阈值
            if (height <= DensityUtil.dpToPx(mInflate.getContext(), 50)) {
                if (mTextview != null)
                    mTextview.setText("下拉刷新");
            } else if (height > DensityUtil.dpToPx(mInflate.getContext(), 50)) {
                if (mTextview != null)
                    mTextview.setText("释放刷新");
            }
            //设置加载条目最高的高度
            if (height > DensityUtil.dpToPx(mInflate.getContext(), 200)) {
                layoutParams.height = DensityUtil.dpToPx(mInflate.getContext(), 200);
                mHeight = DensityUtil.dpToPx(mInflate.getContext(), 200);
            } else {
                layoutParams.height = (int) height;
            }
            mInflate.setLayoutParams(layoutParams);
        }
    }

    @Override
    public View getDefaultView(Context context) {
        setHeight(DensityUtil.dpToPx(context, mHeight));
        mInflate = LayoutInflater.from(context).inflate(R.layout.rv_load_refresh_layout, null);
        mTextview = mInflate.findViewById(R.id.rv_load_more_text);
        //初始化作用
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = 0;
        mInflate.setLayoutParams(layoutParams);
        return mInflate;
    }

    @Override
    public int getItemType() {
        return OftenViewType.LOAD_REFRESH_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
