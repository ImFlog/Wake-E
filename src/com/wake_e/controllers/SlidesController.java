package com.wake_e.controllers;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.wake_e.model.Slide;
import com.wake_e.model.sqlite.WakeEDBHelper;
import com.wake_e.services.managers.SlidesManager;

/**
 * @brief Singleton and main controller of the application
 * @author Wake-E team
 */
public class SlidesController {
    //the db helper
    private WakeEDBHelper db;
    
    //the app context
    private Context context;
    
    //all our managers
    private SlidesManager slidesManager;

    private static SlidesController controller;

    /**
     * @param context
     *            le contexte de l'application
     */
    private SlidesController(Context context) {
	super();
	
	this.context = context;
	this.db = new WakeEDBHelper(context);
	this.slidesManager = new SlidesManager(context, db);
    }

    /**
     * @brief get the Controller instance
     * @param context
     *            the app context
     * @return the Controller instance
     */
    public static SlidesController getInstance(Context context) {
	if (SlidesController.controller == null) {
	    SlidesController.controller = new SlidesController(context);
	}
	return SlidesController.controller;
    }

    // ########### SLIDES ###########
    /**
     * @brief retrieve visible fragments
     * @return the visible fragments
     */
    public List<Fragment> getVisibleFragments() {
	return this.slidesManager.getVisibleFragments(this.context);
    }

    /**
     * @brief update slides
     * @param slides
     *            the slides
     */
    public void updateSlides() {
	this.slidesManager.updateSlides(this.db);
    }

    /**
     * @brief get all slides
     * @return all slides
     */
    public List<Slide> getSlides() {
	return this.slidesManager.getSlides();
    }


}
