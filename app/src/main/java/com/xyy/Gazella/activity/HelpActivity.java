package com.xyy.Gazella.activity;

import android.content.Intent;
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
 * Created by Administrator on 2016/10/26.
 */

public class HelpActivity extends BaseActivity {

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
        setContentView(R.layout.help_activity);
        ButterKnife.bind(this);

        TVTitle.setText(getResources().getString(R.string.help));
    }

    @OnClick({R.id.btnExit,R.id.q1, R.id.q2, R.id.q3, R.id.q4, R.id.q5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(HelpActivity.this);
                break;
            case R.id.q1:
                Intent intent1 = new Intent(this,AnswerActivity.class);
                intent1.putExtra("question",1);
                startActivity(intent1);
                overridePendingTransitionEnter(this);
                break;
            case R.id.q2:
                Intent intent2 = new Intent(this,AnswerActivity.class);
                intent2.putExtra("question",2);
                startActivity(intent2);
                overridePendingTransitionEnter(this);
                break;
            case R.id.q3:
                Intent intent3 = new Intent(this,AnswerActivity.class);
                intent3.putExtra("question",3);
                startActivity(intent3);
                overridePendingTransitionEnter(this);
                break;
            case R.id.q4:
                Intent intent4 = new Intent(this,AnswerActivity.class);
                intent4.putExtra("question",4);
                startActivity(intent4);
                overridePendingTransitionEnter(this);
                break;
            case R.id.q5:
                Intent intent5 = new Intent(this,AnswerActivity.class);
                intent5.putExtra("question",5);
                startActivity(intent5);
                overridePendingTransitionEnter(this);
                break;
        }
    }
}
