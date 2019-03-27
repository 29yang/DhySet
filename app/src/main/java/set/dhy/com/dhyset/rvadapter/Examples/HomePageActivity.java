package set.dhy.com.dhyset.rvadapter.Examples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.dhy.refreshrvbaselibrary.base.Cell;
import com.dhy.refreshrvbaselibrary.base.RVSimpleAdapter;
import com.dhy.refreshrvbaselibrary.example.RVBaseRecycleView;
import com.dhy.utilscorelibrary.status_bar_util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.ImageCell;
import set.dhy.com.dhyset.rvadapter.TextCell;

public class HomePageActivity extends AppCompatActivity implements RVBaseRecycleView.RvRcControl {
    private RVBaseRecycleView mRecyclerView;
    private List<Cell> data;
    private RVSimpleAdapter mRcadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        StatusBarUtils.setStatusBar(this, null, true);
        mRecyclerView = findViewById(R.id.recycleview);
        data = new ArrayList<>();
        mRcadapter = new RVSimpleAdapter();
        mRcadapter.setData(data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mRcadapter);
        mRecyclerView.setRvRcControl(this);
//        mRcadapter.showLoadingKeepCount(1,400,View.inflate(this,R.layout.rv_load_more_layout,null));
//        mRcadapter.showLoading(View.inflate(this,R.layout.rv_load_more_layout,null),200);
//        mRcadapter.showError();
//        mRcadapter.showEmptyKeepCount(1,200);
//        mRcadapter.showLoading();
        mRecyclerView.setIsLoadMode(0);
        click();
    }
    public void click() {
        for (int i = 0; i <4; i++) {
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
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
