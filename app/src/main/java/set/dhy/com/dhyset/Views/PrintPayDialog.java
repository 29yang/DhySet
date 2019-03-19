package set.dhy.com.dhyset.Views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.PrintPay.PrintPayUtils.FingerprintUtil;
import set.dhy.com.dhyset.R;

/**
 * Created by test on 2018/1/12.
 */

public class PrintPayDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.cancle)
    TextView mCancle;
    @BindView(R.id.pw_pay)
    TextView mPwPay;
    @BindView(R.id.pay_suc)
    TextView mPaySuc;
    private Context context;
    private OnSendSuc onSendSuc;


    public PrintPayDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public PrintPayDialog(@NonNull Context context, int themeResId, OnSendSuc onSendSuc) {
        super(context, themeResId);
        this.context = context;
        this.onSendSuc = onSendSuc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_print_pay);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        mCancle.setOnClickListener(this);
        mPwPay.setOnClickListener(this);
        mPaySuc.setText("检查设备是否安全保护");
        mHandler.sendEmptyMessageAtTime(1, 350);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initPrintPay();
        }
    };

    /**
     * 调用指纹支付验证
     */
    private void initPrintPay() {
        FingerprintUtil.callFingerPrint(new FingerprintUtil.OnCallBackListenr() {
            @Override
            public void onSupportFailed() {
                if (onSendSuc != null) {
                    onSendSuc.onSendSix(PrintPayDialog.this, "支付结果", 2);
                }
                showToast("当前设备不支持指纹");
            }

            @Override
            public void onInsecurity() {
                if (onSendSuc != null) {
                    onSendSuc.onSendSix(PrintPayDialog.this, "支付结果", 2);
                }
                showToast("当前设备未处于安全保护中");
            }

            @Override
            public void onEnrollFailed() {
                if (onSendSuc != null) {
                    onSendSuc.onSendSix(PrintPayDialog.this, "支付结果", 2);
                }
                showToast("请到设置中设置指纹");
            }

            @Override
            public void onAuthenticationStart() {
                mPaySuc.setText("指纹验证中...");
            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                showToast(errString.toString());
                mPaySuc.setText("指纹验证失败...");
                if (onSendSuc != null) {
                    onSendSuc.onSendSix(PrintPayDialog.this, "支付结果", 2);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                mPaySuc.setText("指纹验证失败，请重试...");
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                System.out.println("===Help==" + 3);
                showToast(helpString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                showToast("指纹验证成功");
                if (onSendSuc != null) {
                    onSendSuc.onSendSix(PrintPayDialog.this, "支付结果", 2);
                }
            }
        }, context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancle:
                if (onSendSuc != null) {
                    onSendSuc.onSendSix(this, "支付取消", 0);
                }
                break;
            case R.id.pw_pay:
                if (onSendSuc != null) {
                    onSendSuc.onSendSix(this, "密码支付", 1);
                }
                break;
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        //关闭指纹支付
        FingerprintUtil.cancel();
    }

    /**
     * 设置宽度全屏，要设置在show的后面
     * 从底部进入，从底部退出
     */
    @Override
    public void show() {
        super.show();
        getWindow().setWindowAnimations(R.style.showdialog);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public interface OnSendSuc {
        /**
         * dialog弹出框回调接口
         *
         * @param dialog
         * @param content   回调内容
         * @param starIndex 0代表取消  1代表密码支付  2代表验证成功
         */
        void onSendSix(Dialog dialog, String content, int starIndex);
    }

    public void showToast(String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
    }

}
