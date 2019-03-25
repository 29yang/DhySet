package set.dhy.com.dhyset.rvadapter.Examples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.utils.StatusBarUtils;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        StatusBarUtils.setStatusBar(this, "#ffffff", true);

    }
}
