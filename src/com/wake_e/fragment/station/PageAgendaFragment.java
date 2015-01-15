package com.wake_e.fragment.station;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.wake_e.Controller;
import com.wake_e.R;
import com.wake_e.adapt.AgendaAdapter;
import com.wake_e.model.Event;
import com.wake_e.services.deliverers.AgendaDeliverer;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class PageAgendaFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.station, container, false);
		if (v != null) {
			AgendaDeliverer deliverer = Controller.getInstance(Controller.getContext()).getAgendaDeliverer();
			ArrayList<Event> events = deliverer.getEvents();
			TextView title = (TextView) v.findViewById(R.id.title_station);
			title.setText(v.getContext().getString(R.string.agenda));
			ListView gridview = (ListView) v.findViewById(R.id.content);

			gridview.setAdapter(new AgendaAdapter(v.getContext(), events));
			
		}
		return v;
	}
}
