package com.wake_e.services.deliverers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.wake_e.model.Credentials;
import com.wake_e.model.Mail;
import com.wake_e.model.sqlite.WakeEDBHelper;

/**
 * @brief The mails deliverer
 * @author Wake-E team
 */

public class MailDeliverer {
	//mails to deliver
	private WakeEDBHelper db;
	private JsonFactory jsonFactory;
	private HttpTransport httpTransport;
	private GoogleCredential credential;

	public MailDeliverer(WakeEDBHelper db) {
		super();
		this.db = db;
		this.jsonFactory = new JacksonFactory();
		this.httpTransport = new NetHttpTransport();
		this.credential = new GoogleCredential();
	}

	/**
	 * @brief deliver mails
	 * @return today's mails
	 * @throws Exception 
	 */
	public void deliver() throws Exception {
		List<Message> emailsIds = getMails(this.db);
		if (emailsIds != null) {
			this.db.clearMails();
			for (Message id: emailsIds) {
				this.db.insertEmail(getMailContent(id.getId()));
			}
		}
	}


	/**
	 * @brief Returns the last emails from you inbox
	 * @param db the database.
	 * @return the thread list.
	 */
	public List<Message> getMails(WakeEDBHelper db) throws Exception {
		List<Message> m = null;
		Credentials c = db.getCredentials("gmail");
		if (c != null) {
			credential.setAccessToken(c.getAccessToken());

			Gmail service = new Gmail.Builder(httpTransport, jsonFactory, credential).setApplicationName("wake_e").build();
			ListMessagesResponse messagesResponse;
			try {
				// No filter for now, add one with setQ("") before execute
				messagesResponse = service.users().messages().list("me").setQ("label:inbox").setMaxResults(Long.valueOf(10)).execute();
				m = messagesResponse.getMessages();
			} catch (GoogleJsonResponseException e) {
				GoogleJsonError error = e.getDetails();
				// We refresh the token
				if (error.getCode() == 401 && error.getErrors().get(0).getReason().equals("authError")) {
					throw new Exception("Auth exception");
				}
			} catch (HttpResponseException e) {
				// No Json body was returned by the API.
				System.err.println("HTTP Status code: " + e.getStatusCode());
				System.err.println("HTTP Reason: " + e.getMessage());
			} catch (IOException e) {
				// Other errors (e.g connection timeout, etc.).
				System.out.println("An error occurred: " + e);
			}
		}
		return m;
	}

	/**
	 * @brief Returns the emails from your inbox with the associated content.
	 * @param db the database.
	 * @parem threadId the message unique id.
	 * @return a mail with its content.
	 */
	public Mail getMailContent(String messageId) {
		Mail messageDetails = null;
		try {
			Gmail service = new Gmail.Builder(httpTransport, jsonFactory, credential).setApplicationName("Gmail Quickstart").build();
			Message message = service.users().messages().get("me", messageId).setFormat("raw").execute();

			byte[] emailBytes = Base64.decodeBase64(message.getRaw());

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
			InternetAddress[] addr = (InternetAddress[]) email.getFrom();

			String content = getText(email).replaceAll("<img .*/?>", "");
			messageDetails = new Mail(
					message.getId(),
					email.getSubject(),
					addr[0].getPersonal(),
					content
					);
		} catch (MessagingException ex) {
			// TODO
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO
			ex.printStackTrace();
		}
		return messageDetails;
	}

	/**
	 * Return the primary text content of the message.
	 */
	private String getText(Part p) throws
	MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null) {
						text = getText(bp);
					}
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null) {
						return s;
					}
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null) {
					return s;
				}
			}
		}

		return null;
	}
}
