package set.dhy.com.dhyset.RightSlideExit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.RightSlideExit.example.ExitOneActivity;
import set.dhy.com.dhyset.utils.StatusBarUtils;

/**
 * 展示右滑退出的功能，首页面
 */
public class RightSlideExitMainActivity extends AppCompatActivity {

    @BindView(R.id.jump_right_slide_exit)
    TextView mJumpRightSlideExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_slide_exit_main);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBar(this,false,false);
        StatusBarUtils.setNavigationBarColor("",this,false);
        mJumpRightSlideExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RightSlideExitMainActivity.this, ExitOneActivity.class));
            }
        });
    }
}
