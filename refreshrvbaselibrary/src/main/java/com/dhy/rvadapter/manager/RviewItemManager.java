package com.dhy.rvadapter.manager;

import android.support.v4.util.SparseArrayCompat;

import com.dhy.rvadapter.holder.RViewHolder;
import com.dhy.rvadapter.model.RViewItem;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 13:31
 * describe: 条目管理类，配合adapter工作
 */
public class RviewItemManager<T> {
    //key:int viewtype value:RviewItem
    private SparseArrayCompat<RViewItem<T>> styles = new SparseArrayCompat<>();

    public void addStyles(RViewItem<T> item) {
        if (item != null) {
            styles.put(styles.size(), item);
        }
    }

    //获取所有item样式的数量
    public int getItemViewStylesCount() {
        return styles.size();
    }

    //获取styles集合中的key
    public int getItemViewType(T entity, int position) {
        for (int i = styles.size() - 1; i >= 0; i--) {
            RViewItem<T> item = styles.valueAt(i);
            if (item.isItemView(entity, position)) {
                return styles.keyAt(i);
            }
        }
        throw new RuntimeException("管理类没有匹配的type");
    }

    public RViewItem getRviewItem(int viewType) {
        return styles.get(viewType);
    }

    public void convert(RViewHolder holder, T entity, int position) {
        for (int i = 0; i < styles.size(); i++) {
            RViewItem<T> item = styles.valueAt(i);
            //校验完成，实现数据绑定
            if (item.isItemView(entity,position)){
                item.convert(holder,entity,position);
                return;
            }
        }
        throw new RuntimeException("数据绑定失败");
    }
}
