package com.example.picutils;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.picutils.cropdrag.ScaleView;

public class CropActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv;
    Button mButton;
    ScaleView mScaleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        mScaleView = findViewById(R.id.scale_view);
        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.img_show);
        mButton = findViewById(R.id.crop);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScaleView.post(new Runnable() {
                    @Override
                    public void run() {
                        RectF restrictRect = new RectF(tv.getLeft(),tv.getTop(),tv.getRight(),tv.getBottom());
                        mScaleView.setRestrictRect(restrictRect);
                        Bitmap crop = mScaleView.crop();
                        iv.setImageBitmap(crop);
                    }
                });
            }
        });
    }
}
