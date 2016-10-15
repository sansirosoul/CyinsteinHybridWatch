package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/12.
 */

public class PersonActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_birth)
    LinearLayout llBirth;
    @BindView(R.id.tg_male)
    ToggleButton tgMale;
    @BindView(R.id.tg_female)
    ToggleButton tgFemale;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.go)
    Button go;
    @BindView(R.id.head)
    ImageView head;

    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.person_activity);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {

        tgMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tgFemale.setChecked(false);
                }
            }
        });

        tgFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tgMale.setChecked(false);
                }
            }
        });
    }


    @OnClick({R.id.ll_birth, R.id.head, R.id.ll_height, R.id.ll_weight, R.id.back, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_birth:
                break;
            case R.id.head:
                break;
            case R.id.ll_height:
                break;
            case R.id.ll_weight:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.go:
                Intent intent = new Intent(context, PersonalizeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
