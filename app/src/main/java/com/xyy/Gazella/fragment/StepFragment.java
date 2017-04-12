package com.xyy.Gazella.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.YoYo;
import com.xyy.Gazella.activity.HealthyActivity;
import com.xyy.Gazella.activity.StepActivity;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.view.AnalogClock2;
import com.xyy.Gazella.view.NumberProgressBar;
import com.xyy.Gazella.view.RiseNumberTextView;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseFragment;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.daimajia.androidanimations.library.Techniques.StandUp;

/**
 * Created by Administrator on 2016/10/11.
 */

public class StepFragment extends BaseFragment {

    @BindView(R.id.step_num)
    public RiseNumberTextView stepNum;
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
    @BindView(R.id.iv_tip)
    ImageView ivTip;
    @BindView(R.id.tv_day_1)
    TextView tvDay1;
    @BindView(R.id.tv_day_2)
    TextView tvDay2;
    @BindView(R.id.tv_day_3)
    TextView tvDay3;
    @BindView(R.id.tv_day_4)
    TextView tvDay4;
    @BindView(R.id.tv_day_5)
    TextView tvDay5;
    @BindView(R.id.tv_day_6)
    TextView tvDay6;
    @BindView(R.id.tv_day_7)
    TextView tvDay7;
    @BindView(R.id.tv_synchronizationtime)
    TextView tvSynchronizationtime;
    @BindView(R.id.circle)
    AnalogClock2 circle;
    @BindView(R.id.click_layout)
    RelativeLayout clickLayout;
    @BindView(R.id.tv_sync)
    TextView tvSync;

