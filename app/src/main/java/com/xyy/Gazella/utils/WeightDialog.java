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

import com.jp.wheelview.WheelView;
import com.ysp.hybridtwatch.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/10/17.
 */

public class WeightDialog extends Dialog {
    private WheelView wheelView1,wheelView2;
    private Context context;
    private TextView confirm;
    private String weight,unit;
    private OnSelectedListener mSelectedListener;

    public WeightDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weight_dialog);
        setDialogAttributes((Activity) context,this,0.8f,0, Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        wheelView1= (WheelView) findViewById(R.id.wheelview1);
        wheelView2= (WheelView) findViewById(R.id.wheelview2);
        confirm= (TextView) findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedListener.onSelected(weight+unit);
                dismiss();
            }
        });

        ArrayList<String> list = new ArrayList<>();
        for (int i = 15;i<151;i++){
            list.add(i+"");
        }
        wheelView1.setData(list);
        wheelView1.setDefault((int)Math.floor(list.size()/2));
        weight=wheelView1.getItemText((int)Math.floor(list.size()/2));
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("kg");
        wheelView2.setData(list2);
        wheelView2.setDefault(0);
        unit=wheelView2.getItemText(0);
        wheelView1.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                weight=text;
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        wheelView2.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                unit=text;
            }

            @Override
            public void selecting(int id, String text) {

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

    public void setOnSelectedListener(OnSelectedListener listener){
        this.mSelectedListener=listener;
    }

    public interface OnSelectedListener{
        public void onSelected(String text);
    }
}
