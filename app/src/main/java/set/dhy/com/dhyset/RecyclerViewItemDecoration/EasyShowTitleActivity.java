package set.dhy.com.dhyset.RecyclerViewItemDecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;

public class EasyShowTitleActivity extends AppCompatActivity {

    @BindView(R.id.feed_list)
    RecyclerView mFeedList;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.title_ll)
    LinearLayout mTitleLl;
    private EasyAdapter mAdapter;
    private List<EasyListBean> data;
    private int mTitleLlHeight;
    private int mCurrentPosition;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_show_title);
        ButterKnife.bind(this);
        data = new ArrayList<>();
        initData();
        mAdapter = new EasyAdapter(data, this);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mFeedList.setLayoutManager(manager);
        mFeedList.setAdapter(mAdapter);
        //初始化第一个标题
        mTitle.setText("标题" + data.get(mCurrentPosition).getParent());
        mFeedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mTitleLlHeight = mTitleLl.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTitle.setText("标题" + data.get(mCurrentPosition).getParent());
                //判断第二条是否是分类标题的状态
                if (data.get(mCurrentPosition + 1).getItemType() == 0) {
                    //查找第二条view
                    View view = manager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        //根据距离进行位移
                        if (view.getTop() <= mTitleLlHeight) {
                            mTitleLl.setY(-(mTitleLlHeight - view.getTop()));
                            if (view.getTop()<=1){
                                mTitle.setText("标题" + data.get(mCurrentPosition+1).getParent());
                            }
                        } else {
                            mTitleLl.setY(0);
                        }
                    }
                }
                //更新当前页面的第一条数据
                if (mCurrentPosition != manager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = manager.findFirstVisibleItemPosition();
                    mTitleLl.setY(0);
                }
            }
        });

    }

    private void initData() {
        data.add(new EasyListBean(0, 0, "标题0"));
        data.add(new EasyListBean(1, 0, "内容0"));
        data.add(new EasyListBean(1, 0, "内容1"));
        data.add(new EasyListBean(1, 0, "内容2"));
        data.add(new EasyListBean(1, 0, "内容3"));
        data.add(new EasyListBean(1, 0, "内容4"));
        data.add(new EasyListBean(1, 0, "内容5"));
        data.add(new EasyListBean(1, 0, "内容6"));
        data.add(new EasyListBean(0, 1, "标题1"));
        data.add(new EasyListBean(1, 1, "内容0"));
        data.add(new EasyListBean(1, 1, "内容1"));
        data.add(new EasyListBean(1, 1, "内容2"));
        data.add(new EasyListBean(1, 1, "内容3"));
        data.add(new EasyListBean(1, 1, "内容3"));
        data.add(new EasyListBean(1, 1, "内容3"));
        data.add(new EasyListBean(1, 1, "内容3"));
        data.add(new EasyListBean(1, 1, "内容3"));
        data.add(new EasyListBean(1, 1, "内容4"));
        data.add(new EasyListBean(1, 1, "内容5"));
        data.add(new EasyListBean(0, 2, "标题2"));
        data.add(new EasyListBean(1, 2, "内容0"));
        data.add(new EasyListBean(1, 2, "内容1"));
        data.add(new EasyListBean(1, 2, "内容2"));
        data.add(new EasyListBean(1, 2, "内容3"));
        data.add(new EasyListBean(1, 2, "内容4"));
        data.add(new EasyListBean(1, 2, "内容5"));
        data.add(new EasyListBean(0, 3, "标题3"));
        data.add(new EasyListBean(1, 3, "内容0"));
        data.add(new EasyListBean(1, 3, "内容1"));
        data.add(new EasyListBean(1, 3, "内容2"));
        data.add(new EasyListBean(1, 3, "内容3"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容4"));
        data.add(new EasyListBean(1, 3, "内容5"));
        data.add(new EasyListBean(1, 3, "内容5"));
        data.add(new EasyListBean(1, 3, "内容5"));
        data.add(new EasyListBean(1, 3, "内容5"));
        data.add(new EasyListBean(0, 4, "标题4"));
        data.add(new EasyListBean(1, 4, "标题4"));
        data.add(new EasyListBean(1, 4, "标题4"));
        data.add(new EasyListBean(1, 4, "标题4"));
    }
}
