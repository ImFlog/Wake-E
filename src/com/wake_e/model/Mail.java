package com.wake_e.model;

import java.util.UUID;

/**
 * @brief Representation of a e-mail
 * @author Wake-E team
 *
 */
public class Mail {
    
    //mail's ID
    private UUID id;
    
    //mail's title
    private String title;

    //mail's content
    private String content;

    //is the mail read ?
    private boolean isRead;

    
    @SuppressWarnings("unused")
    private Mail() {
	super();
    }
    
    /**
     * @param title
     * @param content
     */
    public Mail(UUID id, String title, String content){
	this.id = id;
	this.title = title;
	this.content = content;
	this.isRead = false;
    }
    
    /**
     * @return the id
     */
    public UUID getId(){
	return id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }
    
    /**
     * @return TRUE if is read, FALSE else
     */
    public boolean isRead(){
	return this.isRead;
    }

}
