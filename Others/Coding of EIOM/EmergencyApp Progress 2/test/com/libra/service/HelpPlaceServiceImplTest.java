package com.libra.service;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.libra.DAO.HelpPlaceDAO;
import com.libra.entity.HelpPlace;

public class HelpPlaceServiceImplTest {
	
	String jsonObjActual,jsonObjExpected;
	
	JSONObject jsonObject = new JSONObject();	
	JSONArray jsonArray = new JSONArray();
	JSONObject actual; JSONObject expect;
	
	ArrayList <HelpPlace> helpPlacesList = new ArrayList<HelpPlace>();
	
	boolean returnBoolean;
	
	int size;
	
	HelpPlaceServiceImpl helpPlaceServiceImpl = null;
	
	HelpPlace h1 = new HelpPlace(1,"Maharaj Nakorn Chiang mai Hospital"
			,"110 Suthep Rd, Mueang Chiang Mai District, Chiang Mai"
			,"053947700",18.789602,98.974209);
	
	HelpPlace h2 = new HelpPlace(2,"Lanna Hospital"
			,"Chang Phuak Amphoe Mueang Chiang Mai, Chang Wat Chiang Mai"
			,"053999758",18.812723,98.991151);
	
	HelpPlace h3 = new HelpPlace(3,"Muang Chiang Mai Police Station"
			,"Si Phum Mueang Chiang Mai District, Chiang Mai"
			,"053276040",18.788341,98.974209);
	
	HelpPlace h4 = new HelpPlace(4,"Chang Phueak Police Station"
			,"Mueang Chiang Mai District, Chiang Mai"
			,"053218443",18.818822,98.975520);
	
	HelpPlace h5 = new HelpPlace(5,"Kriangkrai Konlakan Garage"
			,"7/2, See Ping Mueang Road, Tambon Chang Khlan, Amphoe Mueang, Chiang Mai"
			,"053244599",18.771195,98.983062);

	
	String urlAllHelplace = "http://http://10.70.26.226/8089/helpPlace/listJson";	
	String urlSaveDB = "http://http://10.70.26.226:8089/helpPlace/scopeJson/19.789602/99.974209/30000.0";	
	String urlNearest = "http://http://10.70.26.226:8089/helpPlace/nearestJson/18.789643/98.969758/2";
	
