package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.ClockDialog1;
import com.xyy.Gazella.utils.ClockDialog2;
import com.xyy.Gazella.view.PickerViewHour;
import com.xyy.Gazella.view.PickerViewMinute;
import com.xyy.model.Clock;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/26.
 */

public class AddClockActivity extends BaseActivity {
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
    private ClockDialog1.OnClickListener onClickListener1;
    private ClockDialog2.OnClickListener onClickListener2;
    private int id;
    private BleUtils bleUtils;
    public Observable<RxBleConnection> connectionObservable;
    private String bytestr;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_clock);
        ButterKnife.bind(this);
        context = this;
        id=getIntent().getIntExtra("id",-1);
        initView();
        String address = PreferenceData.getAddressValue(context);
        if (address != null && !address.equals("")) {
            bleUtils = new BleUtils();
            connectionObservable = getRxObservable(this);
        }
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
                if(Clock.transformRate(text)!=5){
                    tvRepeatrate.setText(text);
                }else{
                    tvRepeatrate.setText(Clock.transformCustom(text));
                    bytestr=text;
                }
            }
        };
    }

    @OnClick({R.id.cancel, R.id.save, R.id.del_clock, R.id.rl_ringtime, R.id.rl_repeatrate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                overridePendingTransitionExit(AddClockActivity.this);
                break;
            case R.id.save:
                if(Clock.transformRate(tvRepeatrate.getText().toString())!=5){
                    Write(bleUtils.setWatchAlarm(1, id, Integer.parseInt(hour), Integer.parseInt(minute),
                            Clock.transformSnoozeTime(tvRingtime.getText().toString()),
                            Clock.transformRate(tvRepeatrate.getText().toString()), "00000000",1),connectionObservable);
                }else{
                    System.out.println(bytestr);
                    Write(bleUtils.setWatchAlarm(1, id, Integer.parseInt(hour), Integer.parseInt(minute),
                            Clock.transformSnoozeTime(tvRingtime.getText().toString()),
                            5, bytestr,1),connectionObservable);
                }
                Intent intent = new Intent();
                intent.putExtra("time",hour+":"+minute);
                intent.putExtra("snooze",tvRingtime.getText().toString());
                intent.putExtra("rate",tvRepeatrate.getText().toString());
                intent.putExtra("isOpen",1);
                setResult(1,intent);
                finish();
                overridePendingTransitionEnter(AddClockActivity.this);
                break;
            case R.id.del_clock:
                finish();
                overridePendingTransitionExit(AddClockActivity.this);
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
