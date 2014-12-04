package com.wake_e.adapt;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private final List<Fragment> fragments;

	//On fournit à l'adapter la liste des fragments à afficher
	public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		
		Fragment f = this.fragments.get(position);/*
		if (position == 2){
			
			List<String> mails = new ArrayList<String>();
			
			mails.add("Mail 1");
			mails.add("Mail 2");
			mails.add("Mail 3");
			
			ListView gridview = (ListView) findViewById(R.id.content);
		    gridview.setAdapter(new MailAdapter(MainActivity.that,mails));
		}*/
		return f;
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
}