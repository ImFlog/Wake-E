package com.wake_e.adapt;

import java.util.List;
import com.wake_e.model.Location;
import com.wake_e.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ComptesAdapter extends BaseAdapter{

	List<Location> locations;
	LayoutInflater inflater;
	int index;
	ViewHolder holder;

	public ComptesAdapter(Context context, List<Location> locations) {
		super();
		inflater = LayoutInflater.from(context);
		this.locations = locations;
	}

	@Override
	public int getCount() {
		return locations.size();
	}

	@Override
	public Object getItem(int arg0) {
		return locations.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder {
		TextView name;
		TextView city;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.location_list, null);
		}

		holder.name = (TextView)convertView.findViewById(R.id.locationName);
		holder.name.setText(locations.get(position).getName());

		holder.city = (TextView)convertView.findViewById(R.id.locationCity);
		holder.city.setText(locations.get(position).getCity());

		convertView.setTag(holder);
		holder = (ViewHolder) convertView.getTag();
		return convertView;
	}
}
