package set.dhy.com.dhyset.rvadapter.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author dhy
 * Created by test on 2018/4/16.
 */

public abstract class RVBaseAdapter<C extends RVBaseCell> extends RecyclerView.Adapter<RVBaseViewHolder> {

    protected List<C> mData;

    public RVBaseAdapter() {
        mData = new ArrayList<>();
    }

    /**
     * 通过该方法传入Cell对象list数据
     *
     * @param data
     */
    public void setData(List<C> data) {
        addAll(data);
        notifyDataSetChanged();
    }

    public List<C> getData() {
        return mData;
    }

    /**
     * 根据viewtype创建不同的viewholder对象
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RVBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for (int i = 0; i < getItemCount(); i++) {
            if (viewType == mData.get(i).getItemType()) {
                return mData.get(i).onCreateViewHolder(parent, viewType);
            }
        }
        throw new RuntimeException("wrong viewType");
    }

    @Override
    public void onBindViewHolder(@NonNull RVBaseViewHolder holder, int position) {
        mData.get(position).onBindViewHolder(holder, position);
    }

    /**
     * 当item离开这个页面时可进行调用
     *
     * @param holder
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull RVBaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int position = holder.getAdapterPosition();
        //越界检查 如果是是最后一个或者是第一个position 就直接返回
        if (position < 0 || position >= mData.size()) {
            return;
        }
        //否则可以对资源进行一些释放，例如条目播放器的初始化
        mData.get(position).releaseResource();

    }

    /**
     * 获取条目总数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 为每个条目添加viewType
     *
     * @param position 第几个条目
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    /**
     * 添加条目
     *
     * @param cell
     */
    public void add(C cell) {
        mData.add(cell);
        int indexOf = mData.indexOf(cell);
        notifyItemChanged(indexOf);
    }

    /**
     * 定位添加条目
     *
     * @param index 要添加插入第几个
     * @param cell
     */
    public void add(int index, C cell) {
        mData.add(index, cell);
        notifyItemInserted(index);
    }

    /**
     * remove cell对象
     *
     * @param cell
     */
    public void remove(C cell) {
        int indexOf = mData.indexOf(cell);
        remove(indexOf);
    }

    public void remove(int index) {
        mData.remove(index);
        notifyItemChanged(index);
    }

    public void remove(int start, int count) {
        if ((start + count) > mData.size()) {
            return;
        }
        mData.subList(start, start + count).clear();
        notifyItemRangeRemoved(start, count);
    }

    /**
     * 添加多个cell
     *
     * @param cells
     */
    public void addAll(List<C> cells) {
        if (cells == null || cells.size() == 0) {
            return;
        }
        mData.addAll(cells);
        notifyItemRangeChanged(mData.size() - cells.size(), mData.size());
    }

    public void addAll(int index, List<C> cells) {
        if (cells == null || cells.size() == 0) {
            return;
        }
        mData.addAll(index, cells);
        notifyItemRangeChanged(index, index + cells.size());
    }

    /**
     * 清空条目数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 如果子类需要在onBindViewHolder 回调的时候做的操作可以在这个方法里做
     *
     * @param holder
     * @param position
     */
    protected abstract void onViewHolderBound(RVBaseViewHolder holder, int position);
}
