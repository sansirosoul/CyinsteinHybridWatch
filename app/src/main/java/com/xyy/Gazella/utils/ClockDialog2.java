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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ysp.hybridtwatch.R;


/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockDialog2 extends Dialog implements View.OnClickListener{
    private Context context;
    private Button cancel;
    private RelativeLayout rlOnce,rlEvery,rl15,rl67,rlDefine;
    private ImageView ivOnce,ivEvery,iv15,iv67;
    private OnClickListener mOnClickListener;
    private ClockDialog3.OnClickListener onClickListener;
    private String repeatrate;

    public ClockDialog2(Context context,String repeatrate) {
        super(context, R.style.dialog);
        this.context = context;
        this.repeatrate=repeatrate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clock_dialog2);
        setDialogAttributes((Activity) context, this, 0.9f, 0.7f, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        cancel= (Button) findViewById(R.id.cancel);
        rlOnce= (RelativeLayout) findViewById(R.id.rl_once);
        rlEvery= (RelativeLayout) findViewById(R.id.rl_every);
        rl15= (RelativeLayout) findViewById(R.id.rl15);
        rl67= (RelativeLayout) findViewById(R.id.rl67);
        rlDefine= (RelativeLayout) findViewById(R.id.rl_define);

        ivOnce= (ImageView) findViewById(R.id.iv_once);
        ivEvery=(ImageView) findViewById(R.id.iv_every);
        iv15=(ImageView) findViewById(R.id.iv15);
        iv67=(ImageView) findViewById(R.id.iv67);

        cancel.setOnClickListener(this);
        rlOnce.setOnClickListener(this);
        rlEvery.setOnClickListener(this);
        rl15.setOnClickListener(this);
        rl67.setOnClickListener(this);
        rlDefine.setOnClickListener(this);

        onClickListener = new ClockDialog3.OnClickListener() {
            @Override
            public void onClick(String text) {
                mOnClickListener.onClick(text);
            }
        };

        if(repeatrate.equals(context.getResources().getString(R.string.rate1))){
            ivOnce.setBackgroundResource(R.drawable.page36_sel);
        }
        if(repeatrate.equals(context.getResources().getString(R.string.rate2))){
            ivEvery.setBackgroundResource(R.drawable.page36_sel);
        }
        if(repeatrate.equals(context.getResources().getString(R.string.rate3))){
            iv15.setBackgroundResource(R.drawable.page36_sel);
        }
        if(repeatrate.equals(context.getResources().getString(R.string.rate4))){
            iv67.setBackgroundResource(R.drawable.page36_sel);
        }
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
            case R.id.rl_once:
                mOnClickListener.onClick(context.getResources().getString(R.string.rate1));
                dismiss();
                break;
            case R.id.rl_every:
                mOnClickListener.onClick(context.getResources().getString(R.string.rate2));
                dismiss();
                break;
            case R.id.rl15:
                mOnClickListener.onClick(context.getResources().getString(R.string.rate3));
                dismiss();
                break;
            case R.id.rl67:
                mOnClickListener.onClick(context.getResources().getString(R.string.rate4));
                dismiss();
                break;
            case R.id.rl_define:
                ClockDialog3 clockDialog3 = new ClockDialog3(context);
                clockDialog3.setOnClickListener(onClickListener);
                clockDialog3.show();
                dismiss();
                break;
        }
    }

    public void setOnClickListener(OnClickListener listener){
        this.mOnClickListener=listener;
    }

    public interface OnClickListener{
        public void onClick(String text);
    }
}
