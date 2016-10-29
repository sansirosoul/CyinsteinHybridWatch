package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseFragment;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SmallFragment2 extends BaseFragment {


    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    private View view;

    private float getMinutesValue;
    private float getHourValue;
    private float setMinutesValue;
    private float setHourValue;
    private boolean isChangeTime = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_small_2, container, false);
        ButterKnife.bind(this, view);
        analogclock.setChangeTimeType(2);
        analogclock.setTimeValue(2,0);
        analogclock.setChangeTimeListener(new AnalogClock.ChangeTimeListener() {
            @Override
            public void ChangeTimeListener(int TimeValue) {
                PreferenceData.setSelectedSmall2Value(getActivity(),TimeValue);
            }
        });
        return view;
    }


    public void AddTime() {
        int a = (int) analogclock.getMinutesTimeValue();
        a++;
        analogclock.setTimeValue(2, a);
        isChangeTime = true;
    }

    public void ReduceTime() {
        int a = (int) analogclock.getMinutesTimeValue();
        a--;
        analogclock.setTimeValue(2, a);
        isChangeTime = true;
    }
    public  float  getSmall2TimeValue(){
        return  analogclock.getMinutesTimeValue();
    }
}
