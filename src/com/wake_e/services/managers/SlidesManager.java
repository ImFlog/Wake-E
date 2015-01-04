package com.wake_e.services.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.Fragment;

import com.wake_e.fragment.PageHomePageFragment;
import com.wake_e.fragment.PageReveilFragment;
import com.wake_e.fragment.station.PageAgendaFragment;
import com.wake_e.fragment.station.PageMailFragment;
import com.wake_e.fragment.station.PageMeteoFragment;

/**
 * @brief Used to manager the slides in the morning
 * @author Wake-E team
 */

public class SlidesManager {
    // une liste ordonnée des vues visibles dans le slide. Paramétrable
    // via les paramètres globaux.
    private List<Fragment> visibleViews;
    private Fragment homePage;

    private static SlidesManager manager;

    /**
     * 
     */
    private SlidesManager() {
	super();
	visibleViews = new Vector<Fragment>();
    }

    private SlidesManager(Context context){
	this();
	homePage = Fragment.instantiate(context,PageHomePageFragment.class.getName());
    }

    /**
     * @return slide's views
     */
    public List<Fragment> getVisibleViews() {
	return this.visibleViews;
    }
    
    /**
     * @return all fragments (slide's views + home page)
     */
    public List<Fragment> getAllFragments(){
	List<Fragment> fragments = new Vector<Fragment>();
	fragments.add(this.homePage);
	fragments.addAll(this.visibleViews);
	return fragments;
    }

    /**
     * @brief charger le fichier contenant les slides que l'utilisateur veut
     *        voir
     * @param context
     *            le contexte
     */
    public void loadSlidesFile(Context context) {

	// Empty the fragments list
	this.visibleViews.clear();
	this.visibleViews.add(Fragment.instantiate(context, PageHomePageFragment.class.getName()));

	// Find the directory for the SD Card using the API
	// *Don't* hardcode "/sdcard"
	File sdcard = Environment.getExternalStorageDirectory();

	// Get the text file
	File file = new File(sdcard + "/wake_e", "slides.txt");

	try {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String line;

	    // A line represent a class name
	    // e.g : com.wake_e.fragment.PageHomePageFragment
	    while ((line = br.readLine()) != null) {
		visibleViews.add(Fragment.instantiate(context, line));
	    }
	    br.close();
	} catch (IOException e) {
	    // A la moindre erreur on charge tout automatiquement
	    this.visibleViews.clear();
	    this.visibleViews.add(Fragment.instantiate(context,PageReveilFragment.class.getName()));
	    this.visibleViews.add(Fragment.instantiate(context,PageMailFragment.class.getName()));
	    this.visibleViews.add(Fragment.instantiate(context,PageAgendaFragment.class.getName()));
	    this.visibleViews.add(Fragment.instantiate(context,PageMeteoFragment.class.getName()));
	}
    }

    public static SlidesManager getInstance(Context context) {
	if (SlidesManager.manager == null) {
	    SlidesManager.manager = new SlidesManager(context);
	    SlidesManager.manager.loadSlidesFile(context);
	}
	return SlidesManager.manager;
    }

    public Fragment getHomePage() {
	return homePage;
    }
}
