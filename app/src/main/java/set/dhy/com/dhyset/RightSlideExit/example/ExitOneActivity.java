package set.dhy.com.dhyset.RightSlideExit.example;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dhy.utilscorelibrary.status_bar_util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.RightSlideExit.RightSlideBaseActivity;

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
    }

    private void initListener() {
        mStatus1.setOnClickListener(this);
        mStatus2.setOnClickListener(this);
        mStatus3.setOnClickListener(this);
        mStatus4.setOnClickListener(this);
        mStatus5.setOnClickListener(this);
        mStatus6.setOnClickListener(this);
        mStatus8.setOnClickListener(this);
        mStatus9.setOnClickListener(this);
        mStatus10.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.status1: //修改标题栏为不透明（白色）,状态栏为黑色
                StatusBarUtils.setStatusBar(this, "#ffffff", true);
                break;
            case R.id.status2: //修改标题栏为不透明（红色）,状态栏为黑色
                StatusBarUtils.setStatusBar(this, "#ff0000", true);
                break;
            case R.id.status3: //修改标题栏为不透明（白色）,状态栏为白色
                StatusBarUtils.setStatusBar(this, "#ffffff", false);
                break;
            case R.id.status4: //修改标题栏为不透明（红色）,状态栏为白色
                StatusBarUtils.setStatusBar(this, "#ff0000", false);
                break;
            case R.id.status5: //标题栏为透明状态，状态栏文字色值为黑色
                StatusBarUtils.setStatusBar(this, null, true);
                break;
            case R.id.status6: //标题栏为透明状态，状态栏文字色值为白色
                StatusBarUtils.setStatusBar(this, null, false);
                break;
                case R.id.status8: //修改底部虚拟按键为不透明 绿色
                StatusBarUtils.setNavigationBarColor("#00ff00", this, true);
                break;
                case R.id.status9: //虚拟按键透明
                StatusBarUtils.setNavigationBarColor("", this, false);
                break;
            case R.id.status10: //虚拟按键不透明
                StatusBarUtils.setNavigationBarColor("#ff0000", this, true);
                break;
        }

    }
}
