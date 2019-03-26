package com.dhy.refreshrvbaselibrary.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhy.refreshrvbaselibrary.DensityUtil;
import com.dhy.refreshrvbaselibrary.R;
import com.dhy.refreshrvbaselibrary.base.OftenViewType;
import com.dhy.refreshrvbaselibrary.base.RVBaseViewHolder;


/**
 * author dhy
 * Created by test on 2018/4/16.
 */
public class LoadMoreCell extends RVAbsStateCell {
    public static final int mDefaultHeight = 50;//dp
    private View mInflate;
    private TextView mTextview;
    private int mState; //当前状态 0是未加载状态 1是加载状态

    public LoadMoreCell(Object o) {
        super(o);
    }

    public void setState(int state) {
        //改变当前下拉刷新的状态
        mState = state;
        if (mTextview != null && state == 1)
            mTextview.setText("正在加载");
    }

    @Override
    public int getItemType() {
        return OftenViewType.LOAD_MORE_TYPE;
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        if (height > 0)
            if (mInflate != null && mState == 0) {
                /**
                 * 这里可以根据需求进行动画的展示的效果
                 */
                //当mState没有处于加载中的时候才可以实现阻尼效果
                ViewGroup.LayoutParams layoutParams = mInflate.getLayoutParams();
                //上拉 和释放加载更多状态的一个阈值
                if (height <= DensityUtil.dpToPx(mInflate.getContext(), 50)) {
                    if (mTextview != null)
                        mTextview.setText("上拉加载更多");
                } else if (height > DensityUtil.dpToPx(mInflate.getContext(), 50)) {
                    if (mTextview != null)
                        mTextview.setText("释放立即加载");
                }
                //设置加载条目最高的高度
                if (height > DensityUtil.dpToPx(mInflate.getContext(), 200)) {
                    layoutParams.height = DensityUtil.dpToPx(mInflate.getContext(), 200);
                    mHeight = DensityUtil.dpToPx(mInflate.getContext(), 200);
                } else {
                    layoutParams.height = (int)height;
                }
                mInflate.setLayoutParams(layoutParams);
            }
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }

    @Override
    public View getDefaultView(Context context) {
        // 设置LoadMore View显示的默认高度
//        setHeight(DensityUtil.dpToPx(context, mHeight));
        mInflate = LayoutInflater.from(context).inflate(R.layout.rv_load_more_layout, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.height = 0;
        mInflate.setLayoutParams(layoutParams);
        mTextview = mInflate.findViewById(R.id.rv_load_more_text);
        return mInflate;
    }
}
