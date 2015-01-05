package com.wake_e;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import com.wake_e.adapt.MailAdapter;
import com.wake_e.adapt.MyPagerAdapter;
import com.wake_e.fragment.PageReveilFragment;
import com.wake_e.fragment.station.PageAgendaFragment;
import com.wake_e.fragment.station.PageMailFragment;
import com.wake_e.fragment.station.PageMeteoFragment;
import com.wake_e.tools.FontUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.wake_e.R;

public class StationActivity extends FragmentActivity {


	private PagerAdapter mPagerAdapter;
	public static StationActivity that;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		that = this;

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.viewpager);

		// Création de la liste de Fragments que fera défiler le PagerAdapter
		List<Fragment> fragments = new Vector<Fragment>();


		fragments.add(Fragment.instantiate(this,PageMailFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,PageAgendaFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,PageMeteoFragment.class.getName()));


		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
		pager.setAdapter(this.mPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
