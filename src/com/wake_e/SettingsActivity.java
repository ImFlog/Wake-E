package com.wake_e;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	
	private TextView [] slides;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.settings);
		//todo initiliasation de la liste
		slides = new TextView[4];
				
		slides[0] = (TextView)findViewById(R.id.agenda);
		slides[1] = (TextView)findViewById(R.id.mail);
		slides[2] = (TextView)findViewById(R.id.meteo);
		slides[3] = (TextView)findViewById(R.id.traffic);
	    
		slides[0].setOnTouchListener(touchListenerBouton2);
		slides[1].setOnTouchListener(touchListenerBouton2);
		slides[2].setOnTouchListener(touchListenerBouton2);
		slides[3].setOnTouchListener(touchListenerBouton2);
	    
		
	    for (int i = 0; i < 4; i++){
	    	slides[i].setX(10 + 5*i + 70 * i);
	    }

		ListView comptes	= (ListView)findViewById(R.id.l_comptes);
		ListView locations	= (ListView)findViewById(R.id.l_locations);		
	}

	private OnClickListener agendaSync = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnTouchListener touchListenerBouton2 = new View.OnTouchListener() {
		/**
		 * Old Value
		 */
		private float xx = 0 ;
		private int selection = 0;
		private TextView tmp;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	switch(event.getAction())
	    	{
	    		case MotionEvent.ACTION_DOWN:
	    	    	xx = event.getX();
	    	    	selection = (int) (xx / 105);
	    			break;
	    		case MotionEvent.ACTION_MOVE:
	    			boolean dec = true;
	    			if (xx < event.getX())
	    				dec = false;

	    			//Plus
	    			if (event.getX() > selection + 1 * 105){
	    				tmp = slides[selection];
	    				slides[selection] = slides[selection+1];
	    				slides[selection+1] = tmp;
	    			}
	    			//Moins
	    			if (event.getX() < selection * 105){
	    				tmp = slides[selection];
	    				slides[selection] = slides[selection-1];
	    				slides[selection-1] = tmp;
	    			}
	    			
	    			v.setX(v.getX() - xx + event.getX());
	    			if (v.getX() < 0){v.setY(0);}
	    			
	    			break;
	    		case MotionEvent.ACTION_UP:
	    			v.setX(v.getX() - xx + event.getX());

	    			if (v.getX() < 0){v.setX(0);}
	    			break;
	    	}
	    	return true;
		}
	};
	
	private OnClickListener switchToSettings = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(i);
		}
		
	};

}
