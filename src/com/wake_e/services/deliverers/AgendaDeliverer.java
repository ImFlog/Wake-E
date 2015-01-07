package com.wake_e.services.deliverers;

import java.util.Date;
import java.util.Queue;

import android.database.Cursor;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Instances;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.wake_e.model.Event;

/**
 * @brief The agenda deliverer
 * @author Wake-E team
 */
public class AgendaDeliverer implements FunctionalitiesDeliverer<Event> {
	// The type of agenda mapped : local or Google ?
	// TODO : constantes données par l'utilisateur via les paramètres ?
	@SuppressWarnings("unused")
	private String type;

	// Today's events
	private Queue<Event> events;
	
	private FragmentActivity activity = null;

	/**
     * 
     */
	public AgendaDeliverer(FragmentActivity activity) {
		super();
		this.activity = activity;
		
		String[] projection = new String[] { Calendars._ID, Calendars.NAME,
				Calendars.ACCOUNT_NAME, Calendars.ACCOUNT_TYPE };
		Cursor calCursor = activity.getApplicationContext().getContentResolver().query(Calendars.CONTENT_URI,
				projection, Calendars.VISIBLE + " = 1", null,
				Calendars._ID + " ASC");
		if (calCursor.moveToFirst()) {
			do {
				long id = calCursor.getLong(0);
				String displayName = calCursor.getString(1);
				Log.i("CALENDAR", displayName);
			} while (calCursor.moveToNext());
		}
		
		
		
		long begin = new Date().getTime();// starting time in milliseconds
		long end = begin+1000*60*60*24*7;// ending time in milliseconds
		String[] proj = 
		      new String[]{
		            Instances._ID, 
		            Instances.BEGIN, 
		            Instances.END, 
		            Instances.EVENT_ID};
		Cursor cursor = Instances.query(activity.getContentResolver(), proj, begin, end);
		Log.i("calendar", "nb:"+cursor.getCount());
		if (cursor.getCount() > 0) {
			Log.i("calendar", cursor.getString(0));
			/*
			for(String n : cursor.getColumnNames()) {
			   Log.i("Calendar", n);
		   	}
		   */
		} else {
			
		}
	    
	}

	/**
	 * @brief retrieve today's events from the synchronized agenda
	 */
	public void getEvents() {
		

	}

	/**
	 * @brief deliver the next event of the day
	 */
	public Event deliver() {
		/* TODO : pop the first event of the list */
		return events.poll();
	}

}
