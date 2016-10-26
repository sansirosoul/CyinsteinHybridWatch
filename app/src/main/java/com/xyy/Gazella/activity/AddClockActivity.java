package com.xyy.Gazella.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyy.Gazella.utils.ClockDialog1;
import com.xyy.Gazella.utils.ClockDialog2;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/26.
 */

public class AddClockActivity extends BaseActivity {
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.save)
    ImageView save;
    @BindView(R.id.del_clock)
    Button delClock;
    @BindView(R.id.rl_ringtime)
    RelativeLayout rlRingtime;
    @BindView(R.id.rl_repeatrate)
    RelativeLayout rlRepeatrate;
    public static TextView tvRingtime;
    public static TextView tvRepeatrate;
    private Context context;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_clock);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.cancel, R.id.save, R.id.del_clock, R.id.rl_ringtime, R.id.rl_repeatrate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                overridePendingTransitionExit(AddClockActivity.this);
                break;
            case R.id.save:
                break;
            case R.id.del_clock:
                break;
            case R.id.rl_ringtime:
                ClockDialog1 clockDialog1 = new ClockDialog1(context);
                clockDialog1.show();
                break;
            case R.id.rl_repeatrate:
                ClockDialog2 clockDialog2 = new ClockDialog2(context);
                clockDialog2.show();
                break;
        }
    }
}
