package set.dhy.com.dhyset;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * author dhy
 * Created by test on 2018/12/25.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {
    private Context mContext;
    private List<mainBean> mData;
    private OnClick mOnClick;

    public void setOnClick(OnClick onClick) {
        mOnClick = onClick;
    }

    public MainAdapter(Context context, List<mainBean> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(View.inflate(mContext, R.layout.main_list_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position).name);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClick != null)
                    mOnClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public viewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item);
        }
    }

    public interface OnClick {
        void onClick(int position);
    }
}


