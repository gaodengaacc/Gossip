package com.gordon.main.bindingadapter.button;

import android.databinding.BindingAdapter;
import android.view.animation.Animation;

import com.gordon.main.view.MyButton;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */

public class ViewBindAdapter {
    @BindingAdapter("startAnimation")
    public static void startAnimation(MyButton button, Animation animation) {
            if (animation != null)
                button.startAnimation(animation);
            else
                button.clearAnimation();
        }
}
