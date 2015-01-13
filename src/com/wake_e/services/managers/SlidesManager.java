package com.wake_e.services.managers;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.support.v4.app.Fragment;


import com.wake_e.model.Slide;
import com.wake_e.model.sqlite.WakeEDBHelper;
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
	private List<Slide> slides;
	private Fragment homePage;

	/**
	 * 
	 */
	private SlidesManager() {
		super();
		slides = new Vector<Slide>();
	}

	public SlidesManager(Context context, WakeEDBHelper db){
		this();
		this.loadSlides(db);
	}

	/**
	 * @return slide's views
	 */
	public List<Fragment> getVisibleFragments(Context context) {
		Vector<Fragment> visibles = new Vector<Fragment>();
		for(Slide s : slides){
			if(s.visible()){
				visibles.add(Fragment.instantiate(context, s.getSlideClass()));
			}
		}
		return visibles;
	}

	/**
	 * @return slide's views
	 */
	public List<Slide> getVisibleSlides() {
		Vector<Slide> visibles = new Vector<Slide>();
		for(Slide s : slides){
			if(s.visible()){
				visibles.add(s);
			}
		}
		return visibles;
	}

	/**
	 * @brief charger la base contenant les slides que l'utilisateur veut voir
	 * @param context le contexte
	 */
	private void loadSlides(WakeEDBHelper db) {
		this.slides = db.getAllSlides();
	} 

	/**
	 * @brief get the Home Page
	 * @return the Home Page fragment
	 */
	public Fragment getHomePage() {
		return homePage;
	}

	/**
	 * @brief get a reference to the slides list : THIS IS NOT A COPY
	 * @return the slides list
	 */
	public List<Slide> getSlides(){
		return this.slides;
	}

	/**
	 * @brief update the slides table in the database
	 * @param db the WakeEDBHelper
	 */
	public void updateSlides(WakeEDBHelper db) {
		db.updateSlides(this.slides);
	}

}
