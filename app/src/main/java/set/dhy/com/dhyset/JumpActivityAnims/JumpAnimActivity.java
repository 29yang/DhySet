package set.dhy.com.dhyset.JumpActivityAnims;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;

public class JumpAnimActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.explode)
    TextView mExplode;
    @BindView(R.id.slide)
    TextView mSlide;
    @BindView(R.id.fade)
    TextView mFade;
    @BindView(R.id.share)
    TextView mShare;
    public static int type = 1;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_anim);
        ButterKnife.bind(this);
        mExplode.setOnClickListener(this);
        mSlide.setOnClickListener(this);
        mFade.setOnClickListener(this);
        mShare.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.explode:
                type = 1;
                Intent intent1 = new Intent(this, JumpToActivity.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.slide:
                type = 2;
                Intent intent2 = new Intent(this, JumpToActivity.class);
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.fade:
                type = 3;
                Intent intent3 = new Intent(this, JumpToActivity.class);
                startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.share:
                type = 4;
                Intent intent4 = new Intent(this, ShareAnimActivity.class);
                startActivity(intent4, ActivityOptions.makeSceneTransitionAnimation(this,
                                Pair.create((View) mIv, "iv"),
                                Pair.create((View) mTv, "tv")).toBundle());

                break;
        }

    }
}
