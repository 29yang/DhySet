package set.dhy.com.dhyset.rvadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dhy.refreshrvbaselibrary.base.RVBaseCell;
import com.dhy.refreshrvbaselibrary.base.RVBaseViewHolder;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.Examples.Entry;


/**
 * Created by zhouwei on 17/1/19.
 */

public class ImageCell extends RVBaseCell<Entry> {
    public static final int TYPE = 1;
    public ImageCell(Entry entry) {
        super(entry);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_cell_layout,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        holder.getTextView(R.id.textview).setText(mData.content);
    }

}
