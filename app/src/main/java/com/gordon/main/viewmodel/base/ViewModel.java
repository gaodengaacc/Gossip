package com.gordon.main.viewmodel.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import net.funol.databinding.watchdog.Watchdog;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */
public abstract class ViewModel extends BaseObservable {

    @Deprecated
    private Context mContext;

    public ViewModel() {
    }

    @Deprecated
    public ViewModel(Context context) {
        this.mContext = context;
    }

    @Deprecated
    public Context getContext() {
        return mContext;
    }

    public <T extends ViewModel> T setPropertyChangeListener(Object beNotified) {
        Watchdog.newBuilder()
                .watch(this)
                .notify(beNotified)
                .build();
        return (T) this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {
        mContext = null;
    }

    public void onDestroyView() {

    }

}
