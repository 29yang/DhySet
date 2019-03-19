package set.dhy.com.dhyset.RightSlideExit.example;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.RightSlideExit.RightSlideBaseActivity;
import set.dhy.com.dhyset.utils.StatusBarUtils;

public class ExitOneActivity extends RightSlideBaseActivity implements View.OnClickListener {

    @BindView(R.id.status1)
    TextView mStatus1;
    @BindView(R.id.status2)
    TextView mStatus2;
    @BindView(R.id.status3)
    TextView mStatus3;
    @BindView(R.id.status4)
    TextView mStatus4;
    @BindView(R.id.status5)
    TextView mStatus5;
    @BindView(R.id.status6)
    TextView mStatus6;
    @BindView(R.id.status7)
    TextView mStatus7;
    @BindView(R.id.status8)
    TextView mStatus8;
    @BindView(R.id.status9)
    TextView mStatus9;
    @BindView(R.id.status10)
    TextView mStatus10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_one);
        ButterKnife.bind(this);
        initListener();
        //页面展示了相关的用法和沉浸式的实现功能
        StatusBarUtils.setStatusBar(this, false, false);
        StatusBarUtils.setNavigationBarColor("#EC6D65", this, false);
    }

    private void initListener() {
        mStatus1.setOnClickListener(this);
        mStatus2.setOnClickListener(this);
        mStatus3.setOnClickListener(this);
        mStatus4.setOnClickListener(this);
        mStatus5.setOnClickListener(this);
        mStatus6.setOnClickListener(this);
        mStatus7.setOnClickListener(this);
        mStatus8.setOnClickListener(this);
        mStatus9.setOnClickListener(this);
        mStatus10.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.status1: //修改标题栏为不透明,需要使用状态栏为暗色调
                StatusBarUtils.setStatusBar(this, true, true);
                break;
            case R.id.status2: //修改标题栏为不透明,不需要使用状态栏为暗色调
                StatusBarUtils.setStatusBar(this, true, true);
                break;
            case R.id.status3: //修改标题栏为透明,需要使用状态栏为暗色调
                StatusBarUtils.setStatusBar(this, false, false);
                break;
            case R.id.status4: //修改标题栏为透明,不需要使用状态栏为暗色调
                StatusBarUtils.setStatusBar(this, false, true);
                break;
            case R.id.status5: //修改标题栏为透明状态栏为暗色调，设置状态栏文字色值为深色调
                StatusBarUtils.setStatusBar(this, false, false);
                StatusBarUtils.setStatusTextColor(true, this);
                break;
            case R.id.status6: //修改标题栏为不透明状态栏为暗色调，设置状态栏文字色值为深色调
                StatusBarUtils.setStatusBar(this, true, false);
                StatusBarUtils.setStatusTextColor(true, this);
                break;
            case R.id.status7: //修改标题栏为透明状态栏为不暗色调，设置状态栏文字色值不为深色调
                StatusBarUtils.setStatusBar(this, false, true);
                StatusBarUtils.setStatusTextColor(false, this);
                break;
            case R.id.status8: //修改标题栏为不透明，设置状态栏文字色值不为深色调
                StatusBarUtils.setStatusBar(this, true, true);
                StatusBarUtils.setStatusTextColor(false, this);
                break;
            case R.id.status9: //虚拟按键透明
                StatusBarUtils.setNavigationBarColor("#00000000", this, false);
                break;
            case R.id.status10: //虚拟按键不透明
                StatusBarUtils.setNavigationBarColor("#EC6D65", this, true);
                break;
        }

    }
}
