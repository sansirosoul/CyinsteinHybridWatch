package com.xyy.Gazella.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ysp.hybridtwatch.R;
import com.ysp.newband.PreferenceData;
import com.ysp.newband.WacthSeries;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/14.
 */

public class CheckUpdateDialog1 extends Dialog {
    @BindView(R.id.tv_context)
    TextView tvContext;
    private Context context;

    public CheckUpdateDialog1(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.check_update_dialog1);
        setDialogAttributes((Activity) context, this, 0, 0, Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(PreferenceData.getDeviceType(context).equals(WacthSeries.CT003)){
                    int ver = Integer.parseInt(PreferenceData.getDeviceFwvValue(context));
                    if(ver==11){
                        Toast.makeText(context,context.getResources().getString(R.string.the_last_version),Toast.LENGTH_SHORT).show();
                        dismiss();
                    }else{
                        dismiss();
                        CheckUpdateDialog2 dialog2 = new CheckUpdateDialog2(context);
                        dialog2.show();
                    }
                }else{
                    dismiss();
                    CheckUpdateDialog2 dialog2 = new CheckUpdateDialog2(context);
                    dialog2.show();
                }

            }
        }, 500);
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
}
