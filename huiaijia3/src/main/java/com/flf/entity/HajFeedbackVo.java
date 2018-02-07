package com.flf.entity;

public class HajFeedbackVo extends HajFeedback {
	private static final long serialVersionUID = 1L;

	// APP使用的手机号，因为之前同事写错了参数，跟HajFeedback中的telPhone不一致导致出问题
	// 为了兼容旧版本，因此增加次扩展参数
	private String telephone;

	// 以下参数用户后台反馈页面
	private String province;
	private String city;
	private String community;
	private String communityName;
	private String username;
	private Integer ismember;
	private String userinfo;
	private String communityInfo;
	private String provinceCode;
	private String cityCode;
	private String communityCode;
	private String beginTime;
	private String endTime;
	private Page page;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getIsmember() {
		return ismember;
	}

	public void setIsmember(Integer ismember) {
		this.ismember = ismember;
	}

	public String getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}

	public String getCommunityInfo() {
		return communityInfo;
	}

	public void setCommunityInfo(String communityInfo) {
		this.communityInfo = communityInfo;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
