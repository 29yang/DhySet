package com.dhy.rvadapter.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dhy.rvadapter.holder.RViewHolder;
import com.dhy.rvadapter.listenner.ItemListener;
import com.dhy.rvadapter.manager.RviewItemManager;
import com.dhy.rvadapter.model.RViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhy
 * Date: 2019/9/26
 * Time: 13:31
 * describe:
 */
public class RviewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {
    private RviewItemManager<T> itemStyle; //item类型管理器
    private ItemListener<T> itemListener; //item点击事件监听
    private List<T> datas;//数据源

    public void addDatas(List<T> datas){
        if (datas == null) return;
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void updataDatas(List<T> datas){
        if (datas == null) return;
        this.datas = datas;
        notifyDataSetChanged();
    }

    //单一布局
    public RviewAdapter(List<T> datas) {
        if (datas == null) this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RviewItemManager<>();
    }

    //嵌套（多样式的布局）
    public RviewAdapter(List<T> datas, RViewItem<T> item) {
        if (datas == null) this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RviewItemManager<>();
        //将以重新的item类型加入到多样式集合中
        addItemStyle(item);
    }

    public void addItemStyle(RViewItem<T> item) {
        itemStyle.addStyles(item);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //根据返回的viewType。从集合中得到RviewItem
        RViewItem item = itemStyle.getRviewItem(viewType);
        int layoutId = item.getItemLayouut();
        RViewHolder holder = RViewHolder.createViewHolder(viewGroup.getContext(),viewGroup,layoutId);
        if (item.openClick()) setListener(holder);
        return holder;
    }

    private void setListener(final RViewHolder holder) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null){
                    int position = holder.getAdapterPosition();
                    itemListener.onItemClick(v,datas.get(position),position);
                }
            }
        });
        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemListener != null){
                    int position = holder.getAdapterPosition();
                    itemListener.onItemLongClick(v,datas.get(position),position);
                }
                return true;
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int positin) {
        convert(holder,datas.get(positin));
    }

    private void convert(RViewHolder holder, T entity) {
        itemStyle.convert(holder,entity,holder.getAdapterPosition());
    }

    //是否有多样式的RViewItem
    private boolean hasMultiStyle() {
        return itemStyle.getItemViewStylesCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        //如果有多样式，就需要判断分开加载
        if (hasMultiStyle()){
            return itemStyle.getItemViewType(datas.get(position),position);
        }
        return super.getItemViewType(position);
    }

    public void setItemListener(ItemListener<T> itemListener){
        this.itemListener = itemListener;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
}
