package com.wake_e.services.deliverers;

import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import com.wake_e.model.Mail;

/**
 * @brief The mails deliverer
 * @author Wake-E team
 */

public class MailDeliverer extends FunctionnalitiesDeliverer<Queue<Mail>> {
    //mails to deliver
    private Queue<Mail> mails;

    
    
    /**
     * 
     */
    public MailDeliverer() {
	super();
	this.mails = new SynchronousQueue<Mail>();
    }

    /**
     * @brief deliver mails
     * @return today's mails
     */
    public Queue<Mail> deliver() {
	// TODO implement me
	return this.mails;
    }

}
