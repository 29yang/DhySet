package com.dhy.mvplibrary.MyMvp;


public class MvpPresenter<T extends MvpView> {
    private T mView;

    public MvpPresenter() {
    }

    /**
     * 绑定view ,在初始化中调用该方法
     *
     * @param mvpView
     */
    public void attachView(T mvpView) {
        this.mView = mvpView;
    }

    /**
     * 断开view 一般在ondestory方法中调用
     */
    public void detachView() {
        this.mView = null;
    }

    /**
     * 判断是否与view相连
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    public void getData(String params) {
        mView.showLoading();
        MvpModel.getData(params, new MvpCallBack() {

            @Override
            public void onSuccess(Object data) {
                if (isViewAttached())
                    mView.showData(String.valueOf(data));
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached())
                    mView.showFailureMessage(msg);
            }

            @Override
            public void onError() {
                if (isViewAttached())
                    mView.showErrorMessage();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    mView.hideLoading();
            }
        });
    }

}
