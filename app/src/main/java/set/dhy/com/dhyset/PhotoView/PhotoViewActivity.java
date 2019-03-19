package set.dhy.com.dhyset.PhotoView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.utils.StatusBarUtils;

public class PhotoViewActivity extends AppCompatActivity {

    @BindView(R.id.view_pager_photo)
    PhotoViewPager mViewPagerPhoto;
    @BindView(R.id.tv_image_count)
    TextView mTvImageCount;
    @BindView(R.id.toolbar)
    TextView mToolbar;
    @BindView(R.id.back)
    ImageView mBack;
    private int index;
    private List<String> imagePaths;
    private MyImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        StatusBarUtils.setStatusBar(this, null, true);
        ButterKnife.bind(this);
        mToolbar.setHeight(StatusBarUtils.getStatusBarHeight(this));
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        imagePaths = intent.getStringArrayListExtra("imagePath");
        adapter = new MyImageAdapter(imagePaths, this);
        mViewPagerPhoto.setAdapter(adapter);
        mViewPagerPhoto.setCurrentItem(index, false);
        mTvImageCount.setText(index + 1 + "/" + imagePaths.size());
        mViewPagerPhoto.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                index = position;
                mTvImageCount.setText(index + 1 + "/" + imagePaths.size());
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoViewActivity.this.finish();
            }
        });
    }
}
