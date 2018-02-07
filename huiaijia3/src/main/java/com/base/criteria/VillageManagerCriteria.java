package com.base.criteria;

import com.base.entity.BaseEntity;
import com.flf.entity.Page;

public class VillageManagerCriteria extends BaseEntity {
	private java.lang.Integer currentPage = 1; // 当前页

	private java.lang.Integer pageSize = 15; // 页大小

	private java.lang.String orderByRegistererNumber;// 注册人数排序字段

	private java.lang.String limitClause;// 分页字段

	private java.lang.String orderByMembersNumber;// 会员人数排序字段

	private java.lang.String orderByHisOrderNum;// 历史订单量排序字段

	private java.lang.String exactSearch;// 精确查找参数

	private java.lang.String communityName;// 小区名称

	private java.lang.Integer status;// 小区状态

	private java.lang.String membersNumber;// 会员人数

	private java.lang.String level;// 小区等级

	private java.lang.String planMemberNumber;// 计划会员人数

	private java.lang.String memberStatus;// 会员人数状态

	private java.lang.String area;// 区域 pushStatus

	private java.lang.String pushStatus;// 推送状态（1已推送，0未推送）

	private Page page;

	private String provinceCode;
	private String communityCode;
	private String cityCode;

	private Integer sorterId;

	private String createTimeMin;                 //创建时间筛选最小值

	private String createTimeMax;                 //创建时间筛选最大值

	private String activationTimeMin;             //激活时间筛选最小值

	private String activationTimeMax;             //激活时间筛选最大值

	private Integer distributionStatus;           //配送情况

	/**
	 * 排序字段
	 */
	protected String orderByClause;

	public java.lang.String getArea() {
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

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

	public java.lang.String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(java.lang.String memberStatus) {
		this.memberStatus = memberStatus;
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

	public java.lang.String getOrderByRegistererNumber() {
		return orderByRegistererNumber;
	}

	public void setOrderByRegistererNumber(java.lang.String orderByRegistererNumber) {
		this.orderByRegistererNumber = orderByRegistererNumber;
	}

	public String getLimitClause() {
		int pageOffset = (this.currentPage - 1) * this.pageSize;
		limitClause = " limit " + pageOffset + "," + pageSize;
		return limitClause;
	}

	public void setLimitClause(java.lang.String limitClause) {
		this.limitClause = limitClause;
	}

	public java.lang.String getOrderByMembersNumber() {
		return orderByMembersNumber;
	}

	public void setOrderByMembersNumber(java.lang.String orderByMembersNumber) {
		this.orderByMembersNumber = orderByMembersNumber;
	}

	public java.lang.String getOrderByHisOrderNum() {
		return orderByHisOrderNum;
	}

	public void setOrderByHisOrderNum(java.lang.String orderByHisOrderNum) {
		this.orderByHisOrderNum = orderByHisOrderNum;
	}

	public java.lang.String getExactSearch() {
		return exactSearch;
	}

	public void setExactSearch(java.lang.String exactSearch) {
		this.exactSearch = exactSearch;
	}

	public java.lang.String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(java.lang.String communityName) {
		this.communityName = communityName;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getMembersNumber() {
		return membersNumber;
	}

	public void setMembersNumber(java.lang.String membersNumber) {
		this.membersNumber = membersNumber;
	}

	public java.lang.String getLevel() {
		return level;
	}

	public void setLevel(java.lang.String level) {
		this.level = level;
	}

	public java.lang.String getPlanMemberNumber() {
		return planMemberNumber;
	}

	public void setPlanMemberNumber(java.lang.String planMemberNumber) {
		this.planMemberNumber = planMemberNumber;
	}

	public java.lang.String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(java.lang.String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public Integer getSorterId() {
		return sorterId;
	}

	public void setSorterId(Integer sorterId) {
		this.sorterId = sorterId;
	}

	public String getCreateTimeMin() {
		return createTimeMin;
	}

	public String getCreateTimeMax() {
		return createTimeMax;
	}

	public void setCreateTimeMin(String createTimeMin) {
		this.createTimeMin = createTimeMin;
	}

	public void setCreateTimeMax(String createTimeMax) {
		this.createTimeMax = createTimeMax;
	}

	public String getActivationTimeMin() {
		return activationTimeMin;
	}

	public String getActivationTimeMax() {
		return activationTimeMax;
	}

	public void setActivationTimeMin(String activationTimeMin) {
		this.activationTimeMin = activationTimeMin;
	}

	public void setActivationTimeMax(String activationTimeMax) {
		this.activationTimeMax = activationTimeMax;
	}

	public Integer getDistributionStatus() {
		return distributionStatus;
	}

	public void setDistributionStatus(Integer distributionStatus) {
		this.distributionStatus = distributionStatus;
	}

}
