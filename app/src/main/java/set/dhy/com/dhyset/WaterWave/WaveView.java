package set.dhy.com.dhyset.WaterWave;

/**
 * author dhy
 * Created by test on 2018/12/25.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

import set.dhy.com.dhyset.R;

public class WaveView extends View {

    /**
     * 默认波浪1长度
     */
    private final int WAVE_LENGTH1 = 200;

    /**
     * 默认波浪1高度
     */
    private final int WAVE_HEIGHT1 = 30;

    /**
     * 波浪1高度
     */
    private int mWaveHeight1 = WAVE_HEIGHT1;

    /**
     * 波浪1长度
     */
    private int mWaveLenght1 = WAVE_LENGTH1;

    /**
     * 默认波浪1颜色
     */
    private final int WAVE_COLOR1 = Color.parseColor("#0000ff");

    /**
     * 默认边框颜色
     */
    private final int BORDER_COLOR = Color.parseColor("#800000ff");

    /**
     * 默认文字颜色
     */
    private final int DEFAULT_TEXT_COLOR = Color.parseColor("#ff0000");
    /**
     * 默认文字大小
     */
    private final int DEFAULT_TEXT_SIZE = 30;

    /**
     * 文字颜色
     */
    private int mTextColor = DEFAULT_TEXT_COLOR;
    /**
     * 文字大小
     */
    private int mTextSize = DEFAULT_TEXT_SIZE;

    /**
     * 波浪1颜色
     */
    private int mWaveColor1 = WAVE_COLOR1;

    /**
     * 默认每一次重绘时波峰1的移动的距离，实现移动效果
     */
    private final int WAVE_OFFSET1 = 8;

    /**
     * 每一次重绘时波峰1的移动的距离，实现移动效果
     */
    private int mOffset1 = WAVE_OFFSET1;

    /**
     * 默认边框宽度
     */
    private final int BORDER_WIDTH = 2;

    /**
     * 边框颜色
     */
    private int mBorderColor = BORDER_COLOR;

    /**
     * 边框宽度
     */
    private int mBorderWidth = BORDER_WIDTH;

    /**
     * 绘制的高度，百分比。0表示内有高度，1表示全部高度
     */
    private float mPrecent = 0.5f;

    /**
     * 形状枚举，暂时只支持矩形和圆形，可扩展
     */
    public enum ShowShape {
        RECT,
        CIRE
    }

    /**
     * 形状默认矩形
     */
    private ShowShape mShape = ShowShape.RECT;

    /**
     * 默认两次重绘之间间隔的时间，5毫秒
     */
    private final int DEFAULT_TIME = 5;

    /**
     * 两次重绘之间间隔的时间，毫秒。
     */
    private int mTime = DEFAULT_TIME;

    /**
     * 设置两次重绘之间的间隔时间，毫秒
     *
     * @param time
     * @return
     */
    public WaveView setTime(int time) {
        this.mTime = time;
        return this;
    }

    /**
     * 波浪1画笔
     */
    private Paint mWavePaint1;

    /**
     * 边框画笔
     */
    private Paint mBorderPaint;
    /**
     * 文字画笔
     */
    private Paint mTextPaint;

    /**
     * 波浪1路径
     */
    private Path mWavePath1;

    /**
     * 定义数字格式转行类
     */
    private DecimalFormat mFormat;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;

    /**
     * 水位上升时不断变化的 y 坐标
     */
    private float mChangeY;
    /**
     * 水位最终的高度，通过控件的高度和百分比计算出来
     */
    private float mFinalY;
    /**
     * 波峰的个数
     */
    private int mWaveCount = 8;
    /**
     * 重置标记，开始为重置状态
     */
    private boolean isReset = true;

    /**
     * 当前百分比
     */
    private float mCurrentPrecent = 0.0f;
    /**
     * 重绘值波峰移动距离的和
     */
    private int mMoveSum1;
    /**
     * 能够绘制标记位，开始不能绘制
     */
    private boolean invalidateFlag = false;
    /**
     * 百分比改变监听
     */
    private PrecentChangeListener mPrecentChangeListener;


    /**
     * 百分比改变监听接口
     */
    public interface PrecentChangeListener {
        /**
         * 百分比发生改变时调用的方法
         *
         * @param precent 当前的百分比，格式 0.00 范围 [0.00 , 1.00]
         */
        void precentChange(double precent);
    }


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs); // 获取布局文件中dingy9i的属性
        init();
    }

    //获取布局中的初始属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        mWaveLenght1 = typedArray.getInteger(R.styleable.WaveView_wave1Length, WAVE_LENGTH1);
        mWaveHeight1 = typedArray.getInteger(R.styleable.WaveView_wave1Height, WAVE_HEIGHT1);
        mWaveColor1 = typedArray.getColor(R.styleable.WaveView_wave1Color, WAVE_COLOR1);
        mOffset1 = typedArray.getInteger(R.styleable.WaveView_wave1Offset, WAVE_OFFSET1);

        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.WaveView_borderWidth, BORDER_WIDTH);
        mBorderColor = typedArray.getColor(R.styleable.WaveView_borderColor, BORDER_COLOR);

        mTime = typedArray.getInteger(R.styleable.WaveView_intervalTime, DEFAULT_TIME);
        mPrecent = typedArray.getFloat(R.styleable.WaveView_precent, 0.5f);
        /**
         * 绘制的高度，百分比。0表示内有高度，1表示全部高度
         */
        int shapeValue = typedArray.getInteger(R.styleable.WaveView_showShape, 0);

        mShape = ShowShape.RECT;
        typedArray.recycle();
    }

    private void init() {
        mWavePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mWavePath1 = new Path();

        mWavePaint1.setColor(mWaveColor1);
        mWavePaint1.setStyle(Paint.Style.FILL);

        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        // 定义数字显示个格式
        mFormat = new DecimalFormat("###,###,##0.00");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mChangeY = mHeight;
        // 计算波峰个数，为了实现移动效果，保证至少绘制两个波峰
        mFinalY = (1 - mPrecent) * mHeight; // 计算水位最终高度
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        mWavePath1.reset();

        int radius = mWidth > mHeight ? mHeight / 2 : mWidth / 2;
        if (mBorderWidth > 0) {
            // 边框大于0,表示需要绘制边框
            if (mShape == ShowShape.RECT) {
                canvas.drawRect(0, 0, mWidth, mHeight, mBorderPaint);
            } else if (mShape == ShowShape.CIRE) {
                canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mBorderPaint);
            }
        }

        mWavePath1.moveTo(-mWaveLenght1, mChangeY);

        if (!isReset) { // 判断重置标记
            // 利用贝塞尔曲线绘制波浪
            for (int i = 0; i < mWaveCount; i++) {
                // 绘制波浪1的曲线
                mWavePath1.quadTo((-mWaveLenght1 * 3 / 4) + (i * mWaveLenght1) + mMoveSum1, mChangeY + mWaveHeight1, (-mWaveLenght1 / 2) + (i * mWaveLenght1) + mMoveSum1, mChangeY);
                mWavePath1.quadTo((-mWaveLenght1 * 1 / 4) + (i * mWaveLenght1) + mMoveSum1, mChangeY - mWaveHeight1, (i * mWaveLenght1) + mMoveSum1, mChangeY);

            }
            // 填充矩形，让上涨之后的水位下面填充颜色
            mWavePath1.lineTo(mWidth, mHeight);
            mWavePath1.lineTo(0, mHeight);
            mWavePath1.close();
            if (mShape == ShowShape.CIRE) {
                Path path = new Path();
                path.addCircle(mWidth / 2, mHeight / 2, radius, Path.Direction.CCW);
                mWavePath1.op(path, Path.Op.INTERSECT);
            }
            canvas.drawPath(mWavePath1, mWavePaint1);

            // 不断改变高度，实现逐渐水位逐渐上涨效果
            mChangeY -= 1;
            if (mChangeY < mFinalY) mChangeY = mFinalY;

            // 波峰1往右移动，波峰2往左移动
            mMoveSum1 += mOffset1;
            if (mMoveSum1 >= mWaveLenght1) mMoveSum1 = 0;
        } else {
            // 是重置
            canvas.drawColor(Color.TRANSPARENT);
        }

        //高度到达设置高度
        if (mChangeY == mFinalY) {
            invalidateFlag = false;
            if (mShape == ShowShape.CIRE) {
                canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mWavePaint1);
            } else if (mShape == ShowShape.RECT) {
                Path path = new Path();
                path.addRect(0, 0, mWidth, mHeight, Path.Direction.CCW);
                canvas.drawPath(path, mWavePaint1);
            }
        }

        // 计算当前的百分比
        mCurrentPrecent = 1 - mChangeY / mHeight;
        // 格式化数字格式
        String format1 = mFormat.format(mCurrentPrecent);
        // 绘制文字，将百分比绘制到界面。此处用的是 "50%" 的形式，可以根据需求改变格式
        double parseDouble = Double.parseDouble(format1);
        canvas.drawText((int) (parseDouble * 100) + " %", (mWidth - mTextPaint.measureText(format1)) / 2, mHeight / 5, mTextPaint);
        // 监听对象不为null并且没有达到设置高度时，调用监听方法
        if (mPrecentChangeListener != null && mChangeY != mFinalY) {
            mPrecentChangeListener.precentChange(parseDouble);
        }
        // 判断绘制标记
        if (invalidateFlag) postInvalidateDelayed(mTime);
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth
     * @return
     */
    public WaveView setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
        return this;
    }

    /**
     * 设置波浪1颜色
     *
     * @param waveColor1
     * @return
     */
    public WaveView setWaveColor1(int waveColor1) {
        this.mWaveColor1 = waveColor1;
        return this;
    }

    /**
     * 设置边框颜色
     *
     * @param borderColor
     * @return
     */
    public WaveView setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     * @return
     */
    public WaveView setTextColor(int textColor) {
        this.mTextColor = textColor;
        return this;
    }

    /**
     * 设置百分比
     *
     * @param precent
     * @return
     */
    public WaveView setPrecent(float precent) {
        this.mPrecent = precent;
        return this;
    }


    /**
     * 设置文字大小
     *
     * @param textSize
     * @return
     */
    public WaveView setTextSize(int textSize) {
        this.mTextSize = textSize;
        return this;
    }


    /**
     * 设置当前显示形状
     *
     * @param shape
     * @return
     */
    public WaveView setShape(ShowShape shape) {
        this.mShape = shape;
        return this;
    }

    /**
     * 开始
     */
    public void start() {
        invalidateFlag = true;
        isReset = false;
        postInvalidateDelayed(mTime);
    }

    /**
     * 暂停
     */
    public void stop() {
        invalidateFlag = false;
        isReset = false;
    }

    /**
     * 重置
     */
    public void reset() {
        invalidateFlag = false;
        isReset = true;
        mChangeY = mHeight;
        postInvalidate();
    }
}
