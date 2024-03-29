package com.wake_e;

import java.io.IOException;
import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SnoozeActivity extends Activity {
	ImageView iv ;
	ImageView iv2 ;
	TextView heure;
	TextView tv;
	SnoozeActivity that;
	MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_snooze);
		
		iv = (ImageView) this.findViewById(R.id.imageTuto);
		iv2 = (ImageView) this.findViewById(R.id.imageView2);
		heure = (TextView)  this.findViewById(R.id.heure);
		tv = (TextView) this.findViewById(R.id.snooze);
		
		Animation rightAnim = AnimationUtils.loadAnimation(this, R.anim.scale_stop);
		iv.setAnimation(rightAnim);
		rightAnim.startNow();
		
		iv.setOnClickListener(stopRing);
		iv2.setOnClickListener(stopRing);
		tv.setOnClickListener(snooze);
		
		heure.setTypeface(MainActivity.future);
		tv.setTypeface(MainActivity.future);

		Uri myUri = Uri.parse(
				Controller.getInstance(this).getBellManager().getBell().toURI().toString());
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setScreenOnWhilePlaying(true);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(getApplicationContext(), myUri);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.start();

		that = this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.snooze, menu);
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
	
	private OnClickListener stopRing = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Stop the alarm
			mediaPlayer.stop();
			// Release the ressource
			mediaPlayer.release();
			Toast.makeText(that, "L'alarme a été arrété", Toast.LENGTH_LONG).show();
		}
	};
	
	private OnClickListener snooze = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mediaPlayer.stop();
			mediaPlayer.release();
			Toast.makeText(that, "L'alarme a été snoozer", Toast.LENGTH_LONG).show();
		}
	};
	
}
