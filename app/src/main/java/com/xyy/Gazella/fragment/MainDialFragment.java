package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseFragment;
import com.ysp.newband.PreferenceData;
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

    private boolean isChangeTime = false;
    private  ViewTreeObserver vto;
    private  boolean saveValue=true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_main_dial, container, false);
        ButterKnife.bind(this, view);


        vto = analogclock.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(saveValue) {
                    PreferenceData.setSelectedHourValue(getActivity(), analogclock.getHourTimeValue());
                    PreferenceData.setSelectedMuinutesValue(getActivity(), analogclock.getMinutesTimeValue());
                }
                return true;
            }
        });
        return view;
    }

    public void setChangeTimeType(int ChangeTimeType) {
        analogclock.setChangeTimeType(ChangeTimeType);
    }
    public  void setHourTimeValue(float value){
         analogclock.setTimeValue(1,value);
    }
    public  void setMuinutesTimeValue(float value){
         analogclock.setTimeValue(2,value);
    }

    public void AddTime() {
        if (analogclock.ChangeTimeType == 1) {
            int a = (int)analogclock.getHourTimeValue();
            a++;
            analogclock.setTimeValue(1, a);
        }else {
            int a = (int)analogclock.getMinutesTimeValue();
            a++;
            analogclock.setTimeValue(2, a);
        }
        isChangeTime = true;

    }

    public void ReduceTime() {
        if (analogclock.ChangeTimeType == 1) {
            int a =(int) analogclock.getHourTimeValue();
            if(a==0)a=60;
            a--;
            analogclock.setTimeValue(1, a);
        }else {
            int a = (int)analogclock.getMinutesTimeValue();
            if(a==0)a=60;
            a--;
            analogclock.setTimeValue(2, a);
        }
        isChangeTime = true;
    }

    public  void  setHourDrawable(int drawable){
        analogclock.setHourDrawable(drawable);
    }
    public  void  setMuinutesDrawable(int drawable){
        analogclock.setMinuteDrawable(drawable);
    }

    @Override
    public void onStop() {
        super.onStop();
        saveValue=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        saveValue=true;
    }
}
