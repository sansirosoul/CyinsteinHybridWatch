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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockDialog3 extends Dialog implements View.OnClickListener {
    private TextView cancel,confirm;
    private Context context;
    private RelativeLayout rl1,rl2,rl3,rl4,rl5,rl6,rl7;
    private ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7;
    private boolean flag1,flag2,flag3,flag4,flag5,flag6,flag7 = false;
    private OnClickListener mOnClickListener;

    public ClockDialog3(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clock_dialog3);
        setDialogAttributes((Activity) context, this, 0.9f, 0.8f, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView() {
        cancel = (TextView) findViewById(R.id.cancel);
        confirm=(TextView) findViewById(R.id.confirm);

        rl1= (RelativeLayout) findViewById(R.id.rl1);
        rl2= (RelativeLayout) findViewById(R.id.rl2);
        rl3= (RelativeLayout) findViewById(R.id.rl3);
        rl4= (RelativeLayout) findViewById(R.id.rl4);
        rl5= (RelativeLayout) findViewById(R.id.rl5);
        rl6= (RelativeLayout) findViewById(R.id.rl6);
        rl7= (RelativeLayout) findViewById(R.id.rl7);

        iv1= (ImageView) findViewById(R.id.iv1);
        iv2= (ImageView) findViewById(R.id.iv2);
        iv3= (ImageView) findViewById(R.id.iv3);
        iv4= (ImageView) findViewById(R.id.iv4);
        iv5= (ImageView) findViewById(R.id.iv5);
        iv6= (ImageView) findViewById(R.id.iv6);
        iv7= (ImageView) findViewById(R.id.iv7);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:

                dismiss();
                break;
            case R.id.rl1:
                if (flag1){
                    iv1.setBackgroundResource(R.drawable.page36_nor);
                    flag1=false;
                }else{
                    iv1.setBackgroundResource(R.drawable.page36_sel);
                    flag1=true;
                }
                break;
            case R.id.rl2:
                if (flag2){
                    iv2.setBackgroundResource(R.drawable.page36_nor);
                    flag2=false;
                }else{
                    iv2.setBackgroundResource(R.drawable.page36_sel);
                    flag2=true;
                }
                break;
            case R.id.rl3:
                if (flag3){
                    iv3.setBackgroundResource(R.drawable.page36_nor);
                    flag3=false;
                }else{
                    iv3.setBackgroundResource(R.drawable.page36_sel);
                    flag3=true;
                }
                break;
            case R.id.rl4:
                if (flag4){
                    iv4.setBackgroundResource(R.drawable.page36_nor);
                    flag4=false;
                }else{
                    iv4.setBackgroundResource(R.drawable.page36_sel);
                    flag4=true;
                }
                break;
            case R.id.rl5:
                if (flag5){
                    iv5.setBackgroundResource(R.drawable.page36_nor);
                    flag5=false;
                }else{
                    iv5.setBackgroundResource(R.drawable.page36_sel);
                    flag5=true;
                }
                break;
            case R.id.rl6:
                if (flag6){
                    iv6.setBackgroundResource(R.drawable.page36_nor);
                    flag6=false;
                }else{
                    iv6.setBackgroundResource(R.drawable.page36_sel);
                    flag6=true;
                }
                break;
            case R.id.rl7:
                if (flag7){
                    iv7.setBackgroundResource(R.drawable.page36_nor);
                    flag7=false;
                }else{
                    iv7.setBackgroundResource(R.drawable.page36_sel);
                    flag7=true;
                }
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
