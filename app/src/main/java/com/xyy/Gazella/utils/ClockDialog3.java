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

import com.ysp.hybridtwatch.R;


/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockDialog3 extends Dialog implements View.OnClickListener {
    private TextView cancel, confirm;
    private Context context;
    private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7;
    private boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7 = false;
    private OnClickListener mOnClickListener;
    private String[] selectStr = new String[8];

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
        confirm = (TextView) findViewById(R.id.confirm);

        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        rl3 = (RelativeLayout) findViewById(R.id.rl3);
        rl4 = (RelativeLayout) findViewById(R.id.rl4);
        rl5 = (RelativeLayout) findViewById(R.id.rl5);
        rl6 = (RelativeLayout) findViewById(R.id.rl6);
        rl7 = (RelativeLayout) findViewById(R.id.rl7);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);
        iv7 = (ImageView) findViewById(R.id.iv7);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        for (int i = 0; i < selectStr.length; i++) {
            selectStr[i] = "0";
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
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : selectStr) {
                    stringBuilder.append(s);
                }
                System.out.println(stringBuilder.toString());
                if(!stringBuilder.toString().equals("00000000")){
                    mOnClickListener.onClick(stringBuilder.toString());
                    dismiss();
                }else{
//                    Toast.makeText(context,"请选择重复频率",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl1:
                if (flag1) {
                    iv1.setBackgroundResource(R.drawable.page36_nor);
                    flag1 = false;
                    selectStr[7] = "0";
                } else {
                    iv1.setBackgroundResource(R.drawable.page36_sel);
                    flag1 = true;
                    selectStr[7] = "1";
                }
                break;
            case R.id.rl2:
                if (flag2) {
                    iv2.setBackgroundResource(R.drawable.page36_nor);
                    flag2 = false;
                    selectStr[6] = "0";
                } else {
                    iv2.setBackgroundResource(R.drawable.page36_sel);
                    flag2 = true;
                    selectStr[6] = "1";
                }
                break;
            case R.id.rl3:
                if (flag3) {
                    iv3.setBackgroundResource(R.drawable.page36_nor);
                    flag3 = false;
                    selectStr[5] = "0";
                } else {
                    iv3.setBackgroundResource(R.drawable.page36_sel);
                    flag3 = true;
                    selectStr[5] = "1";
                }
                break;
            case R.id.rl4:
                if (flag4) {
                    iv4.setBackgroundResource(R.drawable.page36_nor);
                    flag4 = false;
                    selectStr[4] = "0";
                } else {
                    iv4.setBackgroundResource(R.drawable.page36_sel);
                    flag4 = true;
                    selectStr[4] = "1";
                }
                break;
            case R.id.rl5:
                if (flag5) {
                    iv5.setBackgroundResource(R.drawable.page36_nor);
                    flag5 = false;
                    selectStr[3] = "0";
                } else {
                    iv5.setBackgroundResource(R.drawable.page36_sel);
                    flag5 = true;
                    selectStr[3] = "1";
                }
                break;
            case R.id.rl6:
                if (flag6) {
                    iv6.setBackgroundResource(R.drawable.page36_nor);
                    flag6 = false;
                    selectStr[2] = "0";
                } else {
                    iv6.setBackgroundResource(R.drawable.page36_sel);
                    flag6 = true;
                    selectStr[2] = "1";
                }
                break;
            case R.id.rl7:
                if (flag7) {
                    iv7.setBackgroundResource(R.drawable.page36_nor);
                    flag7 = false;
                    selectStr[1] = "0";
                } else {
                    iv7.setBackgroundResource(R.drawable.page36_sel);
                    flag7 = true;
                    selectStr[1] = "1";
                }
                break;
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public interface OnClickListener {
        public void onClick(String text);
    }
}
