package set.dhy.com.dhyset.rvadapter.Examples.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.Examples.Entry;
import set.dhy.com.dhyset.rvadapter.base.RVBaseCell;
import set.dhy.com.dhyset.rvadapter.base.RVBaseViewHolder;


/**
 * Created by zhouwei on 17/1/19.
 */

public class TextCell extends RVBaseCell<Entry> {
    public static final int TYPE = 0;
    public TextCell(Entry entry) {
        super(entry);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_cell_layout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
       holder.setText(R.id.text_content,mData.content);
    }
}
