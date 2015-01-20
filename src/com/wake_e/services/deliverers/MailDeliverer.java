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

import android.os.AsyncTask;
import android.util.Log;

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
	public void deliver() {
		GmailTask task = new GmailTask();
		task.execute(this.db);
	}

	private class GmailTask extends AsyncTask<WakeEDBHelper, Void, Void> {

		@Override
		protected Void doInBackground(WakeEDBHelper... params) {
			WakeEDBHelper db = params[0];
			List<Message> emailsIds = getMails(db);
			if (emailsIds != null) {
				db.clearMails();
				for (Message id: emailsIds) {
					db.insertEmail(getMailContent(id.getId()));
				}
			}
			return null;
		}

		/**
		 * @brief Returns the last emails from you inbox
		 * @param db the database.
		 * @return the thread list.
		 */
		public List<Message> getMails(WakeEDBHelper db) {
			List<Message> m = null;
			Credentials c = db.getCredentials("gmail");
			if (c != null) {
				credential.setAccessToken(c.getAccessToken());

				Gmail service = new Gmail.Builder(httpTransport, jsonFactory, credential).setApplicationName("wake_e").build();
				ListMessagesResponse messagesResponse;
				try {
					messagesResponse = service.users().messages().list("me").setQ("label:inbox").setMaxResults(Long.valueOf(10)).execute();
					m = messagesResponse.getMessages();
				} catch (GoogleJsonResponseException e) {
					GoogleJsonError error = e.getDetails();
					// We refresh the token
					if (error.getCode() == 401 && error.getErrors().get(0).getReason().equals("authError")) {
						Log.e("Deliverer", error.getErrors().get(0).getReason());
					}
				} catch (HttpResponseException e) {
					// No Json body was returned by the API.
					Log.e("Deliverer", "HTTP Status code: " + e.getStatusCode());
					Log.e("Deliverer", "HTTP Reason: " + e.getMessage());
				} catch (IOException e) {
					// Other errors (e.g connection timeout, etc.).
					Log.e("Deliverer", "An error occurred: " + e);
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
				Log.e("Deliverer", ex.getMessage());
			} catch (IOException ex) {
				Log.e("Deliverer", ex.getMessage());
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
}
