package com.wake_e.fragment.station;

import java.util.List;

import com.wake_e.Controller;
import com.wake_e.CredentialActivity;
import com.wake_e.MainActivity;
import com.wake_e.R;
import com.wake_e.adapt.MailAdapter;
import com.wake_e.model.Mail;
import com.wake_e.services.deliverers.MailDeliverer;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class PageMailFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		View v = inflater.inflate(R.layout.station, container, false);

		// REMOVE THIS IN PROD, FOR TEST PURPOSE
		/*MailDeliverer deliverer = Controller.getInstance(v.getContext()).getMailDeliverer();
		try {
			deliverer.deliver();
		} catch (Exception e) {
			Intent intent = new Intent(v.getContext(), CredentialActivity.class);
			intent.putExtra("type", "gmail");
			startActivity(intent);
		}*/

		List<Mail> emails = Controller.getInstance(v.getContext()).getMails();
		if (v != null && emails.size() > 0) {
			TextView title = (TextView) v.findViewById(R.id.title_station);
			title.setText(v.getContext().getString(R.string.mail));
			title.setTypeface(MainActivity.future);

			ListView gridview = (ListView) v.findViewById(R.id.content);
			gridview.setAdapter(new MailAdapter(v.getContext(), emails));
		}
		return v;
	}
}
