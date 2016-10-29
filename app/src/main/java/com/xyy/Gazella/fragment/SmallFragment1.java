package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.util.Log;
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

public class SmallFragment1 extends BaseFragment {

    private  static  final  String TAG= SmallFragment1.class.getName();

    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    private View view;


    private boolean isChangeTime = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_small_1, container, false);
        ButterKnife.bind(this, view);
        analogclock.setChangeTimeType(2);
        analogclock.setTimeValue(2, 0);

        analogclock.setChangeTimeListener(new AnalogClock.ChangeTimeListener() {
            @Override
            public void ChangeTimeListener(int TimeValue) {

                Log.i(TAG,String.valueOf(TimeValue));
                PreferenceData.setSelectedSmall1Value(getActivity(),TimeValue);
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
    public  float  getSmall1TimeValue(){
        return  analogclock.getMinutesTimeValue();
    }
}
