package com.example.picutils.cropdrag;

/**
 * Created by dhy
 * Date: 2019/7/24
 * Time: 11:40
 * describe:
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.picutils.R;


/**
 * 自定义图片样式，顶部圆角显示
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private Path mPath;
    private RectF mRectF;
    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = new float[8];
    private PaintFlagsDrawFilter paintFlagsDrawFilter;

    public float[] getRids() {
        return rids;
    }

    /**
     * 可以在属性中修改，也可在代码中修改参数
     * @param left_top_x 左上
     * @param left_top_y 左上
     * @param right_top_x 右上
     * @param right_top_y 右上
     * @param right_bottom_x 右下
     * @param right_bottom_y 右下
     * @param left_bottom_x 左下
     * @param left_bottom_y 左下
     */
    public void setRids(float left_top_x,float left_top_y,float right_top_x,float right_top_y,
                        float right_bottom_x,float right_bottom_y,float left_bottom_x,float left_bottom_y) {
        rids[0] = left_top_x;
        rids[1] = left_top_y;
        rids[2] = right_top_x;
        rids[3] = right_top_y;
        rids[4] = right_bottom_x;
        rids[5] = right_bottom_y;
        rids[6] = left_bottom_x;
        rids[7] = left_bottom_y;
        invalidate();
    }

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        //获取输入的圆角信息   可以根据不同的x y
        float left_top_x = array.getDimension(R.styleable.RoundImageView_left_top_x, 0);
        float left_top_y = array.getDimension(R.styleable.RoundImageView_left_top_y, 0);
        float right_top_x = array.getDimension(R.styleable.RoundImageView_right_top_x, 0);
        float right_top_y = array.getDimension(R.styleable.RoundImageView_right_top_y, 0);
        float right_bottom_x = array.getDimension(R.styleable.RoundImageView_right_bottom_x, 0);
        float right_bottom_y = array.getDimension(R.styleable.RoundImageView_right_bottom_y, 0);
        float left_bottom_x = array.getDimension(R.styleable.RoundImageView_left_bottom_x, 0);
        float left_bottom_y = array.getDimension(R.styleable.RoundImageView_left_bottom_y, 0);
        //分别赋值 0是左上角x半径 1是左上角y半径  以此类推
        rids[0] = left_top_x;
        rids[1] = left_top_y;
        rids[2] = right_top_x;
        rids[3] = right_top_y;
        rids[4] = right_bottom_x;
        rids[5] = right_bottom_y;
        rids[6] = left_bottom_x;
        rids[7] = left_bottom_y;
        array.recycle();
        //用于绘制的类
        mPath = new Path();
        //抗锯齿
        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        //关闭硬件加速，同时其他地方依然享受硬件加速
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Log.d("mylog", "onDraw: ");
        //重置path
        mPath.reset();
        //p1:大小，p2:圆角，p3:CW:顺时针绘制path，CCW:逆时针
        mPath.addRoundRect(mRectF, rids, Path.Direction.CW);
        //添加抗锯齿
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.save();
        //该方法不支持硬件加速，如果开启会导致效果出不来，所以之前设置关闭硬件加速
        //Clip(剪切)的时机：通常理解的clip(剪切)，是对已经存在的图形进行clip的。
        // 但是，在android上是对canvas（画布）上进行clip的，要在画图之前对canvas进行clip，
        // 如果画图之后再对canvas进行clip不会影响到已经画好的图形。一定要记住clip是针对canvas而非图形
        //开始根据path裁剪
        canvas.clipPath(mPath);
        super.onDraw(canvas);
        //可以在此设置边框颜色 宽度
//        Paint paint = new Paint();
//        paint.setARGB(255,255,20,20);
//        paint.setStrokeWidth(10);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setAntiAlias(true); //设置抗锯齿
//        paint.setDither(true);  //设置防抖动
//        canvas.drawPath(mPath, paint);
        canvas.restore();
    }

    int a,b;
    //执行在onDraw()之前
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        a = w;
        b = h;
        mRectF = new RectF(0, 0, w, h);
    }

}
