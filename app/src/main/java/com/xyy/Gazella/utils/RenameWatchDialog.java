package com.xyy.Gazella.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/24.
 */

public class RenameWatchDialog extends BaseActivity implements View.OnClickListener {
    private TextView cancel;
    private TextView confirm;
    private EditText etName;
    private BleUtils bleUtils;
    public Observable<RxBleConnection> connectionObservable;

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

        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")){
            bleUtils = new BleUtils();
            connectionObservable=getRxObservable(this);
            Notify(connectionObservable);
            Write(bleUtils.getDeviceName(),connectionObservable);
        }
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        if(bleUtils.returnDeviceName(bytes)!=null){
            etName.setText(bleUtils.returnDeviceName(bytes));
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                if (!etName.getText().toString().equals("")) {
                    if(connectionObservable!=null)
                    Write(bleUtils.setDeviceName(etName.getText().toString()), connectionObservable);
                    finish();
                }
                break;
        }
    }
}
