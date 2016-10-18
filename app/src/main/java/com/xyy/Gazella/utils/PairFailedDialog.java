package com.xyy.Gazella.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/18.
 */

public class PairFailedDialog extends Dialog {
    private Context context;

    public PairFailedDialog(Context context) {
        super(context, R.style.dialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pair_failed);
    }
}
