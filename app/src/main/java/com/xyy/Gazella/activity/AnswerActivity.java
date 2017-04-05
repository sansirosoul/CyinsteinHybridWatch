package com.xyy.Gazella.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
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
 * Created by Administrator on 2017/2/15.
 */

public class AnswerActivity extends BaseActivity {

    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.quartzwatch_q1)
    LinearLayout quartzwatchQ1;
    @BindView(R.id.hybridwatch_q1)
    LinearLayout hybridwatchQ1;
    @BindView(R.id.hybridwatch_q2)
    LinearLayout hybridwatchQ2;
    @BindView(R.id.hybridwatch_q3)
    LinearLayout hybridwatchQ3;
    @BindView(R.id.common_q1)
    LinearLayout commonQ1;
    @BindView(R.id.common_q2)
    LinearLayout commonQ2;
    @BindView(R.id.common_q4)
    LinearLayout commonQ4;
    @BindView(R.id.common_q6)
    LinearLayout commonQ6;
    @BindView(R.id.common_q3)
    LinearLayout commonQ3;
    @BindView(R.id.common_q5)
    LinearLayout commonQ5;
    @BindView(R.id.synchronization_time_next)
    ImageView synchronizationTimeNext;
    @BindView(R.id.rl_synchronization_time)
    RelativeLayout rlSynchronizationTime;
    @BindView(R.id.ll_synchronization_time)
    LinearLayout llSynchronizationTime;
    @BindView(R.id.notifition_next)
    ImageView notifitionNext;
    @BindView(R.id.rl_notifition)
    RelativeLayout rlNotifition;
    @BindView(R.id.ll_notifition)
    LinearLayout llNotifition;
    @BindView(R.id.health_next)
    ImageView healthNext;
    @BindView(R.id.rl_health)
    RelativeLayout rlHealth;
    @BindView(R.id.ll_health)
    LinearLayout llHealth;
    @BindView(R.id.setting_next)
    ImageView settingNext;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.answer_activity);
        ButterKnife.bind(this);
        text.setText(Html.fromHtml(descString(),getImageGetterInstance(),null));
        String tag = getIntent().getStringExtra("tag");
        if (tag.equals("quartzwatch_q1")) {
            TVTitle.setText(getResources().getString(R.string.quartzwatch) + "--1");
            quartzwatchQ1.setVisibility(View.VISIBLE);
        } else if (tag.equals("hybridwatch_q1")) {
            TVTitle.setText(getResources().getString(R.string.hybridwatch) + "--1");
            hybridwatchQ1.setVisibility(View.VISIBLE);
        } else if (tag.equals("hybridwatch_q2")) {
            TVTitle.setText(getResources().getString(R.string.hybridwatch) + "--2");
            hybridwatchQ2.setVisibility(View.VISIBLE);
        } else if (tag.equals("hybridwatch_q3")) {
            TVTitle.setText(getResources().getString(R.string.hybridwatch) + "--3");
            hybridwatchQ3.setVisibility(View.VISIBLE);
        } else if (tag.equals("common_q1")) {
            TVTitle.setText(getResources().getString(R.string.common_question) + "--1");
            commonQ1.setVisibility(View.VISIBLE);
        } else if (tag.equals("common_q2")) {
            TVTitle.setText(getResources().getString(R.string.common_question) + "--2");
            commonQ2.setVisibility(View.VISIBLE);
        } else if (tag.equals("common_q3")) {
            TVTitle.setText(getResources().getString(R.string.common_question) + "--3");
            commonQ3.setVisibility(View.VISIBLE);
        } else if (tag.equals("common_q4")) {
            TVTitle.setText(getResources().getString(R.string.common_question) + "--4");
            commonQ4.setVisibility(View.VISIBLE);
        } else if (tag.equals("common_q5")) {
            TVTitle.setText(getResources().getString(R.string.common_question) + "--5");
            commonQ5.setVisibility(View.VISIBLE);
        } else if (tag.equals("common_q6")) {
            TVTitle.setText(getResources().getString(R.string.common_question) + "--6");
            commonQ6.setVisibility(View.VISIBLE);
        }
    }

    private String descString() {
        return getResources().getString(R.string.hybridwatch_a222)+ "<img src='" + R.drawable.mark
                + "'/>"+getResources().getString(R.string.hybridwatch_a223)+"";
    }

    /**
     * ImageGetter用于text图文混排
     *
     * @return
     */
    public Html.ImageGetter getImageGetterInstance() {
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int fontH = (int) (getResources().getDimension(
                        R.dimen.textSizeMedium) * 1.5);
                int id = Integer.parseInt(source);
                Drawable d = getResources().getDrawable(id);
                int height = fontH;
                int width = (int) ((float) d.getIntrinsicWidth() / (float) d
                        .getIntrinsicHeight()) * fontH;
                if (width == 0) {
                    width = d.getIntrinsicWidth();
                }
                d.setBounds(0, 0, width, height);
                return d;
            }
        };
        return imgGetter;
    }


    @OnClick({R.id.btnExit, R.id.rl_synchronization_time, R.id.rl_notifition, R.id.rl_health, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(this);
                break;
            case R.id.rl_synchronization_time:
                if (llSynchronizationTime.getVisibility() == View.VISIBLE) {
                    llSynchronizationTime.setVisibility(View.GONE);
                    synchronizationTimeNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_up));
                } else {
                    llSynchronizationTime.setVisibility(View.VISIBLE);
                    synchronizationTimeNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_down));
                }
                break;
            case R.id.rl_notifition:
                if (llNotifition.getVisibility() == View.VISIBLE) {
                    llNotifition.setVisibility(View.GONE);
                    notifitionNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_up));
                } else {
                    llNotifition.setVisibility(View.VISIBLE);
                    notifitionNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_down));
                }
                break;
            case R.id.rl_health:
                if (llHealth.getVisibility() == View.VISIBLE) {
                    llHealth.setVisibility(View.GONE);
                    healthNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_up));
                } else {
                    llHealth.setVisibility(View.VISIBLE);
                    healthNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_down));
                }
                break;
            case R.id.rl_setting:
                if (llSetting.getVisibility() == View.VISIBLE) {
                    llSetting.setVisibility(View.GONE);
                    settingNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_up));
                } else {
                    llSetting.setVisibility(View.VISIBLE);
                    settingNext.setImageDrawable(getResources().getDrawable(R.drawable.hl_bar_down));
                }
                break;
        }
    }
}
