package com.xyy.Gazella.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.activity.HealthyActivity;
import com.xyy.Gazella.activity.StepActivity;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.view.NumberProgressBar;
import com.ysp.newband.BaseFragment;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepFragment extends BaseFragment {
    @BindView(R.id.circle)
    ImageView circle;
    @BindView(R.id.step_num)
    TextView stepNum;
    @BindView(R.id.step_target)
    TextView stepTarget;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.cal)
    TextView cal;
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.numberbar)
    NumberProgressBar numberbar;
    @BindView(R.id.ll_NumberProgressBar)
    LinearLayout llNumberProgressBar;
    @BindView(R.id.ll_quality)
    LinearLayout llQuality;
    private View view;
    private BleUtils bleUtils;
    private Observable<RxBleConnection> connectionObservable;
    public boolean isgetTodayStep = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    if (numberbar.getProgress() == 100) {
                        numberbar.setProgress(0);
                        llNumberProgressBar.setVisibility(View.GONE);
                        llQuality.setVisibility(View.VISIBLE);
                    } else {
                        numberbar.incrementProgressBy(1);
                    }
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, view);
        initView();
        String address = PreferenceData.getAddressValue(getActivity());
        if (address != null && !address.equals(""))
            connectionObservable = HealthyActivity.install.connectionObservable;
        bleUtils = new BleUtils();
        if (isgetTodayStep)
            mHandler.post(getTodayStep);

        return view;
    }


    private void initView() {
        final ViewGroup.LayoutParams params = circle.getLayoutParams();
        ViewTreeObserver vto = circle.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                params.width = circle.getHeight();
                circle.setLayoutParams(params);
                return true;
            }
        });
        stepTarget.setText(String.valueOf(PreferenceData.getTargetRunValue(getActivity())));
    }

    @OnClick(R.id.circle)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle:
                Intent intent = new Intent(getActivity(), StepActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_righttoleft);
                break;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1001);
            mHandler.postDelayed(this, 50);
        }
    };

    Runnable getTodayStep = new Runnable() {
        @Override
        public void run() {
            Write(bleUtils.getTodayStep(), connectionObservable);
            mHandler.sendEmptyMessage(1002);
            mHandler.postDelayed(this, 1000);
        }
    };

    public void setSynchronization() {
        llNumberProgressBar.setVisibility(View.VISIBLE);
        llQuality.setVisibility(View.GONE);
        mHandler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isgetTodayStep=false;
    }

    public void setStepNum(String num) {
        stepNum.setText(num);
    }

}
