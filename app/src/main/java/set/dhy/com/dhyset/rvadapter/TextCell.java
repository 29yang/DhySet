package set.dhy.com.dhyset.rvadapter;

import android.view.View;
import android.view.ViewGroup;

import com.dhy.refreshrvbaselibrary.base.RVBaseCell;
import com.dhy.refreshrvbaselibrary.base.RVBaseViewHolder;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.Examples.Entry;

public class TextCell extends RVBaseCell<Entry> {
    public static final int TYPE = 2;
    public TextCell(Entry entry) {
        super(entry);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(View.inflate(parent.getContext(), R.layout.text_item,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
