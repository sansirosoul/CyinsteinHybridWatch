package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/5.
 */

public class AnswerActivity extends BaseActivity {

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.q1)
    LinearLayout q1;
    @BindView(R.id.q2)
    LinearLayout q2;
    @BindView(R.id.q3)
    LinearLayout q3;
    @BindView(R.id.q4)
    LinearLayout q4;
    @BindView(R.id.q5)
    LinearLayout q5;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.answer_activity);
        ButterKnife.bind(this);

        int question = getIntent().getIntExtra("question", -1);
        if (question == 1) {
            TVTitle.setText(getResources().getString(R.string.q1));
            q1.setVisibility(View.VISIBLE);
        } else if (question == 2) {
            TVTitle.setText(getResources().getString(R.string.q2));
            q2.setVisibility(View.VISIBLE);
        } else if (question == 3) {
            TVTitle.setText(getResources().getString(R.string.q3));
            q3.setVisibility(View.VISIBLE);
        } else if (question == 4) {
            TVTitle.setText(getResources().getString(R.string.q4));
            q4.setVisibility(View.VISIBLE);
        } else {
            TVTitle.setText(getResources().getString(R.string.q5));
            q5.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.btnExit)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(this);
                break;
        }
    }
}
