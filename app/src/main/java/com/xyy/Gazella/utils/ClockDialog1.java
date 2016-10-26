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

import com.xyy.Gazella.activity.AddClockActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockDialog1 extends Dialog implements View.OnClickListener{
    private Button cancel,btn5,btn10,btn15,btn20,btn25,btn30;
    private Context context;

    public ClockDialog1(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clock_dialog1);
        setDialogAttributes((Activity) context, this, 0.9f, 0.7f, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        cancel= (Button) findViewById(R.id.cancel);
        btn5= (Button) findViewById(R.id.btn5);
        btn10= (Button) findViewById(R.id.btn10);
        btn15= (Button) findViewById(R.id.btn15);
        btn20= (Button) findViewById(R.id.btn20);
        btn25= (Button) findViewById(R.id.btn25);
        btn30= (Button) findViewById(R.id.btn30);

        cancel.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn25.setOnClickListener(this);
        btn30.setOnClickListener(this);
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
            case R.id.btn5:
                AddClockActivity.tvRingtime.setText("5分钟");
                dismiss();
                break;
            case R.id.btn10:
                AddClockActivity.tvRingtime.setText("10分钟");
                dismiss();
                break;
            case R.id.btn15:
                AddClockActivity.tvRingtime.setText("15分钟");
                dismiss();
                break;
            case R.id.btn20:
                AddClockActivity.tvRingtime.setText("20分钟");
                dismiss();
                break;
            case R.id.btn25:
                AddClockActivity.tvRingtime.setText("25分钟");
                dismiss();
                break;
            case R.id.btn30:
                AddClockActivity.tvRingtime.setText("30分钟");
                dismiss();
                break;
        }
    }
}
