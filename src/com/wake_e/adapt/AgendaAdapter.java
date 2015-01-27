package com.wake_e.adapt;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.wake_e.MainActivity;
import com.wake_e.R;
import com.wake_e.model.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AgendaAdapter extends BaseAdapter {
	ArrayList<Event> agenda;
	LayoutInflater inflater;
	int index;
	ViewHolder holder;

	public AgendaAdapter(Context context, ArrayList<Event> tasks) {
		inflater = LayoutInflater.from(context);
		this.agenda = tasks;
	}

	public void setTasks(ArrayList<Event> tasks) {
		this.agenda = tasks;
	}

	@Override
	public boolean isEnabled(int position) {
	    return false;
	}
	
	@Override
	public int getCount() {
		return agenda.size();
	}

	@Override
	public Object getItem(int arg0) {	
		return agenda.get(arg0);
	}
	
	public Event getEvent(int arg0) {	
		return agenda.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return agenda.get(arg0).getId();
	}

	private class ViewHolder {
		TextView subject;
		TextView location;
		TextView hours;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.agenda, null);

			Event e = (Event) this.getEvent(position);
			holder = new ViewHolder();
			
			// Subject
			holder.subject = (TextView) convertView.findViewById(R.id.agendaSubjet);
			holder.subject.setText(e.getName());
			
			// Hours.
			holder.hours = (TextView) convertView.findViewById(R.id.agendaHeure);
			Date d = e.getBegin();
			Calendar c = Calendar.getInstance();
			DecimalFormat df = new DecimalFormat();
			df.applyPattern("00");
			c.setTime(d);
			String h = c.get(Calendar.HOUR_OF_DAY)+"h"+df.format(c.get(Calendar.MINUTE))+" - ";
			d = e.getEnd();
			c.setTime(d);
			h = h+c.get(Calendar.HOUR_OF_DAY)+"h"+df.format(c.get(Calendar.MINUTE));
			holder.hours.setText(h);
			
			// Location.
			holder.location = (TextView) convertView.findViewById(R.id.agendaLieu);
			holder.location.setText(e.getLocation());
			
			holder.location.setTypeface(MainActivity.future);
			holder.subject.setTypeface(MainActivity.future);
			holder.hours.setTypeface(MainActivity.future);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

}
