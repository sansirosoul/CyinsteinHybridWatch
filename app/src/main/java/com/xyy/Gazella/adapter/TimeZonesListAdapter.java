package com.xyy.Gazella.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xyy.Gazella.utils.SomeUtills;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class TimeZonesListAdapter extends BaseAdapter {

    private Context context;
    private List<TimeZonesData> list;
    private LayoutInflater mLayoutInflater;

    public TimeZonesListAdapter(Context context, List<TimeZonesData> list) {
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHoldler v;
        if (convertView == null) {
            v = new ViewHoldler();
            mLayoutInflater = LayoutInflater.from(context);
            convertView = mLayoutInflater.inflate(R.layout.item_time_zones_list, null);
            v.nameTv = (TextView) convertView.findViewById(R.id.time_name);
            v.zonesTv = (TextView) convertView.findViewById(R.id.time_zones);
            convertView.setTag(v);
        } else {
            v = (ViewHoldler) convertView.getTag();
        }
        v.nameTv.setText(list.get(position).getName());
        v.zonesTv.setText(new SomeUtills().getZonesTime(list.get(position).getGtm()));

        if(list.get(position).isClick()){
            v.nameTv.setTextColor(context.getResources().getColor(R.color.title_linear));
            v.zonesTv.setTextColor(context.getResources().getColor(R.color.title_linear));
        }else {
            v.nameTv.setTextColor(context.getResources().getColor(R.color.white));
            v.zonesTv.setTextColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    public static class ViewHoldler {
        TextView nameTv, zonesTv;
    }

}
