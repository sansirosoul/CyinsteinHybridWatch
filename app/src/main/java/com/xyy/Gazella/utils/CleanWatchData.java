package com.xyy.Gazella.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleConnection;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;
import com.ysp.smartwatch.R;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/24.
 */

public class CleanWatchData extends BaseActivity implements View.OnClickListener {
    private TextView cancel;
    private TextView confirm;
    private BleUtils bleUtils;
    public Observable<RxBleConnection> connectionObservable;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.clean_watch_data);
        setFinishOnTouchOutside(false);

        cancel = (TextView) findViewById(R.id.cancel);
        confirm = (TextView) findViewById(R.id.confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")){
            bleUtils = new BleUtils();
            connectionObservable=getRxObservable(this);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                if(connectionObservable!=null)
                Write(bleUtils.eraseWatchData(), connectionObservable);
                finish();
                break;
        }
    }
}
