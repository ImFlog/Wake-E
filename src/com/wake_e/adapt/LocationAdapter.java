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

public class LocationAdapter extends BaseAdapter {
	List<Location> locations;
	LayoutInflater inflater;
	int index;
	ViewHolder holder;

	public LocationAdapter(Context context, List<Location> locations) {
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
		if(convertView==null){
			// inflate the layout
			convertView = inflater.inflate(R.layout.location_list, null);

			// well set up the ViewHolder
			holder = new ViewHolder();
			holder.name = (TextView)convertView.findViewById(R.id.locationName);
			holder.city = (TextView)convertView.findViewById(R.id.locationCity);

			// store the holder with the view.
			convertView.setTag(holder);

		}else{
			// we've just avoided calling findViewById() on resource everytime
			// just use the viewHolder
			holder = (ViewHolder) convertView.getTag();
		}

		// object item based on the position
		Location currLoc= locations.get(position);

		// assign values if the object is not null
		if(currLoc != null) {
			// get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
			holder.name.setText(currLoc.getName());
			holder.city.setText(currLoc.getCity());
		}

		return convertView;
	}
}
