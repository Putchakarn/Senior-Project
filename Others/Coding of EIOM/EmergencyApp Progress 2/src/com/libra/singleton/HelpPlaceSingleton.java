package com.libra.singleton;

import com.libra.service.HelpPlaceService;
import com.libra.service.HelpPlaceServiceImpl;

public class HelpPlaceSingleton {
	private static HelpPlaceService helpPlace;
	
	public static HelpPlaceService getHelpPlaceService(){			
			helpPlace = new HelpPlaceServiceImpl();
		return helpPlace;
	}
	
	
	
	
}
