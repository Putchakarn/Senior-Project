package com.libra.emergencyapp;
import java.util.ArrayList;

import org.json.JSONException;

import com.libra.calculation.CheckingDistance;
import com.libra.controller.OfflineMapController;
import com.libra.controller.OnlineMapController;
import com.libra.database.DatabaseConnection;
import com.libra.entity.HelpPlace;
import com.libra.searching.SearchingHelpPlaces;
import com.libra.singleton.DBConnectionSingleton;
import com.libra.singleton.HelpPlaceSingleton;
import com.libra.view.InformationView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapswithme.maps.api.MWMPoint;
import com.mapswithme.maps.api.MapsWithMeApi;

import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

public class MainActivity extends FragmentActivity {

	public static String ip = "10.70.27.150:8089";
	
	Button buttonPrev, buttonNext, buttonSearch;
	ViewSwitcher viewSwitcher;
	Animation slide_in_left, slide_out_right;

	GoogleMap mMap;
	Marker userMaker, mMarker;
	LocationManager lm;
	MWMPoint[] markers;

	double latCurrent, lngCurrent;
	int makersPoint;

	LatLng latandlngOfMaker;
	String nameOfMaker;
	String addressOfMaker;

	OfflineMapController offlineMap;
	OnlineMapController onlineMap;
	HelpPlaceSingleton singleton;
	//String url;
	
	SearchingHelpPlaces searchingHelpPlaces = new SearchingHelpPlaces();
	
	CheckingDistance checkingDistance = new CheckingDistance();

	
	ToggleButton btn_host, btn_police, btn_garage, btn_highway;
	int valueOfScope;
	SubMenu submenu;

	HelpPlace helpPlace,helpPlaceView;
	
	public static boolean onSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// create database connect
		DatabaseConnection db;
		db = new DatabaseConnection(MainActivity.this);
		db.selectDatabase();
		db.copyDatabase();
		DBConnectionSingleton.getInstance().setDB(db);

		// create objects
		offlineMap = new OfflineMapController();
		onlineMap = new OnlineMapController();
		
		onlineMap.setURL(
//				"http://10.70.26.226:8089/helpPlace/listJson");
//				"http://10.0.0.204:8089/helpPlace/listJson");
//				"http://"+ip+"/helpPlace/listJson");
				"http://"+ip+"/helpPlace/listJson");

		// create variable to check network connection
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		// condition check network connect
		if (networkInfo != null && networkInfo.isAvailable()
				&& networkInfo.isConnected()) {
			
			Log.i("Simple Log", "Internet connected");
			
			setContentView(R.layout.activity_main);
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap(); // //get online map
			// Searching
			
			
			try {
				for (HelpPlace helpPlace: onlineMap.createMarker()) {
				
					mMap.addMarker(new MarkerOptions()
					.position(new LatLng(helpPlace.getLat(), helpPlace.getLon()))
					.title(helpPlace.getName()).snippet(" "+helpPlace.getAddress()));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				public void onInfoWindowClick(Marker chooseMarker) {
					Intent selectedMarker = new Intent(getApplicationContext(),
							InformationView.class);

					
					
//					latandlngOfMaker = chooseMarker.getPosition();
					nameOfMaker = chooseMarker.getTitle().toString();
//					addressOfMaker = chooseMarker.getSnippet();
					
					
					helpPlaceView = searchingHelpPlaces.findHelpPlaceView(nameOfMaker);
					
					
					
					Log.i("Simple Log", "Name: "+nameOfMaker);
					
					selectedMarker.putExtra("name", helpPlaceView.getName());
					selectedMarker.putExtra("address", helpPlaceView.getAddress());
					selectedMarker.putExtra("phoneNumber", helpPlaceView.getPhoneNumber());
					selectedMarker.putExtra("category", helpPlaceView.getCategory());
					selectedMarker.putExtra("lat", helpPlaceView.getLat());
					selectedMarker.putExtra("lng", helpPlaceView.getLon());
					
					startActivity(selectedMarker);
				}
			});
			
			
			if (onSearch == true ) {
				Intent search = getIntent();
				
				String helpplaceName = search.getStringExtra("name");
				String address = search.getStringExtra("address");
				String phoneNumber = search.getStringExtra("phoneNumber");
				String category = search.getStringExtra("category");
				
				double lat = search.getDoubleExtra("latitude", latCurrent);
				double lng = search.getDoubleExtra("longitude",lngCurrent);
				
				addMarkerForSearch(lat,lng,helpplaceName,address);
			}
			

		} else {
			Log.i("Simple Log", "No Internet connected");
			markers = offlineMap.getAllHelpplaceSaved();
			final String title = markers.length == 1 ? markers[0].getName()
					: "Thailand Offline Map";
			showMarkerOfflinMap(markers, title);
			Log.i("Simple Log", "Check maker: " + markers.length);

		}
	
		// *************************************
		
//		checkingDistance.calculateDistance(19.789602, 99.974209); // Check insert DB
		
