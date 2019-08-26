package com.example.tantancarddemo.usemytouch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by dhy
 * Date: 2019/8/23
 * Time: 14:59
 * describe:
 */
public class SwipeCardListener implements View.OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    /**
     * 当前view在总数据中得下标
     */
    private Object itemAtDataPosition;
    /**
     * 要监听touch时间的控件
     */
    private final View mView;
    private final ItemSwipListener mItemSwipListener;
    /**
     * 原始view的 x y width height
     */
    private float viewX;
    private float viewY;
    private int viewW;
    private int viewH;
    /**
     * view的要移动到的位置 x y
     */
    private float mTargetX;
    private float mTargetY;
    /**
     * 手指点击位置 x y
     */
    private float mTouchX;
    private float mTouchY;
    /**
     * 旋转角度
     */
    private float ROTATE_DEGREES;
    /**
     * 父控件的宽度
     */
    private int parentW;
    /**
     * 点击的手指id
     */
    private int pointerId;
    private float MAX_COS = (float) Math.cos(Math.toRadians(45)); //最大选择角度
    private boolean isAnimationRunning = false;

    public SwipeCardListener(View view, Object itemAtDataPosition, int parentW, ItemSwipListener itemSwipListener) {
        this(view, itemAtDataPosition, parentW, 15f, itemSwipListener);
    }

    public SwipeCardListener(View view, Object itemAtDataPosition, int parentW, float rotate_degrees, ItemSwipListener itemSwipListener) {
        this.mView = view;
        this.mItemSwipListener = itemSwipListener;
        this.itemAtDataPosition = itemAtDataPosition;
        this.viewX = view.getX();
        this.viewY = view.getY();
        this.viewW = view.getWidth();
        this.viewH = view.getHeight();
        this.parentW = parentW;
        this.ROTATE_DEGREES = rotate_degrees;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                pointerId = event.getPointerId(0);
                float x = 0;
                float y = 0;
                boolean isSuc = false;
                try {
                    x = event.getX(pointerId);
                    y = event.getY(pointerId);
                    isSuc = true;
                } catch (Exception e) {

                }
                if (isSuc) {
                    mTouchX = x;
                    mTouchY = y;
                    if (mTargetX == 0)
                        mTargetX = viewX;
                    if (mTargetY == 0)
                        mTargetY = viewY;
                }
//                mView.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                //清除手指id
                pointerId = INVALID_POINTER_ID;
                //重置列表
                resetCardViewOnStack();
//                mView.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // Extract the index of the pointer that left the touch sensor
                final int pointerIndex = (event.getAction() &
                        MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int newpointerId = event.getPointerId(pointerIndex);
                if (newpointerId == pointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    this.pointerId = event.getPointerId(newPointerIndex);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex1 = event.findPointerIndex(pointerId);
                float movex = event.getX(pointerIndex1);
                float moveY = event.getY(pointerIndex1);
                float dx = movex - mTouchX;
                float dy = moveY - mTouchY;
                mTargetX += dx;
                mTargetY += dy;
                //旋转角度可以根据自己的实际情况改乘法
                float rotate = (mTargetX - viewX) * 3 * ROTATE_DEGREES / parentW;
                mView.setX(mTargetX);
                mView.setY(mTargetY);
                mView.setRotation(rotate);
                mItemSwipListener.onScroll(getScrollProgressPercen());
                break;
            case MotionEvent.ACTION_CANCEL:
                pointerId = INVALID_POINTER_ID;
//                mView.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    //当手指抬起时根据滑动的距离进行列表的复位
    private void resetCardViewOnStack() {
        if (movedBeyondLeftBoder()) { //超出左侧阀值
            onScrolled(true, getExitPoint(-viewW), 100, false);
            mItemSwipListener.onScroll(-1f);
            return;
        }
        if (movedBeyondRightBoder()) {
            onScrolled(false, getExitPoint(parentW), 100, false);
            mItemSwipListener.onScroll(1f);
            return;
        }
        if (mTargetX - viewX < 1) {
            //当滑动距离小的时候默认是点击事件
            mItemSwipListener.onClick(itemAtDataPosition);
        }
        final float progressPercen = getScrollProgressPercen();
        //复位
        mTargetX = 0;
        mTargetY = 0;
        mTouchX = 0;
        mTouchY = 0;
        mView.animate().setDuration(200)
                .setInterpolator(new OvershootInterpolator(1.5f)) //插值器
                .x(viewX)
                .y(viewY)
                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Log.e("======", animation.getCurrentPlayTime() + "");
                        mItemSwipListener.onScroll(progressPercen * (200f - animation.getCurrentPlayTime()) / 200f);
                    }
                })
                .setListener(null)
                .rotation(0);

    }

    private void onScrolled(final boolean isLeft, float endY, int deration, final boolean isDrag) {
        isAnimationRunning = true;
        float endX;
        if (isLeft) {
            endX = -viewW - getRotationWidthOffset();
        } else {
            endX = parentW + getRotationWidthOffset();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mView.animate().setDuration(deration)
                    .setInterpolator(new AccelerateInterpolator()) //插值器
                    .x(endX)
                    .y(endY)
                    .rotation(getExitRotation(isLeft))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //复位
                            mTargetX = 0;
                            mTargetY = 0;
                            mTouchX = 0;
                            mTouchY = 0;
                            if (isLeft) {
                                mItemSwipListener.onCardExit(viewX, viewY);
                                mItemSwipListener.onLeftExit(itemAtDataPosition);
                            } else {
                                mItemSwipListener.onCardExit(viewX, viewY);
                                mItemSwipListener.onRightExit(itemAtDataPosition);
                            }
                            isAnimationRunning = false;
                        }
                    })
                    .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if (isDrag)
                                mItemSwipListener.onScroll(animation.getAnimatedFraction());
                        }
                    });
        }
    }

    private float getExitRotation(boolean isleft) {
        float v = ROTATE_DEGREES * 2.f * (parentW - viewX) / parentW;
        if (isleft)
            return -v;
        return v;
    }

    /**
     * Starts a default left exit animation.
     */
    public void selectLeft() {
        if (!isAnimationRunning)
            onScrolled(true, viewY, 200, true);
    }

    /**
     * Starts a default right exit animation.
     */
    public void selectRight() {
        if (!isAnimationRunning)
            onScrolled(false, viewY, 200, true);
    }

    private float getRotationWidthOffset() {
        return viewW / MAX_COS - viewW;
    }

    private float getExitPoint(int exitXPoint) {
        float[] x = new float[2];
        x[0] = viewX;
        x[1] = mTargetX;

        float[] y = new float[2];
        y[0] = viewY;
        y[1] = mTargetY;

        LinearRegression regression = new LinearRegression(x, y);
        //Your typical y = ax+b linear regression
        return (float) regression.slope() * exitXPoint + (float) regression.intercept();
    }

    /**
     * 获取滑动的百分比 -1-1之间
     */
    private float getScrollProgressPercen() {
        if (movedBeyondLeftBoder())
            return -1;
        if (movedBeyondRightBoder())
            return 1;
        float zeroToOneValue = (mTargetX + viewW / 2f - leftBorder()) / (rightborder() - leftBorder());
        return zeroToOneValue * 2f - 1f;
    }

    /**
     * 判断是否超过左侧阀值
     */
    private boolean movedBeyondLeftBoder() {
        return mTargetX + viewW / 2 < leftBorder();
    }

    private boolean movedBeyondRightBoder() {
        return mTargetX + viewW / 2 > rightborder();
    }

    /**
     * 左侧阀值
     */
    private float leftBorder() {
        return parentW / 6f;
    }

    /**
     * 右侧阀值
     */
    private float rightborder() {
        return 5 * parentW / 6f;
    }

    public boolean isTouching() {
        return this.pointerId != INVALID_POINTER_ID;
    }

    public PointF getLastPoint() {
        return new PointF(this.mTargetX, this.mTargetY);
    }

    public interface ItemSwipListener {
        void onCardExit(float x, float y);

        void onLeftExit(Object itemAtDataPosition);

        void onRightExit(Object itemAtDataPosition);

        void onClick(Object itemAtDataPosition);

        void onScroll(float scrollProgressPercent);
    }
}
