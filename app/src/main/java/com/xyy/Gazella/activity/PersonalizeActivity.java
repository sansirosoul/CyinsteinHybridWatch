package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.view.Window;

import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/15.
 */

public class PersonalizeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personalize_activity);
    }
}