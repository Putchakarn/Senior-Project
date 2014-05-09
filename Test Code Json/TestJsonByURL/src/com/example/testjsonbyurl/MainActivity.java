
package com.example.testjsonbyurl;


import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List;

import org.json.JSONArray; 
import org.json.JSONException; 
import org.json.JSONObject; 

import com.jitesh.androidjsonparser.R; 

import android.os.AsyncTask; 
import android.os.Bundle;
import android.app.Activity; 
import android.app.ListActivity; 
import android.app.ProgressDialog; 
import android.content.Context; 
import android.content.Intent; 
import android.util.Log;
import android.view.Menu; 
import android.view.View; 
import android.widget.AdapterView; 
import android.widget.ListAdapter; 
import android.widget.ListView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 
import android.widget.Toast; 
import android.widget.AdapterView.OnItemClickListener; 

public class MainActivity extends ListActivity 
{ 
	private static String url = "http://192.168.1.20/testJson/testJson.php";
	JSONArray foodArray;
	ArrayList<HashMap<String, String>> foodList = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JSONParser jParser = new JSONParser();
		// Read data from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		try {
			foodArray = json.getJSONArray("foods"); // the word “foods” from
													// JSON
			for (int i = 0; i < foodArray.length(); i++) {
				JSONObject f = foodArray.getJSONObject(i);
				// Storing each JSON item in variable
				String foodid = f.getString("food_id");
				String foodname = f.getString("food_name");
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				// adding each child node to HashMap key => value
				map.put("id", String.valueOf(i));
				map.put("food_id", foodid);
				map.put("food_name", foodname);
				// adding HashList to ArrayList
				foodList.add(map);
			}
			
			
			
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		// Create List from foodList and show data in custome_row_view.xml
		ListAdapter adapter = new SimpleAdapter(MainActivity.this, foodList,
				R.layout.custom_list_view,
				new String[] {"food_id", "food_name" }, new int[] {R.id.item_id, R.id.item_name});
		setListAdapter(adapter);
		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv
						.getItemAtPosition(position);
				
				Toast.makeText(MainActivity.this, o.get("food_name"),
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}