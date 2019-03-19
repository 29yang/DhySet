package set.dhy.com.dhyset.shuang_biao_tou;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * author dhy
 * Created by test on 2018/7/18.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
    private View mView;

    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != mView)
            mView.scrollTo(l, t);
    }

    public void setScrollView(View mView) {
        this.mView = mView;
    }

}
