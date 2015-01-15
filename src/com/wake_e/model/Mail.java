package com.wake_e.model;

/**
 * @brief Representation of a e-mail
 * @author Wake-E team
 *
 */
public class Mail {

	//mail's ID
	private String id;

	//mail's title
	private String subject;

	// mail sender
	private String sender;

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
	public Mail(String id, String subject, String sender, String content){
		this.id = id;
		this.subject = subject;
		this.sender = sender;
		this.content = content;
		this.isRead = false;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the id
	 */
	public String getId(){
		return id;
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
