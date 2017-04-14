package com.gordon.main.bindingadapter.textview;

import android.databinding.BindingAdapter;
import android.view.animation.Animation;

import com.gordon.main.view.MyTextView;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */

public class ViewBindAdapter {
    @BindingAdapter("startAnimation")
    public static void startAnimation(MyTextView textView, Animation animation) {
            if (animation != null)
                textView.setAnimation(animation);
            else
                textView.clearAnimation();
        }
}
