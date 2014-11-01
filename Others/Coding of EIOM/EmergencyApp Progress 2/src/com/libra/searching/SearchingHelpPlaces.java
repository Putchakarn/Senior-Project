package com.libra.searching;

import java.util.ArrayList;
import java.util.Locale;

import com.libra.controller.OnlineMapController;
import com.libra.emergencyapp.MainActivity;
import com.libra.emergencyapp.R;
import com.libra.entity.HelpPlace;
import com.libra.view.InformationView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

public class SearchingHelpPlaces extends Activity {

	// private String urlForSearching =
	// "http://androidexample.com/media/webservice/JsonReturn.php";
	private ArrayList<HelpPlace> helpPlaces;
	private OnlineMapController onlineSearching;

	// Declare Variables
	private ListView list;
	private ListViewAdapter adapter;
	private EditText editSearch;
	private ArrayList<HelpPlace> arrayList = new ArrayList<HelpPlace>();

	// String helpPlaceName, address, telephoneNumber, id, category;
	// double lat, lng;
	String userLat, userLng;
	int cateID;
	// JSONArray jsonArray;
	// JSONObject jsonObject;
	String url;

	HelpPlace helpPlace;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);

		onlineSearching = new OnlineMapController();
		onlineSearching.setURL("http://" + MainActivity.ip
				+ "/helpPlace/listJson");

		helpPlaces = new ArrayList<HelpPlace>();
		helpPlaces = onlineSearching.setHelpPlacesForSearching();

		for (int i = 0; i < helpPlaces.size(); i++) {
			helpPlaces.get(i);
		}

		// Locate the ListView in listview_main.xml
		list = (ListView) findViewById(R.id.listview);

		for (int i = 0; i < helpPlaces.size(); i++) {
			// Binds all strings into an array
			arrayList.add(helpPlaces.get(i));
		}

		// Pass results to ListViewAdapter Class
		adapter = new ListViewAdapter(this, arrayList);

		// Binds the Adapter to the ListView
		list.setAdapter(adapter);

		// Locate the EditText in listview_main.xml
		editSearch = (EditText) findViewById(R.id.search);

		// Capture Text in EditText
		editSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = editSearch.getText().toString()
						.toLowerCase(Locale.getDefault());
				adapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
		});
	}

	public HelpPlace findHelpPlaceView(String name) {
		helpPlace = new HelpPlace();

		onlineSearching = new OnlineMapController();
		onlineSearching.setURL("http://" + MainActivity.ip
				+ "/helpPlace/listJson");

		helpPlaces = new ArrayList<HelpPlace>();
		helpPlaces = onlineSearching.setHelpPlacesForSearching();

		for (int i = 0; i < helpPlaces.size(); i++) {
			helpPlaces.get(i);
		}

		Log.i("Simple Log", "SSSS1: " + helpPlaces.get(0).getName());

		for (int i = 0; i < helpPlaces.size(); i++) {
			if (name.equals(helpPlaces.get(i).getName())) {
				helpPlace = helpPlaces.get(i);
			}

		}
		return helpPlace;
	}

	public HelpPlace findNearestHelpplace(String categoryHelpplace, double lat, // change
																				// return
																				// value
			double lng) {
		onlineSearching = new OnlineMapController();
		userLat = Double.toString(lat);
		userLng = Double.toString(lng);

		url = "http://" + MainActivity.ip + "/helpPlace/nearestJson" + "/"
				+ userLat + "/" + userLng + "/";

		if (categoryHelpplace == "hospital") {
			cateID = 1;
			onlineSearching.findNearestHelpPlace(url + cateID);

		} else if (categoryHelpplace == "police") {
			cateID = 2;
			onlineSearching.findNearestHelpPlace(url + cateID);

		} else if (categoryHelpplace == "garage") {
			cateID = 3;
			onlineSearching.findNearestHelpPlace(url + cateID);

		} else if (categoryHelpplace == "highway") {
			cateID = 4;
			// onlineSearching.findNearestHelpPlace(url + cateID);
		}

		return onlineSearching.findNearestHelpPlace(url + cateID);

	}
}
