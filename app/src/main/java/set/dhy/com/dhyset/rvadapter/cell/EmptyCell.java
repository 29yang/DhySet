package set.dhy.com.dhyset.rvadapter.cell;

import android.content.Context;
import android.view.View;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.base.OftenViewType;
import set.dhy.com.dhyset.rvadapter.base.RVBaseViewHolder;

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
        return View.inflate(context, R.layout.rv_empty_layout, null);
    }
}
