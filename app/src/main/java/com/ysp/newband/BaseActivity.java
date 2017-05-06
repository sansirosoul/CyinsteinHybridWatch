package com.ysp.newband;


import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.exchange.android.engine.ExchangeProxy;
import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.vise.baseble.ViseBluetooth;
import com.xyy.Gazella.exchange.ExangeErrorHandler;
import com.xyy.Gazella.services.BluetoothService;
import com.xyy.Gazella.utils.CommonDialog;
import com.xyy.Gazella.utils.HexString;
import com.ysp.hybridtwatch.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.xyy.Gazella.services.BluetoothService.STATE_CONNECT_FAILED;


public class BaseActivity extends FragmentActivity {


    private static final String TAG = BaseActivity.class.getName();

    public static Context mContext;
    public final static String serviceUUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String ReadUUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private RxBleDevice bleDevicme;
    public static String mDeviceAddress;

    Handler mActivityHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BluetoothService.STATE_CONNECTED:
                    GazelleApplication.isBleConnected = true;
                    isServicesDiscovered(true);
                    if (dialog!=null&&dialog.isShowing())
                        dialog.dismiss();
                    Logger.e("连接成功");
                    onConnectionState(1);
                    break;
                case BluetoothService.STATE_DISCONNECTED:
                    onConnectionState(2);
                    Logger.e("连接断开");
                    GazelleApplication.isBleConnected = false;
                    break;
                case STATE_CONNECT_FAILED:
                    onConnectionState(0);
                    GazelleApplication.isBleConnected=false;
                    isServicesDiscovered(false);
                    if (dialog!=null&&dialog.isShowing()) {
                        dialog.setllBottomVisibility(View.VISIBLE);
                        dialog.setButOk(View.VISIBLE);
                        dialog.setButAdgin(View.VISIBLE);
                        dialog.setLoadingVisibility(View.GONE);
                        dialog.setTvContext(getResources().getString(R.string.connect_failed_reconnect));
                        dialog.setButOkText(getResources().getString(R.string.reconnect));
                        dialog.setButAdginText(getResources().getString(R.string.cancel_connect));
                        dialog.onButOKListener(new CommonDialog.onButOKListener() {
                            @Override
                            public void onButOKListener() {
                                dialog.dismiss();
                                ViseBluetooth.getInstance().disconnect();
                                ViseBluetooth.getInstance().close();
                                ViseBluetooth.getInstance().clear();
                                connectBLEbyMac(mDeviceAddress);
                            }
                        });
                        dialog.onButAdginListener(new CommonDialog.onButAdginListener() {
                            @Override
                            public void onButAdginListener() {
                                dialog.dismiss();
                            }
                        });
                    }
                    break;
                case BluetoothService.NOTIFY_SUCCESS:
                    if(msg.obj!=null){
                        byte[] bytes = (byte[]) msg.obj;
                        Logger.e("收到数据" + HexString.bytesToHex(bytes));
                        if(bytes!=null&&bytes.length!=0){
                            if (bytes[0] == 0x07 && (bytes[1] & 0xff) == 0x81) {
                                endCall();
                            }
                            onReadReturn(bytes);
                        }
                    }
                    break;
                case BluetoothService.WRITE_SUCCESS:
                    if(msg.obj!=null){
                        byte[] bytes = (byte[]) msg.obj;
                        if(bytes!=null&&bytes.length!=0){
                            onWriteReturn(bytes);
                        }
                    }
                    break;
            }
        }
    };

    public void writeCharacteristic(byte[] bytes) {
        GazelleApplication.mBluetoothService.writeCharacteristic(bytes);
    }

    public void setNotifyCharacteristic() {
        GazelleApplication.mBluetoothService.setNotifyCharacteristic();
    }

    public void isServicesDiscovered(boolean flag) {
    }

    public void connectBLEbyMac(String address) {
        mDeviceAddress = address;
        dialog = new CommonDialog(this);
        dialog.show();
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        if (!blueadapter.isEnabled()) {
            if (dialog.isShowing()) {
                dialog.setllBottomVisibility(View.VISIBLE);
                dialog.setButOk(View.VISIBLE);
                dialog.setButAdgin(View.VISIBLE);
                dialog.setLoadingVisibility(View.GONE);
                dialog.setTvContext(getResources().getString(R.string.is_open_bluetooth));
                dialog.setButOkText(getResources().getString(R.string.cancel_open_bluetooth));
                dialog.setButAdginText(getResources().getString(R.string.open_bluetooth));
                dialog.onButOKListener(new CommonDialog.onButOKListener() {
                    @Override
                    public void onButOKListener() {
                        dialog.dismiss();
                    }
                });
                dialog.onButAdginListener(new CommonDialog.onButAdginListener() {
                    @Override
                    public void onButAdginListener() {
                        dialog.dismiss();
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 10010);
                    }
                });
            }
        } else {
            GazelleApplication.mBluetoothService.connectByAddress(address);
        }
    }

    public void onConnectionState(int state) {

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ExchangeProxy.setApplicationDefaultErrorHandle(new ExangeErrorHandler());// 设置报错处理handler
        ExchangeProxy.setProgressModelVisible(false);// 设置弹出框是否显示
        if (Build.VERSION.SDK_INT >= 19) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mContext = this;
        getTelephony();
        String address = PreferenceData.getAddressValue(this);
        if (address != null && !address.equals(""))
            bleDevicme = GazelleApplication.getRxBleClient(this).getBleDevice(address);
        RxBleClient.setLogLevel(RxBleLog.DEBUG);
        if(GazelleApplication.mBluetoothService!=null)
        GazelleApplication.mBluetoothService.setActivityHandler(mActivityHandler);
    }

    public void setActivityHandler(){
        if(mActivityHandler!=null)
        GazelleApplication.mBluetoothService.setActivityHandler(mActivityHandler);
    }

    private CommonDialog dialog;

    protected void onReadReturn(byte[] bytes) {
    }

    protected void onWriteReturn(byte[] bytes) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10010) {
            if (resultCode == Activity.RESULT_OK) {
                if (dialog.isShowing())
                    dialog.dismiss();
                connectBLEbyMac(mDeviceAddress);
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 回退键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            animfinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void animfinish() {
        mContext = null;
        finish();
        overridePendingTransitionExit(this);
    }

    /***
     * 进入页面调用  从左到右进入
     *
     * @param at
     */
    public static void overridePendingTransitionEnter(Activity at) {
        at.overridePendingTransition(R.anim.in_from_right, R.anim.out_righttoleft);

    }

    /***
     * 退出页面调用 从右到左退出
     *
     * @param at
     */
    public static void overridePendingTransitionExit(Activity at) {
        at.overridePendingTransition(R.anim.in_lefttoright, R.anim.out_to_left);
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message var1) {
            Uoi var2 = (Uoi) var1.getData().getSerializable("INPUT_DATA");
            Uoo var3 = (Uoo) var1.getData().getSerializable("RETURN_DATA");
            BaseActivity.this.callbackByExchange(var2, var3);
        }
    };

    public BaseActivity() {
    }

    public void callbackByExchange(Uoi var1, Uoo var2) {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);

    }

    private Toast result;
    private TextView textView;

    protected void showToatst(Context context, String tvStr) {

        if (result == null) {
            result = new Toast(context);
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.toast, null);
            textView = (TextView) v.findViewById(R.id.tv_context);
            textView.setText(tvStr);
            result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            result.setView(v);
        } else
            textView.setText(tvStr);
        result.show();
    }

    private Object iTelephony;
    // 初始电话实例
    public void getTelephony() {
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            iTelephony = getITelephonyMethod.invoke(telMgr, (Object[]) null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //挂电话
    public void endCall() {
        System.out.println("挂电话>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
            Method endCallmethod = iTelephony.getClass().getDeclaredMethod("endCall");
            endCallmethod.invoke(iTelephony);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
