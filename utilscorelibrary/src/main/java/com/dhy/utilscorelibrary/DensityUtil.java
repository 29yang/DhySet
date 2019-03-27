package com.dhy.utilscorelibrary;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * author dhy
 * Created by test on 2018/4/16.
 */

public class DensityUtil {
    /**
     * dp 转换 px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dpToPx(Context context, float dip) {
//        final float SCALE = context.getResources().getDisplayMetrics().density;
//        float valueDips = dip;
//        int valuePixels = (int) (valueDips * SCALE + 0.5f);
//        return valuePixels;
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());

    }
    /** dp转px */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /** sp转px */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /** px转dp */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /** px转sp */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕宽度
     */
    public static float getWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static float getHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的高度
        return dm.heightPixels;
    }


}
