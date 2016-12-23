package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeZonesActivity extends BaseActivity {

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zones);
        ButterKnife.bind(this);



//        listview.setAdapter(adapter);

    }

    @OnClick({R.id.btnExit, R.id.btnOpt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);
                break;
            case R.id.btnOpt:
                break;
        }
    }
}
