package com.libra.view;

import com.mapswithme.maps.api.MWMResponse;

import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.libra.emergencyapp.MainActivity;
import com.libra.emergencyapp.*;
import com.libra.phoneCall.PhoneCallListener;

public class InformationView extends Activity {
	
	public static String EXTRA_FROM_MWM = "from-maps-with-me";
	private Button callButton;
	private TextView txtName, txtAddress, txtLatitude, txtLongitude, txtPhoneNumber;
	private String telNumOffline = null, telNumOnline = null;
	private String name, address, telephone;
	private double latitude, longitude;
	
	
	
	String helpPace_name;
	String helpPace_address;
	String helpPace_phoneNumber;
	String lat,lng;
	
	
	
	public InformationView() {
	}

	public static PendingIntent getPendingIntent(Context context) {
		Intent i = new Intent(context, InformationView.class);
		i.putExtra(EXTRA_FROM_MWM, true);
		return PendingIntent.getActivity(context, 0, i, 0);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		// *************** Online Part *************** 
		txtName = (TextView) findViewById(R.id.txtName);
		txtAddress = (TextView) findViewById(R.id.textDetails);
		txtLatitude = (TextView) findViewById(R.id.textLat);
		txtLongitude = (TextView) findViewById(R.id.textLon);
		txtPhoneNumber = (TextView) findViewById(R.id.textPhoneNum);
		
			helpPace_name = getIntent().getStringExtra("name");
			helpPace_address = getIntent().getStringExtra("address");
			helpPace_phoneNumber = getIntent().getStringExtra("phoneNumber");
			
			lat = Double.toString(getIntent().getDoubleExtra("lat", 18.57563));
			lng = Double.toString(getIntent().getDoubleExtra("lng",85.5471));
			
			
		
		txtName.setText(helpPace_name);
		txtAddress.setText(helpPace_address);
		txtPhoneNumber.setText(helpPace_phoneNumber);
		txtLatitude.setText(lat);
		txtLongitude.setText(lng);
		
		handleIntent(getIntent());

		// *************** Phone Call ***************
		callButton = (Button) findViewById(R.id.call_button);

		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,
				PhoneStateListener.LISTEN_CALL_STATE);

		// add button listener
		callButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (helpPace_phoneNumber != null) {
					
					// *** Input telephone number from clicked maker online map
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + helpPace_phoneNumber)); 
					startActivity(callIntent);

				} else {
					// *** Input telephone number from clicked maker offline map
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + telNumOffline)); 
					startActivity(callIntent);
				}

			}
		});		
	}
	
	 @Override
	    public void onBackPressed() {
	    Log.d("CDA", "onBackPressed Called");
	    com.libra.emergencyapp.MainActivity.onSearch = false;
	    Intent setIntent = new Intent(this,MainActivity.class);
        startActivity(setIntent); 
        finish();
        return;

	    }  

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    onBackPressed();
	    return true;
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) { // ////ใส่ข้อมูลในหน้า page data
												// จาก offline map

		if (intent.getBooleanExtra(EXTRA_FROM_MWM, false)) {
			final MWMResponse response = MWMResponse.extractFromIntent(this,
					intent);

			if (response.getPoint() != null) {
				String addressAndTel = response.getPoint().getId();
				Log.i("Info: ", "Address and Teliphone No. : " + addressAndTel);

				// ****set value into text view offline map****
				String[] addressTel = addressAndTel.split("\\+");
				address = addressTel[0];
				telephone = addressTel[1];
				name = response.getPoint().getName();
				latitude = response.getPoint().getLat();
				longitude = response.getPoint().getLon();
				telNumOffline = telephone; // *** set telephone number
				
				String showLat = "Latitude: "+String.valueOf(latitude);
				String showLon = "Longitude: "+String.valueOf(longitude);

				txtName.setText(name);
				txtAddress.setText(address);
				txtPhoneNumber.setText(telNumOffline);
				txtLatitude.setText(showLat);
				txtLongitude.setText(showLon);

			}
		}
	}

}