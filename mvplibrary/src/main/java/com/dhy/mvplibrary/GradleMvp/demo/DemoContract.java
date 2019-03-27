package com.dhy.mvplibrary.GradleMvp.demo;

import android.content.Context;

import com.dhy.mvplibrary.GradleMvp.mvp.BasePresenter;
import com.dhy.mvplibrary.GradleMvp.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class DemoContract {
    interface View extends BaseView {
        void success();
    }

    interface  Presenter extends BasePresenter<View> {
        void login(String name,String pw);
    }
}
