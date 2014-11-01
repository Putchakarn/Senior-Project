package com.libra.calculation;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.libra.emergencyapp.MainActivity;
import com.libra.entity.HelpPlace;
import com.libra.service.HelpPlaceService;
import com.libra.service.HelpPlaceServiceImpl;

public class CheckingDistance {

	HelpPlaceService helpPlaceService = new HelpPlaceServiceImpl();
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371; // in km
	double latCurrent, lngCurrent; // same point
	double latTruning, lngTruning; // new point
	double latDistance, lngDistance;
	double defaultDistance = 5.0;
	double distance = 0;
	double resultDistance, resuleSqrt;

	int scope = 50; // again
	
	String url;
	ArrayList<HelpPlace> helpPlacesList;
	JSONArray jsonArray;
	JSONObject jsonObject;

	double scopeInMeter;
	
//	String helpPlaceName, address, telephoneNumber, id, category;
//	double lat, lng;

	public CheckingDistance() {
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public void calculateDistance(double latTruning, double lngTruning) {
		Log.i("Simple Log", "Lat: " + latTruning + "Lng:" + lngTruning);
		Log.i("Simple Log", "Scope: " + scope);
		this.latTruning = latTruning;
		this.lngTruning = lngTruning;
		
		// feck data
		latCurrent = 18.789643;	
		lngCurrent = 98.969758;
			
		latDistance = Math.toRadians(latCurrent - latTruning);
		lngDistance = Math.toRadians(lngCurrent - lngTruning);

		resultDistance = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(latCurrent))
				* Math.cos(Math.toRadians(latTruning))
				* Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

		resuleSqrt = 2 * Math.atan2(Math.sqrt(resultDistance), Math.sqrt(1 - resultDistance));

		distance = (AVERAGE_RADIUS_OF_EARTH * resuleSqrt);

		Log.i("Simple Log", "Distance: " + distance);
		
		if (distance > defaultDistance) {
			saveDistance();
		} else {

		}
	}

	public double getDistanceBetweenPoint() {
		return distance;
	}

	public void saveDistance() {

		helpPlaceService.deleteAllHelpPlaces();

		latCurrent = latTruning;
		lngCurrent = latTruning;
		
		scopeInMeter = scope*1000;

		try {
			url = "http://"+MainActivity.ip+"/helpPlace/scopeJson/"+latTruning+"/"+lngTruning+"/"+scopeInMeter;
//			url = "http://10.0.0.204:8089/helpPlace/scopeJson/"+latTruning+"/"+lngTruning+"/"+scopeInMeter;

			Log.i("Simple Log", "HHH: "+url);
			
			jsonObject = helpPlaceService.getJSONObjShowHelpPlacesInOnlinemap(url); 
			jsonArray = helpPlaceService.getJSONArrayToSaveInDB(jsonObject);

			helpPlacesList = helpPlaceService.getHelpPlacesList(jsonArray,jsonObject);
			helpPlaceService.insertHelpPlace(helpPlacesList);
			
			Log.i("Simple Log", "WW: "+helpPlaceService.insertHelpPlace(helpPlacesList).size());

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
