package com.wake_e.fragment.station;

import java.util.List;

import com.wake_e.Controller;
import com.wake_e.MainActivity;
import com.wake_e.R;
import com.wake_e.adapt.MeteoAdapter;
import com.wake_e.model.Meteo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PageMeteoFragment extends Fragment {

	View v;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.station, container, false);
		if (v != null){

			TextView title = (TextView) v.findViewById(R.id.title_station);
			title.setText(v.getContext().getString(R.string.meteo));
			title.setTypeface(MainActivity.future);
			
			Controller.getInstance(v.getContext()).getMeteoDeliverer().deliver(this);
		}
		return v;
	}
	
	public void updateView(List<Meteo> weather){
		if (v != null){
			if (weather != null){
				ListView gridview = (ListView) v.findViewById(R.id.content);
			    gridview.setAdapter(new MeteoAdapter(v.getContext(),weather));
			    v.invalidate();
			}
		}
	}
}
