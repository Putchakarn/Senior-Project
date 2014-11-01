package com.libra.DAO;

import java.util.ArrayList;
import android.util.Log;
import com.libra.entity.HelpPlace;
import com.libra.singleton.DBConnectionSingleton;

public class HelpPlaceDAOImpl implements HelpPlaceDAO{
	
	public HelpPlaceDAOImpl(){
	}
	
	@Override
	public ArrayList<HelpPlace> getAllHelpPlaces() {
		return DBConnectionSingleton.getInstance().getDB().getAllHelpPlaces();
	}
	

	@Override
	public boolean insertHelpPlace(ArrayList<HelpPlace> helpPlace) {
		DBConnectionSingleton.getInstance().getDB().insertHelpPlaces(helpPlace);
		Log.i("Simple LogX2", "Test" );
		Log.i("Simple Log111", "YYYYYYYYYYYYYYYYYYY:");
		return true;
	}

	@Override
	public boolean deleteAllHelpPlaces(){
		DBConnectionSingleton.getInstance().getDB().deleteAllHelpPlaces();
		return true;
		
	}
}
