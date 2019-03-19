package set.dhy.com.dhyset.utils;

import android.content.Context;

/**
 * author dhy
 * Created by test on 2018/4/16.
 */

public class DpAndPx {
    /**
     * dp 转换 px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dpToPx(Context context, float dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int) (valueDips * SCALE + 0.5f);
        return valuePixels;
    }


}
