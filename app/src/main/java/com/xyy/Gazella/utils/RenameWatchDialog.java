package com.xyy.Gazella.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xyy.Gazella.activity.SettingActivity;
import com.ysp.newband.BaseActivity;
import com.ysp.smartwatch.R;

/**
 * Created by Administrator on 2016/10/24.
 */

public class RenameWatchDialog extends BaseActivity implements View.OnClickListener {
    private TextView cancel;
    private TextView confirm;
    private EditText etName;
    private BleUtils bleUtils;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.rename_watch_dialog);
        setFinishOnTouchOutside(false);

        bleUtils = new BleUtils();

        cancel = (TextView) findViewById(R.id.cancel);
        confirm = (TextView) findViewById(R.id.confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.et_name);
        Write(bleUtils.getDeviceName(),SettingActivity.connectionObservable);
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                if (!etName.getText().toString().equals("")) {
                    Write(bleUtils.setDeviceName(etName.getText().toString()), SettingActivity.connectionObservable);
                    finish();
                }
                break;
        }
    }
}
