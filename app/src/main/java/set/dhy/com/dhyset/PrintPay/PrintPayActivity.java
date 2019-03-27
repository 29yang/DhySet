package set.dhy.com.dhyset.PrintPay;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dhy.utilscorelibrary.status_bar_util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.R;
import set.dhy.com.dhyset.Views.PrintPayDialog;

public class PrintPayActivity extends AppCompatActivity {

    @BindView(R.id.print_pay)
    TextView mPrintPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_pay);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBar(this,"#ffffff",false);
        mPrintPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PrintPayDialog(PrintPayActivity.this, R.style.dialog, new PrintPayDialog.OnSendSuc() {
                    @Override
                    public void onSendSix(Dialog dialog, String content, int starIndex) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
    }
}
