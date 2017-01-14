package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xyy.Gazella.adapter.TimeZonesListAdapter;
import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeZonesActivity extends BaseActivity {
    private static final String TAG = TimeZonesActivity.class.getName();
    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.btnDate)
    Button btnDate;
    @BindView(R.id.TVTitle)
    TextView TVTitle;

    private ArrayList<TimeZonesData> dateList = new ArrayList<TimeZonesData>();
    private TimeZonesData data;
    private TimeZonesListAdapter adapter;
    private int postion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zones);
        ButterKnife.bind(this);
        InitView();
    }

    private void InitView() {
        TVTitle.setText(getResources().getString(R.string.Time_Zones));
        dateList = new SomeUtills().getTimeZones(TimeZonesActivity.this);
        String id = PreferenceData.getTimeZonesState(TimeZonesActivity.this);
        for (int i = 0; i < dateList.size(); i++) {
            if (dateList.get(i).getGtm().equals(id))
                dateList.get(i).setClick(true);
        }
        adapter = new TimeZonesListAdapter(TimeZonesActivity.this, dateList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListviewItemClick());
    }

    @OnClick({R.id.btnExit, R.id.btnOpt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                if (postion != -1) {
                    PreferenceData.setTimeZonesState(TimeZonesActivity.this, dateList.get(postion).getGtm());
                    setResult(RESULT_OK, new Intent());
                } else
                    setResult(100, new Intent());
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);
                break;
            case R.id.btnOpt:
                break;
        }
    }

    public class ListviewItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            for (int n = 0; n < dateList.size(); n++) {
                dateList.get(n).setClick(false);
            }

            dateList.get(i).setClick(true);
            adapter.notifyDataSetChanged();
            postion = i;

        PreferenceData.setTimeZonesState(TimeZonesActivity.this, dateList.get(postion).getGtm());
                setResult(RESULT_OK, new Intent());
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);

        }
    }
}
