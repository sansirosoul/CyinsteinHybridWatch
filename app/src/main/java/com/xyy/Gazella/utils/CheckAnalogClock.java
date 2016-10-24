package com.xyy.Gazella.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/17.
 */

public class CheckAnalogClock extends Dialog{


    private Context context;

    private onItemClickListener mListener = null;
    private ImageView ivSmall1;
    private ImageView ivSmall2;
    private ImageView ivSmall3;
    private Button butClose;


    public CheckAnalogClock(Context context) {
        this(context, R.style.activity_dlalog_style);
    }

    public CheckAnalogClock(Context context, int themeResId) {
        super(context, R.style.activity_dlalog_style);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_check_analogclock);
        ivSmall1= (ImageView) findViewById(R.id.iv_small1);
        ivSmall2= (ImageView) findViewById(R.id.iv_small2);
        ivSmall3= (ImageView) findViewById(R.id.iv_small3);
        butClose= (Button) findViewById(R.id.but_close);
        ivSmall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSmall1Click();
            }
        });
        ivSmall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSmall2Click();
            }
        });
        ivSmall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSmall3Click();
            }
        });
        butClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCloseClick();
            }
        });
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public interface onItemClickListener {
        void onSmall1Click();
        void onSmall2Click();
        void onSmall3Click();
        void onCloseClick();
    }

}
