package set.dhy.com.dhyset.rvadapter.Examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.rvadapter.Examples.cell.BannerCell;
import set.dhy.com.dhyset.rvadapter.Examples.cell.ImageCell;
import set.dhy.com.dhyset.rvadapter.Examples.cell.TextCell;
import set.dhy.com.dhyset.rvadapter.base.Cell;
import set.dhy.com.dhyset.rvadapter.cell.LoadMoreCell;
import set.dhy.com.dhyset.rvadapter.fragment.AbsBaseFragment;

import static set.dhy.com.dhyset.rvadapter.Examples.DataMocker.mockMoreData;

/**
 * author dhy
 * Created by test on 2018/4/19.
 */

public class HomePageFrame extends AbsBaseFragment<Entry> {

    //初始化完，可以做一些操作了
    @Override
    public void onRecyclerViewInitialized() {
        //初始化View和数据加载
        //设置刷新进度条颜色
        setColorSchemeResources(R.color.colorAccent);
        loadData();
    }

    //模拟加载数据
    private void loadData() {
        mBaseAdapter.showLoading();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseAdapter.hideLoading();
                mBaseAdapter.addAll(getCells(DataMocker.mockData()));
            }
        }, 1000);
    }

    //下拉刷新
    @Override
    public void onPullRefresh() {
        //下拉刷新回调
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshing(false);
            }
        }, 2000);
    }

    //当加载更多时
    @Override
    public void onLoadMore() {
        //上拉加载回调
        loadMore();
    }


    private void loadMore() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadMore();
                mBaseAdapter.addAll(getCells(mockMoreData()));
            }
        }, 3000);
    }

    @Override
    protected List<Cell> getCells(List<Entry> list) {
        //根据实体生成Cell
        List<Cell> cells = new ArrayList<>();
        cells.add(new BannerCell(Arrays.asList(DataMocker.images)));
        for (int i = 0; i < list.size(); i++) {
            Entry entry = list.get(i);
            if (entry.type == Entry.TYPE_IMAGE) {
                cells.add(new ImageCell(entry));
            } else {
                cells.add(new TextCell(entry));
            }
        }
        return cells;
    }
}
