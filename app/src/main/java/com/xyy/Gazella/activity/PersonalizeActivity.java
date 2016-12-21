package com.xyy.Gazella.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.widget.TextView;

import com.xyy.Gazella.view.MViewOne;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;

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
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_Birth)
    TextView textBirth;
    @BindView(R.id.text_height)
    TextView textHeight;
    @BindView(R.id.text_weight)
    TextView textWeight;
    private int cur = 0;
    private Animation loadImageAnimation;
    private MViewOne.OnProgressListener listener;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.personalize_activity);
        ButterKnife.bind(this);

//        handler.post(runnable);
//         loadImageAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_lefttoright);
//        loadImageAnimation.setFillAfter(!loadImageAnimation.getFillAfter());

        listener = new MViewOne.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                num.setText(progress+"%");
                if(progress==100){
                    text.setText(getResources().getText(R.string.personalized));
                    handler.sendEmptyMessage(2);
                }
            }
        };
        circle.setOnProgressListener(listener);
        circle.setValue(100);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Intent intent = new Intent(PersonalizeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransitionEnter(PersonalizeActivity.this);
                    break;
                case 1:
                    num.setText(cur + "%");
                    circle.setProgress((float) cur);
                    switch (cur) {
                        case 10:
//                            textName.setVisibility(View.VISIBLE);
//                            textName.startAnimation(loadImageAnimation);
                            break;
                        case 20:
//                            textSex.setVisibility(View.VISIBLE);
//                            textSex.startAnimation(loadImageAnimation);
                            break;
                        case 40:
//                            textBirth.setVisibility(View.VISIBLE);
//                            textBirth.startAnimation(loadImageAnimation);
                            break;
                        case 60:
//                            textHeight.setVisibility(View.VISIBLE);
//                            textHeight.startAnimation(loadImageAnimation);
                            break;
                        case 80:
//                            textWeight.setVisibility(View.VISIBLE);
//                            textWeight.startAnimation(loadImageAnimation);
                            break;
                        case 100:
//                            Intent intent = new Intent(PersonalizeActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
//                            overridePendingTransitionEnter(PersonalizeActivity.this);
                            break;
                    }
//                    if(cur==100){
//                        Intent intent = new Intent(PersonalizeActivity.this,HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                        overridePendingTransitionEnter(PersonalizeActivity.this);
//                    }
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
                handler.post(this);
            } else {
                text.setText(getResources().getText(R.string.personalized));
            }
        }
    };
}