    private View view;
    private BleUtils bleUtils;
    private setStepNumTextOnEndListener setStepNumTextOnEndListener;
    private int barNum;
    private static String TAG = StepFragment.class.getName();
    private boolean isRun = true;
    private int count;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    circle.setTimeValue(2, count);
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);
        initView();
        bleUtils = new BleUtils();
        circle.setMinuteHandGone();
        if (GazelleApplication.isBleConnected) {
            tvSync.setVisibility(View.VISIBLE);
            circle.setMinuteDrawable(R.drawable.point);
            circle.setTimeValue(2, 0);
            mHandler.post(runnable);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    HealthyActivity.type = 0;
                    writeCharacteristic(bleUtils.getTodayStep());
                }
            }, 300);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    HealthyActivity.type = 1;
                    writeCharacteristic(bleUtils.getStepData(6));
                }
            }, 1000);
        }
        return view;
    }

    public void getTodayStepPost() {
        HealthyActivity.type = 0;
        if (GazelleApplication.isBleConnected)
            mHandler.post(getTodayStep);
    }

    public void removeTodayStepPost() {
        if (getTodayStep != null)
            mHandler.removeCallbacks(getTodayStep);
    }

    private void initView() {
        stepNum.setTextSize(50);
        String Synchronizationtime = PreferenceData.getSynchronizationTime(getActivity());
        if (Synchronizationtime != null && !Synchronizationtime.equals("")) {
            tvSynchronizationtime.setText(getResources().getString(R.string.last_synchronize_time) + Synchronizationtime);
        }
//        final ViewGroup.LayoutParams params = circle.getLayoutParams();
//        ViewTreeObserver vto = circle.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                params.width = circle.getHeight();
//                circle.setLayoutParams(params);
//                return true;
//            }
//        });
        circle.setDialDrawable(R.drawable.page15_step);


        stepTarget.setText(String.valueOf(PreferenceData.getTargetRunValue(getActivity())));
        stepNum.setOnEndListener(new RiseNumberTextView.EndListener() {
            @Override
            public void onEndFinish() {
                if (setStepNumTextOnEndListener != null) {
                    setStepNumTextOnEndListener.setStepNumTextOnEndListener();
                }
            }
        });

        setIvTip(this.getResources().getDrawable(R.drawable.page15_nanguo), this.getResources().getString(R.string.no_over_target));
        time.setText("0" + getResources().getString(R.string.minute));
        distance.setText("0" + getResources().getString(R.string.mi));
        cal.setText("0" + getResources().getString(R.string.card));
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

    Runnable getTodayStep = new Runnable() {
        @Override
        public void run() {
            if (GazelleApplication.isBleConnected)
                writeCharacteristic(bleUtils.getTodayStep());
            mHandler.sendEmptyMessage(1002);
            mHandler.postDelayed(this, 1000);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRun) {
                count++;
                mHandler.sendEmptyMessage(1001);
                mHandler.postDelayed(this, 30);
            }
        }
    };

    public void removePoint() {
        tvSync.setVisibility(View.INVISIBLE);
        mHandler.removeCallbacks(runnable);
        circle.setMinuteHandGone();
    }

    public void setSynchronizationData() {
        llNumberProgressBar.setVisibility(View.VISIBLE);
        llQuality.setVisibility(View.GONE);
        numberbar.setProgress(0);
        tvDay1.setText("...");
        tvDay2.setText("...");
        tvDay3.setText("...");
        tvDay4.setText("...");
        tvDay5.setText("...");
        tvDay6.setText("...");
        tvDay7.setText("...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HealthyActivity.type = 1;
                writeCharacteristic(bleUtils.getStepData(6));
            }
        }, 1000);
    }

    public void setBerbarNum(int type, int day) {
        switch (type) {
            case 1:
                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_1));
                tvDay1.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(10);
                break;

            case 2:
                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_2));
                tvDay2.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(30);
                break;

            case 3:
                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_3));
                tvDay3.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(40);
                break;

            case 4:
                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_4));
                tvDay4.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(60);
                break;

            case 5:
                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_5));
                tvDay5.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(80);
                break;

            case 6:
                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_6));
                tvDay6.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(90);
                break;

            case 7:
                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_7));
                tvDay7.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(100);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llNumberProgressBar.setVisibility(View.GONE);
                        llQuality.setVisibility(View.VISIBLE);
                    }
                }, 500);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setStepNum(int num, RiseNumberTextView stepNum) {
        // 设置数据
        stepNum.withNumber(num);
        // 设置动画播放时间
        stepNum.setDuration(1000);
        // 开始播放动画
        stepNum.start();
    }

    public void setStepNumFalse(int num) {
        stepNum.setText(String.valueOf(num));
    }

    public void setCalcalNum(String num) {
        cal.setText(num);
    }

    public void setTime(String t) {
        time.setText(t);
    }

    public void setDistanceNum(String num) {
        distance.setText(num);
    }

    public void setIvTip(Drawable drawable, String Str) {
        ivTip.setBackground(drawable);
        details.setText(Str);
    }

    public void setTvSynchronizationtime() {
        Time mCalendar;
        StringBuffer sb = new StringBuffer();
        if (PreferenceData.getTimeZonesState(getActivity()).equals("local")) {
            mCalendar = new Time();
        } else {
            TimeZone tz = TimeZone.getTimeZone(PreferenceData.getTimeZonesState(getActivity()));
            mCalendar = new Time(tz.getID());
        }
        mCalendar.setToNow();
        String time = sb.append(String.valueOf(mCalendar.year)).append(".").append(String.valueOf(mCalendar.month + 1)).append(".")
                .append(String.valueOf(mCalendar.monthDay)).append("  ").append(String.valueOf(mCalendar.hour)).append(":")
                .append(String.valueOf(mCalendar.minute)).append(":").append(String.valueOf(mCalendar.second)).toString();
        tvSynchronizationtime.setText(getResources().getString(R.string.last_synchronize_time) + time);
        PreferenceData.setSynchronizationTime(getActivity(), time);
    }

    @OnClick(R.id.circle)
    public void onClick() {
    }


    public interface setStepNumTextOnEndListener {
        void setStepNumTextOnEndListener();
    }

    public void setStepNumTextOnEndListener(setStepNumTextOnEndListener setStepNumTextOnEndListener) {
        this.setStepNumTextOnEndListener = setStepNumTextOnEndListener;
    }
}
