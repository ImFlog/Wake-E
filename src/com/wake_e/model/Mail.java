package com.wake_e.model;

/**
 * @brief Representation of a e-mail
 * @author Wake-E team
 *
 */
public class Mail {
    
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
    public Mail(String title, String content){
	this.title = title;
	this.content = content;
	this.isRead = false;
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
    public boolean isRead() {
	return this.isRead;
    }

}
