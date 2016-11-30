package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xyy.Gazella.adapter.ClockListAdapter;
import com.xyy.Gazella.view.ListViewForScrollView;
import com.xyy.model.Clock;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListViewForScrollView listview;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.add)
    RelativeLayout add;
    private Context context;
    private List<Clock> clocks = new ArrayList<>();
    private ClockListAdapter adapter;
    public final static int REQUEST_ADD = 1;
    public final static int REQUEST_EDIT = 2;
    private int position = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.clock_activity);
        ButterKnife.bind(this);
        context = this;
        initView();

    }

    private void initView() {
        Clock clock = new Clock();
        clock.setTime("07:00");
        clock.setRate("只响一次");
        clock.setSnoozeTime("5分钟");
        clock.setIsOpen(0);
        clocks.add(clock);
        adapter = new ClockListAdapter(context, clocks);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, EditClockActivity.class);
                intent.putExtra("time", clocks.get(i).getTime());
                intent.putExtra("snooze", clocks.get(i).getSnoozeTime());
                intent.putExtra("rate", clocks.get(i).getRate());
                intent.putExtra("isOpen", clocks.get(i).getIsOpen());
                position = i;
                startActivityForResult(intent, REQUEST_EDIT);
                overridePendingTransitionEnter(ClockActivity.this);
            }
        });
    }


    @OnClick({R.id.back, R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransitionExit(ClockActivity.this);
                break;
            case R.id.add:
                if (clocks.size() == 8) {
                    Toast.makeText(context, "闹钟数量已达上限", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, AddClockActivity.class);
                    startActivityForResult(intent, REQUEST_ADD);
                    overridePendingTransitionEnter(ClockActivity.this);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ADD:
                if (data != null) {
                    Clock clock = new Clock();
                    clock.setTime(data.getStringExtra("time"));
                    clock.setSnoozeTime(data.getStringExtra("snooze"));
                    clock.setRate(data.getStringExtra("rate"));
                    clock.setIsOpen(data.getIntExtra("isOpen", -1));
                    clocks.add(clock);
                    adapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_EDIT:
                if (data != null) {
                    if (data.getStringExtra("result").equals("del")) {
                        clocks.remove(position);
                        adapter.notifyDataSetChanged();
                    } else if (data.getStringExtra("result").equals("edit")) {
                        Clock clock = clocks.get(position);
                        clock.setTime(data.getStringExtra("time"));
                        clock.setSnoozeTime(data.getStringExtra("snooze"));
                        clock.setRate(data.getStringExtra("rate"));
                        clock.setIsOpen(data.getIntExtra("isOpen", -1));
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
