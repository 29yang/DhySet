package set.dhy.com.dhyset.rvadapter.Examples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.utils.StatusBarUtils;

public class AllExampleActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.button_grid)
    Button mButtonGrid;
    @BindView(R.id.button_test_state)
    Button mButtonTestState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_example);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBar(this,"#ffffff",false);

        initView();
    }

    private void initView() {

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllExampleActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllExampleActivity.this,GridActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_test_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllExampleActivity.this,StateActivity.class);
                startActivity(intent);
            }
        });
    }
}
