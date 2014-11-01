package com.libra.searching;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.libra.emergencyapp.MainActivity;
import com.libra.emergencyapp.R;
import com.libra.entity.HelpPlace;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	private Context mContext;
	private LayoutInflater inflater;
	private List<HelpPlace> helpPlacesList = null;
	private ArrayList<HelpPlace> arrayList;

	public ListViewAdapter(Context context, List<HelpPlace> helpPlacesList) {
		mContext = context;		
		inflater = LayoutInflater.from(mContext);
		
		this.helpPlacesList = helpPlacesList;
		this.arrayList = new ArrayList<HelpPlace>();
		this.arrayList.addAll(helpPlacesList);
	}

	public class ViewHolder {
		TextView hName, hAddress;
	}

	@Override
	public int getCount() {
		return helpPlacesList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			holder.hName = (TextView) view.findViewById(R.id.name);
			holder.hAddress = (TextView) view.findViewById(R.id.category);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.hName.setText(helpPlacesList.get(position).getName()+"");
		holder.hAddress.setText(helpPlacesList.get(position).getAddress()+"");
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				SearchingHelpPlaces s = new SearchingHelpPlaces();
//				s.showSearchPoint(mContext,hList.get(position));
				com.libra.emergencyapp.MainActivity.onSearch = true;
				
//				ArrayList<HelpPlace> hList = new ArrayList<HelpPlace>();
				
				Log.i("DDDDD", "EEEEE:"+helpPlacesList.get(position).toString()+" 5555");
				
			//	hList.set(0, helpPlacesList.get(position));
				
				Intent intent = new Intent(mContext, MainActivity.class);
				
				
				intent.putExtra("name",helpPlacesList.get(position).getName());
				intent.putExtra("address",helpPlacesList.get(position).getAddress());
				intent.putExtra("phoneNumber",helpPlacesList.get(position).getPhoneNumber());
				intent.putExtra("category",helpPlacesList.get(position).getCategory());
				intent.putExtra("id",helpPlacesList.get(position).getId());
				
				intent.putExtra("latitude",helpPlacesList.get(position).getLat());
				intent.putExtra("longitude",helpPlacesList.get(position).getLon());
				
				Log.i("Nar.......", "Chech intent:"+helpPlacesList.get(position).getLat()+"  "+helpPlacesList.get(position).getLon()+" 5555");
				mContext.startActivity(intent);
				
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		helpPlacesList.clear();
		if (charText.length() == 0) {
			helpPlacesList.addAll(arrayList);
		} 
		else 
		{
			for (int i =0;i<arrayList.size();i++) 
			{
				if ((arrayList.get(i)+"").toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					helpPlacesList.add(arrayList.get(i));
				}
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

}
