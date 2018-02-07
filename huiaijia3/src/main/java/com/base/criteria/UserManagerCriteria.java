package com.base.criteria;

import com.base.entity.BaseEntity;
import com.flf.entity.Page;

/**
 * 后台用户管理模块公共条件查询
 */
public class UserManagerCriteria extends BaseEntity {

	private java.lang.Integer currentPage = 1; // 当前页

	private java.lang.Integer pageSize = 15; // 页大小

	private java.lang.String orderByOrder;// 订单排序字段

	private java.lang.String limitClause;// 分页字段

	private java.lang.String orderByDate;// 注册时间排序字段

	private java.lang.String exactSearch;// 精确查找参数

	private java.lang.String ismember;// 会员

	private java.lang.Integer familymembers;// 家庭人数个数

	private java.lang.String residential;// 小区

	private Integer villageId;// 小区ID

	private java.lang.String address;// 详细地址

	private String provinceCode;
	private String communityCode;
	private String cityCode;
	private String[] phoneList;

	private String registerBeginTime;// 开始时间
	private String registerEndTime;// 结束时间

	private String community;	//小区名称


	/**
	 * 排序字段
	 */
	protected String orderByClause;

	private Page page;

	/**
	 * 是否预约
	 */
	private String isAppointment;

	/**
	 * 排序方式
	 */
	private java.lang.String sortType;

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public java.lang.String getIsmember() {
		return ismember;
	}

	public void setIsmember(java.lang.String ismember) {
		this.ismember = ismember;
	}

	public java.lang.Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(java.lang.Integer currentPage) {
		this.currentPage = currentPage;
	}

	public java.lang.Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(java.lang.Integer pageSize) {
		this.pageSize = pageSize;
	}

	public java.lang.String getOrderByOrder() {
		return orderByOrder;
	}

	public void setOrderByOrder(java.lang.String orderByOrder) {
		this.orderByOrder = orderByOrder;
	}

	public String getLimitClause() {
		int pageOffset = (this.currentPage - 1) * this.pageSize;
		limitClause = " limit " + pageOffset + "," + pageSize;
		return limitClause;
	}

	public void setLimitClause(java.lang.String limitClause) {
		this.limitClause = limitClause;
	}

	public java.lang.String getOrderByDate() {
		return orderByDate;
	}

	public void setOrderByDate(java.lang.String orderByDate) {
		this.orderByDate = orderByDate;
	}

	public java.lang.String getExactSearch() {
		return exactSearch;
	}

	public void setExactSearch(java.lang.String exactSearch) {
		this.exactSearch = exactSearch;
	}

	public java.lang.Integer getFamilymembers() {
		return familymembers;
	}

	public void setFamilymembers(java.lang.Integer familymembers) {
		this.familymembers = familymembers;
	}

	public java.lang.String getResidential() {
		return residential;
	}

	public void setResidential(java.lang.String residential) {
		this.residential = residential;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public String[] getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(String[] phoneList) {
		this.phoneList = phoneList;
	}

	public String getIsAppointment() {
		return isAppointment;
	}

	public void setIsAppointment(String isAppointment) {
		this.isAppointment = isAppointment;
	}

	public java.lang.String getSortType() {
		return sortType;
	}

	public void setSortType(java.lang.String sortType) {
		this.sortType = sortType;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}
	public String getRegisterBeginTime() {
		return registerBeginTime;
	}

	public void setRegisterBeginTime(String registerBeginTime) {
		this.registerBeginTime = registerBeginTime;
	}

	public String getRegisterEndTime() {
		return registerEndTime;
	}

	public void setRegisterEndTime(String registerEndTime) {
		this.registerEndTime = registerEndTime;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
}
