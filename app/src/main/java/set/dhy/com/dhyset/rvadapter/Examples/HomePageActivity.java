package set.dhy.com.dhyset.rvadapter.Examples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.utils.StatusBarUtils;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        StatusBarUtils.setStatusBar(this, "#ffffff", false);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame, new HomePageFrame()).commit();
    }
}
