package set.dhy.com.dhyset.rvadapter.Examples.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.Examples.Entry;
import set.dhy.com.dhyset.rvadapter.base.RVBaseCell;
import set.dhy.com.dhyset.rvadapter.base.RVBaseViewHolder;

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
        Picasso.with(holder.getItemView().getContext()).load(mData.imageUrl).into(holder.getImageView(R.id.image));
    }

}
