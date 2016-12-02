package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.activity.TimeSynchronization;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.view.AnalogClock;
import com.ysp.newband.BaseFragment;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SmallFragment1 extends BaseFragment {

    private static final String TAG = SmallFragment1.class.getName();

    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    private View view;


    private boolean isChangeTime = false;
    private ViewTreeObserver vto;
    private boolean saveValue = true;
    private BleUtils bleUtils;
    private Observable<RxBleConnection> connectionObservable;
    private int newTime, senTime, laoTime;
    private boolean conut = true;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_small_1, container, false);
        ButterKnife.bind(this, view);

        bleUtils = new BleUtils();
        if (isconnectionObservable())
            connectionObservable = TimeSynchronization.install.connectionObservable;

        analogclock.setChangeTimeType(2);
        analogclock.setTimeValue(2, 0);
        analogclock.setTimeValue(2, PreferenceData.getSelectedSmall1Value(getActivity()));

        vto = analogclock.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (saveValue) {
                    PreferenceData.setSelectedSmall1Value(getActivity(), (int) analogclock.getMinutesTimeValue());
                }
                return true;
            }
        });

        analogclock.setChangeTimeListener(new AnalogClock.ChangeTimeListener() {
            @Override
            public void ChangeTimeListener(int mMinutes, int mHour) {

                if (conut) {
                    Write(bleUtils.adjSecondHand(1, 1), connectionObservable);
                } else {
                    laoTime = newTime;
                    newTime = mMinutes;
                    if (newTime > laoTime) {
                        senTime = newTime - laoTime;
                        if (senTime != 0 && isconnectionObservable())
                            Write(bleUtils.adjSecondHand(1, senTime), connectionObservable);
                    } else {
                        senTime = laoTime - newTime;
                        if (senTime != 0 && isconnectionObservable())
                            Write(bleUtils.adjSecondHand(2, senTime), connectionObservable);
                    }
                }
                conut = false;
            }
        });
        return view;
    }

    public void AddTime() {
        int a = (int) analogclock.getMinutesTimeValue();
        a++;
        analogclock.setTimeValue(2, a);
        isChangeTime = true;
        Write(bleUtils.adjSecondHand(1, 1), connectionObservable);
    }

    public void ReduceTime() {
        int a = (int) analogclock.getMinutesTimeValue();
        if (a == 0) a = 60;
        a--;
        analogclock.setTimeValue(2, a);
        isChangeTime = true;
        Write(bleUtils.adjSecondHand(2, 1), connectionObservable);
    }

    public float getSmall1TimeValue() {
        return analogclock.getMinutesTimeValue();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveValue = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        saveValue = true;
    }

    private boolean isconnectionObservable() {
        if (TimeSynchronization.install.connectionObservable != null)
            return true;
        else
            return false;
    }
}
