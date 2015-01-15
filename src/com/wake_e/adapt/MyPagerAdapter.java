package com.wake_e.adapt;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private final List<Fragment> fragments;

	//On fournit a l'adapter la liste des fragments a afficher
	public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		
		Fragment f = this.fragments.get(position);
		return f;
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
}