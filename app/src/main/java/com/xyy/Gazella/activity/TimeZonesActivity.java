package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyy.Gazella.adapter.TimezonesAdapter;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.AnalogClock;
import com.xyy.Gazella.view.MyGridView;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeZonesActivity extends BaseActivity {
    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    @BindView(R.id.local_time)
    TextView localTime;
    @BindView(R.id.gridView)
    MyGridView gridView;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    private Context context;
    private TimezonesAdapter adapter;
    private ArrayList<TimeZonesData> dateList = new ArrayList<TimeZonesData>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_time_zones);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        TVTitle.setText(R.string.timezone);

        Time time = new Time();
        time.setToNow();
        int minute = time.minute;
        int hour = time.hour;
        String strHour;
        String strMinute;
        if (hour < 10)
            strHour = String.format("%2d", hour).replace(" ", "0");
        else
            strHour = String.valueOf(hour);
        if (minute < 10)
            strMinute = String.format("%2d", minute).replace(" ", "0");
        else
            strMinute = String.valueOf(minute);

        localTime.setText(strHour + " : " + strMinute);
        analogclock.setTimeValue(1, hour);
        analogclock.setTimeValue(2, minute);
        analogclock.setDialDrawable(R.drawable.localtime);
        analogclock.setHourDrawable(R.drawable.hour_big);
        analogclock.setMinuteDrawable(R.drawable.minute_big);

        dateList = new SomeUtills().getTimeZones(this);
        String id = PreferenceData.getTimeZonesState(this);
        for (int i = 0; i < dateList.size(); i++) {
            if (dateList.get(i).getGtm().equals(id))
                dateList.get(i).setClick(true);
        }
        adapter = new TimezonesAdapter(this, dateList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dateList.get(i).setClick(true);
                PreferenceData.setTimeZonesState(TimeZonesActivity.this, dateList.get(i).getGtm());
                setResult(RESULT_OK, new Intent());
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);
            }
        });
    }


    @OnClick({R.id.btnExit, R.id.local_time,R.id.analogclock})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);
                break;
            case R.id.analogclock:
                PreferenceData.setTimeZonesState(TimeZonesActivity.this, "local");
                setResult(RESULT_OK, new Intent());
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);
                break;
        }
    }

}
