package com.gordon.main.activity;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.gordon.main.R;
import com.gordon.main.databinding.ActivityMainBinding;
import com.gordon.main.viewmodel.AboutDialogViewModel;
import com.gordon.main.viewmodel.MainViewModel;
import com.gordon.main.viewmodel.MenuDialogViewModel;
import com.gordon.main.viewmodel.base.ViewModel;
import com.gordon.main.viewmodel.watchdog.IMainViewModelCallbacks;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */
public class MainActivity extends MvvmActivity<ActivityMainBinding, MainViewModel> implements IMainViewModelCallbacks {
    private MenuDialogViewModel bottomDialog;
    private AboutDialogViewModel aboutDialog;
    private long exitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XiaomiUpdateAgent.setUpdateAutoPopup(false);
        XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.STATUS_UPDATE:
                        // 有更新， UpdateResponse为本次更新的详细信息
                        // 其中包含更新信息，下载地址，MD5校验信息等，可自行处理下载安装
                        // 如果希望 SDK继续接管下载安装事宜，可调用
                        //  XiaomiUpdateAgent.arrange()
                        Toast.makeText(getBaseContext(),"有更新"+updateInfo,Toast.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.STATUS_NO_UPDATE:
                        // 无更新， UpdateResponse为null
                        Toast.makeText(getBaseContext(),"无更新"+updateInfo,Toast.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.STATUS_NO_WIFI:
                        // 设置了只在WiFi下更新，且WiFi不可用时， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_NO_NET:
                        // 没有网络， UpdateResponse为null
                        Toast.makeText(getBaseContext(),"无网络"+updateInfo,Toast.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.STATUS_FAILED:
                        // 检查更新与服务器通讯失败，可稍后再试， UpdateResponse为null
                        Toast.makeText(getBaseContext(),"失败"+updateInfo,Toast.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.STATUS_LOCAL_APP_FAILED:
                        // 检查更新获取本地安装应用信息失败， UpdateResponse为null
                        break;
                    default:
                        break;
                }
            }
        });
        XiaomiUpdateAgent.update(this);
    }

    @NonNull
    @Override
    protected MainViewModel createViewModel() {
        return new MainViewModel().setPropertyChangeListener(this);
    }

    @NonNull
    @Override
    protected ViewModel getBodyViewModel() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showDialog(BaseObservable observableField, int fieldId) {
        if (aboutDialog == null)
            bottomDialog = new MenuDialogViewModel(this);
        bottomDialog.setOnItemClickListener(clickListener);
        bottomDialog.show();
    }

    @Override
    public void showText(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(getBaseContext(), observableField.get(), Toast.LENGTH_LONG).show();
    }

    MenuDialogViewModel.OnItemClickListener clickListener = new MenuDialogViewModel.OnItemClickListener() {
        @Override
        public void onClick(View view, int id) {
            switch (id) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    if (aboutDialog == null)
                        aboutDialog = new AboutDialogViewModel(MainActivity.this);
                    aboutDialog.show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
