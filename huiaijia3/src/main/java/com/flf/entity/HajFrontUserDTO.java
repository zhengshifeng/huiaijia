package com.flf.entity;

/**
 * Created by SevenWong on 2017-4-11 011 15:2
 */
public class HajFrontUserDTO extends HajFrontUser {

	private String city;
	private String addressSplit;
	private String unit;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressSplit() {
		return addressSplit;
	}

	public void setAddressSplit(String addressSplit) {
		this.addressSplit = addressSplit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
