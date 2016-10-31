package com.xyy.Gazella.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import com.ysp.smartwatch.R;

import static com.ysp.smartwatch.R.id.iv_loading;

/**
 * Created by Administrator on 2016/10/14.
 */

public class LoadingDialog extends Dialog {
    private Context context;
    private ProgressBar ivLoading;

    public LoadingDialog(Context context) {
        super(context,R.style.dialog);
        this.context=context;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.loading_rotate);
//        LinearInterpolator linearInterpolator = new LinearInterpolator();
//        animation.setInterpolator(linearInterpolator);
//        iv_loading.startAnimation(animation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);
        ivLoading= (ProgressBar) findViewById(iv_loading);
        setCanceledOnTouchOutside(false);
    }
}
