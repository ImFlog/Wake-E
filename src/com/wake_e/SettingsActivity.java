package com.wake_e;

import java.util.ArrayList;
import java.util.List;

import com.wake_e.fragment.station.PageAgendaFragment;
import com.wake_e.fragment.station.PageMailFragment;
import com.wake_e.fragment.station.PageMeteoFragment;
import com.wake_e.model.Slide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

		slides[0].setOnTouchListener(touchListenerBouton2);
		slides[1].setOnTouchListener(touchListenerBouton2);
		slides[2].setOnTouchListener(touchListenerBouton2);
		slides[3].setOnTouchListener(touchListenerBouton2);

		save = (TextView) this.findViewById(R.id.save);
		cancel = (TextView) this.findViewById(R.id.cancel);
		save.setOnClickListener(onSaveClick);
		cancel.setOnClickListener(onCancelClick);

		ListView comptes	= (ListView)findViewById(R.id.l_comptes);
		TextView addAccount = (TextView)findViewById(R.id.addAccount);
		if (Controller.getInstance(this).getCredentials() != null) {
			addAccount.setCompoundDrawables(null, null, null, null);
			addAccount.setText("Gmail");
		} else {
			// Add account listener
			addAccount.setOnClickListener(credentialStart);
		}
		
		ListView locations	= (ListView)findViewById(R.id.l_locations);
		
		ListView sounds		= (ListView)findViewById(R.id.l_sounds);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		List<Slide> slideList = new ArrayList<Slide>();
		String name = null;
		String className = null;
		int order;
		boolean visible = true;

		int position = 1;
		
		for(ImageView v: slides) {
			order = position;
			visible = true;
			if (v.getId() == R.id.meteo) {
				name = "Météo";
				className = PageMeteoFragment.class.getName();
			} else if (v.getId() == R.id.mail) {
				name = "Mail";
				className = PageMailFragment.class.getName();
			} else if (v.getId() == R.id.agenda) {
				name = "Agenda";
				className = PageAgendaFragment.class.getName();
			} else if (v.getId() == R.id.traffic) {
				// TODO ?
			}
			if (name != null) {
				slideList.add(new Slide(name, className, order, visible));
				position++;
			}
		}
		Controller.getInstance(this).updateSlides(slideList);
		return super.onKeyDown(keyCode, event);
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
	
	private OnClickListener onSaveClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			//SAVE
			
		}
	};
	
	
	private OnClickListener onCancelClick = new OnClickListener(){
		@Override
		public void onClick(View v) {
			//CANCEL
			
		}
	};

}
