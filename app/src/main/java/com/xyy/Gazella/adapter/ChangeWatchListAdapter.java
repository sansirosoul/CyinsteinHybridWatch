package com.xyy.Gazella.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ysp.hybridtwatch.R;

import java.util.List;

public class ChangeWatchListAdapter extends BaseAdapter {

	private Context context;
	private List<BluetoothDevice> dataList;
	private LayoutInflater mLayoutInflater;

	public ChangeWatchListAdapter(Context context, List<BluetoothDevice> deviceList ){
		this.context=context;
		this.dataList=deviceList;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoldler v;
		if (convertView == null) {
			v = new ViewHoldler();
			mLayoutInflater = LayoutInflater.from(context);
			convertView = mLayoutInflater.inflate(R.layout.change_watch_list_item,null);
			v.nameTv=(TextView) convertView.findViewById(R.id.nameTV);
			v.ressTv=(TextView) convertView.findViewById(R.id.ressTv);

			convertView.setTag(v);
		} else {
			v = (ViewHoldler) convertView.getTag();
		}

	    v.nameTv.setText(dataList.get(position).getName());
        v.ressTv.setText(dataList.get(position).getAddress());
		return convertView;
	}

	
	public static class ViewHoldler {

		TextView nameTv,ressTv;
	}
}
