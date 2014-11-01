package com.libra.service;

import android.util.Log;

import com.libra.DAO.HelpPlaceDAO;
import com.libra.DAO.HelpPlaceDAOImpl;
import com.libra.entity.HelpPlace;
import com.libra.json.JSONParser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HelpPlaceServiceImpl implements HelpPlaceService {

	private JSONParser jParser = new JSONParser();
	private JSONObject JSONObjHelpPlacesInOnlineMap,JSONObjToSaveHelpPlacesInDB,JSONObjNearestHelpplace;
	private JSONArray helpPlacesShowInOnlineMap;

	private HelpPlaceDAO dao = new HelpPlaceDAOImpl();
	boolean answer;
	private ArrayList<HelpPlace> helpPlacesList = new ArrayList<HelpPlace>();
	private String helpPlaceName, address, phoneNumber, category;
	private Integer id;
	private double lat, lng;
	
	HelpPlace helpPlace;
	
	@Override
	public JSONObject getJSONObjShowHelpPlacesInOnlinemap(String url)
	{
		JSONObjHelpPlacesInOnlineMap = jParser.getJSONFromUrl(url);	
		return JSONObjHelpPlacesInOnlineMap;
	}

	@Override
	public JSONArray getJSONArrayShowHelpPlacesInOnlinemap(JSONObject jsonOBJ)
			throws JSONException {
		helpPlacesShowInOnlineMap = jsonOBJ.getJSONArray("Helpplaces");
		return helpPlacesShowInOnlineMap;
	}

	@Override
	public JSONObject getJSONObjToSaveInDB(String url) 
	{
		JSONObjToSaveHelpPlacesInDB = jParser.getJSONFromUrl(url);
		return JSONObjToSaveHelpPlacesInDB;
	}

	@Override
	public JSONArray getJSONArrayToSaveInDB(JSONObject jsonOBJ)
			throws JSONException {
		return jsonOBJ.getJSONArray("HelpplacesInScope");
	}

	@Override
	public ArrayList<HelpPlace> getAllHelpPlacesOnDevice() {
		return dao.getAllHelpPlaces();
	}

	@Override
	public ArrayList<HelpPlace> insertHelpPlace(ArrayList<HelpPlace> helpPlaces) {
			dao.insertHelpPlace(helpPlaces);
		return helpPlaces;
	}

	@Override
	public JSONObject findJSONObjNearestHelpPlace(String url) {
		JSONObjNearestHelpplace = jParser.getJSONFromUrl(url);
		return JSONObjNearestHelpplace;
	}

	@Override
	public JSONArray findJSONArrayNearestHelppalce(JSONObject jsonOBJ)
			throws JSONException {
		return jsonOBJ.getJSONArray("NearestHelpPlace");
	}

	@Override
	public boolean deleteAllHelpPlaces() {
		dao.deleteAllHelpPlaces();
		return true;
	}

	@Override
	public ArrayList<HelpPlace> getHelpPlacesList(JSONArray jsonArray,
			JSONObject jsonObject) {
	
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);

				id = jsonObject.getInt("id");
				helpPlaceName = jsonObject.getString("name");
				address = jsonObject.getString("address");
				phoneNumber = jsonObject.getString("phoneNumber");
				category = jsonObject.getString("category");
				lat = jsonObject.getDouble("latitude");
				lng = jsonObject.getDouble("longitude");

				Log.i("Simple Log", "Helpplace: " + id+helpPlaceName+address+lat+" "+lng);
				
				helpPlacesList.add(new HelpPlace(id, helpPlaceName, address,
						phoneNumber, lat, lng));
				}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return helpPlacesList;
	}

	@Override
	public HelpPlace getNearestHelpPlace(JSONArray jsonArray, JSONObject jsonObject) {
		try {
				jsonObject = jsonArray.getJSONObject(0);

				id = jsonObject.getInt("id");
				helpPlaceName = jsonObject.getString("name");
				address = jsonObject.getString("address");
				phoneNumber = jsonObject.getString("phoneNumber");
				category = jsonObject.getString("category");
				lat = Double.parseDouble(jsonObject.getString("latitude"));
				lng = Double.parseDouble(jsonObject.getString("longitude"));

				helpPlace = new HelpPlace(id, helpPlaceName, address,
						phoneNumber, lat, lng);
			
				Log.i("Simple Log", "nearestHelpplace: " + id+helpPlaceName+address+lat+" "+lng);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return helpPlace;
	}

	@Override
	public void setHelpPlaceDAO(HelpPlaceDAO dao) {
		this.dao = dao;
	}
}
