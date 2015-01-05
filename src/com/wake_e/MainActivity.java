package com.wake_e;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wake_e.adapt.MyPagerAdapter;
import com.wake_e.services.managers.SlidesManager;

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
		
		//super.setContentView(R.layout.viewpager);



		// Ajout des Fragments dans la liste
//		fragments.add(Fragment.instantiate(this,PageHomePageFragment.class.getName()));
//		fragments.add(Fragment.instantiate(this,PageReveilFragment.class.getName()));
//		fragments.add(Fragment.instantiate(this,PageMailFragment.class.getName()));
//		fragments.add(Fragment.instantiate(this,PageAgendaFragment.class.getName()));
//		fragments.add(Fragment.instantiate(this,PageMeteoFragment.class.getName()));

		// Creation de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		// Creation de la liste de Fragments que fera defiler le PagerAdapter
		List<Fragment> fragments = SlidesManager.getInstance(this).getAllFragments();

		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

		pager = (ViewPager) super.findViewById(R.id.pager);
		pager.setAdapter(this.mPagerAdapter);
		
		ll = (LinearLayout) findViewById(R.id.id_station);
	    ll.setOnTouchListener(touchListenerBouton1);
	    relative = (RelativeLayout) findViewById(R.id.id_fullStation);

		this.setVisible(false);
		ll.setY(0);
		pager.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ss, menu);
		return true;
	}
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		positionSlider = pager.getHeight();
		ll.setY(positionSlider);
		this.setVisible(true);
		/*
		pager.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, relative.getHeight() - ll.getHeight()));
		//relative.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,0));
		
		TranslateAnimation animation = new TranslateAnimation(0.0f,0.0f,0.0f, first); //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
	    animation.setDuration(1000);
	    //animation.setFillAfter(true);
	    ll.startAnimation(animation);
	    ll.setY(first);
	    */
		//first = pager.getHeight();
		
	}

	private OnTouchListener touchListenerBouton1 = new View.OnTouchListener() {
		/**
		 * Old Value
		 */
		private float yy = 0 ;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	switch(event.getAction())
	    	{
	    		case MotionEvent.ACTION_DOWN:
	    	    	yy = event.getY();
	    			break;
	    		case MotionEvent.ACTION_MOVE:
	    			v.setY(v.getY() - yy + event.getY());
	    			if (v.getY() > positionSlider) v.setY(positionSlider);
	    			if (v.getY() < 0){v.setY(0);}
	    			break;
	    		case MotionEvent.ACTION_UP:
	    			v.setY(v.getY() - yy + event.getY());

	    			if (v.getY() > positionSlider) v.setY(positionSlider);
	    			if (v.getY() < 0){v.setY(0);}
	    			break;
	    	}
	    	
	    	relative.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) v.getY()));
	    	//pager.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) v.getY() - relative.getHeight()));
		    return true;
		}
	};
}