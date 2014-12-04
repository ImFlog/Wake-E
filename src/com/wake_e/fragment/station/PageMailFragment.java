package com.wake_e.fragment.station;

import java.util.ArrayList;
import java.util.List;

import com.wake_e.R;
import com.wake_e.adapt.MailAdapter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class PageMailFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.station, container, false);
		if (v != null){
			List<String> mails = new ArrayList<String>();
			
			mails.add("Mail 1");
			mails.add("Mail 2");
			mails.add("Mail 3");
			
			TextView title = (TextView) v.findViewById(R.id.title_station);
			title.setText(v.getContext().getString(R.string.mail));
			
			ListView gridview = (ListView) v.findViewById(R.id.content);
		    gridview.setAdapter(new MailAdapter(v.getContext(),mails));
		}
		return v;/*inflater.inflate(R.layout.station, container, false);*/
	}
}
