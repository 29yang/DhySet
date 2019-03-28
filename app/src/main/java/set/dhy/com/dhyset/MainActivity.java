package set.dhy.com.dhyset;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.dhy.utilscorelibrary.status_bar_util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import set.dhy.com.dhyset.JumpActivityAnims.JumpAnimActivity;
import set.dhy.com.dhyset.PhotoView.PhotoViewActivity;
import set.dhy.com.dhyset.PrintPay.PrintPayActivity;
import set.dhy.com.dhyset.RecyclerViewItemDecoration.EasyShowTitleActivity;
import set.dhy.com.dhyset.RightSlideExit.RightSlideExitMainActivity;
import set.dhy.com.dhyset.WaterWave.WaveActivity;
import set.dhy.com.dhyset.rvadapter.Examples.AllExampleActivity;
import set.dhy.com.dhyset.shuang_biao_tou.TwoTitleActivity;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    @BindView(R.id.list)
    RecyclerView mList;
    private List<mainBean> mData;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initListener();
        StatusBarUtils.setStatusBar(this, "#ffffff", true);
    }

    //初始化监听器
    private void initListener() {
        mData = new ArrayList<>();
        mData.add(new mainBean("右划推出，标题导航设置", 1));
        mData.add(new mainBean("万能适配器加下拉上拉刷新", 2));
        mData.add(new mainBean("指纹支付测试", 3));
        mData.add(new mainBean("图片查看器", 4));
        mData.add(new mainBean("备份通讯录", 5));
        mData.add(new mainBean("简易的顶部悬停", 6));
        mData.add(new mainBean("5.0新转场动画（activity跳转）", 7));
        mData.add(new mainBean("Retrofit+Rxjava", 8));
        mData.add(new mainBean("双表头上下左右滑动，并且合并行", 9));
        mData.add(new mainBean("水波纹加载进度", 10));
        mAdapter = new MainAdapter(this, mData);
        mAdapter.setOnClick(new MainAdapter.OnClick() {
            @Override
            public void onClick(int position) {
                StartActivity(mData.get(position).type);
            }
        });
        MyLayoutManager manager = new MyLayoutManager();
        mList.setLayoutManager(manager);
        mList.setAdapter(mAdapter);
    }

    private void StartActivity(int type) {
        switch (type) {
            case 1: //右滑退出的功能实现
                startActivity(new Intent(this, RightSlideExitMainActivity.class));
                break;
            case 2: //rectcleview的万能适配器
                startActivity(new Intent(this, AllExampleActivity.class));
                break;
            case 3: //指纹支付的功能测试
                startActivity(new Intent(this, PrintPayActivity.class));
                break;
            case 4: //图片查看器
                ArrayList<String> mImgaesPath = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    if (i % 2 == 0) {
                        mImgaesPath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524308098524&di=23c2f5f5b17ffc735478bab5814a8a60&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F25%2F09%2F29658PICRyz_1024.jpg");
                    } else {
                        mImgaesPath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524308126753&di=61e3c5b5435ea34de679f800bdf4eddb&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120422013500980.JPG");
                    }
                }
                Intent intent = new Intent(this, PhotoViewActivity.class);
                intent.putExtra("index", 0);
                intent.putStringArrayListExtra("imagePath", mImgaesPath);
                startActivity(intent);
                break;
            case 5: //调用系统相机，保存固定目录
                startCanera();
                break;
            case 6: //简易的顶部悬停
                startActivity(new Intent(this, EasyShowTitleActivity.class));
                break;
            case 7: //5.0新转场动画(activity跳转)
                startActivity(new Intent(this, JumpAnimActivity.class));
                break;
            case 8: //Retrofit＋RxJava + OkHttp的封装使用

                break;
            case 9:
                startActivity(new Intent(this, TwoTitleActivity.class));
                break;
            case 10: //水波纹
                startActivity(new Intent(this, WaveActivity.class));
                break;
        }
    }


    //检查权限，和申请权限的功能
    private void startCanera() {
        //使用三方框架 andpermission
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            doBackup();
            return;
        }
        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                doBackup();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    /**
     * 第 4 步: 备份通讯录操作
     */
    private void doBackup() {
        // 本文主旨是讲解如果动态申请权限, 具体备份代码不再展示, 就假装备份一下
        Toast.makeText(this, "正在备份通讯录...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}