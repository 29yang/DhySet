package set.dhy.com.dhyset.RecyclerViewItemDecoration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;

/**
 * author dhy
 * Created by test on 2018/4/26.
 */

public class EasyAdapter extends RecyclerView.Adapter {
    private List<EasyListBean> data;
    private Context mContext;

    public EasyAdapter(List<EasyListBean> data, Context context) {
        this.data = data;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view0 = LayoutInflater.from(mContext).inflate(R.layout.show_title_hand_layout, parent, false);
                return new ViewHolder0(view0);
            case 1:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.show_title_content_layout, parent, false);
                return new ViewHolder1(view1);
        }
        throw new RuntimeException("wrong viewType");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder0) {
            ((ViewHolder0) holder).mItemTitle.setText(data.get(position).getContent());
        } else if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).mItemContent.setText(data.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getItemType();
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {
        TextView mItemTitle;

        public ViewHolder0(View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.item_title);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView mItemContent;

        public ViewHolder1(View itemView) {
            super(itemView);
            mItemContent = itemView.findViewById(R.id.item_content);
        }
    }
}