		// create action of button
		buttonPrev = (Button) findViewById(R.id.prev);
		buttonNext = (Button) findViewById(R.id.next);
		buttonSearch = (Button) findViewById(R.id.searching);

		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewswitcher);

		slide_in_left = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);
		slide_out_right = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);
		try {
			viewSwitcher.setInAnimation(slide_in_left);
			viewSwitcher.setOutAnimation(slide_out_right);

			buttonPrev.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					viewSwitcher.showPrevious();
				}
			});
			buttonNext.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					markers = offlineMap.getAllHelpplaceSaved();
					final String title = markers.length == 1 ? markers[0]
							.getName() : "Thailand Offline Map";
					showMarkerOfflinMap(markers, title);
				}
			});

			buttonSearch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent searchIntent = new Intent(getApplicationContext(),
							SearchingHelpPlaces.class);
					startActivity(searchIntent);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		btn_host = (ToggleButton) findViewById(R.id.button_host);
		btn_police = (ToggleButton) findViewById(R.id.button_police);
		btn_garage = (ToggleButton) findViewById(R.id.button_garage);
		btn_highway = (ToggleButton) findViewById(R.id.button_highway);
		
		btn_host.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				helpPlace = searchingHelpPlaces.findNearestHelpplace("hospital",latCurrent,lngCurrent);
				addMarkerForSearch(helpPlace.getLat(), helpPlace.getLon(), helpPlace.getName(), helpPlace.getAddress());
			}
		});
		
		btn_police.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				helpPlace = searchingHelpPlaces.findNearestHelpplace("police",latCurrent,lngCurrent);
				addMarkerForSearch(helpPlace.getLat(), helpPlace.getLon(), helpPlace.getName(), helpPlace.getAddress());
			}
		});
		
		btn_garage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				
				
				helpPlace = searchingHelpPlaces.findNearestHelpplace("garage",latCurrent,lngCurrent);
				addMarkerForSearch(helpPlace.getLat(), helpPlace.getLon(), helpPlace.getName(), helpPlace.getAddress());
			
				
				
			}
		});
		
		btn_highway.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				searchingHelpPlaces.findNearestHelpplace("highway",latCurrent,lngCurrent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		valueOfScope = 10;
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		submenu = menu.addSubMenu(0, Menu.FIRST, Menu.NONE, "Setting");
		submenu.setHeaderTitle("Set Saving Scope");
		submenu.setHeaderIcon(R.drawable.user_marker);
		submenu.add(0, 10, Menu.NONE, valueOfScope + " KM (defalut value)")
				.setChecked(true);
		submenu.add(0, 15, Menu.NONE, "15 KM");
		submenu.add(0, 20, Menu.NONE, "20 KM");
		submenu.add(0, 20, Menu.NONE, "25 KM");
		submenu.add(0, 20, Menu.NONE, "30 KM");
		submenu.setGroupCheckable(0, true, true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case 10:
			Log.i("Simple Log", "SSSSS " + valueOfScope);
			return true;
		case 15:
			valueOfScope = 15;
			checkingDistance.setScope(valueOfScope);
			submenu.getItem(1).setChecked(true);
			Log.i("Simple Log", "SSSSS " + valueOfScope);
			return true;
		case 20:
			valueOfScope = 20;
			checkingDistance.setScope(valueOfScope);
			submenu.getItem(2).setChecked(true);
			Log.i("Simple Log", "SSSSS " + valueOfScope);
			return true;
		case 25:
			valueOfScope = 25;
			checkingDistance.setScope(valueOfScope);
			submenu.getItem(3).setChecked(true);
			Log.i("Simple Log", "SSSSS " + valueOfScope);
			return true;
		case 30:
			valueOfScope = 30;
			checkingDistance.setScope(valueOfScope);
			submenu.getItem(4).setChecked(true);
			Log.i("Simple Log", "SSSSS " + valueOfScope);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showMarkerOfflinMap(MWMPoint[] points, String title) {
		MapsWithMeApi.showPointsOnMap(this, title,
				InformationView.getPendingIntent(this), points);
	}

	LocationListener listener = new LocationListener() {
		public void onLocationChanged(Location loc) {
			if (!onSearch) {
				LatLng coordinate = new LatLng(loc.getLatitude(),
						loc.getLongitude());
				latCurrent = loc.getLatitude();
				lngCurrent = loc.getLongitude();

				// send to check scope change
				
				//****** Õ‘Õ‘ ************
//				checkingDistance.calculateDistance(latCurrent, lngCurrent);
				
				if (mMarker != null)
					mMarker.remove();

				mMarker = mMap.addMarker(new MarkerOptions()
						.position(new LatLng(latCurrent, lngCurrent))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.user_marker))
						.title("I'm here"));

				// // increase progress 2
				if (getIntent() != null) {

					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
							coordinate, 13));
				}
			}
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};

	public void addMarkerForSearch(double lat, double lng, String name, String address)
	{
		mMap.addMarker(new MarkerOptions()
		.position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(
			    BitmapDescriptorFactory.HUE_GREEN))
		.title(name + "")
		.snippet(address+"").visible(true));
			
		LatLng positionSearch = new LatLng(lat, lng);
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
		positionSearch, 13));
	}
	
	
	public void eventButtonSearch() {
		btn_host.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

			}
		});
	}

	public void onResume() {
		super.onResume();
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean isNetwork = lm
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (isNetwork) {
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000,
					10, listener);
			Location loc = lm
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (loc != null) {
				latCurrent = loc.getLatitude();
				lngCurrent = loc.getLongitude();
			}
		}

		if (isGPS) {
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,
					listener);
			Location loc = lm
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (loc != null) {
				latCurrent = loc.getLatitude();
				lngCurrent = loc.getLongitude();
			}
		}
	}

	public void onPause() {
		super.onPause();
		lm.removeUpdates(listener);
	}
}