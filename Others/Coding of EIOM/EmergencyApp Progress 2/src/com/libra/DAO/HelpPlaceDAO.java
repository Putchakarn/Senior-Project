package com.libra.DAO;

import java.util.ArrayList;
import com.libra.entity.HelpPlace;

public interface HelpPlaceDAO {
	 
	 public boolean insertHelpPlace(ArrayList<HelpPlace> helpPlace);
	 
	 public ArrayList<HelpPlace> getAllHelpPlaces();
	
	 public boolean deleteAllHelpPlaces();
}
