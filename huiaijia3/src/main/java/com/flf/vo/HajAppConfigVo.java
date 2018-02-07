package com.flf.vo;

import com.flf.entity.HajAppConfig;

/**
 * Created by SevenWong on 2017/8/19 14:56
 */
public class HajAppConfigVo extends HajAppConfig {

	private String headlines;
	private String shoppingCartTipSZ;
	private String shoppingCartTipGZ;
	private String newUsersCouponsSz;
	private String newUsersCouponsGz;

	public String getHeadlines() {
		return headlines;
	}

	public void setHeadlines(String headlines) {
		this.headlines = headlines;
	}

	public String getShoppingCartTipSZ() {
		return shoppingCartTipSZ;
	}

	public void setShoppingCartTipSZ(String shoppingCartTipSZ) {
		this.shoppingCartTipSZ = shoppingCartTipSZ;
	}

	public String getShoppingCartTipGZ() {
		return shoppingCartTipGZ;
	}

	public void setShoppingCartTipGZ(String shoppingCartTipGZ) {
		this.shoppingCartTipGZ = shoppingCartTipGZ;
	}

	public String getNewUsersCouponsSz() {
		return newUsersCouponsSz;
	}

	public void setNewUsersCouponsSz(String newUsersCouponsSz) {
		this.newUsersCouponsSz = newUsersCouponsSz;
	}

	public String getNewUsersCouponsGz() {
		return newUsersCouponsGz;
	}

	public void setNewUsersCouponsGz(String newUsersCouponsGz) {
		this.newUsersCouponsGz = newUsersCouponsGz;
	}
}
