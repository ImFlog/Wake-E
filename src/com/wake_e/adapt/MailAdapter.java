package com.wake_e.adapt;

import java.util.List;

import com.wake_e.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MailAdapter extends BaseAdapter {
	List<String> mails;
	LayoutInflater inflater;
    int index;
    ViewHolder holder;

	public MailAdapter(Context context,List<String> tasks) {
	    inflater = LayoutInflater.from(context);
	    this.mails = tasks;
	}
	public void setTasks(List<String> tasks){
		this.mails = tasks;
	}
	@Override
	public int getCount() {
		return mails.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mails.get(arg0);
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
	        holder.subjet.setText(mails.get(position));

	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    return convertView;
	}

}
