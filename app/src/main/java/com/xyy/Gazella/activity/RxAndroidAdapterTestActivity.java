package com.xyy.Gazella.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.polidea.rxandroidble.RxBleDeviceServices;
import com.ysp.smartwatch.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public class RxAndroidAdapterTestActivity extends BaseAdapter {

    private static  String TAG=RxAndroidAdapterTestActivity.class.getName();

    private Context context;
    private List<RxBleDeviceServices> dataList;
    private LayoutInflater mLayoutInflater;


    public RxAndroidAdapterTestActivity(Context context, List<RxBleDeviceServices> deviceList) {
        this.context = context;
        this.dataList = deviceList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (dataList == null)
            return null;
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
       ViewHoldler v;
        if (convertView == null) {
            v = new ViewHoldler();
            mLayoutInflater = LayoutInflater.from(context);
            convertView = mLayoutInflater.inflate(R.layout.device_list_item, null);
            v.Tv1 = (TextView) convertView.findViewById(R.id.tv_1);
            v.Tv2 = (TextView) convertView.findViewById(R.id.tv_2);
            v.Tv3 = (TextView) convertView.findViewById(R.id.tv_3);
            v.Tv4 = (TextView) convertView.findViewById(R.id.tv_4);
            v.Tv5 = (TextView) convertView.findViewById(R.id.tv_5);

            convertView.setTag(v);
        } else {
            v = (ViewHoldler) convertView.getTag();
        }

//        for (int i=0;i<dataList.get(position).getBluetoothGattServices().size();i++) {
//            Logger.t(TAG).e("UUID"+String.valueOf(dataList.get(i).getBluetoothGattServices().get(i).getUuid())+"\n"+
//                                     "TYPE"+String.valueOf(dataList.get(i).getBluetoothGattServices().get(i).getType())+"\n");
//        }


        v.Tv1.setText(String.valueOf(dataList.get(position).getBluetoothGattServices().size()));

        return convertView;
    }


    public static class ViewHoldler {

        TextView Tv1, Tv2,Tv3,Tv4,Tv5;
    }


}
