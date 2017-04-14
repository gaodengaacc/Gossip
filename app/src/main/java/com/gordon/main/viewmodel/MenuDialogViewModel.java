package com.gordon.main.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.gordon.main.R;
import com.gordon.main.view.dialog.MenuBottomDialog;
import com.gordon.main.viewmodel.base.DialogViewModel;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */

public class MenuDialogViewModel extends DialogViewModel {
    public final ObservableField<String> itemText1 = new ObservableField<>();
    public final ObservableField<String> itemText2 = new ObservableField<>();
    public final ObservableField<String> itemText3 = new ObservableField<>();
    private OnItemClickListener onItemClickListener;
    private int viewId;
    public MenuDialogViewModel(Context context){
        init();
        new MenuBottomDialog(context,this);
    }
    private void init() {
        itemText1.set("余额");
        itemText2.set("购买积分");
        itemText3.set("玩法详情");
    }

    public void OnClick(View view){
         switch (view.getId()){
             case R.id.menu_item1:
                 viewId = 1;
                 break;
             case R.id.menu_item2:
                 viewId = 2;
                 break;
             case R.id.menu_item3:
                 viewId = 3;
                 break;
             default:
                 break;
         }
         if(onItemClickListener!=null)
             onItemClickListener.onClick(view,viewId);
    }
    public interface OnItemClickListener {
        void onClick(View view,int id);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}
