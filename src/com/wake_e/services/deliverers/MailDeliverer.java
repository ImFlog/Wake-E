package com.wake_e.services.deliverers;

import java.util.Set;

import com.wake_e.model.Mail;

/**
 * @brief The mails deliverer
 * @author Wake-E team
 */

public class MailDeliverer extends FunctionalitiesDeliverer<Set<Mail>> {
    //mails to deliver
    private Set<Mail> mails;

    
    /**
     * 
     */
    public MailDeliverer() {
	super();
    }

    /**
     * @brief deliver mails
     * @return today's mails
     */
    public Set<Mail> deliver() {
	// TODO implement me
	return this.mails;
    }

}
