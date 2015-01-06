/**
 * 
 */
package com.wake_e.utils;

/**
 * @author nugetchar
 *
 */
public class Slide {

    private String slide_name;
    private String slide_class;
    private int order;
    private boolean visible;
    
    public Slide(String slide_name, String slide_class, int order, boolean visible) {
	this.slide_name = slide_name;
	this.slide_class = slide_class;
	this.order = order;
	this.visible = visible;
    }

    public Slide() {
	// TODO Auto-generated constructor stub
    }

    public String getSlideName(){
	return slide_name;
    }
    
    public void setSlideName(String slide_name){
	this.slide_name = slide_name;
    }
    
    public String getSlideClass(){
	return slide_class;
    }
    
    public void setSlideClass(String slide_class){
	this.slide_class = slide_class;
    }
    
    public Integer getOrder(){
	return order;
    }
    
    public void setSlideOrder(int order){
	this.order = order;
    }
    
    public boolean visible(){
	return visible;
    }
    
    public void setVisible(boolean visible){
	this.visible = visible;
	if(!visible){
	    this.order = -1;
	}
    }
}
