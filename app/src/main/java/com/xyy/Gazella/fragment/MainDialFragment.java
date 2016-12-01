package com.xyy.Gazella.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
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

public class MainDialFragment extends BaseFragment {

    private static final String TAG = MainDialFragment.class.getName();

    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    private View view;

    private boolean isChangeTime = false;
    private ViewTreeObserver vto;
    private boolean saveValue = true;
    private RxBleDevice bleDevice;
    private Observable<RxBleConnection> connectionObservable;
    private BleUtils bleUtils;
    private int laoTime;
    private int newTime;
    private int conut = 0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_main_dial, container, false);
        ButterKnife.bind(this, view);

        bleUtils = new BleUtils();
        if(isconnectionObservable())
        connectionObservable=TimeSynchronization.install.connectionObservable;
        vto = analogclock.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (saveValue) {
                    PreferenceData.setSelectedHourValue(getActivity(), analogclock.getHourTimeValue());
                    PreferenceData.setSelectedMuinutesValue(getActivity(), analogclock.getMinutesTimeValue());
                }
                return true;
            }
        });

        analogclock.setChangeTimeListener(new AnalogClock.ChangeTimeListener() {
            @Override
            public void ChangeTimeListener(int TimeValue) {
                int senTime ;
                if (conut == 0) {
                    laoTime = TimeValue / 2;
                    if (analogclock.ChangeTimeType == 1) {
                        if (isconnectionObservable())
                            Write(bleUtils.adjHourHand(1, (laoTime)), connectionObservable);
                    }else {
                        if (isconnectionObservable())
                            Write(bleUtils.adjMinuteHand(1, (laoTime)), connectionObservable);
                    }
                } else {
                    laoTime = newTime;
                    newTime = TimeValue / 2;
                    if (analogclock.ChangeTimeType == 1) {
                        if (newTime > laoTime) {
                            Logger.t(TAG).e("newTime>>>>>>    " + String.valueOf(newTime) + "\n" + "laoTime>>>>>>>    " + String.valueOf(laoTime));
                            senTime = newTime - laoTime;
                            Logger.t(TAG).e("senTime>>>>>>    " + String.valueOf(senTime));
                            if(senTime!=0&&isconnectionObservable())
                            Write(bleUtils.adjHourHand(1, senTime), connectionObservable);
                        } else {
                            senTime = laoTime - newTime;
                            Logger.t(TAG).e("senTime>>>>>>    " + String.valueOf(senTime));
                            if(senTime!=0&&isconnectionObservable())
                            Write(bleUtils.adjHourHand(2, senTime), connectionObservable);
                        }
                    } else {
                        if (newTime > laoTime) {
                            senTime = newTime - laoTime;
                            Logger.t(TAG).e("senTime>>>>>>    " + String.valueOf(senTime));
                            if(senTime!=0&&isconnectionObservable())
                            Write(bleUtils.adjMinuteHand(1, senTime), connectionObservable);
                        }else {
                            senTime = laoTime - newTime;
                            Logger.t(TAG).e("senTime>>>>>>    " + String.valueOf(senTime));
                            if(senTime!=0&&isconnectionObservable())
                            Write(bleUtils.adjMinuteHand(2, senTime), connectionObservable);
                        }
                    }
                }
                conut++;
            }
        });
        return view;
    }

    public void setChangeTimeType(int ChangeTimeType) {
        analogclock.setChangeTimeType(ChangeTimeType);
    }

    public void setHourTimeValue(float value) {
        analogclock.setTimeValue(1, value);
    }

    public void setMuinutesTimeValue(float value) {
        analogclock.setTimeValue(2, value);
    }

    public int getHourTimeValue() {
        return (int) analogclock.getHourTimeValue();
    }

    public int getMuinutesTimeValue() {
        return (int) analogclock.getMinutesTimeValue();
    }

    public void AddTime() {
        if (analogclock.ChangeTimeType == 1) {
            int a = (int) analogclock.getHourTimeValue();
            a++;
            analogclock.setTimeValue(1, a);
            if(isconnectionObservable())
            Write(bleUtils.adjHourHand(1, 1), connectionObservable);

        } else {
            int a = (int) analogclock.getMinutesTimeValue();
            a++;
            analogclock.setTimeValue(2, a);
            if(isconnectionObservable())
            Write(bleUtils.adjMinuteHand(1, 1), connectionObservable);
        }
        isChangeTime = true;
    }

    public void ReduceTime() {
        if (analogclock.ChangeTimeType == 1) {
            int a = (int) analogclock.getHourTimeValue();
            if (a == 0) a = 60;
            a--;
            analogclock.setTimeValue(1, a);
            if(isconnectionObservable())
            Write(bleUtils.adjHourHand(2, 1), connectionObservable);

        } else {
            int a = (int) analogclock.getMinutesTimeValue();
            if (a == 0) a = 60;
            a--;
            analogclock.setTimeValue(2, a);
            if(isconnectionObservable())
            Write(bleUtils.adjMinuteHand(2, 1), connectionObservable);

        }
        isChangeTime = true;
    }

    public void setHourDrawable(int drawable) {
        analogclock.setHourDrawable(drawable);
    }

    public void setMuinutesDrawable(int drawable) {
        analogclock.setMinuteDrawable(drawable);
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

    private  boolean  isconnectionObservable(){
        if(TimeSynchronization.install.connectionObservable!=null)
            return  true;
        else
            return false;
    }
}
