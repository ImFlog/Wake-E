package com.wake_e;

import java.io.File;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class SettingsActivity extends Activity {

	private ImageView [] slides;
	private int size;
	private Button buttonBell;
	public static SettingsActivity that;
	final static int RQS_OPEN_AUDIO_MP3 = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		that = this;
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.settings);
		
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
		
		
		
        buttonBell = (Button)findViewById(R.id.open_bell);
        buttonBell.setOnClickListener(buttonOpenOnClickListener);
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

	private OnClickListener buttonOpenOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setType("audio/mp3");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Cherche sonnerie"), RQS_OPEN_AUDIO_MP3);
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == RQS_OPEN_AUDIO_MP3) {
				Uri audioFileUri = data.getData();				
				String[] proj = { MediaStore.Images.Media.DATA };
			    CursorLoader loader = new CursorLoader(this.getApplicationContext(), audioFileUri, proj, null, null, null);
			    Cursor cursor = loader.loadInBackground();
			    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			    cursor.moveToFirst();
			    File audioFile = new File(cursor.getString(column_index));
				Log.i("calendar", audioFile.getPath());
			} 
		} 
	}
}
