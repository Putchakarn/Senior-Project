package com.libra.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.libra.DAO.HelpPlaceDAO;
import com.libra.entity.HelpPlace;

public interface HelpPlaceService {

	// public JSONObject getJsonObjByURL(String url);

	// public JSONArray getHelpPlacesByJsonOBJ(JSONObject jsonOBJ)
	// throws JSONException;

	// Offline
	
	public void setHelpPlaceDAO(HelpPlaceDAO dao);
	
	public ArrayList<HelpPlace> getAllHelpPlacesOnDevice();

	public ArrayList<HelpPlace> insertHelpPlace(ArrayList<HelpPlace> helpPlace);
	
	public boolean deleteAllHelpPlaces();
	
	// Online
	public JSONObject getJSONObjShowHelpPlacesInOnlinemap(String url);

	public JSONArray getJSONArrayShowHelpPlacesInOnlinemap(JSONObject jsonOBJ)
			throws JSONException;

	// Save help places into DB
	public JSONObject getJSONObjToSaveInDB(String url);

	public JSONArray getJSONArrayToSaveInDB(JSONObject jsonOBJ)
			throws JSONException;

	

	// find nearest help places
	public JSONObject findJSONObjNearestHelpPlace(String url);

	public JSONArray findJSONArrayNearestHelppalce(JSONObject jsonOBJ)
			throws JSONException;

	public ArrayList<HelpPlace> getHelpPlacesList(JSONArray jsonArray,
			JSONObject jsonObject);
	
	public HelpPlace getNearestHelpPlace(JSONArray jsonArray,
			JSONObject jsonObject);
}
