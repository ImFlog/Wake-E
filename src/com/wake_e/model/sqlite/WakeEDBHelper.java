/**
 * 
 */
package com.wake_e.model.sqlite;

import java.util.Collections;
import java.util.List;
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
import com.wake_e.tools.SlidesComparator;
import com.wake_e.utils.Slide;

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

    // Table Create Statements
    private static final String CREATE_TABLE_SLIDES= "CREATE TABLE "
	    + TABLE_SLIDES + "(slide_name VARCHAR(255) PRIMARY KEY,"
	    + " slide_class VARCHAR(255), slide_order INTEGER NOT NULL,"
	    + "slide_visible INTEGER NOT NULL)";

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public WakeEDBHelper(Context context, String name, CursorFactory factory,
	    int version) {
	super(context, name, factory, version);
	// TODO Auto-generated constructor stub
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
    public WakeEDBHelper(Context context, String name, CursorFactory factory,
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
	this.populateSlides(db);
    }


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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// on upgrade drop older tables
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLIDES);

	// create new tables
	onCreate(db);
    }

}
