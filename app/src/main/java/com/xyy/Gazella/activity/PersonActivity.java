package com.xyy.Gazella.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.wx.wheelview.widget.WheelViewDialog;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/12.
 */

public class PersonActivity extends BaseActivity implements View.OnClickListener{
    private ToggleButton tg_male,tg_female;
    private Button back,go;
    private LinearLayout ll_birth,ll_height,ll_weight;
    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.person_activity);
        context=this;
        initView();
    }

    private void initView() {
        tg_male= (ToggleButton) findViewById(R.id.tg_male);
        tg_female= (ToggleButton) findViewById(R.id.tg_female);

        tg_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tg_female.setChecked(false);
                }
            }
        });

        tg_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tg_male.setChecked(false);
                }
            }
        });

        back= (Button) findViewById(R.id.back);
        go= (Button) findViewById(R.id.go);
        back.setOnClickListener(this);
        go.setOnClickListener(this);

        ll_birth= (LinearLayout) findViewById(R.id.ll_birth);
        ll_height= (LinearLayout) findViewById(R.id.ll_height);
        ll_weight= (LinearLayout) findViewById(R.id.ll_weight);

        ll_birth.setOnClickListener(this);
        ll_height.setOnClickListener(this);
        ll_weight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.ll_birth:

                break;
            case R.id.ll_height:

                break;
            case R.id.ll_weight:

                break;
            default:
                break;
        }
    }
}
