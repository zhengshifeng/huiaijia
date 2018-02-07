package com.flf.entity;

import com.base.entity.BaseEntity;

public class OrderVO extends BaseEntity {
	private static final long serialVersionUID = 2023518388793472221L;
	private String residential;
	private int status;// 1等待付款，2等待配送，3已发货，已完成（评论），5待退款，6已取消
	private double sprice;// 区间开始价格
	private double eprice;// 区间结束价格
	private int source;// 1家人喜好 2正常购买（砍掉）
	private String beginTime;// 开始时间
	private String endTime;// 结束时间
	private int beginHour; // 时间段 开始几点
	private int endHour; // 时间段 结束几点

	private String orderByClause; // 排序字段

	private String orderNo; // 订单编号

	private String userInfo; // 用户名/联系方式/用户编号

	private int currentPage;// 当前页

	private Page page; // 分页

	private String commodityName;// 商品名称

	private String isGrouponOrder;// 订单分类 0普通1团购2补单
	private String[] residentialList;// 小区名称list

	private String sorterId;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getResidential() {
		return residential;
	}

	public void setResidential(String residential) {
		this.residential = residential;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getSprice() {
		return sprice;
	}

	public void setSprice(double sprice) {
		this.sprice = sprice;
	}

	public double getEprice() {
		return eprice;
	}

	public void setEprice(double eprice) {
		this.eprice = eprice;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
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

	public int getBeginHour() {
		return beginHour;
	}

	public void setBeginHour(int beginHour) {
		this.beginHour = beginHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderNo() {
		if (null != orderNo) {
			return orderNo.trim();
		}
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserInfo() {
		if (null != userInfo) {
			return userInfo.trim();
		}
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getIsGrouponOrder() {
		return isGrouponOrder;
	}

	public void setIsGrouponOrder(String isGrouponOrder) {
		this.isGrouponOrder = isGrouponOrder;
	}

	public String[] getResidentialList() {
		return residentialList;
	}

	public void setResidentialList(String[] residentialList) {
		this.residentialList = residentialList;
	}

	public String getSorterId() {
		return sorterId;
	}

	public void setSorterId(String sorterId) {
		this.sorterId = sorterId;
	}

}
