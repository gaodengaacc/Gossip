package com.gordon.main.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.gordon.main.view.dialog.AboutDialog;
import com.gordon.main.viewmodel.base.DialogViewModel;

/**
 * @author Gordon
 * @since 2017/4/13
 * do()
 */

public class AboutDialogViewModel extends DialogViewModel {
    public final ObservableField<String> playDes = new ObservableField<>();
    public final ObservableField<String> doDes = new ObservableField<>();

    public AboutDialogViewModel(Context context) {
        init();
        new AboutDialog(context,this);
    }

    private void init() {
        playDes.set("可测算姓名号码详情信息，包括吉凶以及名字的综合得分和号码的估算价值。" +
                "\n名字可以是姓名、公司名等。" +
                "\n号码可以是手机号、身份证号、QQ号、车牌号等。");
        doDes.set("输入名字和号码，点击八卦按钮即可测算。" +
                "\n点击手机菜单或右上角菜单键可查看余额，玩法和详情，以及充值等等。" +
                "\n（最好在网络哦良好下测算，网络状况不好的话可能会有点慢）." +
                "\n测算结果仅供参考。详情请咨询：" +
                "\nQQ：1838231208 " +
                "\n 新浪微博：名号大师");
    }

    public void onClick(View view) {
        dismiss();
    }
}
