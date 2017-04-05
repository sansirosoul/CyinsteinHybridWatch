package com.xyy.Gazella.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.activity.SettingActivity;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.GazelleApplication;
import com.ysp.newband.PreferenceData;

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
        if(PreferenceData.getDeviceName(this)!=null||!PreferenceData.getDeviceName(this).equals("")){
            etName.setText(PreferenceData.getDeviceName(this));
            etName.setSelection(PreferenceData.getDeviceName(this).length());
        }

        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals("")) {
            bleUtils = new BleUtils();
            if(GazelleApplication.isBleConnected){
                setNotifyCharacteristic();
            }
//            connectionObservable = getRxObservable(this);
//            Notify(connectionObservable);
//            Write(bleUtils.getDeviceName(), connectionObservable);
        }
    }

    @Override
    protected void onReadReturn(byte[] bytes) {
        super.onReadReturn(bytes);
        if (bleUtils.returnDeviceName(bytes) != null) {
            etName.setText(bleUtils.returnDeviceName(bytes));
            etName.setSelection(bleUtils.returnDeviceName(bytes).length());
        } else if (HexString.bytesToHex(bytes).equals("0707010F24")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PreferenceData.setDeviceName(RenameWatchDialog.this,etName.getText().toString());
                    Toast.makeText(RenameWatchDialog.this,getResources().getString(R.string.rename_success),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RenameWatchDialog.this, SettingActivity.class);
                    startActivity(intent);
                    finish();
                }
            },300);
        }else if(bytes[0]==0x07&&bytes[1]==0x07&&bytes[2]==0x00){
            Toast.makeText(this,getResources().getString(R.string.rename_failed),Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                if (etName.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.input_device_name), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etName.getText().toString().length() > 13) {
                    Toast.makeText(this, getResources().getString(R.string.device_long), Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (connectionObservable == null) {
//                    showToatst(this, "请先连接手表蓝牙");
//                    return;
//                }
                GazelleApplication.isNormalDisconnet = true;
                if(GazelleApplication.isBleConnected)
                    writeCharacteristic(bleUtils.setDeviceName(etName.getText().toString()));
//                Write(bleUtils.setDeviceName(etName.getText().toString()), connectionObservable);
                break;
        }
    }
}
