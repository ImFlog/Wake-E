package com.wake_e;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.settings);
		TextView agenda = (TextView) findViewById(R.id.agenda);
		TextView mail = (TextView) findViewById(R.id.mail);
		TextView home = (TextView) findViewById(R.id.home);
		TextView dest = (TextView) findViewById(R.id.destinations);
		TextView slides = (TextView) findViewById(R.id.slides);

		agenda.setOnClickListener(agendaSync);
		mail.setOnClickListener(mailSync);
		home.setOnClickListener(homeSettings);
		dest.setOnClickListener(destSettings);
		slides.setOnClickListener(slideSettings);
	}

	private OnClickListener agendaSync = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener mailSync = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};


	private OnClickListener homeSettings = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};
	private OnClickListener destSettings = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener slideSettings = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};
}
