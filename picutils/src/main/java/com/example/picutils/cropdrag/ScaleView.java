package com.example.picutils.cropdrag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * Created by dhy
 * Date: 2019/8/6
 * Time: 14:21
 * describe:
 * 当前的自定义view 拖拽和放大缩小图片 是保证控件大小没有改变，通过矩阵变换进行的图片定位展示，
 * 不能改变控件的大小，多用于图片裁剪时使用
 */
public class ScaleView extends android.support.v7.widget.AppCompatImageView implements OnGlobalLayoutListener, OnScaleGestureListener, View.OnTouchListener {
    /**
     * 是否是第一次加载图片
     */
    private boolean isOne;
    /**
     * 初始缩放比例
     */
    private float mInitScale = 0.1f;
    /**
     * 最大缩放比例
     */
    private float mMaxScale = mInitScale * 4;
    /**
     * 最小缩放比例
     */
    private float mMinScale = mInitScale / 4;
    /**
     * 图片缩放矩阵
     */
    private Matrix mMatrix;
    /**
     * 图片缩放手势
     */
    private ScaleGestureDetector mScaleGesture;

    // ----------------------------自由移动--------------------------------
    /**
     * 可移动最短距离限制，大于这个值时就可移动
     */
    private int mTouchSlop;

    /**
     * 限制（或者说截取）图片的范围
     */
    private RectF mRestrictRect;
    private float mIntentScale = 1f;
    /**
     * 最后点击手指的个数
     */
    private int mLastPointerCount;
    private boolean isCanDrag; //是否可以拖拽
    private float mLastx;
    private float mLasty;

    public ScaleView(Context context) {
        this(context, null);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        mMatrix = new Matrix();
        // 设置缩放模式  使用矩阵定位图片必须设置
        super.setScaleType(ScaleType.MATRIX);
        //是用于处理缩放的工具类 ScaleGestureDetector 方法onScale、onScaleBegin、onScaleEnd
        mScaleGesture = new ScaleGestureDetector(context, this);
        /**
         * 触发移动事件的最小距离，自定义View处理touch事件的时候，有的时候需要判断用户是否真的存在movie，
         * 系统提供了这样的方法。表示滑动的时候，手的移动要大于这个返回的距离值才开始移动控件。
         */
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 首先开始展示图片时进行中心展示
     */
    @Override
    public void onGlobalLayout() {
        //第一次加载图片时将图片居中展示
        if (!isOne) {
            //获取控件的高度和宽度
            int width = getWidth();
            int height = getHeight();
            //获取图片的宽高
            Drawable drawable = getDrawable();
            if (drawable == null)
                return;
            int bitmapWidth = drawable.getIntrinsicWidth();
            int bitmapHeight = drawable.getIntrinsicHeight();
            //设定比例值
            float scale = 1.0f;
            //如果图片的高度大于控件的高度
            if (bitmapHeight > height && bitmapWidth < width) {
                scale = height * 1f / bitmapHeight;
            }
            //如果图片的宽度大于控件的宽度
            if (bitmapHeight < height && bitmapWidth > width) {
                scale = width * 1f / bitmapWidth;
            }
            //如果图片的宽高都大于控件的宽高，或者都小于控件的宽高
            if ((bitmapHeight > height && bitmapWidth > width) || (bitmapHeight < height && bitmapWidth < width)) {
                float v = height * 1f / bitmapHeight;
                float v1 = width * 1f / bitmapWidth;
                scale = Math.min(v, v1);
            }
            //初始化缩放比例
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMinScale = mInitScale / 4;
            //计算移动距离
            int dx = width / 2 - bitmapWidth / 2;
            int dy = height / 2 - bitmapHeight / 2;
            //平移矩阵
            mMatrix.postTranslate(dx, dy);
            //在控件中心缩放
            mMatrix.postScale(scale, scale, width / 2, height / 2);
            //设置矩阵
            setImageMatrix(mMatrix);
            isOne = true;
        }
    }

    /**
     * 缩放进行中，返回值表示是否下次缩放需要重置，如果返回ture，那么detector就会重置缩放事件，如果返回false，detector会在之前的缩放上继续进行计算
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d("=====", " onScale" + detector.getScaleFactor());
        if (getDrawable() == null) {
            return true;
        }
        //获取缩放因子，>1放大  <1缩小
        mIntentScale = detector.getScaleFactor();
        float scale = getScale();
        //如果是放大 当前的放大值小于mMaxScale 或者缩小时当前的scale小于mInitScale时进行操作
        if ((mIntentScale > 1 && scale < mMaxScale) || (mIntentScale < 1 && scale > mMinScale)) {
            // scale 变小时， intentScale变小
            if (scale * mIntentScale < mMinScale) {
                // intentScale * scale = mInitScale ;
                mIntentScale = mMinScale / scale;
            }

            // scale 变大时， intentScale变大
            if (scale * mIntentScale > mMaxScale) {
                // intentScale * scale = mMaxScale ;
                mIntentScale = mMaxScale / scale;
            }
            // 以手势为中心缩放
            mMatrix.postScale(mIntentScale, mIntentScale, detector.getFocusX(), detector.getFocusY());
            // 以控件中心为中心缩放
//            mMatrix.postScale(mIntentScale, mIntentScale, getWidth()/2, getHeight()/2);
            setImageMatrix(mMatrix);
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGesture.onTouchEvent(event);
        int pointerCount = event.getPointerCount();
        float x = 0f;
        float y = 0f;
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;
        if (pointerCount != mLastPointerCount) {
            isCanDrag = false;
            mLastx = x;
            mLasty = y;
        }
        mLastPointerCount = pointerCount;

        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当图片的当前宽高大于控件的宽高就让父控件截断事件（目测没啥用，有待探讨）
//                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
                break;
            case MotionEvent.ACTION_MOVE:
//                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
                float dx = x - mLastx;
                float dy = y - mLasty;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                /**
                 * 如果能移动
                 */
                if (isCanDrag) {
                    if (getDrawable() == null) {
                        return true;
                    }
                    mMatrix.postTranslate(dx, dy);
                    setImageMatrix(mMatrix);
                }

                mLastx = x;
                mLasty = y;

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 清除手指
                mLastPointerCount = 0;
                break;
        }
        return true;
    }

    /**
     * 图片裁剪
     */
    public Bitmap crop() {
        if (getDrawable() == null) {
            return null;
        }
        if (mRestrictRect == null) {
            return null;
        }
        Bitmap tmpBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(tmpBitmap);
        draw(canvas);
        //通过裁剪创建新的画布
        Bitmap ret = Bitmap.createBitmap(tmpBitmap, (int) mRestrictRect.left,
                (int) mRestrictRect.top, (int) mRestrictRect.width(),
                (int) mRestrictRect.height());
        tmpBitmap.recycle();
        tmpBitmap = null;

        return ret;
    }

    /**
     * 获取当前的缩放比例
     *
     * @return
     */
    public float getScale() {
        //矩阵是3*3的
        float[] values = new float[9];
        //通过该方法获取矩阵中的3*3的9个数据存入 float数组中
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    public RectF getMatrixRectF() {
        Matrix matrix = mMatrix;
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            // 初始化矩阵
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            // 移动s  将此矩阵应用于矩形，并将变换后的矩形写回其中
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 求得两点的距离
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    /**
     * 传入要裁剪的范围
     *
     * @param restrictRect
     */
    public void setRestrictRect(RectF restrictRect) {
        mRestrictRect = restrictRect;
    }

    /**
     * 缩放开始，返回值表示是否受理后续的缩放事件
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 注册全局事件
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 移除全局事件
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

}
