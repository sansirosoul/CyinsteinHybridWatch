package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.util.Log;

import com.xyy.Gazella.view.CustomAnalogClock;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeSynchronization extends BaseActivity {

    private static final String TAG = TimeSynchronization.class.getName();

    @BindView(R.id.customanalogclock)
    CustomAnalogClock customanalogclock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_synchronization);
        ButterKnife.bind(this);

        customanalogclock.setChangeTimeListener(new CustomAnalogClock.ChangeTimeListener() {
            @Override
            public void ChangeTimeListener(int TimeValue) {
                Log.i(TAG,"TimeValue========="+String.valueOf(TimeValue));
            }
        });
    }
}
