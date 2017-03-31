package org.nobibi.startrace.astro.bean;

import java.util.Map;

public class PlanetState {
	
	private int NO;
	
	private String name;
	
	private String sign;
	
	private int degree;
	
	private int minute;
	
	private int second;
		
	private double longitude;
	
	private int house;
		
	private Map<String, Object> attributes;;
	
	public PlanetState() {
		super();
	}

	public PlanetState(int nO, double longitude) {
		super();
		NO = nO;
		this.longitude = longitude;
	}

	public int getNO() {
		return NO;
	}

	public void setNO(int nO) {
		NO = nO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
	
}
