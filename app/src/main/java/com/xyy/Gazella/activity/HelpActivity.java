package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/26.
 */

public class HelpActivity extends BaseActivity {

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.TVTitle)
    TextView TVTitle;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.help_activity);
        ButterKnife.bind(this);

        TVTitle.setText("帮助");
    }

    @OnClick(R.id.btnExit)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnExit:
                 finish();
                overridePendingTransitionExit(HelpActivity.this);
                break;
        }
    }
}
