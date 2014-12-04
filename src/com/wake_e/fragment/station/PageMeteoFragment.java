package com.wake_e.fragment.station;

import java.util.ArrayList;
import java.util.List;

import com.wake_e.R;
import com.wake_e.adapt.MailAdapter;
import com.wake_e.adapt.MeteoAdapter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class PageMeteoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.station, container, false);
		if (v != null){
			List<String> meteos = new ArrayList<String>();
			
			meteos.add("Météo 1");
			meteos.add("Météo 2");

			TextView title = (TextView) v.findViewById(R.id.title_station);
			title.setText(v.getContext().getString(R.string.meteo));
			
			ListView gridview = (ListView) v.findViewById(R.id.content);
		    gridview.setAdapter(new MeteoAdapter(v.getContext(),meteos));
		}
		return v;/*inflater.inflate(R.layout.station, container, false);*/
	}
}
