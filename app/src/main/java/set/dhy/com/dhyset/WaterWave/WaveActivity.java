package set.dhy.com.dhyset.WaterWave;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import set.dhy.com.dhyset.R;

public class WaveActivity extends AppCompatActivity {
    private WaveView waveview1;
    private Button btStart;
    private Button btStop;
    private Button btReset;
    private Button fang;
    private Button ciecle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        waveview1 = (WaveView) findViewById(R.id.waveview1);

        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        btReset = (Button) findViewById(R.id.bt_reset);
        fang = (Button) findViewById(R.id.fang);
        ciecle = (Button) findViewById(R.id.circle);

        // 代码设置相关属性
        waveview1.setBorderWidth(2)
                .setWaveColor1(Color.RED)
                .setBorderColor(Color.GREEN)
                .setTextColor(Color.BLUE)
                .setShape(WaveView.ShowShape.RECT)
                .setTextSize(36)
                .setPrecent(1f)//设置水波纹的百分比
                .setTime(2);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveview1.start();
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveview1.stop();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveview1.reset();
            }
        });
        fang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveview1.setShape(WaveView.ShowShape.RECT);
                waveview1.reset();
                waveview1.start();
            }
        });
        ciecle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveview1.setShape(WaveView.ShowShape.CIRE);
                waveview1.reset();
                waveview1.start();
            }
        });
    }
}