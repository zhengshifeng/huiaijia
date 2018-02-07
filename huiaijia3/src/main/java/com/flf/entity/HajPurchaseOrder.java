package com.flf.entity;

import java.math.BigDecimal;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajPurchaseOrderEntity<br>
 */
public class HajPurchaseOrder extends BaseEntity {
	private static final long serialVersionUID = 1988965562639526469L;
	private java.lang.Integer id;//
	private java.lang.String purchaseNo;// 采购单号
	private java.lang.String commodityNo;// 商品编号
	private java.util.Date createTime;// 创建时间
	private java.lang.Integer number;// 数量
	private BigDecimal price;// 单价
	private BigDecimal money;// 合计
	private int version;// mysql乐观锁版本号
	private BigDecimal saleMoney;// 售出合计

	private String beginTime;// 开始时间
	private String endTime;// 结束时间
	private String supplyChain;// 供应商
	private String sTypeName;// 小分类
	private String bTypeName;// 大分类
	private String commodityAttr;

	private Page page;// 分页

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getPurchaseNo() {
		return this.purchaseNo;
	}

	public void setPurchaseNo(java.lang.String purchaseNo) {
		this.purchaseNo = purchaseNo;
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

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

}
