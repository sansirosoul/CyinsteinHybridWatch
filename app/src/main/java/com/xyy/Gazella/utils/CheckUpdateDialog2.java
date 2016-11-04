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
import android.widget.TextView;

import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/24.
 */

public class CheckUpdateDialog2 extends Dialog implements View.OnClickListener{
    public TextView cancel;
    public TextView confirm;
    public TextView tvContext;

    private Context context;

    public    setBtnlListener  setBtnlListener;

    public CheckUpdateDialog2(Context context) {
        super(context,R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.check_update_dialog2);
        setDialogAttributes((Activity) context, this, 0.8f, 0, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        cancel= (TextView) findViewById(R.id.cancel);
        confirm= (TextView) findViewById(R.id.confirm);
        tvContext= (TextView) findViewById(R.id.tv_context);
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
                if(setBtnlListener!=null){
                    setBtnlListener.onCancelListener();
                }else
                dismiss();
                break;
            case R.id.confirm:
                if (setBtnlListener!=null){
                    setBtnlListener.onConfirm();
                }else {
                    dismiss();
                    CheckUpdateDialog3 dialog3 = new CheckUpdateDialog3(context);
                    dialog3.show();
                }
                break;
        }
    }

    public  void  setTvContext(String str){
        tvContext.setText(str);
    }
    public  void  setCancel(String str){
        cancel.setText(str);
    }
    public  void  setConfirm(String str){
        confirm.setText(str);
    }
    public void setBtnlListener(setBtnlListener listener) {
        this.setBtnlListener = listener;
    }

    public  interface  setBtnlListener{
        void  onCancelListener();
        void  onConfirm();
    }
}
