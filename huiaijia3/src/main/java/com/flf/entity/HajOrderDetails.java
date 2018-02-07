package com.flf.entity;

import com.base.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * <br>
 * <b>功能：</b>HajOrderDetailsEntity<br>
 */
public class HajOrderDetails extends BaseEntity {
	private static final long serialVersionUID = 7101091909145220350L;
	private java.lang.Integer id;//
	private HajOrder orderId;// 订单id（订单表外键）
	private java.lang.String commodityName;// 商品名称
	private BigDecimal totalMoney;// 总金额
	private BigDecimal commodityListPrice;// 商品列表价格
	private java.lang.Integer number;// 数量
	private BigDecimal actualPayment;// 实际付款
	private BigDecimal feeWaiver;// 减免费用（优惠）
	private java.lang.Integer status;// 前端订单状态（1等待付款，2等待配送，3已发货，已完成（评论），5待退款，6已取消）
	private java.lang.String source;// 家人喜好数量
	private java.lang.String allSource;// 全部来源数量
	private java.lang.String remark3;//
	private java.lang.Integer commodityType;// 1爱家餐桌，2爱家果园
	private String typeName;// 大类名称
	private BigDecimal promotionPrice; // 商品促销价
	private String commodityNo;// 商品编号
	private String afterSaleRecord;// 售后问题记录
	private String weight; //添加规格字段


	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public HajOrder getOrderId() {
		return orderId;
	}

	public void setOrderId(HajOrder orderId) {
		this.orderId = orderId;
	}

	public java.lang.String getCommodityName() {
		return this.commodityName;
	}

	public void setCommodityName(java.lang.String commodityName) {
		this.commodityName = commodityName;
	}

	public BigDecimal getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getCommodityListPrice() {
		return this.commodityListPrice;
	}

	public void setCommodityListPrice(BigDecimal commodityListPrice) {
		this.commodityListPrice = commodityListPrice;
	}

	public java.lang.Integer getNumber() {
		return this.number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}

	public BigDecimal getActualPayment() {
		return this.actualPayment;
	}

	public void setActualPayment(BigDecimal actualPayment) {
		this.actualPayment = actualPayment;
	}

	public BigDecimal getFeeWaiver() {
		return this.feeWaiver;
	}

	public void setFeeWaiver(BigDecimal feeWaiver) {
		this.feeWaiver = feeWaiver;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getSource() {
		return source;
	}

	public void setSource(java.lang.String source) {
		this.source = source;
	}

	public java.lang.String getAllSource() {
		return allSource;
	}

	public void setAllSource(java.lang.String allSource) {
		this.allSource = allSource;
	}

	public java.lang.String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public java.lang.Integer getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(java.lang.Integer commodityType) {
		this.commodityType = commodityType;
	}

	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getAfterSaleRecord() {
		return afterSaleRecord;
	}

	public void setAfterSaleRecord(String afterSaleRecord) {
		this.afterSaleRecord = afterSaleRecord;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
}
