package com.gordon.main.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.gordon.main.BR;
import com.gordon.main.viewmodel.base.ViewModel;
/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */
public abstract class MvvmActivity<VDB extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {

    private VDB mActivityViewDataBinding;
    protected VM mActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityViewDataBinding = DataBindingUtil.setContentView(this, getContentLayoutId());
        mActivityViewModel = createViewModel();
        registerViewModel(mActivityViewModel);
        mActivityViewDataBinding.setVariable(BR.mvvm, mActivityViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getBodyViewModel() != null)
            getBodyViewModel().onResume();
        if (mActivityViewModel != null)
            mActivityViewModel.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getBodyViewModel() != null)
            getBodyViewModel().onStop();
        if (mActivityViewModel != null)
            mActivityViewModel.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getBodyViewModel() != null)
            getBodyViewModel().onPause();
        if (mActivityViewModel != null)
            mActivityViewModel.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyViewDataBinding(mActivityViewDataBinding);
        if (getBodyViewModel() != null)
            getBodyViewModel().onDestroy();
        if (mActivityViewModel != null)
            mActivityViewModel.onDestroy();
    }

    protected void destroyViewDataBinding(ViewDataBinding viewDataBinding) {
        viewDataBinding.unbind();
        viewDataBinding.executePendingBindings();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getBodyViewModel() != null)
            getBodyViewModel().onActivityResult(requestCode, resultCode, data);
        if (mActivityViewModel != null)
            mActivityViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Deprecated
    protected <T extends ViewModel> T registerViewModel(T viewModel) {
        return viewModel;
    }

    @NonNull
    protected abstract VM createViewModel();

    @NonNull
    protected abstract ViewModel getBodyViewModel();

    @LayoutRes
    protected abstract int getContentLayoutId();

    protected VM getActivityViewModel() {
        return mActivityViewModel;
    }

    protected VDB getActivityViewDataBinding() {
        return mActivityViewDataBinding;
    }

}
