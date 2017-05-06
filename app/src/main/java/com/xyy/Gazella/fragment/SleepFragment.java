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
import com.xyy.Gazella.view.RiseNumberTextView;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseFragment;
import com.ysp.newband.PreferenceData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    RiseNumberTextView tvHour;
    @BindView(R.id.tv_min)
    RiseNumberTextView tvMin;
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
    @BindView(R.id.iv_tip)
    ImageView ivTip;

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
    private List<Partner> partners = new ArrayList<>();

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

    @Override
    protected void onReadReturn(int type, byte[] bytes) {
        super.onReadReturn(type, bytes);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_sleep, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        tvHour.setTextSize(50);
        tvMin.setTextSize(50);
        setIvTip(this.getResources().getDrawable(R.drawable.page15_nanguo), this.getResources().getString(R.string.no_over_target));
        deepTime.setText(0 + getResources().getString(R.string.minute));
        lowTime.setText(0 + getResources().getString(R.string.minute));
        wakeTime.setText(0 + getResources().getString(R.string.minute));
        String Synchronizationtime = PreferenceData.getSleepSynchronizationTime(getActivity());
        if (Synchronizationtime != null && !Synchronizationtime.equals("")) {
            tvSynchronizationtime.setText(getResources().getString(R.string.last_synchronize_time) + Synchronizationtime);
        }

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

    public void setToDayTime() {
        initTime();
        strday = "";
        String yesterday = "";
        sb.setLength(0);
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

        yesterday = new SomeUtills().getAmountDate(date, 0, 0);
        initData(strday, yesterday);


//        Date date1 = new Date();
//        today=(date1.getYear()+1900)+"."+(date1.getMonth()+1)+"."+date1.getDate();
//        Date date2 = HealthyActivity.getBeforeDay(date1,1);
//        yeday = (date2.getYear()+1900)+"."+(date2.getMonth()+1)+"."+date2.getDate();
//
//        initData(today,yeday);
    }

    public void initData(String Today, String yesterday) {
        int lightsleepHour = 0;
        int lightsleepMin = 0;
        int deepsleepHour = 0;
        int deepsleepMin = 0;
        int sleepHour = 0;
        int sleepMin = 0;
        int awakeHour = 0;
        int awakeMin = 0;

        if (todayPartners != null || todayPartners.size() > 0) {
            partners.clear();
            todayPartners.clear();
            yesterdayPartners.clear();
        }
        todayPartners = HealthyActivity.install.mCommonUtils.queryByBuilder("sleep", Today);
        yesterdayPartners = HealthyActivity.install.mCommonUtils.queryByBuilder("sleep", yesterday);
        for (int i = 0; i < yesterdayPartners.size(); i++) {
            String[] times = yesterdayPartners.get(i).getTime().split("\\.");
            int hour = Integer.parseInt(times[0]);
            if (hour >= 12) {
                partners.add(yesterdayPartners.get(i));
            }
        }
        for (int i = 0; i < todayPartners.size(); i++) {
            String[] times = todayPartners.get(i).getTime().split("\\.");
            int hour = Integer.parseInt(times[0]);
            if (hour < 12) {
                partners.add(todayPartners.get(i));
            }
        }

        if (partners.size() != 0) {
            for (int i = 0; i < partners.size(); i++) {
                if (i < partners.size() - 1) {
                    int year1 = Integer.parseInt(partners.get(i).getDate().split("\\.")[0]);
                    int month1 = Integer.parseInt(partners.get(i).getDate().split("\\.")[1]);
                    int date1 = Integer.parseInt(partners.get(i).getDate().split("\\.")[2]);
                    int hour1 = Integer.parseInt(partners.get(i).getTime().split("\\.")[0]);
                    int min1 = Integer.parseInt(partners.get(i).getTime().split("\\.")[1]);
                    Calendar c1 = Calendar.getInstance();
                    c1.set(year1, month1, date1, hour1, min1);

                    int year2 = Integer.parseInt(partners.get(i + 1).getDate().split("\\.")[0]);
                    int month2 = Integer.parseInt(partners.get(i + 1).getDate().split("\\.")[1]);
                    int date2 = Integer.parseInt(partners.get(i + 1).getDate().split("\\.")[2]);
                    int hour2 = Integer.parseInt(partners.get(i + 1).getTime().split("\\.")[0]);
                    int min2 = Integer.parseInt(partners.get(i + 1).getTime().split("\\.")[1]);
                    Calendar c2 = Calendar.getInstance();
                    c2.set(year2, month2, date2, hour2, min2);

                    int second = (int) ((c2.getTime().getTime() - c1.getTime().getTime()) / 1000);
                    int min = second/60;
                    switch (partners.get(i).getSleep()) {
                        case "2":
                            lightsleepMin += min;
                            break;
                        case "3":
                            deepsleepMin += min;
                            break;
                    }
                }
            }
        }

        sleepHour = (lightsleepMin+deepsleepMin)/60;
        sleepMin = (lightsleepMin+deepsleepMin)%60;

        awakeHour = (24*60-lightsleepMin-deepsleepMin)/60;
        awakeMin = (24*60-lightsleepMin-deepsleepMin)%60;

        lightsleepHour=lightsleepMin/60;
        lightsleepMin=lightsleepMin%60;

        deepsleepHour=deepsleepMin/60;
        deepsleepMin=deepsleepMin%60;


        tvHour.withNumber(sleepHour);
        tvHour.setDuration(1000);
        tvHour.start();
        tvMin.withNumber(sleepMin);
        tvMin.setDuration(1000);
        tvMin.start();

        deepTime.setText(String.valueOf(deepsleepHour) + getResources().getString(R.string.hour) + deepsleepMin + getResources().getString(R.string.minute));
        lowTime.setText(String.valueOf(lightsleepHour) + getResources().getString(R.string.hour) + lightsleepMin + getResources().getString(R.string.minute));
        wakeTime.setText(awakeHour+getResources().getString(R.string.hour)+awakeMin+getResources().getString(R.string.minute));

        if (sleepHour < PreferenceData.getTargetSleepHourValue(getActivity())) {
            quality.setText(getResources().getString(R.string.bad));
            setIvTip(this.getResources().getDrawable(R.drawable.page15_nanguo), this.getResources().getString(R.string.no_over_target));
        } else {
            quality.setText(getResources().getString(R.string.good));
            setIvTip(this.getResources().getDrawable(R.drawable.page15_kaixin), this.getResources().getString(R.string.over_target));
        }
    }

    public void setIvTip(Drawable drawable, String Str) {
        ivTip.setBackground(drawable);
        details.setText(Str);
    }

    @OnClick(R.id.circle)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle:
                Intent intent = new Intent(getActivity(), SleepActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,0);
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
                HealthyActivity.type = 2;
                writeCharacteristic(bleUtils.getSleepData(6));
            }
        }, 1000);
    }

    public void setBerbarNum(int type, int day) {

        switch (type) {
            case 1:
//                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_1));
                tvDay1.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(10);
                break;

            case 2:
//                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_2));
                tvDay2.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(30);
                break;

            case 3:
//                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_3));
                tvDay3.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(40);
                break;

            case 4:
//                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_4));
                tvDay4.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(60);
                break;

            case 5:
//                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_5));
                tvDay5.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(80);
                break;

            case 6:
//                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_6));
                tvDay6.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(90);
                break;

            case 7:
//                YoYo.with(StandUp).duration(700).playOn(view.findViewById(R.id.tv_day_7));
                tvDay7.setText(String.valueOf(day) + getResources().getString(R.string.date) + getResources().getString(R.string.update_success));
                numberbar.setProgress(100);
                break;
        }
    }

    public void setUploadFinsh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                llNumberProgressBar.setVisibility(View.GONE);
                llQuality.setVisibility(View.VISIBLE);
            }

        }, 500);
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
        PreferenceData.setSleepSynchronizationTime(getActivity(), time);
    }

    private void initTime() {
        myear = 0;
        month = 0;
        day = 0;
        mCalendar = new Time();
        mCalendar.setToNow();
        myear = mCalendar.year;
        month = mCalendar.month + 1;
        day = mCalendar.monthDay;
    }
}
