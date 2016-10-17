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

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.xyy.Gazella.activity.PersonActivity;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

public class HeightDialog extends Dialog {

    private Context context;

    private WheelView wheelView;

    public HeightDialog(Context context) {
        super(context,R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.height_dialog);
        setDialogAttributes((Activity) context,this,0.8f,0, Gravity.CENTER);
       wheelView= (WheelView) findViewById(R.id.wheelview);

        List<String> list = new ArrayList<>();
        for (int i = 150;i<201;i++){
            list.add(i+"cm");
        }
        wheelView.setWheelAdapter(new ArrayWheelAdapter(context));
        wheelView.setWheelData(list);
        wheelView.setSkin(WheelView.Skin.Holo);
        wheelView.setLoop(true);

        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int i, Object o) {
                PersonActivity.tvHeight.setText(o+"");
                PersonActivity.tvHeight.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
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
