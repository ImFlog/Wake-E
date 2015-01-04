package com.wake_e;

<<<<<<< Updated upstream
import android.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


/**
 * @brief Our main activity
 * @author Wake-E team
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
=======
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.wake_e.adapt.MyPagerAdapter;
import com.wake_e.services.managers.SlidesManager;

public class MainActivity extends FragmentActivity {


    private PagerAdapter mPagerAdapter;


    public static MainActivity that;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	that = this;
	/*
		super.onCreate(savedInstanceState);
		setContentView(R.layout.station);
		FontUtil.overrideFont(getApplicationContext(), "future", "fonts/fufure.ttf");

		List<String> mails = new ArrayList<String>();

		mails.add("Mail 1");
		mails.add("Mail 2");
		mails.add("Mail 3");

		ListView gridview = (ListView) findViewById(R.id.content);
	    gridview.setAdapter(new MailAdapter(this,mails));
	 */
	super.onCreate(savedInstanceState);
	super.setContentView(R.layout.viewpager);

	// Creation de la liste de Fragments que fera defiler le PagerAdapter
	//List<Fragment> fragments = new Vector<Fragment>();
	
	/**
	 * TTO : J'ai deplace ce code dans le try...catch de loadSlidesFile() de SlidesManager
	 */
	// Ajout des Fragments dans la liste
	//		fragments.add(Fragment.instantiate(this,PageHomePageFragment.class.getName()));
	//		fragments.add(Fragment.instantiate(this,PageReveilFragment.class.getName()));
	//		fragments.add(Fragment.instantiate(this,PageMailFragment.class.getName()));
	//		fragments.add(Fragment.instantiate(this,PageAgendaFragment.class.getName()));
	//		fragments.add(Fragment.instantiate(this,PageMeteoFragment.class.getName()));

	// Creation de l'adapter qui s'occupera de l'affichage de la liste de
	// Fragments
	List<Fragment> fragments = SlidesManager.getInstance(this).getAllFragments();
	this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

	ViewPager pager = (ViewPager) super.findViewById(R.id.pager);
	// Affectation de l'adapter au ViewPager
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
>>>>>>> Stashed changes
    }
}
