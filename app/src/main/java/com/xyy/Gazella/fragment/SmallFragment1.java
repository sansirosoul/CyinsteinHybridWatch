package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseFragment;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SmallFragment1 extends BaseFragment {


    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    private View view;

    private float getMinutesValue;
    private float getHourValue;
    private float setMinutesValue;
    private float setHourValue;
    private boolean isChangeTime = false;
    private  boolean ismove;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_small_1, container, false);
        ButterKnife.bind(this, view);
        analogclock.setChangeTimeType(2);
        analogclock.setTimeValue(2,0);

        return view;
    }
    public void AddTime() {

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
    }

    public void ReduceTime() {

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
    }

    private  boolean getIsMove(){

        return ismove;
    }
}
