package com.wake_e;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	private ImageView [] slides;
	private int size;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		


		ListView comptes	= (ListView)findViewById(R.id.l_comptes);
		
		ListView locations	= (ListView)findViewById(R.id.l_locations);
		
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
	
	private OnClickListener switchToSettings = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(i);
		}
		
	};

}
