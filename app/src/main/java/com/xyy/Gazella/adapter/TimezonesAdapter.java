package com.xyy.Gazella.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.Gazella.view.AnalogClock;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */

public class TimezonesAdapter extends BaseAdapter {
    private Context context;
    private List<TimeZonesData> list;
    private LayoutInflater mLayoutInflater;

    public TimezonesAdapter(Context context,List<TimeZonesData> list){
        this.context = context;
        this.list = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null)
            return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHoldler v;
        if (convertView == null) {
            v = new ViewHoldler();
            mLayoutInflater = LayoutInflater.from(context);
            convertView = mLayoutInflater.inflate(R.layout.timezone_item, null);
            v.nameTv = (TextView) convertView.findViewById(R.id.time_name);
            v.zonesTv = (TextView) convertView.findViewById(R.id.time_zones);
            v.analogClock = (AnalogClock) convertView.findViewById(R.id.analogclock);
            v.analogClock.setDialDrawable(R.drawable.zoon_nor);
            v.analogClock.setHourDrawable(R.drawable.hour_small);
            v.analogClock.setMinuteDrawable(R.drawable.minute_small);
            convertView.setTag(v);
        } else {
            v = (ViewHoldler) convertView.getTag();
        }
        if(list.get(position).isClick()){
            v.analogClock.setDialDrawable(R.drawable.zoon_sec);
            v.nameTv.setTextColor(context.getResources().getColor(R.color.title_linear));
            v.zonesTv.setTextColor(context.getResources().getColor(R.color.title_linear));
        }else{
            v.analogClock.setDialDrawable(R.drawable.zoon_nor);
            v.nameTv.setTextColor(context.getResources().getColor(R.color.help_text));
            v.zonesTv.setTextColor(context.getResources().getColor(R.color.help_text));
        }
        v.nameTv.setText(list.get(position).getName());
        v.zonesTv.setText(new SomeUtills().getZonesTime(list.get(position).getGtm()));
        String time = new SomeUtills().getZonesTime(list.get(position).getGtm());
        int hour = Integer.parseInt(time.split(" : ")[0]);
        int min = Integer.parseInt(time.split(" : ")[1]);
        if(hour>12){
            hour=hour-12;
        }
        v.analogClock.setTimeValue(1,hour);
        v.analogClock.setTimeValue(2,min);
        return convertView;
    }

    public static class ViewHoldler {
        TextView nameTv, zonesTv;
        AnalogClock analogClock;
    }
}
