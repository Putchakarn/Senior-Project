package com.libra.entity;

import com.mapswithme.maps.api.MWMPoint;

public class HelpPlace {
	private double lat;
	private double lon;

	private Integer id;
	private String name;
	private String address;
	private String phoneNumber;
	private String category;

	public HelpPlace() {
	}

	public HelpPlace(Integer id, String name, String address,
			String phoneNumber, double lat, double lon) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.lat = lat;
		this.lon = lon;
	}
	
	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return name;
	}

	public MWMPoint toMWMPoint() {

		return new MWMPoint(lat, lon, name, address + "+" + phoneNumber);
	}

}
