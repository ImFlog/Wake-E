package com.wake_e;

import com.wake_e.model.Location;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ConfigActivity extends Activity {

	
	TextView mdepart;
	TextView mpreparation;
	TextView mheureArrivee;
	TextView mlieuArrivee;
	
	ImageView mvelo;
	ImageView mvoiture;
	ImageView mtrain;
	

	//DATA
	int preparation;
	Time heureArrivee;
	Location depart;
	Location arrivee;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		mdepart = (TextView) this.findViewById(R.id.TextView03);
		mpreparation = (TextView) this.findViewById(R.id.TextView02);
		mheureArrivee = (TextView) this.findViewById(R.id.TextView01);
		mlieuArrivee = (TextView) this.findViewById(R.id.textView2);
		
		mvelo = (ImageView) this.findViewById(R.id.imageView3);
		mvoiture = (ImageView) this.findViewById(R.id.imageView4);
		mtrain = (ImageView) this.findViewById(R.id.imageView2);
		
		
		mdepart.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	final Dialog dialog = new Dialog(ConfigActivity.this);
		        dialog.setContentView(R.layout.list);
		
				 ListView lv = (ListView ) dialog.findViewById(R.id.ListView1);
				 dialog.setCancelable(true);
				 dialog.setTitle("Départ");
				 
				 //TODO remplir avec location
				 String[] listeStrings = {"France","Allemagne","Russie"};
				 
				 lv.setAdapter(new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_1,listeStrings));
			 

			     lv.setOnItemClickListener(new OnItemClickListener() {
			            @Override
			            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			                //do something on click
			            	//TODO udpdate
			                dialog.dismiss();
			            }
			        });
				 dialog.show();
		    }
		});
		
		mpreparation.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	final Dialog dialog = new Dialog(ConfigActivity.this);
		        dialog.setContentView(R.layout.number);
		
				 NumberPicker tp = (NumberPicker ) dialog.findViewById(R.id.numberPicker1);
				 dialog.setCancelable(true);
				 dialog.setTitle("Préparation");
				 
				 //TODO remplir avec l'heure d'arrivée courrente

				 dialog.show();
		    }
		});
		
		mheureArrivee.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	final Dialog dialog = new Dialog(ConfigActivity.this);
		        dialog.setContentView(R.layout.time_picker);
		
				 TimePicker tp = (TimePicker ) dialog.findViewById(R.id.timePicker1);
				 dialog.setCancelable(true);
				 dialog.setTitle("Heure d'arrivée");
				 
				 //TODO remplir avec l'heure d'arrivée courrente

				 dialog.show();
		    }
		});
		
		mlieuArrivee.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	final Dialog dialog = new Dialog(ConfigActivity.this);
		        dialog.setContentView(R.layout.list);
		
				 ListView lv = (ListView ) dialog.findViewById(R.id.ListView1);
				 dialog.setCancelable(true);
				 dialog.setTitle("Arrivée");
				 
				 //TODO remplir avec location
				 String[] listeStrings = {"France","Allemagne","Russie"};
				 
				 lv.setAdapter(new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_list_item_1,listeStrings));
			 

			     lv.setOnItemClickListener(new OnItemClickListener() {
			            @Override
			            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			                //do something on click
			            	//TODO udpdate
			                dialog.dismiss();
			            }
			        });
				 dialog.show();
		    }
		});
		
		mvelo.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	mvelo.setImageResource(R.drawable.velo);
		    	mtrain.setImageResource(R.drawable.utrain);
		    	mvoiture.setImageResource(R.drawable.uvoiture);
		    }
		});
		mvoiture.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	mvelo.setImageResource(R.drawable.uvelo);
		    	mtrain.setImageResource(R.drawable.utrain);
		    	mvoiture.setImageResource(R.drawable.voiture);
		    }
		});
		mtrain.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	mvelo.setImageResource(R.drawable.uvelo);
		    	mtrain.setImageResource(R.drawable.train);
		    	mvoiture.setImageResource(R.drawable.uvoiture);
		    }
		});
		
    }
	
	
	private Location updateDepart(){
		return null;
	}
	
	private Location updateArrivee(){
		return null;
	}
	
	private Time updateHeureArrivee(){
		return null;
	}

	private int updatePreparation(){
		return preparation;
	}

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
}
