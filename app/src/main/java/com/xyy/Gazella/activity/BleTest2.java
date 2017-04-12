package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xyy.Gazella.view.AnalogClock2;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/4.
 */

public class BleTest2 extends BaseActivity {

    @BindView(R.id.analogclock2)
    AnalogClock2 analogclock2;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.test2);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.analogclock2)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.analogclock2:
                Toast.makeText(this,"ssssssssss",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
