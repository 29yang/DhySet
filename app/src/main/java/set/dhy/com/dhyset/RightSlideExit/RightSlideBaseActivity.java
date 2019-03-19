package set.dhy.com.dhyset.RightSlideExit;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * author dhy
 * 创建的activity继承该baseactivity即可实现右滑退出功能
 * Created by test on 2018/4/4.
 */

public class RightSlideBaseActivity extends SwipeBackActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
    }
}
