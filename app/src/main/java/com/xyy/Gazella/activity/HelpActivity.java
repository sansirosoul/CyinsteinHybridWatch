package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    @BindView(R.id.common_next)
    ImageView commonNext;
    @BindView(R.id.rl_common)
    RelativeLayout rlCommon;
    @BindView(R.id.ll_common_detail)
    LinearLayout llCommonDetail;

    @BindView(R.id.rl_hybridwatch)
    RelativeLayout rlHybridwatch;
    @BindView(R.id.ll_hybridwatch_detail)
    LinearLayout llHybridwatchDetail;
    @BindView(R.id.rl_quartzwatch)
    RelativeLayout rlQuartzwatch;
    @BindView(R.id.ll_quartzwatch_detail)
    LinearLayout llQuartzwatchDetail;
    @BindView(R.id.hybridwatch_next)
    ImageView hybridwatchNext;
    @BindView(R.id.quartzwatch_next)
    ImageView quartzwatchNext;
    @BindView(R.id.common_q1)
    TextView commonQ1;
    @BindView(R.id.common_q2)
    TextView commonQ2;
    @BindView(R.id.common_q3)
    TextView commonQ3;
    @BindView(R.id.common_q4)
    TextView commonQ4;
    @BindView(R.id.hybridwatch_q1)
    TextView hybridwatchQ1;
    @BindView(R.id.hybridwatch_q2)
    TextView hybridwatchQ2;
    @BindView(R.id.hybridwatch_q3)
    TextView hybridwatchQ3;
    @BindView(R.id.quartzwatch_q1)
    TextView quartzwatchQ1;
    @BindView(R.id.common_q5)
    TextView commonQ5;
    @BindView(R.id.common_q6)
    TextView commonQ6;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.help_activity);
        ButterKnife.bind(this);

        TVTitle.setText(getResources().getString(R.string.help));
    }

    @OnClick({R.id.btnExit, R.id.rl_common, R.id.rl_hybridwatch, R.id.rl_quartzwatch, R.id.common_q1, R.id.common_q2,
            R.id.common_q3, R.id.common_q4,R.id.common_q5,R.id.common_q6, R.id.hybridwatch_q1, R.id.hybridwatch_q2, R.id.hybridwatch_q3, R.id.quartzwatch_q1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(HelpActivity.this);
                break;
            case R.id.rl_common:
                if (llCommonDetail.getVisibility() == View.VISIBLE) {
                    llCommonDetail.setVisibility(View.GONE);
                    commonNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_up));
                } else {
                    llCommonDetail.setVisibility(View.VISIBLE);
                    commonNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_down));
                }
                break;
            case R.id.rl_hybridwatch:
                if (llHybridwatchDetail.getVisibility() == View.VISIBLE) {
                    llHybridwatchDetail.setVisibility(View.GONE);
                    hybridwatchNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_up));
                } else {
                    llHybridwatchDetail.setVisibility(View.VISIBLE);
                    hybridwatchNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_down));
                }
                break;
            case R.id.rl_quartzwatch:
                if (llQuartzwatchDetail.getVisibility() == View.VISIBLE) {
                    llQuartzwatchDetail.setVisibility(View.GONE);
                    quartzwatchNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_up));
                } else {
                    llQuartzwatchDetail.setVisibility(View.VISIBLE);
                    quartzwatchNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_down));
                }
                break;
            case R.id.common_q1:
                Intent cq1intent = new Intent(this, AnswerActivity.class);
                cq1intent.putExtra("tag", "common_q1");
                startActivity(cq1intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.common_q2:
                Intent cq2intent = new Intent(this, AnswerActivity.class);
                cq2intent.putExtra("tag", "common_q2");
                startActivity(cq2intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.common_q3:
                Intent cq3intent = new Intent(this, AnswerActivity.class);
                cq3intent.putExtra("tag", "common_q3");
                startActivity(cq3intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.common_q4:
                Intent cq4intent = new Intent(this, AnswerActivity.class);
                cq4intent.putExtra("tag", "common_q4");
                startActivity(cq4intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.common_q5:
                Intent cq5intent = new Intent(this, AnswerActivity.class);
                cq5intent.putExtra("tag", "common_q5");
                startActivity(cq5intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.common_q6:
                Intent cq6intent = new Intent(this, AnswerActivity.class);
                cq6intent.putExtra("tag", "common_q6");
                startActivity(cq6intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.hybridwatch_q1:
                Intent hq1intent = new Intent(this, AnswerActivity.class);
                hq1intent.putExtra("tag", "hybridwatch_q1");
                startActivity(hq1intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.hybridwatch_q2:
                Intent hq2intent = new Intent(this, AnswerActivity.class);
                hq2intent.putExtra("tag", "hybridwatch_q2");
                startActivity(hq2intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.hybridwatch_q3:
                Intent hq3intent = new Intent(this, AnswerActivity.class);
                hq3intent.putExtra("tag", "hybridwatch_q3");
                startActivity(hq3intent);
                overridePendingTransitionEnter(this);
                break;
            case R.id.quartzwatch_q1:
                Intent qq1intent = new Intent(this, AnswerActivity.class);
                qq1intent.putExtra("tag", "quartzwatch_q1");
                startActivity(qq1intent);
                overridePendingTransitionEnter(this);
                break;
        }
    }

}
