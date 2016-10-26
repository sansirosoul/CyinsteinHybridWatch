package com.xyy.Gazella.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/25.
 */

public class UserSetting extends BaseActivity {

    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.ll_birth)
    LinearLayout llBirth;
    @BindView(R.id.tg_male)
    ToggleButton tgMale;
    @BindView(R.id.tg_female)
    ToggleButton tgFemale;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.save)
    ImageView save;
    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_setting_activity);
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

    @OnClick({R.id.back, R.id.head, R.id.ll_birth, R.id.ll_height, R.id.ll_weight, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransitionExit(UserSetting.this);
                break;
            case R.id.save:
                if (edName.getText() == null || edName.getText().toString().equals("")) {
                    Toast.makeText(context, R.string.input_name, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvBirth.getText().equals(getResources().getString(R.string.choose_birth))) {
                    Toast.makeText(context, R.string.choose_birth, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvHeight.getText().equals(getResources().getString(R.string.choose_height))) {
                    Toast.makeText(context, R.string.choose_height, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvWeight.getText().equals(getResources().getString(R.string.choose_weight))) {
                    Toast.makeText(context, R.string.choose_weight, Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.head:
                break;
            case R.id.ll_birth:
                break;
            case R.id.ll_height:
                break;
            case R.id.ll_weight:
                break;
        }
    }
}
