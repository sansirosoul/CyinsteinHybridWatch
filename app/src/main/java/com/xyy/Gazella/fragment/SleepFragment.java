package com.xyy.Gazella.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.partner.entity.Partner;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.activity.HealthyActivity;
import com.xyy.Gazella.activity.SleepActivity;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.NumberProgressBar;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseFragment;
import com.ysp.newband.PreferenceData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

import static com.ysp.hybridtwatch.R.id.deep_time;

/**
 * Created by Administrator on 2016/10/11.
 */

public class SleepFragment extends BaseFragment {

    @BindView(R.id.circle)
    ImageView circle;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.quality)
    TextView quality;
    @BindView(deep_time)
    TextView deepTime;
    @BindView(R.id.low_time)
    TextView lowTime;
    @BindView(R.id.wake_time)
    TextView wakeTime;
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.numberbar)
    NumberProgressBar numberbar;
    @BindView(R.id.ll_NumberProgressBar)
    LinearLayout llNumberProgressBar;
    @BindView(R.id.ll_quality)
    LinearLayout llQuality;
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
    @BindView(R.id.tv_hour_num)
    TextView tvHourNum;
    private View view;
    private Observable<RxBleConnection> connectionObservable;
    private BleUtils bleUtils;
    private Time mCalendar;
    private int myear, month, day, previousDay;
    private StringBuffer sb = new StringBuffer();
    private List<Partner> todayPartners = new ArrayList<>();
    private List<Partner> yesterdayPartners = new ArrayList<>();
    private String strMonth, strDay;
    private String today, yeday, strday;
    private String[] xValue;

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
        view = inflater.inflate(R.layout.fragment_sleep, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {

        String Synchronizationtime = PreferenceData.getSleepSynchronizationTime(getActivity());
        if (Synchronizationtime != null && !Synchronizationtime.equals("")) {
            tvSynchronizationtime.setText("最后同步时间  " + Synchronizationtime);
        }

        connectionObservable = HealthyActivity.install.connectionObservable;
        bleUtils = new BleUtils();
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
        setToDayTime();
    }

    private void setToDayTime() {
        initTime();
        if (month < 10)
            strMonth = sb.append("0").append(String.valueOf(month)).toString();
        else
            strMonth = String.valueOf(month);
        sb.setLength(0);
        previousDay = day - 1;
        if (day < 10)
            strDay = sb.append("0").append(String.valueOf(day)).toString();
        else
            strDay = String.valueOf(day);
        sb.setLength(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        strday = sb.append(String.valueOf(myear)).append(".").append(strMonth).append(".").append(strDay).toString();
        try {
            date = sdf.parse(strday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String yesterday = new SomeUtills().getAmountDate(date, 0, 0);
        initData(strday, yesterday);

    }

    public void initData(String Today, String yesterday) {
        int sleepTime = 0;
        int lightsleepTime = 0;
        int sleepingTime = 0;
        int awakeTime = 0;
        xValue = new String[24];
        if (todayPartners != null || todayPartners.size() > 0) {
            todayPartners.clear();
            yesterdayPartners.clear();
        }
        todayPartners = HealthyActivity.install.mCommonUtils.queryByBuilder("sleep", Today);
        yesterdayPartners =  HealthyActivity.install.mCommonUtils.queryByBuilder("sleep", yesterday);

        if (todayPartners.size() == 24 && yesterdayPartners.size() == 24 && !yesterday.equals(strday)) {
            yesterdayPartners = yesterdayPartners.subList(12, 24);
            todayPartners = todayPartners.subList(0, 12);
            for (int i = 0; i < yesterdayPartners.size(); i++) {
                xValue[i] = yesterdayPartners.get(i).getSleep();
            }
            for (int i = 0; i < todayPartners.size(); i++) {
                xValue[i + 12] = todayPartners.get(i).getSleep();
            }
        }

        for (int i = 0; i < xValue.length; i++) {
            String state = xValue[i];
            if (state != null) {
                switch (state) {
                    case "0":
                        awakeTime += 1;
                        break;
                    case "1":
                        sleepTime += 1;
                        lightsleepTime += 1;
                        break;
                    case "2":
                        sleepTime += 1;
                        sleepingTime += 1;
                        break;
                    case "3":
                        awakeTime += 1;
                        break;
                }
            }
        }

        tvHour.setText(String.valueOf(sleepTime));
        deepTime.setText(String.valueOf(sleepingTime)+"小时");
        lowTime.setText(String.valueOf(lightsleepTime)+"小时");
        wakeTime.setText(String.valueOf(awakeTime)+"小时");

    }


    @OnClick(R.id.circle)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle:
                Intent intent = new Intent(getActivity(), SleepActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_righttoleft);
                break;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1001);
            mHandler.postDelayed(this, 100);
        }
    };

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
                Write(bleUtils.getSleepData(6), connectionObservable);
            }
        }, 1000);
    }

    public void setBerbarNum(int type, int day) {

        switch (type) {
            case 1:
                tvDay1.setText(String.valueOf(day) + "号" + "更新完成");
//                tvDay1.setTextColor(getResources().getColor(R.color.title_linear));
                numberbar.setProgress(10);
                break;

            case 2:
                tvDay2.setText(String.valueOf(day) + "号" + "更新完成");
                //    tvDay2.setTextColor(getResources().getColor(R.color.title_linear));
                numberbar.setProgress(30);
                break;

            case 3:
                tvDay3.setText(String.valueOf(day) + "号" + "更新完成");
                //     tvDay3.setTextColor(getResources().getColor(R.color.title_linear));
                numberbar.setProgress(40);
                break;

            case 4:
                tvDay4.setText(String.valueOf(day) + "号" + "更新完成");
                //    tvDay4.setTextColor(getResources().getColor(R.color.title_linear));
                numberbar.setProgress(60);
                break;

            case 5:
                tvDay5.setText(String.valueOf(day) + "号" + "更新完成");
                //    tvDay5.setTextColor(getResources().getColor(R.color.title_linear));
                numberbar.setProgress(80);
                break;

            case 6:
                tvDay6.setText(String.valueOf(day) + "号" + "更新完成");
                //    tvDay6.setTextColor(getResources().getColor(R.color.title_linear));
                numberbar.setProgress(90);
                break;

            case 7:
                tvDay7.setText(String.valueOf(day) + "号" + "更新完成");
                //    tvDay7.setTextColor(getResources().getColor(R.color.title_linear));
                numberbar.setProgress(100);
                llNumberProgressBar.setVisibility(View.GONE);
                llQuality.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setTvSynchronizationtime() {
        Time mCalendar;
        StringBuffer sb = new StringBuffer();
        TimeZone tz = TimeZone.getTimeZone(PreferenceData.getTimeZonesState(getActivity()));
        mCalendar = new Time(tz.getID());
        mCalendar.setToNow();
        String time = sb.append(String.valueOf(mCalendar.year)).append(".").append(String.valueOf(mCalendar.month + 1)).append(".")
                .append(String.valueOf(mCalendar.monthDay)).append("  ").append(String.valueOf(mCalendar.hour)).append(":")
                .append(String.valueOf(mCalendar.minute)).append(":").append(String.valueOf(mCalendar.second)).toString();
        tvSynchronizationtime.setText("最后同步时间  " + time);
        PreferenceData.setSleepSynchronizationTime(getActivity(), time);
    }

    private void initTime() {
        mCalendar = new Time();
        mCalendar.setToNow();
        myear = mCalendar.year;
        month = mCalendar.month + 1;
        day = mCalendar.monthDay;
    }
}
