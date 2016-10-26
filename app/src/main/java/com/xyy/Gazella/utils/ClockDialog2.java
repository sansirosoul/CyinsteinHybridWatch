package com.xyy.Gazella.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockDialog2 extends Dialog implements View.OnClickListener{
    private Context context;
    private Button cancel,btnOnce,btnEvery,btn15,btn67,btnDefine;

    public ClockDialog2(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clock_dialog2);
        setDialogAttributes((Activity) context, this, 0.9f, 0.7f, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        cancel= (Button) findViewById(R.id.cancel);
        btnOnce= (Button) findViewById(R.id.btn_once);
        btnEvery= (Button) findViewById(R.id.btn_every);
        btn15= (Button) findViewById(R.id.btn15);
        btn67= (Button) findViewById(R.id.btn67);
        btnDefine= (Button) findViewById(R.id.btn_define);

        cancel.setOnClickListener(this);
        btnOnce.setOnClickListener(this);
        btnEvery.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn67.setOnClickListener(this);
        btnDefine.setOnClickListener(this);
    }

    public void setDialogAttributes(Activity context, final Dialog dialog,
                                    float widthP, float heightP, int gravity) {
        Display d = context.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();

        Point mPoint = new Point();
        d.getSize(mPoint);
        if (heightP != 0)
            p.height = (int) (mPoint.y * heightP);
        if (widthP != 0)
            p.width = (int) (mPoint.x * widthP);
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(gravity);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.btn_define:
                ClockDialog3 clockDialog3 = new ClockDialog3(context);
                clockDialog3.show();
                dismiss();
                break;
        }
    }
}
