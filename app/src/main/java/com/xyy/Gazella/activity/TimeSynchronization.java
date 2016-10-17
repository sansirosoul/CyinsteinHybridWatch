package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.xyy.Gazella.utils.CheckAnalogClock;
import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeSynchronization extends BaseActivity {

    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    @BindView(R.id.but_reduce)
    ImageButton butReduce;
    @BindView(R.id.but_add)
    ImageButton butAdd;
    @BindView(R.id.but_hour)
    Button butHour;
    @BindView(R.id.but_muinutes)
    Button butMuinutes;
    @BindView(R.id.but_second)
    Button butSecond;
    @BindView(R.id.but_reset)
    Button butReset;
    @BindView(R.id.but_synchronization)
    Button butSynchronization;
    @BindView(R.id.activity_time_synchronization)
    LinearLayout activityTimeSynchronization;
    private int getMinutesValue;
    private int getHourValue;
    private int setMinutesValue;
    private int setHourValue;
    private boolean isChangeTime = false;
    private CheckAnalogClock checkAnalogClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_synchronization);
        ButterKnife.bind(this);

        InitView();
    }

    private void InitView() {
        checkAnalogClock = new CheckAnalogClock(TimeSynchronization.this);


        butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
        butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
        butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));

        checkAnalogClock.setOnItemClickListener(new CheckAnalogClock.onItemClickListener() {

            @Override
            public void onSmall1Click() {

            }

            @Override
            public void onSmall2Click() {

            }

            @Override
            public void onSmall3Click() {

            }

            @Override
            public void onCloseClick() {
                checkAnalogClock.dismiss();
                butReset.setVisibility(View.VISIBLE);
                butSynchronization.setVisibility(View.VISIBLE);
            }
        });

    }

    @OnClick({R.id.but_reduce, R.id.but_add, R.id.but_hour, R.id.but_muinutes, R.id.but_second, R.id.but_reset, R.id.but_synchronization})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_reduce:
                getHourValue = analogclock.getHourTimeValue();
                getMinutesValue = analogclock.getMinutesTimeValue();
                if (!isChangeTime) {
                    setHourValue = getHourValue;
                    setMinutesValue = getMinutesValue;
                }
                if (analogclock.ChangeTimeType == 1) {
                    analogclock.setTimeValue(1, setHourValue);
                    setHourValue--;
                } else {
                    analogclock.setTimeValue(2, setMinutesValue);
                    setMinutesValue--;
                }
                isChangeTime = true;
                break;

            case R.id.but_add:
                getHourValue = analogclock.getHourTimeValue();
                getMinutesValue = analogclock.getMinutesTimeValue();
                if (!isChangeTime) {
                    setHourValue = getHourValue;
                    setMinutesValue = getMinutesValue;
                }
                if (analogclock.ChangeTimeType == 1) {
                    analogclock.setTimeValue(1, setHourValue);
                    setHourValue++;
                } else {
                    analogclock.setTimeValue(2, setMinutesValue);
                    setMinutesValue++;
                }
                isChangeTime = true;
                break;

            case R.id.but_hour:
                analogclock.setChangeTimeType(1);
                butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
                butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                break;
            case R.id.but_muinutes:
                analogclock.setChangeTimeType(2);
                butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));
                butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                break;
            case R.id.but_second:

                checkAnalogClock.show();
                butReset.setVisibility(View.GONE);
                butSynchronization.setVisibility(View.GONE);
                butHour.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butMuinutes.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_press));
                butSecond.setBackground(getResources().getDrawable(R.drawable.time_circlebtn_normal));

                break;
            case R.id.but_reset:
                break;
            case R.id.but_synchronization:
                break;
        }
    }
}
