package set.dhy.com.dhyset.rvadapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.base.OftenViewType;
import set.dhy.com.dhyset.rvadapter.base.RVBaseViewHolder;
import set.dhy.com.dhyset.utils.DpAndPx;


/**
 * author dhy
 * Created by test on 2018/4/16.
 */
public class LoadMoreCell extends RVAbsStateCell {
    public static final int mDefaultHeight = 50;//dp

    public LoadMoreCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return OftenViewType.LOAD_MORE_TYPE;
    }


    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }

    @Override
    public View getDefaultView(Context context) {
        // 设置LoadMore View显示的默认高度
        setHeight(DpAndPx.dpToPx(context, mDefaultHeight));
        return LayoutInflater.from(context).inflate(R.layout.rv_load_more_layout, null);
    }
}
