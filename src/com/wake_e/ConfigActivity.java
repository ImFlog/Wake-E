package com.wake_e;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wake_e.constants.WakeEConstants;
import com.wake_e.exceptions.NoRouteFoundException;
import com.wake_e.model.Location;

public class ConfigActivity extends Activity {


	TextView mdepart;
	TextView mpreparation;
	TextView mheureArrivee;
	TextView mlieuArrivee;

	ImageView mvelo;
	ImageView mvoiture;
	ImageView mtrain;


	private TextView cancel;
	private TextView save;

	//DATA
	int preparation;
	int heureArrivee;
	Location depart;
	Location arrivee;
	String transport;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);

		preparation = 0;
		heureArrivee = 0;
		depart = null;
		arrivee = null;
		transport = null;

		mdepart = (TextView) this.findViewById(R.id.depart);
		mpreparation = (TextView) this.findViewById(R.id.TextView02);
		mheureArrivee = (TextView) this.findViewById(R.id.TextView01);
		mlieuArrivee = (TextView) this.findViewById(R.id.textView2);

		mvelo = (ImageView) this.findViewById(R.id.imageView3);
		mvoiture = (ImageView) this.findViewById(R.id.imageView4);
		mtrain = (ImageView) this.findViewById(R.id.imageView2);

		save = (TextView) this.findViewById(R.id.save);
		cancel = (TextView) this.findViewById(R.id.cancel);
		save.setOnClickListener(onSaveClick);
		cancel.setOnClickListener(onCancelClick);

		mdepart.setOnClickListener(setDepartLocation);

		mpreparation.setOnClickListener(setPreparation);

		mheureArrivee.setOnClickListener(setArriveeTime);

		mlieuArrivee.setOnClickListener(setArriveLocation);

		mvelo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				transport = WakeEConstants.Transports.TRANSPORT_VELO;
				mvelo.setImageResource(R.drawable.velo);
				mtrain.setImageResource(R.drawable.utrain);
				mvoiture.setImageResource(R.drawable.uvoiture);
			}
		});
		mvoiture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				transport = WakeEConstants.Transports.TRANSPORT_VOITURE;
				mvelo.setImageResource(R.drawable.uvelo);
				mtrain.setImageResource(R.drawable.utrain);
				mvoiture.setImageResource(R.drawable.voiture);
			}
		});
		mtrain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				transport = WakeEConstants.Transports.TRANSPORT_COMMUN;
				mvelo.setImageResource(R.drawable.uvelo);
				mtrain.setImageResource(R.drawable.train);
				mvoiture.setImageResource(R.drawable.uvoiture);
			}
		});

	}

	OnClickListener setDepartLocation = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			final Dialog dialog = new Dialog(ConfigActivity.this);
			dialog.setContentView(R.layout.list);

			ListView lv = (ListView ) dialog.findViewById(R.id.ListView1);
			dialog.setCancelable(true);
			dialog.setTitle("Départ");

			final List<String> listeLoc = Controller.getInstance(v.getContext()).getLocationNames();
			lv.setAdapter(new ArrayAdapter<String>(
					ConfigActivity.this, android.R.layout.simple_list_item_1,listeLoc));

			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					depart = Controller.getInstance(view.getContext()).getLocation(listeLoc.get(position));
					dialog.dismiss();
					((TextView)v).append(": " + depart.getName());
				}
			});
			dialog.show();
		}
	};

	OnClickListener setArriveLocation = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			final Dialog dialog = new Dialog(ConfigActivity.this);
			dialog.setContentView(R.layout.list);

			ListView lv = (ListView ) dialog.findViewById(R.id.ListView1);
			dialog.setCancelable(true);
			dialog.setTitle("Arrivée");

			final List<String> listeLoc = Controller.getInstance(v.getContext()).getLocationNames();
			lv.setAdapter(new ArrayAdapter<String>(
					ConfigActivity.this, android.R.layout.simple_list_item_1,listeLoc));

			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					arrivee = Controller.getInstance(view.getContext()).getLocation(listeLoc.get(position));
					dialog.dismiss();
					((TextView)v).append(": " + arrivee.getName());
				}
			});
			dialog.show();
		}
	};

	OnClickListener setArriveeTime = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			final Dialog dialog = new Dialog(ConfigActivity.this);
			dialog.setContentView(R.layout.time_picker);

			final TimePicker tp = (TimePicker ) dialog.findViewById(R.id.timePicker1);
			tp.setIs24HourView(true);
			Button validate = (Button) dialog.findViewById(R.id.validate);
			dialog.setCancelable(true);
			dialog.setTitle("Heure d'arrivée");
			validate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					heureArrivee = (tp.getCurrentHour() * 60 + tp.getCurrentMinute()) *  3600;
					dialog.dismiss();
					((TextView)v).setText(
							Integer.toString(tp.getCurrentHour()) + ":"
							+ Integer.toString(tp.getCurrentMinute()));
				}
			});
			dialog.show();
		}
	};

	OnClickListener setPreparation = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			final Dialog dialog = new Dialog(ConfigActivity.this);
			dialog.setContentView(R.layout.number);

			final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
			Button validate = (Button) dialog.findViewById(R.id.validate);
			np.setMaxValue(300);
			np.setMinValue(0);
			dialog.setCancelable(true);
			dialog.setTitle("Préparation");

			validate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					preparation = np.getValue() * 60000;
					dialog.dismiss();
					((TextView)v).append(": " + Integer.toString(np.getValue()) + "min");
				}
			});
			dialog.show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private OnClickListener onSaveClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if (depart != null && arrivee != null && heureArrivee != 0 && preparation != 0 &&
					transport != null) {
				try {
					Controller.getInstance(v.getContext()).createAlarm(
							v.getContext(),
							depart,
							arrivee,
							preparation,
							Controller.getInstance(v.getContext()).getBellManager().getBell().getPath(),
							transport,
							heureArrivee);
				} catch (NoRouteFoundException e) {
					Toast.makeText(
							getApplicationContext(),
							"Erreur lors de la création de l'alarme",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Veuillez remplir tous les champs pour créer une alarme",
						Toast.LENGTH_SHORT).show();
			}
		}
	};


	private OnClickListener onCancelClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			finish();
		}
	};
}
