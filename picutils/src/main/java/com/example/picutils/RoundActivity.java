package com.example.picutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.picutils.crop.RoundImageView;

public class RoundActivity extends AppCompatActivity {
    RoundImageView mRoundImageView;
    SeekBar mBar0, mBar1, mBar2, mBar3, mBar4, mBar5, mBar6, mBar7;
    TextView mTv0, mTv1, mTv2, mTv3, mTv4, mTv5, mTv6, mTv7;
    float x0, y0, x1, y1, x2, y2, x3, y3 ;
    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        mRoundImageView = findViewById(R.id.iv_round);
        ViewGroup.LayoutParams layoutParams = mRoundImageView.getLayoutParams();
        width = layoutParams.width;
        height = layoutParams.height;
        mBar0 = findViewById(R.id.sb_0);
        mBar1 = findViewById(R.id.sb_1);
        mBar2 = findViewById(R.id.sb_2);
        mBar3 = findViewById(R.id.sb_3);
        mBar4 = findViewById(R.id.sb_4);
        mBar5 = findViewById(R.id.sb_5);
        mBar6 = findViewById(R.id.sb_6);
        mBar7 = findViewById(R.id.sb_7);
        mTv0 = findViewById(R.id.text_0);
        mTv1 = findViewById(R.id.text_1);
        mTv2 = findViewById(R.id.text_2);
        mTv3 = findViewById(R.id.text_3);
        mTv4 = findViewById(R.id.text_4);
        mTv5 = findViewById(R.id.text_5);
        mTv6 = findViewById(R.id.text_6);
        mTv7 = findViewById(R.id.text_7);
        mBar0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                x0 = width*(progress/100f);
                drawPic();
                mTv0.setText(String.valueOf(x0+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                y0 = width*(progress/100f);
                drawPic();
                mTv1.setText(String.valueOf(y0+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                x1 = width*(progress/100f);
                drawPic();
                mTv2.setText(String.valueOf(x1+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                y1 = width*(progress/100f);
                drawPic();
                mTv3.setText(String.valueOf(y1+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                x2 = width*(progress/100f);
                drawPic();
                mTv4.setText(String.valueOf(x2+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                y2 = width*(progress/100f);
                drawPic();
                mTv5.setText(String.valueOf(y2+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBar6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                x3 = width*(progress/100f);
                drawPic();
                mTv6.setText(String.valueOf(x3+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBar7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                y3 = width*(progress/100f);
                drawPic();
                mTv7.setText(String.valueOf(y3+"px"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    //传入参数绘制图片
    private void drawPic() {
        mRoundImageView.setRids(x0,y0,x1,y1,x2,y2,x3,y3);
    }
}
