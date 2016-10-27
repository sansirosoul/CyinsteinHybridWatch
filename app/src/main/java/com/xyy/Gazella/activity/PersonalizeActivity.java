package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    @BindView(R.id.text)
    TextView text;
    private int cur = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.personalize_activity);
        ButterKnife.bind(this);

        handler.post(runnable);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    num.setText(cur + "%");
                    circle.setProgress((float) cur);
                    if(cur==100){
                        Intent intent = new Intent(PersonalizeActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransitionEnter(PersonalizeActivity.this);
                    }
                    break;
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (cur != 100) {
                cur++;
                handler.sendEmptyMessage(1);
                handler.postDelayed(this, 50);
            }else{
                text.setText(getResources().getText(R.string.personalized));
            }
        }
    };
}
