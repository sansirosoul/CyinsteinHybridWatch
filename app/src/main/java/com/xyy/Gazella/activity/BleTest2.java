package com.xyy.Gazella.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

/**
 * Created by Administrator on 2017/2/4.
 */

public class BleTest2 extends BaseActivity {
    @BindView(R.id.analogclock)
    AnalogClock analogclock;
    @BindView(R.id.local_time)
    TextView localTime;
    @BindView(R.id.gridView)
    MyGridView gridView;
    private Context context;
    private TimezonesAdapter adapter;
    private ArrayList<TimeZonesData> dateList = new ArrayList<TimeZonesData>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.test2);
        ButterKnife.bind(this);
        analogclock.setDialDrawable(R.drawable.localtime);
        analogclock.setHourDrawable(R.drawable.hour_big);
        analogclock.setMinuteDrawable(R.drawable.minute_big);
        analogclock.setTimeValue(1,12);
        context = this;

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
                PreferenceData.setTimeZonesState(BleTest2.this, dateList.get(i).getGtm());
                finish();
            }
        });
    }

}
