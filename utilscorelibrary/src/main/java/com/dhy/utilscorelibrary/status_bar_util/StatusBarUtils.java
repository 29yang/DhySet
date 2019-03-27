package com.dhy.utilscorelibrary.status_bar_util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author dhy
 * Created by test on 2018/4/4.
 * 介绍 ：
 * 当设置为标题栏为透明时，布局会被标题栏遮挡 当在布局中添加     android:fitsSystemWindows="true"
 * 就会自动适配标题栏，但是此时的标题栏背景就是布局的背景，如果不添加该属性可以写一个占位透明布局，在oncreate中
 * 主动获取标题高度并赋予占位布局。
 * 当设置底部导航栏透明时，没有    android:fitsSystemWindows="true" 属性时会被虚拟按键遮挡布局，虚拟按键会显示总布局的颜色
 * 但是当设置为不透明自定义颜色时不设置    android:fitsSystemWindows="true"属性也不会遮挡布局
 */
@SuppressWarnings("all")
public class StatusBarUtils {
    private static int screenWidth;
    private static int screenHeight;
    private static int navigationHeight = 0;

    private static DisplayMetrics mMetrics;
    private static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取底部导航栏高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        navigationHeight = resources.getDimensionPixelSize(resourceId);
        return navigationHeight;
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    /**
     * @param activity
     * @param useThemestatusBarColor   状态栏的颜色，null默认则为透明色 输入格式"#EC6D65"
     * @param withoutUseStatusBarColor 是否不需要使用状态栏为暗色调 true使用深色调（黑色） false使用白色
     */
    public static void setStatusBar(Activity activity, @Nullable String useThemestatusBarColor, boolean withoutUseStatusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (!TextUtils.isEmpty(useThemestatusBarColor)) {
                activity.getWindow().setStatusBarColor(Color.parseColor(useThemestatusBarColor));
            } else {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setStatusTextColor(withoutUseStatusBarColor,activity);
    }

    public static void reMeasure(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        mMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(mMetrics);
        } else {
            display.getMetrics(mMetrics);
        }

        screenWidth = mMetrics.widthPixels;
        screenHeight = mMetrics.heightPixels;
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private static void processFlyMe(boolean isLightStatusBar, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        try {
            Class<?> instance = Class.forName("android.view.WindowManager$LayoutParams");
            int value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp);
            Field field = instance.getDeclaredField("meizuFlags");
            field.setAccessible(true);
            int origin = field.getInt(lp);
            if (isLightStatusBar) {
                field.set(lp, origin | value);
            } else {
                field.set(lp, (~value) & origin);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上  lightStatusBar为真时表示黑色字体
     */
    private static void processMIUI(boolean lightStatusBar, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), lightStatusBar ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    private static int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

    /**
     * 改变OPPO手机状态栏的字体颜色为黑色，要求是
     */
    private static void processOPPO(boolean useDart, Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int vis = window.getDecorView().getSystemUiVisibility();
        //6.0以后使用Google提供View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 的Flag来设置状态栏图标黑色显示效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (useDart) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.L) {
        } else {
            //对Android5.1版本并且是ColorOS3.0的OPPO机型
            // 使用ColorOS提供ColorStatusbarTintUtil.SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT的Flag来
            // 设置状态栏图标黑色效果，由于该标记未公开，开发者需要在应用代码中定义。
            if (useDart) {
                vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            } else {
                vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            }
        }
        window.getDecorView().setSystemUiVisibility(vis);
    }


    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";    //OPPO

    /**
     * 判断手机是否是小米
     *
     * @return
     */
    private static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 判断手机是否是魅族
     *
     * @return
     */
    private static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 判断手机是否是oppo
     * 是否是ColorOS_3.0系统 Android 5.1
     *
     * @return 是否是colorOS_3.0
     */
    private static boolean isColorOS_3() {
        try {
            Class<?> cla = Class.forName("android.os.SystemProperties");
            Method mtd = cla.getMethod("get", String.class);
            String val = (String) mtd.invoke(null, KEY_VERSION_OPPO);
            val = val.replaceAll("[vV]", "");
            val = val.substring(0, 1);
            int version = Integer.parseInt(val);
            return version >= 3;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置状态栏文字色值为深色调
     *
     * @param useDart  是否使用深色调 true使用深色调（黑色） false使用白色
     * @param activity
     */
    private static void setStatusTextColor(boolean useDart, Activity activity) {
        if (isFlyme()) {
            processFlyMe(useDart, activity);
        } else if (isMIUI()) {
            processMIUI(useDart, activity);
        } else if (isColorOS_3()) {
            processOPPO(useDart, activity);
        } else {
            if (useDart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, StatusBarUtils.navigationHeight);
        }
    }

    /**
     * 设置底部导航栏的颜色
     *
     * @param color                 要显示的虚拟按键的颜色
     * @param useNavigationBarColor 是否需要虚拟按键为透明色  true自定义颜色 false透明
     */
    public static void setNavigationBarColor(String color, Activity activity, boolean useNavigationBarColor) {
        //检查是否存在底部导航栏
        if (checkDeviceHasNavigationBar(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (useNavigationBarColor) {
                    activity.getWindow().setNavigationBarColor(Color.parseColor(color));
                } else {
                    // 透明导航栏
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            }
        }
    }
}
