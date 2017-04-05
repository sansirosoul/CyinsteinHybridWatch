package com.xyy.Gazella.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xyy.Gazella.utils.BleUtils;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ysp.hybridtwatch.R.id.target_run_SeekBar;
import static com.ysp.hybridtwatch.R.id.target_walk_text;

public class TargetActivity extends BaseActivity {

    private static String TAG = TargetActivity.class.getName();

    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.TVTitle)
    TextView TVTitle;
    @BindView(target_run_SeekBar)
    SeekBar targetRunSeekBar;
    @BindView(R.id.target_sleep_SeekBar)
    SeekBar targetSleepSeekBar;
    @BindView(target_walk_text)
    TextView targetWalkText;
    @BindView(R.id.target_sleep_hour_text)
    TextView targetSleepHourText;
    @BindView(R.id.target_sleep_minute_text)
    TextView targetSleepMinuteText;
    @BindView(R.id.but_save)
    Button butSave;
    @BindView(R.id.but_default)
    Button butDefault;

    private int max, hours, min;
    private BleUtils bleUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        ButterKnife.bind(this);
        initView();
        if(GazelleApplication.isBleConnected){
            setNotifyCharacteristic();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    writeCharacteristic(bleUtils.getStepTarget());
                }
            },300);
        }
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        if(bytes!=null&&bytes.length!=0){
            if(bytes[0]==0x07&&bytes[1]==0x28&&bytes[2]==0x01){
                showToatst(TargetActivity.this,getResources().getString(R.string.save_success));
                max = targetSleepSeekBar.getProgress() * 30;
                hours = max / 60;
                min = max % 60;
                PreferenceData.setTargetRunValue(TargetActivity.this,targetRunSeekBar.getProgress()*100);
                PreferenceData.setTargetSleepSeekBarValue(TargetActivity.this,targetSleepSeekBar.getProgress());
                PreferenceData.setTargetSleepHourValue(TargetActivity.this,hours);
                PreferenceData.setTargetSleepMinuteValue(TargetActivity.this,min);
            }else if(bleUtils.returnStepTarget(bytes)!=-1){
                int target = bleUtils.returnStepTarget(bytes);
                PreferenceData.setTargetRunValue(this,target);
                targetRunSeekBar.setProgress(target/100);
                targetWalkText.setText(String.valueOf(target)+ getResources().getString(R.string.step_num));
            }
        }
    }

    private void initView() {
        bleUtils = new BleUtils();
        TVTitle.setText(getResources().getString(R.string.sport_sleep_target));
        setDefault();
//        targetRunSeekBar.setProgress(PreferenceData.getTargetRunValue(TargetActivity.this)/100);
        targetSleepSeekBar.setProgress(PreferenceData.getTargetSleepSeekBarValue(TargetActivity.this));
        targetSleepHourText.setText(String.valueOf(PreferenceData.getTargetSleepHourValue(TargetActivity.this)));
        targetSleepMinuteText.setText(String.valueOf(PreferenceData.getTargetSleepMinuteValue(TargetActivity.this)));
//        targetWalkText.setText(String.valueOf(PreferenceData.getTargetRunValue(TargetActivity.this))+ getResources().getString(R.string.step_num));
        targetRunSeekBar.setOnSeekBarChangeListener(new monlistener());
        targetSleepSeekBar.setOnSeekBarChangeListener(new monlistener());

    }

    @OnClick({R.id.btnExit, R.id.btnOpt, R.id.TVTitle,R.id.but_save, R.id.but_default})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                TargetActivity.this.finish();
                overridePendingTransitionExit(TargetActivity.this);
                break;
            case R.id.btnOpt:
                break;
            case R.id.TVTitle:
                break;
            case R.id.but_save:
                writeCharacteristic(bleUtils.setStepTarget(targetRunSeekBar.getProgress()*100));
                break;
            case R.id.but_default:
                setDefault();
                break;
        }
    }

    private void setDefault() {
        targetRunSeekBar.setProgress(100);
        targetSleepSeekBar.setProgress(16);
        hours = 8;
        min = 0;
        targetWalkText.setText(targetRunSeekBar.getProgress() * 100
                +getResources().getString(R.string.step_num));
        targetSleepHourText.setText(String.valueOf(hours));
        targetSleepMinuteText.setText(String.valueOf(min));
    }


    private class monlistener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // TODO Auto-generated method stub
            if (fromUser) {
                switch (seekBar.getId()) {
                    case target_run_SeekBar:
                        if (progress != 0) {
                            targetWalkText.setText(progress * 100 + getResources().getString(R.string.step_num));
                        } else
                            targetWalkText.setText(progress * 100 + getResources().getString(R.string.step_num));
                        break;
                    case R.id.target_sleep_SeekBar:
                        max = progress * 30;
                        hours = max / 60;
                        min = max % 60;
                        targetSleepHourText.setText(String.valueOf(hours));
                        targetSleepMinuteText.setText(String.valueOf(min));
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
