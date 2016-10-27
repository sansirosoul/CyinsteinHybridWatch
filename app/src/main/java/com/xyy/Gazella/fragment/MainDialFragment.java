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

public class MainDialFragment extends BaseFragment {


    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    private View view;

    private int getMinutesValue;
    private int getHourValue;
    private int setMinutesValue;
    private int setHourValue;
    private boolean isChangeTime = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_main_dial, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setChangeTimeType(int ChangeTimeType) {
        analogclock.setChangeTimeType(ChangeTimeType);
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

    public  void  setHourDrawable(int drawable){
        analogclock.setHourDrawable(drawable);
    }
    public  void  setMuinutesDrawable(int drawable){
        analogclock.setMinuteDrawable(drawable);
    }
}
