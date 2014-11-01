package com.libra.controller;

import java.util.ArrayList;

import com.libra.entity.HelpPlace;
import com.libra.service.HelpPlaceServiceImpl;
import com.mapswithme.maps.api.MWMPoint;

public class OfflineMapController {

	HelpPlaceServiceImpl service;
	
	public OfflineMapController() {
		service = new HelpPlaceServiceImpl();
	}
	
	public MWMPoint[] getAllHelpplaceSaved()
	{
		ArrayList<HelpPlace> helpPlaceList = service.getAllHelpPlacesOnDevice();
		MWMPoint[] mWMPoint  = new MWMPoint[helpPlaceList.size()];		
			for(int i=0;i<helpPlaceList.size();i+=1){
			mWMPoint[i] = helpPlaceList.get(i).toMWMPoint();
			}
		return mWMPoint;
	}
}
