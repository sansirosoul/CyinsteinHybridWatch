package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.btnExit)
    Button btnExit;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(R.id.listview)
    ListViewForScrollView listview;
    @BindView(R.id.add)
    ImageView add;
    private Context context;
    private List<Clock> clocks = new ArrayList<>();
    private ClockListAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.clock_activity);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        TVTitle.setText(R.string.clock);
        adapter = new ClockListAdapter(context, clocks);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,AddClockActivity.class);
                startActivityForResult(intent,2);
                overridePendingTransitionEnter(ClockActivity.this);
            }
        });
    }



    @OnClick({R.id.btnExit,R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                overridePendingTransitionExit(ClockActivity.this);
                break;
            case R.id.add:
                Intent intent = new Intent(context,AddClockActivity.class);
                startActivityForResult(intent,1);
                overridePendingTransitionEnter(ClockActivity.this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(data!=null){
                    Clock clock = new Clock();
                    clock.setTime(data.getStringExtra("time"));
                    clock.setRate(data.getStringExtra("repeatrate"));
                    clock.setIsOpen(data.getIntExtra("isOpen",-1));
                    clocks.add(clock);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 2:

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
