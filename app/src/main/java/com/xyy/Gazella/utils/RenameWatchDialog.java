package com.xyy.Gazella.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleConnection;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
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
            etName.setSelection(bleUtils.returnDeviceName(bytes).length());
        }else if(HexString.bytesToHex(bytes).equals("0707010F24")){
            finish();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                if (etName.getText().toString().equals("")){
                    Toast.makeText(this,"请输入设备名称!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(etName.getText().toString().length()>13){
                    Toast.makeText(this,"设备名称太长!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(connectionObservable==null) {
                    showToatst(this,"请先连接手表蓝牙");
                    return;
                }
                    Write(bleUtils.setDeviceName(etName.getText().toString()), connectionObservable);
                break;
        }
    }
}
