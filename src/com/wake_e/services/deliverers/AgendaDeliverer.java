package com.wake_e.services.deliverers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.wake_e.model.Event;

/**
 * @brief The agenda deliverer
 * @author Wake-E team
 */
public class AgendaDeliverer {
	// The type of agenda mapped : local or Google ?
	// TODO : constantes données par l'utilisateur via les paramètres ?
	@SuppressWarnings("unused")
	private String type;

	// Today's events
	private ArrayList<Event> events;

	private Context context = null;
	
	public AgendaDeliverer(Context context) {
		super();
		this.context = context;
		this.events = new ArrayList<Event>();
		long begin = new Date().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(begin);
		cal.add(Calendar.DATE, 1);
		long end = cal.getTime().getTime();
		String[] proj = new String[] { Instances._ID, Instances.BEGIN,
				Instances.END, Instances.EVENT_ID };
		Cursor cursor = Instances.query(context.getContentResolver(), proj,
				begin, end);
		if (cursor.getCount() > 0) {
			loadEvents(cursor);
		} 
	}
	
	/**
	 * @brief retrieve today's events from the synchronized agenda
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}

	/**
	 * Get Events from cursor.
	 * @param c
	 */
	public void loadEvents(Cursor c) {
		if (c.moveToFirst()) {
			do {
				Long id = c.getLong(c.getColumnIndex("event_id"));
				long selectedEventId = id;
				String[] proj = new String[] { Events._ID, Events.DTSTART,
						Events.DTEND, Events.TITLE, Events.DESCRIPTION, Events.EVENT_LOCATION};
				Cursor cursor = context
						.getContentResolver()
						.query(Events.CONTENT_URI,
								proj,
								Events._ID + " = ? ",
								new String[] { Long.toString(selectedEventId) },
								null);
				if (cursor.moveToFirst()) {
					events.add(toEvent(cursor));
				}

			} while (c.moveToNext());
		}
		c.close();
	}
	
	/**
	 * Create Event from cursor.
	 * @param c
	 * @return Event
	 */
	private Event toEvent(Cursor c) {
		long id = c.getLong(c.getColumnIndex("_id"));
		String name = c.getString(c.getColumnIndex("title"));
		Date begin = new Date(c.getLong(c.getColumnIndex("dtstart")));
		Date end = new Date(c.getLong(c.getColumnIndex("dtend")));
		String description = c.getString(c.getColumnIndex("description"));
		String location = c.getString(c.getColumnIndex("eventLocation"));
		Event e = new Event(id, name, begin, end, location, description);
		return e;
	}

	/**
	 * @brief deliver the next event of the day
	 */
	public Event deliver() {
		/* TODO : pop the first event of the list */
		return events.remove(0);
	}
}
