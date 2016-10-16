package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeSynchronization extends BaseActivity {

    private static final String TAG = TimeSynchronization.class.getName();

    @BindView(R.id.analogclock)
    AnalogClock analogClock;
    @BindView(R.id.buyy)
    Button buyy;
    @BindView(R.id.b)
    Button b;
    @BindView(R.id.add)
    Button add;

   private int setHoutValue;
   private int setMinutesValue;
   private  int getHoutValue;
    private  int getMinutesValue;
    private boolean  isChangeTime=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_synchronization);
        ButterKnife.bind(this);
        analogClock.setChangeTimeType(2);

        analogClock.setChangeTimeListener(new AnalogClock.ChangeTimeListener() {
            @Override
            public void ChangeTimeListener(int TimeValue) {
                int Hout = analogClock.getHourTimeValue();
                int Minutes = analogClock.getMinutesTimeValue();
                Log.i(TAG, "Hout=========" + String.valueOf(Hout));
                Log.i(TAG, "Minutes=========" + String.valueOf(Minutes));
            }
        });
    }

    @OnClick({R.id.buyy, R.id.b, R.id.add})
    public void onClick(View view) {
        getHoutValue = analogClock.getHourTimeValue();
        getMinutesValue = analogClock.getMinutesTimeValue();
        if(!isChangeTime){
            setHoutValue = getHoutValue;
            setMinutesValue = getMinutesValue;
        }
        switch (view.getId()) {
            case R.id.buyy:
                Log.i(TAG, "Hout>>>>>>>>>>>>>>>" + String.valueOf(getHoutValue));
                Log.i(TAG, "Minutes>>>>>>>>>>>>" + String.valueOf(getMinutesValue));
                break;

            case R.id.add:

                analogClock.setTimeValue(1, setMinutesValue);
                setHoutValue++;
                setMinutesValue++;
                isChangeTime=true;
                break;

            case R.id.b:
                analogClock.setTimeValue(2, setMinutesValue);
                setHoutValue--;
                setMinutesValue--;
                isChangeTime=true;
                break;
        }
    }
}
