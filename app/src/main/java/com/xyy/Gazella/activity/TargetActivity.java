package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TargetActivity extends BaseActivity {

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.TVTitle)
    TextView TVTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        TVTitle.setText("运动/睡眠目标");
    }

    @OnClick({R.id.btnExit, R.id.btnOpt, R.id.TVTitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                TargetActivity.this.finish();
                overridePendingTransitionExit(TargetActivity.this);
                break;
            case R.id.btnOpt:
                break;


            case R.id.TVTitle:
                break;
        }
    }
}
