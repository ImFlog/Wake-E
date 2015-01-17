package com.wake_e;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.wake_e.fragment.station.PageAgendaFragment;
import com.wake_e.fragment.station.PageMailFragment;
import com.wake_e.fragment.station.PageMeteoFragment;
import com.wake_e.model.Location;
import com.wake_e.model.Slide;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private ImageView [] slides;
	private TextView cancel;
	private TextView save;
	private int size;
	public static SettingsActivity that;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		that = this;
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.settings);

		//
		Display ecran = getWindowManager().getDefaultDisplay(); 
		int largeur= ecran.getWidth();
		size = getWindowManager().getDefaultDisplay().getWidth() / 4;

		slides = new ImageView[4];

		slides[0] = (ImageView)findViewById(R.id.agenda);
		slides[1] = (ImageView)findViewById(R.id.mail);
		slides[2] = (ImageView)findViewById(R.id.meteo);
		slides[3] = (ImageView)findViewById(R.id.traffic);

		List<Slide> dbSlide = Controller.getInstance(this).getSlides();
		for (Slide s: dbSlide) {
			Log.i(s.getSlideName(), s.getOrder().toString());
			if (s.getSlideName().equals("Agenda")) {
				slides[0].setLayoutParams(
						new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(s.getOrder())));
			} else if (s.getSlideName().equals("Mail")) {
				slides[1].setLayoutParams(
						new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(s.getOrder())));
			} else if (s.getSlideName().equals("Météo")) {
				slides[2].setLayoutParams(
						new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(s.getOrder())));
			}
		}

		slides[0].setOnTouchListener(touchListenerBouton2);
		slides[1].setOnTouchListener(touchListenerBouton2);
		slides[2].setOnTouchListener(touchListenerBouton2);
		slides[3].setOnTouchListener(touchListenerBouton2);

		save = (TextView) this.findViewById(R.id.save);
		cancel = (TextView) this.findViewById(R.id.cancel);
		save.setOnClickListener(onSaveClick);
		cancel.setOnClickListener(onCancelClick);

		// ######## ACCOUNTS #########
		ListView comptes	= (ListView)findViewById(R.id.l_comptes);
		TextView addAccount = (TextView)findViewById(R.id.addAccount);
		if (Controller.getInstance(this).getCredentials() != null) {
			addAccount.setCompoundDrawables(null, null, null, null);
			addAccount.setText("Gmail");
		} else {
			// Add account listener
			addAccount.setOnClickListener(credentialStart);
		}

		// ####### LOCATIONS ########
		ListView locations	= (ListView)findViewById(R.id.l_locations);
		TextView addLocation = (TextView)findViewById(R.id.Addlocalisation);
		addLocation.setOnClickListener(locationStart);

		ListView sounds		= (ListView)findViewById(R.id.l_sounds);
	}

	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		for (int i = 0; i < 4; i++){
			slides[i].setX(size * i);
		}
	}
	private OnTouchListener touchListenerBouton2 = new View.OnTouchListener() {
		/**
		 * Old Value
		 */
		private float xx = 0 ;
		private int selection = 0;
		private ImageView tmp;


		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				xx = event.getX();
				selection = (int) Math.floor((xx + v.getX()) / size);
				v.bringToFront();
				break;
			case MotionEvent.ACTION_MOVE:

				//Plus
				if (event.getX() + v.getX() > (selection+1) * size){
					if (selection != slides.length){
						tmp = slides[selection];
						slides[selection] = slides[selection+1];
						slides[selection+1] = tmp;
						selection ++;
					}
				}
				//Moins
				if (event.getX() + v.getX() < selection * size){
					if (selection != 0)
						tmp = slides[selection];
					slides[selection] = slides[selection-1];
					slides[selection-1] = tmp;
					selection --;
				}

				v.setX(v.getX() - xx + event.getX());
				if (v.getX() < 0){v.setY(0);}

				break;
			case MotionEvent.ACTION_UP:

				for (int i = 0; i < 4; i++){
					slides[i].setX(size * i);
				}
				break;
			}
			return true;
		}
	};

	private OnClickListener credentialStart = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), CredentialActivity.class);
			i.putExtra("type", "gmail");
			startActivity(i);
		}
	};

	private OnClickListener locationStart = new OnClickListener() {

		@Override
		public void onClick(View v) {
			final Dialog dialog = new Dialog(SettingsActivity.this);
			dialog.setContentView(R.layout.location);

			final EditText locationName = (EditText)dialog.findViewById(R.id.locationName);
			final EditText locationAddress = (EditText)dialog.findViewById(R.id.locationAddress);
			TextView createLoc = (TextView)dialog.findViewById(R.id.create);

			createLoc.setOnClickListener(new OnClickListener() {
				Location l = null;
				boolean hasError = false;

				@Override
				public void onClick(View v) {
					try {
						l = Controller.getInstance(v.getContext()).createLocation(
								locationName.getText().toString(),
								locationAddress.getText().toString());
					} catch (IOException e) {
						hasError = true;
					}
					if (hasError || l == null) {
						Toast.makeText(
								that,
								"Erreur lors de la création de la localisation",
								Toast.LENGTH_LONG).show();
					}
					dialog.cancel();
				}
			});

			dialog.show();

		}
	};

	private OnClickListener onSaveClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			//SAVE
			List<Slide> slideList = new ArrayList<Slide>();
			int order;
			String className;
			String name;
			boolean visible = true;

			int position = 0;

			for(ImageView view: slides) {
				name = null;
				className = null;
				order = position;
				visible = true;
				switch(view.getId()) {
				case(R.id.meteo) :
					name = "Météo";
				className = PageMeteoFragment.class.getName();
				break;
				case(R.id.mail) :
					name = "Mail";
				className = PageMailFragment.class.getName();
				break;
				case(R.id.agenda):
					name = "Agenda";
				className = PageAgendaFragment.class.getName();
				break;
				}
				if (name != null) {
					slideList.add(new Slide(name, className, order, visible));
					position++;
				}
			}
			Controller.getInstance(that).updateSlides(slideList);
			finish();
		}
	};


	private OnClickListener onCancelClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			//CANCEL
			finish();
		}
	};

}
