package com.xyy.Gazella.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xyy.model.Clock;
import com.ysp.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ClockListAdapter extends BaseAdapter {
    private List<Clock> clocks = new ArrayList<>();
    private Context context;

    public ClockListAdapter(Context context,List<Clock> clocks){
        this.context=context;
        this.clocks=clocks;
    }

    @Override
    public int getCount() {
        return 4;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoldler v;
        if (convertView == null) {
            v = new ViewHoldler();
            convertView= LayoutInflater.from(context).inflate(R.layout.clock_list_item,null);
            v.time= (TextView) convertView.findViewById(R.id.time);
            v.rate=(TextView) convertView.findViewById(R.id.rate);
            v.del= (ImageView) convertView.findViewById(R.id.del);
            v.tgBtn=(ToggleButton) convertView.findViewById(R.id.tg_btn);

            v.tgBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        v.time.setTextColor(context.getResources().getColor(R.color.white));
                    }else{
                        v.time.setTextColor(context.getResources().getColor(R.color.clock_list_gray));
                    }
                }
            });

            convertView.setTag(v);
        }else{
            v= (ViewHoldler) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHoldler {
        TextView time,rate;
        ImageView del;
        ToggleButton tgBtn;
    }
}
