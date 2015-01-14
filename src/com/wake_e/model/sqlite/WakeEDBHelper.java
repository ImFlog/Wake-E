/**
 * 
 */
package com.wake_e.model.sqlite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.wake_e.fragment.station.PageAgendaFragment;
import com.wake_e.fragment.station.PageMailFragment;
import com.wake_e.fragment.station.PageMeteoFragment;
import com.wake_e.model.Credentials;
import com.wake_e.model.Location;
import com.wake_e.model.Slide;
import com.wake_e.tools.SlidesComparator;
import com.wake_e.utils.Point;

/**
 * @author The Wake-E team
 *
 */
public class WakeEDBHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "wakeE";

    // Table Names
    private static final String TABLE_SLIDES = "slides";
    private static final String TABLE_CREDENTIALS = "credentials";
    private static final String TABLE_LOCATIONS = "locations";


    // Table Create Statements
    private static final String CREATE_TABLE_SLIDES= "CREATE TABLE "
	    + TABLE_SLIDES + "(slide_name VARCHAR(255) PRIMARY KEY,"
	    + " slide_class VARCHAR(255) NOT NULL, slide_order INTEGER NOT NULL,"
	    + "slide_visible INTEGER NOT NULL)";

    private static final String CREATE_TABLE_CREDENTIALS= "CREATE TABLE "
	    + TABLE_CREDENTIALS + "(credentials_type VARCHAR(255) PRIMARY KEY,"
	    + "credentials_accessToken VARCHAR(255) NOT NULL)";

    private static final String CREATE_TABLE_LOCATIONS= "CREATE TABLE "
	    + TABLE_LOCATIONS + "(location_uid VARCHAR(16) PRIMARY KEY,"
	    + " location_point VARCHAR(255) NOT NULL, location_address VARCHAR(255) NOT NULL,"
	    + " location_city VARCHAR(255) NOT NULL, location_cp VARCHAR(255) NOT NULL,"
	    + " location_address_line VARCHAR(255) NOT NULL)";

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    private WakeEDBHelper(Context context, String name, CursorFactory factory,
	    int version) {
	super(context, name, factory, version);
    }

    /**
     * @param context
     */
    public WakeEDBHelper(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     * @param errorHandler
     */
    private WakeEDBHelper(Context context, String name, CursorFactory factory,
	    int version, DatabaseErrorHandler errorHandler) {
	super(context, name, factory, version, errorHandler);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
	// creating required tables
	db.execSQL(CREATE_TABLE_SLIDES);
	db.execSQL(CREATE_TABLE_CREDENTIALS);
	db.execSQL(CREATE_TABLE_LOCATIONS);
	this.populateSlides(db);
    }

    //###### SLIDES #####

    private void populateSlides(SQLiteDatabase db) {
	Slide s = new Slide("Mail", PageMailFragment.class.getName(), 1, true);
	this.createSlide(db, s);
	s = new Slide("Agenda", PageAgendaFragment.class.getName(), 2, true);
	this.createSlide(db, s);
	s = new Slide("Meteo", PageMeteoFragment.class.getName(), 3, true);
	this.createSlide(db, s);
    }

    private void createSlide(SQLiteDatabase db, Slide s) {
	ContentValues values = new ContentValues();
	values.put("slide_class", s.getSlideClass());
	values.put("slide_order", s.getOrder());
	values.put("slide_name", s.getSlideName());
	values.put("slide_visible", s.visible());
	db.insert(TABLE_SLIDES, null, values);
	values.clear();
    }

    /**
     * @brief update slides
     * @param slides
     */
    public void updateSlides(List<Slide> slides) {

	//We get the database in writeable mode
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();  
	for(Slide s : slides){
	    values.put("slide_class", s.getSlideClass());
	    values.put("slide_order", s.getOrder());
	    values.put("slide_name", s.getSlideName());
	    values.put("slide_visible", s.visible());

	    // update row
	    db.update(TABLE_SLIDES, values, "slide_class=" + s.getSlideClass(), null);
	    values.clear();
	}
    }


    /**
     * @brief retrieve all slides
     * @return all slides
     */
    public List<Slide> getAllSlides() {
	List<Slide> slides = new Vector<Slide>();

	String selectQuery = "SELECT * FROM " + TABLE_SLIDES;

	SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.rawQuery(selectQuery, null);
	// looping through all rows and adding to list
	if (c.moveToFirst()) {
	    do {
		Slide s = new Slide();
		s.setSlideName(c.getString(c.getColumnIndex("slide_name")));
		s.setSlideClass(c.getString(c.getColumnIndex("slide_class")));
		s.setSlideOrder(c.getInt(c.getColumnIndex("slide_order")));
		s.setVisible((c.getInt(c.getColumnIndex("slide_visible")) == 1) ? true : false);

		// adding to slides list
		slides.add(s);
	    } while (c.moveToNext());
	}
	//on trie les slides par ordre

	Collections.sort(slides, new SlidesComparator());
	return slides;
    }

    //###### CREDENTIALS #####

    private void createCredentials(SQLiteDatabase db, Credentials c) {
	ContentValues values = new ContentValues();

	values.put("credentials_type", c.getType());
	values.put("credentials_accessToken", c.getAccessToken());
	db.insert(TABLE_CREDENTIALS, null, values);
	values.clear();
    }


    /**
     * @brief update credentials
     * @param credentials
     */
    public void updateCredentials(Credentials c) {

	//We get the database in writeable mode
	SQLiteDatabase db = this.getWritableDatabase();

	//First we erase everything
	db.execSQL("delete from "+ TABLE_CREDENTIALS);

	//Then we create new credentials
	this.createCredentials(db, c);
    }


    /**
     * @brief retrieve credential list
     * @return credentials
     */
    public List<Credentials> getCredentials() {
	List<Credentials> cr = new ArrayList<Credentials>();

	String selectQuery = "SELECT * FROM " + TABLE_CREDENTIALS;

	SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.rawQuery(selectQuery, null);
	// looping through all rows and adding to list
	while (c.moveToNext()) {
	    cr.add(new Credentials(
		    c.getString(c.getColumnIndex("credentials_type")),
		    c.getString(c.getColumnIndex("credentials_accessToken"))));
	}
	return cr;
    }

    /**
     * @brief delete a credential.
     * @param type the credential type to delete.
     */
    public void deleteCredential(String type) {
	String deleteQuery = "DELETE FROM " + TABLE_CREDENTIALS +
		" WHERE type = " + type;
	SQLiteDatabase db = this.getWritableDatabase();
	db.rawQuery(deleteQuery, null);
    }

    //###### LOCATIONS #####
    public Set<Location> getLocations() {
	Set<Location> l = new TreeSet<Location>();

	String selectQuery = "SELECT * FROM " + TABLE_LOCATIONS;

	SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.rawQuery(selectQuery, null);
	Point p;
	
	String city, cp, address_line, address;
	
	// looping through all rows and adding to list
	while (c.moveToNext()) {
	    p = Point.pointFromString(c.getString(c.getColumnIndex("location_point")));
	    address =  c.getString(c.getColumnIndex("location_address"));
	    city =  c.getString(c.getColumnIndex("location_city"));
	    cp = c.getString(c.getColumnIndex("location_cp"));
	    address_line = c.getString(c.getColumnIndex("location_address_line"));
	    l.add(new Location(p, address, city, cp, address_line));
	}
	return l;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// on upgrade drop older tables
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLIDES);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
	// create new tables
	onCreate(db);
    }

    public void createLocation(Location l) {
	String selectQuery = " " + TABLE_LOCATIONS;
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put("location_uid", l.getId().toString());
	values.put("location_point", l.getGps().toSQLite());
	values.put("location_address", l.getAddress());
	values.put("location_city", l.getCity());
	values.put("location_cp", l.getCP());
	values.put("location_address_line", l.getAddressLine());
	

	db.insert(TABLE_LOCATIONS, null, values);
	db.close();
    }
}
