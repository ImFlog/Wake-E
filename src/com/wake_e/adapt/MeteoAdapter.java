package com.wake_e.adapt;

import java.util.List;

import com.wake_e.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeteoAdapter extends BaseAdapter {
	List<String> meteos;
	LayoutInflater inflater;
    int index;
    ViewHolder holder;

	public MeteoAdapter(Context context,List<String> tasks) {
	    inflater = LayoutInflater.from(context);
	    this.meteos = tasks;
	}
	public void setTasks(List<String> tasks){
		this.meteos = tasks;
	}
	@Override
	public int getCount() {
		return meteos.size();
	}

	@Override
	public Object getItem(int arg0) {
		return meteos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder {
	    TextView subjet;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		


	    if(convertView == null) {
	        holder = new ViewHolder();
	        convertView = inflater.inflate(R.layout.mail, null);

	        holder.subjet 	= (TextView)convertView.findViewById(R.id.mailFrom);
	        holder.subjet.setText(meteos.get(position));

	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    return convertView;
	}

}
