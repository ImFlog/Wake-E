package com.wake_e.services.managers;

import java.util.List;

import android.view.View;

/**
 * @brief Used to manager the slides in the morning
 * @author Wake-E team
 */

public class SlidesManager {
    // une liste ordonnée des vues visibles dans le slide. Paramétrable
    // via les paramètres globaux.
    private List<View> visibleViews;

    /**
     * 
     */
    public SlidesManager() {
	super();
    }

    /**
     * @return slide's views
     */
    public List<View> getVisibleViews() {
	return this.visibleViews;
    }

    /**
     * @param views
     *            the views for the slide
     */
    public void setVisibleViews(List<View> views) {
	this.visibleViews = views;
    }

}
