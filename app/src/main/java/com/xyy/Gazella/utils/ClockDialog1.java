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

import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockDialog1 extends Dialog implements View.OnClickListener{
    private Button cancel;
    private RelativeLayout rl,rl5,rl10,rl15,rl20,rl25,rl30;
    public ImageView iv,iv5,iv10,iv15,iv20,iv25,iv30;
    private Context context;
    private String ringtime;
    private OnClickListener mOnClickListener;

    public ClockDialog1(Context context,String ringtime) {
        super(context, R.style.dialog);
        this.context = context;
        this.ringtime=ringtime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clock_dialog1);
        setDialogAttributes((Activity) context, this, 0.9f, 0.7f, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        cancel= (Button) findViewById(R.id.cancel);
        rl= (RelativeLayout) findViewById(R.id.rl);
        rl5= (RelativeLayout) findViewById(R.id.rl5);
        rl10= (RelativeLayout) findViewById(R.id.rl10);
        rl15= (RelativeLayout) findViewById(R.id.rl15);
        rl20= (RelativeLayout) findViewById(R.id.rl20);
        rl25= (RelativeLayout) findViewById(R.id.rl25);
        rl30= (RelativeLayout) findViewById(R.id.rl30);

        iv= (ImageView) findViewById(R.id.iv);
        iv5= (ImageView) findViewById(R.id.iv5);
        iv10= (ImageView) findViewById(R.id.iv10);
        iv15= (ImageView) findViewById(R.id.iv15);
        iv20= (ImageView) findViewById(R.id.iv20);
        iv25= (ImageView) findViewById(R.id.iv25);
        iv30= (ImageView) findViewById(R.id.iv30);

        cancel.setOnClickListener(this);
        rl.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl10.setOnClickListener(this);
        rl15.setOnClickListener(this);
        rl20.setOnClickListener(this);
        rl25.setOnClickListener(this);
        rl30.setOnClickListener(this);

        if(ringtime.equals("无")){
            iv.setBackgroundResource(R.drawable.page36_sel);
        }
        if(ringtime.equals("5分钟")){
            iv5.setBackgroundResource(R.drawable.page36_sel);
        }
        if(ringtime.equals("10分钟")){
            iv10.setBackgroundResource(R.drawable.page36_sel);
        }
        if(ringtime.equals("15分钟")){
            iv15.setBackgroundResource(R.drawable.page36_sel);
        }
        if(ringtime.equals("20分钟")){
            iv20.setBackgroundResource(R.drawable.page36_sel);
        }
        if(ringtime.equals("25分钟")){
            iv25.setBackgroundResource(R.drawable.page36_sel);
        }
        if(ringtime.equals("30分钟")){
            iv30.setBackgroundResource(R.drawable.page36_sel);
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
            case R.id.rl:
                mOnClickListener.onClick("无");
                dismiss();
                break;
            case R.id.rl5:
                mOnClickListener.onClick("5分钟");
                dismiss();
                break;
            case R.id.rl10:
                mOnClickListener.onClick("10分钟");
                dismiss();
                break;
            case R.id.rl15:
                mOnClickListener.onClick("15分钟");
                dismiss();
                break;
            case R.id.rl20:
                mOnClickListener.onClick("20分钟");
                dismiss();
                break;
            case R.id.rl25:
                mOnClickListener.onClick("25分钟");
                dismiss();
                break;
            case R.id.rl30:
                mOnClickListener.onClick("30分钟");
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
