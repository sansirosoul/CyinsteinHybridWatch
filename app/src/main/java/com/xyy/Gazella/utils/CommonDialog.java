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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ysp.hybridtwatch.R;


public class CommonDialog extends Dialog {


    private Button butOk;
    private Context context;
    private ProgressBar iv_loading;
    private TextView tvContext;
    private  onButOKListener onButOKListener;

    public CommonDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.commdialog);
        iv_loading = (ProgressBar) findViewById(R.id.iv_loading);
        tvContext = (TextView) findViewById(R.id.tv_context);
        butOk = (Button) findViewById(R.id.but_ok);
        butOk.setOnClickListener(new onButListener());
        setDialogAttributes((Activity) context, this, 0.5f, 0, Gravity.CENTER);
        setCanceledOnTouchOutside(false);
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

    public void setTvContext(String str) {
        tvContext.setText(str);
    }

    public void setButOk(int visibility) {
        butOk.setVisibility(visibility);
    }

    public interface onButOKListener {
        void onButOKListener();
    }
    public void onButOKListener(onButOKListener onButOKListener) {
        this.onButOKListener = onButOKListener;
    }


    class  onButListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case  R.id.but_ok:
                    if(onButOKListener!=null)
                        onButOKListener.onButOKListener();
                    break;
            }
        }
    }
}
