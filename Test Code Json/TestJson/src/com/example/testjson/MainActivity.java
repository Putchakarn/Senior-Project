package com.example.testjson;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;



import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1); 
        
		/*** Sample JSON Code ***'
		 	[{
			"MemberID":"1",
			"Name":"Nar",
			"Tel":"0896340383"
			},
			{
			"MemberID":"2",
			"Name":"Gii",
			"Tel":"0856459880"
			},
			{
			"MemberID":"3",
			"Name":"Bill",
			"Tel":"0876543210"
			},
			{
			"MemberID":"4",
			"Name":"Laa",
			"Tel":"0834598821"
			}]
		*/
		
		String strJSON = "[{\"MemberID\":\"1\",\"Name\":\"Nar\",\"Tel\":\"0896340383\"}" +
						 ",{\"MemberID\":\"2\",\"Name\":\"Gii\",\"Tel\":\"0856459880\"}" +
						 ",{\"MemberID\":\"3\",\"Name\":\"Bill\",\"Tel\":\"0876543210\"}"+
						 ",{\"MemberID\":\"4\",\"Name\":\"Laa\",\"Tel\":\"0834598821\"}]";

		String url = "http://docs.blackberry.com/sampledata.json";
		
		try {
			JSONArray data = new JSONArray(strJSON);
			
			ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;
			
			for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);
                
    			map = new HashMap<String, String>();
    			map.put("MemberID", c.getString("MemberID"));
    			map.put("Name", c.getString("Name"));
    			map.put("Tel", c.getString("Tel"));
    			MyArrList.add(map);
    			
			}


	        SimpleAdapter sAdap;
	        sAdap = new SimpleAdapter(MainActivity.this, MyArrList, R.layout.activity_column,
	                new String[] {"MemberID", "Name", "Tel"}, new int[] {R.id.ColMemberID, R.id.ColName, R.id.ColTel});      
	        lisView1.setAdapter(sAdap);
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
}
  
