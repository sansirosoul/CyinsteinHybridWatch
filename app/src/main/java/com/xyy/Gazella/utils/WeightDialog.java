package com.xyy.Gazella.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/17.
 */

public class WeightDialog extends Dialog {
    //private WheelView wheelView;
    private Context context;

    public WeightDialog(Context context) {
        super(context,R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weight_dialog);
        setDialogAttributes((Activity) context,this,0.8f,0, Gravity.CENTER);
//        wheelView= (WheelView) findViewById(R.id.wheelview);
//
//        List<String> list = new ArrayList<>();
//        for (int i = 30;i<101;i++){
//            list.add(i+"kg");
//        }
//        wheelView.setWheelAdapter(new ArrayWheelAdapter(context));
//        wheelView.setWheelData(list);
//        wheelView.setSkin(WheelView.Skin.Holo);
//        wheelView.setLoop(true);
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
