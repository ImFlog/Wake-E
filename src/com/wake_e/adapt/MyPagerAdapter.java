package com.wake_e.adapt;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private final List<Fragment> fragments;

	//On fournit a l'adapter la liste des fragments a afficher
	public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	public void addItem(List<Fragment> fragments) {
		this.fragments.addAll(fragments);
	}

	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	public void destroyAllFragment(ViewPager pager) {
		for (int i = 0; i < getCount(); i++) {
			try {
				Object object = this.instantiateItem(pager, i);
				if (object != null) {
					FragmentManager manager = ((Fragment) object).getFragmentManager();
					FragmentTransaction trans = manager.beginTransaction();
					trans.remove((Fragment) object);
					trans.commit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}