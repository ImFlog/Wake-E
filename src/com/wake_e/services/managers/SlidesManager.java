package com.wake_e.services.managers;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.wake_e.model.Slide;
import com.wake_e.model.sqlite.WakeEDBHelper;


/**
 * @brief Used to manager the slides in the morning
 * @author Wake-E team
 */

public class SlidesManager {
    // une liste ordonnée des vues visibles dans le slide. Paramétrable
    // via les paramètres globaux.
    private List<Slide> slides;
    private Fragment homePage;
    private WakeEDBHelper db;

    /**
     * 
     */
    private SlidesManager(WakeEDBHelper db) {
	super();
	this.db = db;
	slides = new Vector<Slide>();
    }

    public SlidesManager(Context context, WakeEDBHelper db){
	this(db);
	this.loadSlides();
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
    private void loadSlides() {
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
     * @param slides 
     * @brief update the slides table in the database
     * @param db the WakeEDBHelper
     */
    public void updateSlides() {
	db.updateSlides(slides);
	this.loadSlides();
    }

    public Slide getSlide(String slide_name) {
	for(Slide s : this.slides){
	    if(s.getSlideName().equals(slide_name)){
		return s;
	    }
	}
	return null;
    }

}
