package com.wake_e;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wake_e.adapt.MyPagerAdapter;

public class MainActivity extends FragmentActivity {

	LinearLayout ll;
	RelativeLayout relative;
	float positionSlider;
	float sizeSlider;
	private PagerAdapter mPagerAdapter;
	public static MainActivity that;
	public static ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		that = this;

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.home_page);
		// Creation de la liste de Fragments que fera defiler le PagerAdapter
		List<Fragment> fragments = Controller.getInstance(this.getApplicationContext()).getVisibleFragments();

		// Creation de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new MyPagerAdapter(
				super.getSupportFragmentManager(), fragments);

		pager = (ViewPager) super.findViewById(R.id.pager);
		pager.setAdapter(this.mPagerAdapter);

		ll = (LinearLayout) findViewById(R.id.id_station);
		ll.setOnTouchListener(touchListenerBouton1);
		relative = (RelativeLayout) findViewById(R.id.id_fullStation);

		this.setVisible(false);
		ll.setY(0);
		pager.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ImageView settings = (ImageView) findViewById(R.id.parametre);
		settings.setOnClickListener(switchToSettings);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ss, menu);
		return true;
	}

	public void onClick(View v) {
	    Intent i = new Intent(getApplicationContext(), MapActivity.class);
	    startActivity(i);
	}

	private OnTouchListener touchListenerBouton1 = new View.OnTouchListener() {
		/**
		 * Old Value
		 */
		private float yy = 0;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				yy = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				v.setY(v.getY() - yy + event.getY());
				if (v.getY() > positionSlider)
					v.setY(positionSlider);
				if (v.getY() < 0) {
					v.setY(0);
				}
				break;
			case MotionEvent.ACTION_UP:
				v.setY(v.getY() - yy + event.getY());

				if (v.getY() > positionSlider)
					v.setY(positionSlider);
				if (v.getY() < 0) {
					v.setY(0);
				}
				break;
			}

			relative.setLayoutParams(new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (int) v.getY()));
			// pager.setLayoutParams(new
			// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int)
			// v.getY() - relative.getHeight()));
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