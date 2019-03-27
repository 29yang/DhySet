package com.dhy.mvplibrary.MyMvp;

import android.os.Handler;

/**
 * 具体使用具体进行修改
 */
public class MvpModel {
    public static void getData(final String param, final MvpCallBack callback) {
// 利用postDelayed方法模拟网络请求数据的耗时操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (param) {
                    case "normal":
                        callback.onSuccess("根据参数" + param + "的请求网络数据成功");
                        break;

                    case "failure":
                        callback.onFailure("请求失败：参数有误");
                        break;

                    case "error":
                        callback.onError();
                        break;
                }
                callback.onComplete();
            }

        }, 2000);
    }
}