	@Before
	public void setUp() throws Exception {
		helpPlaceServiceImpl = new HelpPlaceServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testInsertHelpPlace()
	{
		ArrayList <HelpPlace> helpPlacesList = new ArrayList<HelpPlace>();
		
		helpPlacesList.add(h1);
		helpPlacesList.add(h2);
		helpPlacesList.add(h3);
		helpPlacesList.add(h4);
		helpPlacesList.add(h5);			
	
		HelpPlaceDAO mockDAO = mock(HelpPlaceDAO.class);
		when(mockDAO.insertHelpPlace(helpPlacesList)).thenReturn(true);
		
		helpPlaceServiceImpl.setHelpPlaceDAO(mockDAO);
		
		assertEquals(helpPlacesList, helpPlaceServiceImpl.insertHelpPlace(helpPlacesList));
		assertNotNull(helpPlaceServiceImpl.insertHelpPlace(helpPlacesList));
	}
	
	@Test
	public void testDeleteAllHelpPlaces()
	{
		HelpPlaceDAO mockDAO = mock(HelpPlaceDAO.class);
		when(mockDAO.deleteAllHelpPlaces()).thenReturn(true);
		
		helpPlaceServiceImpl.setHelpPlaceDAO(mockDAO);
		
		
		assertEquals(true, helpPlaceServiceImpl.deleteAllHelpPlaces());
	
		
	}
	
//	@Test
//	public void testGetJSONObjShowHelpPlacesInOnlinemap() 
//	{
////		jsonObjActual = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlAllHelplace).toString();		
//		jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlAllHelplace);
//		
//		assertEquals(jsonObject.length(),1); 
//	}
//	
//	@Test
//	public void testGetJSONArrayShowHelpPlacesInOnlinemap()
//	{
//		try {
//		
//			jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlAllHelplace);
//			jsonArray = helpPlaceServiceImpl.getJSONArrayShowHelpPlacesInOnlinemap(jsonObject);
//		
//			assertNotNull(jsonArray.get(0));
//			assertEquals(5, jsonArray.length());
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testGetJSONObjToSaveInDB()
//	{
//		jsonObjActual = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlSaveDB).toString();		
//		jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlSaveDB);
//		
//		assertNotNull(jsonObjActual);
//		assertEquals(jsonObject.length(),1); 
//	}
//	
//	@Test
//	public void testGetJSONArrayToSaveInDB()
//	{
//		try {
//		
//			jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlSaveDB);
//			jsonArray = helpPlaceServiceImpl.getJSONArrayShowHelpPlacesInOnlinemap(jsonObject);
//		
//			assertNotNull(jsonArray.get(0));
//			assertEquals(1, jsonArray.length());
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testFindJSONObjNearestHelpPlace()
//	{
//		jsonObjActual = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlNearest).toString();		
//		jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlNearest);
//		
//		assertNotNull(jsonObjActual);
//		assertEquals(jsonObject.length(),1); 
//	}
//	
//	@Test
//	public void testFindJSONArrayNearestHelpPlace()
//	{
//		try {
//			
//			jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlNearest);
//			jsonArray = helpPlaceServiceImpl.getJSONArrayShowHelpPlacesInOnlinemap(jsonObject);
//		
//			assertNotNull(jsonArray.get(0));
//			assertEquals(1, jsonArray.length());
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	@Test
//	public void testGetHelpPlacesList()
//	{
//		try {
//			
//			jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlAllHelplace);
//			jsonArray = helpPlaceServiceImpl.getJSONArrayShowHelpPlacesInOnlinemap(jsonObject);
//			
//			helpPlacesList = helpPlaceServiceImpl.getHelpPlacesList(jsonArray, jsonObject);
//			
//			String nameActul = helpPlacesList.get(2).getName();
//			String nameExpect = "Chiang Rai Police Station";
//			
//			assertEquals(nameExpect,nameActul);
//			assertEquals(5, helpPlacesList);
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testGetNearestHelpPlace()
//	{
//		try {
//			
//			jsonObject = helpPlaceServiceImpl.getJSONObjShowHelpPlacesInOnlinemap(urlAllHelplace);
//			jsonArray = helpPlaceServiceImpl.getJSONArrayShowHelpPlacesInOnlinemap(jsonObject);
//			
//			helpPlacesList = helpPlaceServiceImpl.getHelpPlacesList(jsonArray, jsonObject);
//			
//			String nameActul = helpPlacesList.get(0).getName();
//			String nameExpect = "Chiang Rai Police Station";
//			
//			assertEquals(nameExpect,nameActul);
//			assertEquals(1, helpPlacesList);
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testGetHelpPlacesByJsonOBJ(){
//		helpPlaceServiceImpl = new HelpPlaceServiceImpl();
//		JSONObject jsonObject;
//		String jsonArray = 
//				"[{\"Address\":\"110 Suthep Rd, Mueang Chiang Mai District, Chiang Mai\","
//				        + "\"PhoneNumber\":\"053947700\","
//				        + "\"Latitude\":\"18.789602\","
//				        + "\"Longitude\":\"98.974209\","
//				        + "\"Name\":\"Maharaj Nakorn Chiang Mai Hospital\"}]";
//
//		try {			
//			jsonObject = new JSONObject("{\"Android\":[{\"Name\":\"Maharaj Nakorn Chiang Mai Hospital\","
//					+ "\"Address\":\"110 Suthep Rd, Mueang Chiang Mai District, Chiang Mai\","
//			        + "\"PhoneNumber\":\"053947700\","
//			        + "\"Latitude\":\"18.789602\","
//			        + "\"Longitude\":\"98.974209\"}]}");
//			
//			JSONArray actual = helpPlaceServiceImpl.getJSONArrayShowHelpPlacesInOnlinemap(jsonObject);
//			JSONArray expect = new JSONArray(jsonArray);
//			
//			assertEquals(expect.length(),actual.length()); // pass
//			//assertEquals(expect.get(0), actual.get(0)); // fail
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testGetHelpPlacesOnDevice()
//	{
//		helpPlaceServiceImpl = new HelpPlaceServiceImpl();
//		ArrayList<HelpPlace> helpPlaceList = new ArrayList<HelpPlace>();
//		helpPlaceList.add(h1);
//		helpPlaceList.add(h2);
//		helpPlaceList.add(h3);
//		helpPlaceList.add(h4);
//		helpPlaceList.add(h5);		
////		helpPlaceServiceImpl.setHelpPlacesOnDeice(helpPlaceList);
////		HelpPlace[] helpPlacesOnDevice = helpPlaceServiceImpl.getHelpPlacesOnDevice();
////		int actualSize = helpPlaceServiceImpl.getHelpPlacesOnDevice().length;
////		
////		assertEquals(5, actualSize); // pass
////		assertEquals(h1,helpPlacesOnDevice[0]); // pass
////		assertEquals(h2,helpPlacesOnDevice[1]); // pass
////		assertEquals(h3,helpPlacesOnDevice[2]); // pass
////		assertEquals(h4,helpPlacesOnDevice[3]); // pass
////		assertEquals(h5,helpPlacesOnDevice[4]); // pass
//	}
}
