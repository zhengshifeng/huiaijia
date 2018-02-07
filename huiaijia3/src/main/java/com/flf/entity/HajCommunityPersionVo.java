package com.flf.entity;

public class HajCommunityPersionVo extends HajCommunityPersion {
	private static final long serialVersionUID = 1L;

	private String province;
	private String city;
	private String community;
	private String cancalNumber;// 取消会员数量
	private String commonNumber;// 普通会员数量
	private String cnt1;//1仅注册
	private String cnt2;//2预备会员
	private String cnt3;//3一元购会员
	private String cnt4;//4会员取消
	private String cnt5;//5普通会员

	public String getCnt1() {
		return cnt1;
	}

	public void setCnt1(String cnt1) {
		this.cnt1 = cnt1;
	}

	public String getCnt2() {
		return cnt2;
	}

	public void setCnt2(String cnt2) {
		this.cnt2 = cnt2;
	}

	public String getCnt3() {
		return cnt3;
	}

	public void setCnt3(String cnt3) {
		this.cnt3 = cnt3;
	}

	public String getCnt4() {
		return cnt4;
	}

	public void setCnt4(String cnt4) {
		this.cnt4 = cnt4;
	}

	public String getCnt5() {
		return cnt5;
	}

	public void setCnt5(String cnt5) {
		this.cnt5 = cnt5;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getCancalNumber() {
		return cancalNumber;
	}

	public void setCancalNumber(String cancalNumber) {
		this.cancalNumber = cancalNumber;
	}

	public String getCommonNumber() {
		return commonNumber;
	}

	public void setCommonNumber(String commonNumber) {
		this.commonNumber = commonNumber;
	}

}
