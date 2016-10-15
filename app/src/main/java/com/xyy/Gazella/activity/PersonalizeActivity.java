package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.xyy.Gazella.view.MViewOne;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/15.
 */

public class PersonalizeActivity extends BaseActivity {


    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.circle)
    MViewOne circle;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personalize_activity);
        ButterKnife.bind(this);



    }
}
