package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyy.Gazella.utils.ClockDialog1;
import com.xyy.Gazella.utils.ClockDialog2;
import com.xyy.Gazella.view.PickerViewHour;
import com.xyy.Gazella.view.PickerViewMinute;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/26.
 */

public class EditClockActivity extends BaseActivity {
    @BindView(R.id.cancel)
    RelativeLayout cancel;
    @BindView(R.id.save)
    RelativeLayout save;
    @BindView(R.id.del_clock)
    Button delClock;
    @BindView(R.id.rl_ringtime)
    RelativeLayout rlRingtime;
    @BindView(R.id.rl_repeatrate)
    RelativeLayout rlRepeatrate;
    public static TextView tvRingtime;
    public static TextView tvRepeatrate;
    @BindView(R.id.pv_hour)
    PickerViewHour pvHour;
    @BindView(R.id.pv_minute)
    PickerViewMinute pvMinute;
    private Context context;
    private String hour="12";
    private String minute="30";
    private int isOpen = 0;
    private ClockDialog1.OnClickListener onClickListener1;
    private ClockDialog2.OnClickListener onClickListener2;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_clock);
        ButterKnife.bind(this);
        context = this;
        initView();


    }

    private void initView() {
        tvRingtime= (TextView) findViewById(R.id.tv_ringtime);
        tvRepeatrate= (TextView) findViewById(R.id.tv_repeatrate);


        List<String> hours = new ArrayList<>();
        List<String> minutes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + i);
            } else {
                hours.add("" + i);

            }
        }
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minutes.add("0" + i);
            } else {
                minutes.add("" + i);
            }
        }

        pvHour.setData(hours);
        pvMinute.setData(minutes);

        tvRingtime.setText(getIntent().getStringExtra("snooze"));
        tvRepeatrate.setText(getIntent().getStringExtra("rate"));
        isOpen=getIntent().getIntExtra("isOpen",-1);
        String[] ss = getIntent().getStringExtra("time").split(":");
        hour=ss[0];
        minute=ss[1];
        pvHour.setSelected(hour);
        pvMinute.setSelected(minute);

        pvHour.setOnSelectListener(new PickerViewHour.onSelectListener() {
            @Override
            public void onSelect(String text) {
                 hour=text;
            }
        });
        pvHour.setOnScrollListener(new PickerViewHour.onScrollListener() {
            @Override
            public void onScroll() {
                pvMinute.setSelect(false);
            }
        });

        pvMinute.setOnSelectListener(new PickerViewMinute.onSelectListener() {
            @Override
            public void onSelect(String text) {
                  minute=text;
            }
        });
        pvMinute.setOnScrollListener(new PickerViewMinute.onScrollListener() {
            @Override
            public void onScroll() {
                pvHour.setSelect(false);
            }
        });

        onClickListener1 = new ClockDialog1.OnClickListener() {
            @Override
            public void onClick(String text) {
                tvRingtime.setText(text);
            }
        };

        onClickListener2 = new ClockDialog2.OnClickListener() {
            @Override
            public void onClick(String text) {
                tvRepeatrate.setText(text);
            }
        };
    }



    @OnClick({R.id.cancel, R.id.save, R.id.del_clock, R.id.rl_ringtime, R.id.rl_repeatrate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                overridePendingTransitionExit(EditClockActivity.this);
                break;
            case R.id.save:
                Intent intent = new Intent();
                intent.putExtra("time",hour+":"+minute);
                intent.putExtra("snooze",tvRingtime.getText().toString());
                intent.putExtra("rate",tvRepeatrate.getText().toString());
                intent.putExtra("isOpen",isOpen);
                intent.putExtra("result","edit");
                setResult(1,intent);
                finish();
                overridePendingTransitionEnter(EditClockActivity.this);
                break;
            case R.id.del_clock:
                Intent delIntent = new Intent();
                delIntent.putExtra("result","del");
                setResult(1,delIntent);
                finish();
                overridePendingTransitionEnter(EditClockActivity.this);
                break;
            case R.id.rl_ringtime:
                ClockDialog1 clockDialog1 = new ClockDialog1(context,tvRingtime.getText().toString());
                clockDialog1.setOnClickListener(onClickListener1);
                clockDialog1.show();
                break;
            case R.id.rl_repeatrate:
                ClockDialog2 clockDialog2 = new ClockDialog2(context,tvRepeatrate.getText().toString());
                clockDialog2.setOnClickListener(onClickListener2);
                clockDialog2.show();
                break;
        }
    }
}
