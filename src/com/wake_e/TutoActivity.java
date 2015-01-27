package com.wake_e;



import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TutoActivity extends Activity {

	private TextView skip;
	private TextView next;
	private ImageView iv;
	private int i = 0;
	private TutoActivity that;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tuto);
		that = this;
		
		skip = (TextView) this.findViewById(R.id.skip);
		next = (TextView) this.findViewById(R.id.next);
		iv   = (ImageView) this.findViewById(R.id.imageTuto);
		skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
			    //SKIP
				Intent i = new Intent(TutoActivity.this,
						MainActivity.class);
				startActivity(i);
				that.finish();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
			    //NEXT
				if (i < 3){
					if (i == 0) {
						((BitmapDrawable)iv.getDrawable()).getBitmap().recycle();
						iv.setImageResource(R.drawable.tuto_slide1);
					}
					if (i == 1){
						((BitmapDrawable)iv.getDrawable()).getBitmap().recycle();
						iv.setImageResource(R.drawable.tuto_slide2);
					}
					if (i == 2){
						((BitmapDrawable)iv.getDrawable()).getBitmap().recycle();
						iv.setImageResource(R.drawable.tuto_station);
						next.setText("END");
					}
					i++;
				} else {
					Intent i = new Intent(TutoActivity.this,
							MainActivity.class);
					startActivity(i);
					that.finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tuto, menu);
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
