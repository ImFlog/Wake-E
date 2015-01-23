package com.wake_e.fragment.station;

import java.util.List;

import com.wake_e.Controller;
import com.wake_e.MainActivity;
import com.wake_e.R;
import com.wake_e.adapt.MailAdapter;
import com.wake_e.model.Mail;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class PageMailFragment extends Fragment {
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.station, container, false);

		Controller.getInstance(view.getContext()).getMailDeliverer().deliver();
		
		List<Mail> emails = Controller.getInstance(view.getContext()).getMails();
		if (view != null && emails.size() > 0) {
			TextView title = (TextView) view.findViewById(R.id.title_station);
			title.setText(view.getContext().getString(R.string.mail));
			title.setTypeface(MainActivity.future);

			ListView gridview = (ListView) view.findViewById(R.id.content);
			gridview.setAdapter(new MailAdapter(view.getContext(), emails));
		}
		return view;
	}
}
