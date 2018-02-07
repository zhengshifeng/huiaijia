package com.flf.entity;

import java.math.BigDecimal;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajSortingOrderEntity<br>
 */
public class HajSortingOrder extends BaseEntity {
	private static final long serialVersionUID = -3364972389847376081L;
	private java.lang.Integer id;//
	private java.lang.String sortingNo;// 分拣单号
	private java.lang.String commodityNo;// 商品编号
	private java.util.Date createTime;// 创建时间
	private java.lang.Integer number;// 数量
	private BigDecimal price;// 单价
	private BigDecimal money;// 合计
	private BigDecimal saleMoney;// 售出合计
	private java.lang.Integer version;// mysql乐观锁版本号
	private java.lang.String residential;// 小区

	private String beginTime;// 开始时间
	private String endTime;// 结束时间
	private String supplyChain;// 供应商
	private String sTypeName;// 小分类
	private String bTypeName;// 大分类
	private String commodityAttr;
	private String[] residentialList;// 小区名称list

	private Page page;// 分页

	private String sorterId;

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

	public String getSupplyChain() {
		return supplyChain;
	}

	public void setSupplyChain(String supplyChain) {
		this.supplyChain = supplyChain;
	}

	public String getsTypeName() {
		return sTypeName;
	}

	public void setsTypeName(String sTypeName) {
		this.sTypeName = sTypeName;
	}

	public String getbTypeName() {
		return bTypeName;
	}

	public void setbTypeName(String bTypeName) {
		this.bTypeName = bTypeName;
	}

	public String getCommodityAttr() {
		return commodityAttr;
	}

	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getSortingNo() {
		return this.sortingNo;
	}

	public void setSortingNo(java.lang.String sortingNo) {
		this.sortingNo = sortingNo;
	}

	public java.lang.String getCommodityNo() {
		return this.commodityNo;
	}

	public void setCommodityNo(java.lang.String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getNumber() {
		return this.number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getSaleMoney() {
		return this.saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public java.lang.Integer getVersion() {
		return this.version;
	}

	public void setVersion(java.lang.Integer version) {
		this.version = version;
	}

	public java.lang.String getResidential() {
		return this.residential;
	}

	public void setResidential(java.lang.String residential) {
		this.residential = residential;
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
