package com.gordon.main.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gordon.main.R;
import com.gordon.main.databinding.DialogAboutBinding;
import com.gordon.main.viewmodel.AboutDialogViewModel;

/**
 * @author Gordon
 * @since 2017/4/13
 * do()
 */

public class AboutDialog  extends MvvmDialog<DialogAboutBinding,AboutDialogViewModel>{
    public AboutDialog(@NonNull Context context, AboutDialogViewModel mDialogViewModel) {
        super(context, mDialogViewModel, R.layout.dialog_about, R.style.aboutDialog);
    }
}
