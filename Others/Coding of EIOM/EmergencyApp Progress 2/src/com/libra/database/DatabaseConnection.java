package com.libra.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.libra.entity.HelpPlace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseConnection extends SQLiteOpenHelper {

	public static final String DATABASE_PATH = "/data/data/com.libra.emergencyapp/databases/";
	public static final String DATABASE_NAME = "myDB.sqlite";
	public static final String TABLE_NAME = "helpPlaces";
	public static final int DATABASE_VERSION = 1;

	public SQLiteDatabase dbSQLite;
	private final Context context;

	public DatabaseConnection(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS helpPlaces (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,name varchar NOT NULL,address varchar NOT NULL,telephone varchar NOT NULL,latitude double,longitude double)");
		Log.d("CREATE TABLE", "Create Table Successful");
	}

	public void selectDatabase() {
		dbSQLite = this.getWritableDatabase();
	}

	public void copyDatabase() {
		InputStream is = null;
		OutputStream us = null;
		String databasePath = DATABASE_PATH + DATABASE_NAME;

		try {
			is = context.getAssets().open(DATABASE_NAME);
			us = new FileOutputStream(databasePath);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				us.write(buffer, 0, length);
			}

			us.flush();
			us.close();
			is.close();

		} catch (IOException e) {
			throw new Error("Problem copying database from resource");
		}
	}

	public ArrayList<HelpPlace> getAllHelpPlaces() {
		ArrayList<HelpPlace> datalist = new ArrayList<HelpPlace>();
		dbSQLite = this.getReadableDatabase();
		String strSQL = "SELECT * FROM " + TABLE_NAME;
		Cursor cursor = dbSQLite.rawQuery(strSQL, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					datalist.add(new HelpPlace
							(cursor.getInt(0), 
							 cursor.getString(1), 
							 cursor.getString(2), 
							 cursor.getString(3), 
							 cursor.getDouble(4),
							 cursor.getDouble(5)
							));
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return datalist;
	}

	public long insertHelpPlaces(ArrayList<HelpPlace> helpPlacesList) {
		try {

			for (HelpPlace helpPlace : helpPlacesList) {

				dbSQLite = this.getWritableDatabase();

				ContentValues Val = new ContentValues();
				Val.put("id", helpPlace.getId());
				Val.put("name", helpPlace.getName());
				Val.put("address", helpPlace.getAddress());
				Val.put("telephone", helpPlace.getPhoneNumber());
				Val.put("latitude", helpPlace.getLat());
				Val.put("longitude", helpPlace.getLon());

				long rows = dbSQLite.insert(TABLE_NAME, null, Val);
				Log.i("Simple Log", "ROW: " + rows);
				
				return rows;
				
				
				
			}
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	public boolean deleteAllHelpPlaces() {
		Log.i("Simple LogX1", "Test");
		String strSQL = "DELETE FROM " + TABLE_NAME;
		dbSQLite.delete(TABLE_NAME, null, null);

		Log.i("Simple Log4", "Test deelte:" + getAllHelpPlaces().size());
		return false;
	}

	// public int getHelpPlaceCount() {
	// int count=0;
	// String countQuery = "SELECT  * FROM " + TABLE_NAME;
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor cursor = db.rawQuery(countQuery, null);
	// while (cursor.moveToNext()) {
	// count++;
	// }
	// cursor.close();
	// db.close();
	// // return count
	// return count;
	// }
	// public String getHelpPlaceNameById(int id) {
	// int count=0;
	// String countQuery = "SELECT  * FROM " + TABLE_NAME+" where id="+id;
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor cursor = db.rawQuery(countQuery, null);
	// while (cursor.moveToNext()) {
	// return cursor.getString(1);
	// }
	// cursor.close();
	// db.close();
	// // return count
	// return "";
	// }

}
