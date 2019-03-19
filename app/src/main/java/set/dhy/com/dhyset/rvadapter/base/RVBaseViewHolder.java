package set.dhy.com.dhyset.rvadapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * author dhy
 * Created by test on 2018/4/16.
 */

public class RVBaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;  //用来存放已经findviewbyid的view控件，以便于减少find的次数
    private View mView;

    public RVBaseViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        mView = itemView;
    }

    /**
     * 获取当前itemview
     *
     * @return
     */
    public View getItemView() {
        return mView;
    }

    /**
     * 通过id获取相对应的view控件 （可根据需求进行增加 button等其他组件的获取方法）
     *
     * @param resId view id
     * @return view控件对象
     */
    public TextView getTextView(int resId) {
        return retrieveView(resId);
    }

    public ImageView getImageView(int resId) {
        return retrieveView(resId);
    }

    public Button getButton(int resId) {
        return retrieveView(resId);
    }

    public View getView(int resId) {
        return retrieveView(resId);
    }

    /**
     * 根据id获取相对应的view控件
     *
     * @param resId 控件id
     * @param <V>   泛型view
     * @return view控件
     */
    @SuppressWarnings("unchecked")
    protected <V extends View> V retrieveView(int resId) {
        View view = views.get(resId);
        if (view == null) {
            view = mView.findViewById(resId);
            views.append(resId, view);
        }
        return (V) view;
    }

    public void setText(int resId,CharSequence text){
        getTextView(resId).setText(text);
    }

    public void setText(int resId,int strId){
        getTextView(resId).setText(strId);
    }
}
