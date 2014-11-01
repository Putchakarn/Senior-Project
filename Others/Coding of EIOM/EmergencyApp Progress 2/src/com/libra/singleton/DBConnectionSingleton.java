package com.libra.singleton;

import com.libra.database.DatabaseConnection;
import com.libra.singleton.DBConnectionSingleton;

public class DBConnectionSingleton {
	private static DBConnectionSingleton mainDB;
	private static DatabaseConnection db;

	public DBConnectionSingleton() {
	}

	public void setDB(DatabaseConnection db) {
		this.db = db;
	}

	public DatabaseConnection getDB() {
		return db;
	}

	public static DBConnectionSingleton getInstance() {
		if (mainDB == null)
			mainDB = new DBConnectionSingleton();
		return mainDB;
	}
}
