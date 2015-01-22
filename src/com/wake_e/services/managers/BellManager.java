package com.wake_e.services.managers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;



import android.content.Context;
import android.content.CursorLoader;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.wake_e.R;
import com.wake_e.model.sqlite.WakeEDBHelper;

public class BellManager {
	// SQLLite Helper
	private WakeEDBHelper db;
	private Context context;
	
	/**
	 * Constructor
	 * @param db
	 */
	public BellManager(Context c, WakeEDBHelper db) {
		this.db = db;
		this.context = c;
	}
	
	/**
	 * Define the alarm bell.
	 * @param file
	 */
	public void setBell(File file) {
		if (file != null && file.exists()) {
			db.setBell(file.getAbsolutePath());
		}
	}
	
	/**
	 * Get the alarm bell.
	 * @return File
	 */
	public File getBell() {
		String filename = db.getBell();
		File f;
		if (filename != null) {
			f = new File(filename);
			if (f.exists()) {
				return f;
			}
		}
		return getDefaultBell();
	}
	
	private File getDefaultBell() {
		File ringFile = new File(Environment.getExternalStorageDirectory().toString() + "/Ringtones", "heyhey.mp3");
		if (ringFile.exists() == false) {
			ringFile.mkdirs();
			try {
	        	InputStream is = context.getAssets().open("sounds/heyhey.mp3");
	        	OutputStream os = new FileOutputStream(ringFile);
		        byte[] buffer = new byte[1024];
		        int bytesRead;
		        //read from is to buffer
		        while((bytesRead = is.read(buffer)) !=-1){
		            os.write(buffer, 0, bytesRead);
		        }
		        is.close();
	            os.flush();
	            os.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
        return ringFile;
	}
}
