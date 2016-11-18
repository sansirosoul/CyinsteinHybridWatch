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
import android.widget.EditText;
import android.widget.TextView;

import com.ysp.newband.GazelleApplication;
import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/24.
 */

public class RenameWatchDialog extends Dialog implements View.OnClickListener{
    private TextView cancel;
    private TextView confirm;
    private EditText etName;
    private Context context;
    private BleUtils bleUtils;

    public RenameWatchDialog(Context context) {
        super(context,R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rename_watch_dialog);
        setDialogAttributes((Activity) context, this, 0.8f, 0, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        bleUtils=new BleUtils();

        cancel= (TextView) findViewById(R.id.cancel);
        confirm= (TextView) findViewById(R.id.confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        etName= (EditText) findViewById(R.id.et_name);
        if(GazelleApplication.deviceName!=null){
            etName.setText(GazelleApplication.deviceName);
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
                if(!etName.getText().toString().equals("")){
                    dismiss();

                }
                break;
        }
    }
}
