package com.xyy.Gazella.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.orhanobut.logger.Logger;
import com.polidea.rxandroidble.RxBleConnection;
import com.xyy.Gazella.utils.BleUtils;
import com.xyy.Gazella.utils.DelClockDialog;
import com.xyy.Gazella.utils.HexString;
import com.xyy.model.Clock;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockListAdapter extends BaseAdapter {
    private List<Clock> clocks = new ArrayList<>();
    private Context context;
    public final static String WriteUUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    private Observable<RxBleConnection> connectionObservable;

    public ClockListAdapter(Context context,List<Clock> clocks){
        this.context=context;
        this.clocks=clocks;
    }

    @Override
    public int getCount() {
        return clocks.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHoldler v ;
        Clock clock = clocks.get(position);
        if (convertView == null) {
            v = new ViewHoldler();
            convertView= LayoutInflater.from(context).inflate(R.layout.clock_list_item,null);
            v.time= (TextView) convertView.findViewById(R.id.time);
            v.rate=(TextView) convertView.findViewById(R.id.rate);
            v.del= (RelativeLayout) convertView.findViewById(R.id.del);
            v.tgBtn=(ToggleButton) convertView.findViewById(R.id.tg_btn);
            convertView.setTag(v);
        }else{
            v= (ViewHoldler) convertView.getTag();
        }

        v.time.setText(clock.getTime());
        v.rate.setText(clock.getRate());

        if (clock.getIsOpen()==0){
            v.time.setTextColor(context.getResources().getColor(R.color.clock_list_gray));
            v.tgBtn.setChecked(false);
        }else if(clock.getIsOpen()==1){
            v.time.setTextColor(context.getResources().getColor(R.color.white));
            v.tgBtn.setChecked(true);
        }

        String[] ss = clock.getTime().split(":");
        String hour=ss[0];
        String minute=ss[1];

        v.tgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(v.tgBtn.isChecked()){
                    String address = PreferenceData.getAddressValue(context);
                    if (address != null && !address.equals("")) {
                        BleUtils bleUtils = new BleUtils();
                        connectionObservable = BaseActivity.getRxObservable(context);
                        if(Clock.transformRate(clock.getRate())!=5){
                            Write(bleUtils.setWatchAlarm(1,clock.getId(),Integer.parseInt(hour), Integer.parseInt(minute),
                                    Clock.transformSnoozeTime(clock.getSnoozeTime()),Clock.transformRate(clock.getRate()),"00000000",1),connectionObservable);
                        }else{
                            Write(bleUtils.setWatchAlarm(1,clock.getId(),Integer.parseInt(hour), Integer.parseInt(minute),
                                    Clock.transformSnoozeTime(clock.getSnoozeTime()),5,clock.getCustom(),1),connectionObservable);
                        }
                    }
                    clock.setIsOpen(1);
                    v.time.setTextColor(context.getResources().getColor(R.color.white));
                }else{
                    String address = PreferenceData.getAddressValue(context);
                    if (address != null && !address.equals("")) {
                        BleUtils bleUtils = new BleUtils();
                        connectionObservable = BaseActivity.getRxObservable(context);
                        if(Clock.transformRate(clock.getRate())!=5){
                            Write(bleUtils.setWatchAlarm(1,clock.getId(),Integer.parseInt(hour), Integer.parseInt(minute),
                                    Clock.transformSnoozeTime(clock.getSnoozeTime()),Clock.transformRate(clock.getRate()),"00000000",0),connectionObservable);
                        }else{
                            Write(bleUtils.setWatchAlarm(1,clock.getId(),Integer.parseInt(hour), Integer.parseInt(minute),
                                    Clock.transformSnoozeTime(clock.getSnoozeTime()),5,clock.getCustom(),0),connectionObservable);
                        }
                    }
                    clock.setIsOpen(0);
                    v.time.setTextColor(context.getResources().getColor(R.color.clock_list_gray));
                }
            }
        });

        final DelClockDialog.OnClickListener onClickListener = new DelClockDialog.OnClickListener() {
            @Override
            public void isDel() {
                String address = PreferenceData.getAddressValue(context);
                if (address != null && !address.equals("")) {
                    BleUtils bleUtils = new BleUtils();
                    connectionObservable = BaseActivity.getRxObservable(context);
                    Write(bleUtils.setWatchAlarm(0,clock.getId(),Integer.parseInt(hour), Integer.parseInt(minute),
                            Clock.transformSnoozeTime(clock.getSnoozeTime()),Clock.transformRate(clock.getRate()),"00000000",0),connectionObservable);
                }
                clocks.remove(position);
                notifyDataSetChanged();
            }
        };
        v.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelClockDialog dialog = new DelClockDialog(context);
                dialog.setOnClickListener(onClickListener);
                dialog.show();
            }
        });


        return convertView;
    }

    public class ViewHoldler {
        TextView time,rate;
        RelativeLayout del;
        ToggleButton tgBtn;

    }

    private Observable<byte[]> WiterCharacteristic(String writeString, Observable<RxBleConnection> connectionObservable) {
        return connectionObservable
                .flatMap(new Func1<RxBleConnection, Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(RxBleConnection rxBleConnection) {
                        return rxBleConnection.writeCharacteristic(UUID.fromString(WriteUUID), HexString.hexToBytes(writeString));
                    }
                });

    }

    protected void Write(byte[] bytes, Observable<RxBleConnection> connectionObservable) {
        WiterCharacteristic(HexString.bytesToHex(bytes), connectionObservable).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        Logger.t("TAG").e("写入数据  >>>>>>  " + HexString.bytesToHex(bytes));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.t("TAG").e("写入数据失败  >>>>>>   " + throwable.toString());
                    }
                });


//        connectionObservable
//                .flatMap(new Func1<RxBleConnection, Observable<Observable<byte[]>>>() {
//                    @Override
//                    public Observable<Observable<byte[]>> call(RxBleConnection rxBleConnection) {
//                        return rxBleConnection.setupNotification(UUID.fromString(ReadUUID));
//                    }
//                }).doOnNext(new Action1<Observable<byte[]>>() {
//            @Override
//            public void call(Observable<byte[]> observable) {
//                Logger.t(TAG).e("开始接收通知  >>>>>>  ");
//
//                WiterCharacteristic(HexString.bytesToHex(bytes), connectionObservable).observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<byte[]>() {
//                            @Override
//                            public void call(byte[] bytes) {
//                                Logger.t(TAG).e("写入数据  >>>>>>  " + HexString.bytesToHex(bytes));
//                                onWriteReturn(type,bytes);
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                Logger.t(TAG).e("写入数据失败  >>>>>>   " + throwable.toString());
//                            }
//                        });
//            }
//        }).flatMap(new Func1<Observable<byte[]>, Observable<byte[]>>() {
//            @Override
//            public Observable<byte[]> call(Observable<byte[]> notificationObservable) {
//                return notificationObservable;
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<byte[]>() {
//            @Override
//            public void call(byte[] bytes) {
//                Logger.t(TAG).e("接收数据  >>>>>>  " + HexString.bytesToHex(bytes) + "\n" + ">>>>>>>>" + new String(bytes));
//                onReadReturn(type, bytes);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                Logger.t(TAG).e("接收数据失败 >>>>>>  " + throwable.toString());
//            }
//        });
    }
}
