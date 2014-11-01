		package com.libra.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.libra.entity.HelpPlace;
import com.libra.service.HelpPlaceService;
import com.libra.service.HelpPlaceServiceImpl;

public class OnlineMapController {
	
	HelpPlaceService helpPlaceService;
	
	JSONArray jsonArray;
	JSONObject jsonObj;
	
	String url;
	
	ArrayList<HelpPlace> helpPlacesList = new ArrayList<HelpPlace>();
	
	Context mContext;
	Intent intent;
	boolean result = false;
	
	public OnlineMapController() {
		helpPlaceService = new HelpPlaceServiceImpl();
	}
	
	public void setURL(String url)
	{
		this.url = url;
		jsonObj = helpPlaceService.getJSONObjShowHelpPlacesInOnlinemap(url);
	}
	
	public HelpPlace findNearestHelpPlace(String url)
	{
		jsonObj = helpPlaceService.findJSONObjNearestHelpPlace(url);
		try {
			jsonArray = helpPlaceService.findJSONArrayNearestHelppalce(jsonObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return helpPlaceService.getNearestHelpPlace(jsonArray,jsonObj);
	}
	
	public ArrayList<HelpPlace> createMarker() throws JSONException {
		try {
			jsonObj = helpPlaceService.getJSONObjShowHelpPlacesInOnlinemap(url);
			jsonArray = helpPlaceService.getJSONArrayShowHelpPlacesInOnlinemap(jsonObj);
			
			helpPlacesList = helpPlaceService.getHelpPlacesList(jsonArray,jsonObj);		

		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return helpPlacesList;
	}
	
	public ArrayList<HelpPlace> setHelpPlacesForSearching()
	{
		try {
			jsonObj = helpPlaceService.getJSONObjShowHelpPlacesInOnlinemap(url);
			jsonArray = helpPlaceService.getJSONArrayShowHelpPlacesInOnlinemap(jsonObj);
			
			helpPlacesList = helpPlaceService.getHelpPlacesList(jsonArray,jsonObj);		

		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return helpPlacesList;
	}
	
	public boolean getShowSearchPoint() {
		if(result==true)
		{
			mContext.startActivity(intent);
		}
			return result;
	}
}