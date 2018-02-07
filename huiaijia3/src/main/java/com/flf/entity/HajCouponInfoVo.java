package com.flf.entity;

/**
 * Created by SevenWong on 2017-6-15 015 13:51
 */
public class HajCouponInfoVo extends HajCouponInfo {

	private String passport;

	private Integer userLimit;

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Integer getUserLimit() {
		return userLimit;
	}

	public void setUserLimit(Integer userLimit) {
		this.userLimit = userLimit;
	}
}
