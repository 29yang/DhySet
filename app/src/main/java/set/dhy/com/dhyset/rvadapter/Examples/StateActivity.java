package set.dhy.com.dhyset.rvadapter.Examples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dhy.refreshrvbaselibrary.base.Cell;
import com.dhy.refreshrvbaselibrary.base.RVSimpleAdapter;
import com.dhy.refreshrvbaselibrary.example.RVBaseRecycleView;

import java.util.ArrayList;
import java.util.List;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.ImageCell;
import set.dhy.com.dhyset.rvadapter.TextCell;
import set.dhy.com.dhyset.utils.StatusBarUtils;

public class StateActivity extends AppCompatActivity implements RVBaseRecycleView.RvRcControl {
private RVBaseRecycleView mRecyclerView;
private List<Cell> data;
private RVSimpleAdapter mRcadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        StatusBarUtils.setStatusBar(this, "#ffffff", true);

        mRecyclerView = findViewById(R.id.recycleview);
        data = new ArrayList<>();
        mRcadapter = new RVSimpleAdapter();
        mRcadapter.setData(data);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //消除自带的动画（可选）
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mRcadapter);
        mRecyclerView.setRvRcControl(this);
        click();
//        mRcadapter.showLoadingKeepCount(1,400,View.inflate(this,R.layout.rv_load_more_layout,null));
//        mRcadapter.showLoading(View.inflate(this,R.layout.rv_load_more_layout,null),200);
//        mRcadapter.showError();
        mRcadapter.showEmptyKeepCount(mRcadapter.getItemCount(),200);
//        mRcadapter.showLoading();
        mRecyclerView.setIsLoadMode(0);
    }
    public void click() {
        for (int i = 0; i <8; i++) {
            Entry entry = new Entry();
            entry.content = "==" + i;
            data.add(new ImageCell(entry));
            data.add(new TextCell(entry));
        }
        mRcadapter.addAll(data);
    }

    @Override
    public void loadMoreData() {
        //上拉加载更多
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.hideRvRcControlLoad(0);
                            mRcadapter.showEmptyKeepCount(mRcadapter.getItemCount(),200);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void refreshData() {
        //下拉刷新数据
        //上拉加载更多
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.hideRvRcControlLoad(1);
                            mRcadapter.hideEmpty();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
