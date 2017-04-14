package com.gordon.main.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.gordon.main.R;
import com.gordon.main.databinding.MenuDialogLayoutBinding;
import com.gordon.main.utils.DisplayUtil;
import com.gordon.main.viewmodel.MenuDialogViewModel;


/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */

public class MenuBottomDialog extends MvvmDialog<MenuDialogLayoutBinding, MenuDialogViewModel> {
    public MenuBottomDialog(@NonNull Context context, MenuDialogViewModel mDialogViewModel) {
        super(context, mDialogViewModel, R.layout.menu_dialog_layout, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);//设置dialog显示位置
        setContentView(R.layout.menu_dialog_layout);
        //宽度全屏
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = DisplayUtil.getScreenMetrics(getContext()).x;
        getWindow().setAttributes(layoutParams);
        //点击dialog外部消失
        setCanceledOnTouchOutside(true);
        super.onCreate(savedInstanceState);
    }
}
