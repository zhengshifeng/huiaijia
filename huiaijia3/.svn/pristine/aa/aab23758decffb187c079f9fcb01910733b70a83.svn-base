package com.flf.entity;

import com.base.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>HajOrderEntity<br>
 */
public class HajOrder extends BaseEntity {
	private static final long serialVersionUID = 2789390517935370915L;
	private java.lang.Integer id;//
	private java.lang.String orderNo;// 订单号
	private BigDecimal totalMoney;// 订单总价
	private java.lang.Integer number;// 数量
	private String createTime;// 下单时间
	private java.lang.Integer userId;// 用户表id（外键）
	private java.lang.Integer status;// 前端订单状态（1等待付款，2等待配送，3已发货，4已完成（评论），5待退款，6已取消,7退款完成,8补单,9系统取消）
	private java.lang.Integer payStatus;// 付款状态（1未付款、2已付款、3交易关闭、4待退款）
	private java.lang.Integer handleStatus;// 处理状态(1已审核、2、已作废)
	private java.lang.Integer lockStatus;// 锁定状态(1是、0否)
	private String deliveryTime;// 预计送达时间
	private java.lang.String receiver;// 收货人
	private java.lang.String address;// 收货地址（配送地址）
	private java.lang.String contactPhone;// 联系电话
	private BigDecimal actualPayment;// 实际付款
	private BigDecimal feeWaiver;// 减免费用（优惠）
	private BigDecimal postFee;// 邮费
	private java.lang.String commodityCost;// 商品成本
	private java.lang.Integer points;// 本单积分
	private BigDecimal dispensingTip;// 配送小费
	private java.lang.String source;//
	private Double profitloss;// 订单盈亏
	private java.lang.String delflag;// 订单删除（1是 0否）
	private String residential; // 小区

	private String toDayOrderNumber; // 是否包当天订单含1元购商品（1是0否）
	private int sortingOrderStatus; // 生成分拣单状态（默认1，已生成3）
	private int isGrouponOrder; // 是否为团购订单（1：是，0：否）
	private String remark;// 订单备注

	// 3.10.1版本后收取运费的方式改为随单收取
	private int orderPostFeeStatus;// // orderPostFeeStatus用于区分订单收取运费的方式，1: 次日收取; 2: 随单收取; 3: 已生成运费
	private int wmshandleStatus; // 是否同步到wms系统（1未同步，2同步中 3已同步）
	private Integer paymentWay; // 订单支付方式(0:余额;1:微信;2:支付宝)

	private Date paymentTime; // 支付时间
	private Date cancelTime; // 订单取消时间
	private Date refundTime; // 退款时间
	private BigDecimal counponMoney;// 优惠卷抵扣金额
	private Integer counponId;// 优惠卷编号
	private Integer application;// 下单的应用(0, APP; 1, 微信小程序; 2, 后台补单)

	private BigDecimal refund; // 退款金额（表中无此字段）

	private List<HajOrderDetails> orderDetailList;

	public List<HajOrderDetails> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<HajOrderDetails> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public java.lang.Integer getNumber() {
		return this.number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(java.lang.Integer payStatus) {
		this.payStatus = payStatus;
	}

	public java.lang.Integer getHandleStatus() {
		return this.handleStatus;
	}

	public void setHandleStatus(java.lang.Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	public java.lang.Integer getLockStatus() {
		return this.lockStatus;
	}

	public void setLockStatus(java.lang.Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public java.lang.String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(java.lang.String receiver) {
		this.receiver = receiver;
	}

	public java.lang.String getAddress() {
		return this.address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(java.lang.String contactPhone) {
		this.contactPhone = contactPhone;
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

	public BigDecimal getPostFee() {
		return this.postFee;
	}

	public void setPostFee(BigDecimal postFee) {
		this.postFee = postFee;
	}

	public java.lang.String getCommodityCost() {
		return commodityCost;
	}

	public void setCommodityCost(java.lang.String commodityCost) {
		this.commodityCost = commodityCost;
	}

	public java.lang.Integer getPoints() {
		return this.points;
	}

	public void setPoints(java.lang.Integer points) {
		this.points = points;
	}

	public BigDecimal getDispensingTip() {
		return this.dispensingTip;
	}

	public void setDispensingTip(BigDecimal dispensingTip) {
		this.dispensingTip = dispensingTip;
	}

	public java.lang.String getSource() {
		return source;
	}

	public void setSource(java.lang.String source) {
		this.source = source;
	}

	public Double getProfitloss() {
		return profitloss;
	}

	public void setProfitloss(Double profitloss) {
		this.profitloss = profitloss;
	}

	public java.lang.String getDelflag() {
		return delflag;
	}

	public void setDelflag(java.lang.String delflag) {
		this.delflag = delflag;
	}

	public String getResidential() {
		return residential;
	}

	public void setResidential(String residential) {
		this.residential = residential;
	}

	public String getToDayOrderNumber() {
		return toDayOrderNumber;
	}

	public void setToDayOrderNumber(String toDayOrderNumber) {
		this.toDayOrderNumber = toDayOrderNumber;
	}

	public int getSortingOrderStatus() {
		return sortingOrderStatus;
	}

	public void setSortingOrderStatus(int sortingOrderStatus) {
		this.sortingOrderStatus = sortingOrderStatus;
	}

	public int getIsGrouponOrder() {
		return isGrouponOrder;
	}

	public void setIsGrouponOrder(int isGrouponOrder) {
		this.isGrouponOrder = isGrouponOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrderPostFeeStatus() {
		return orderPostFeeStatus;
	}

	public void setOrderPostFeeStatus(int orderPostFeeStatus) {
		this.orderPostFeeStatus = orderPostFeeStatus;
	}

	public int getWmshandleStatus() {
		return wmshandleStatus;
	}

	public void setWmshandleStatus(int wmshandleStatus) {
		this.wmshandleStatus = wmshandleStatus;
	}

	public Integer getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(Integer paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public BigDecimal getCounponMoney() {
		return counponMoney;
	}

	public void setCounponMoney(BigDecimal counponMoney) {
		this.counponMoney = counponMoney;
	}

	public Integer getCounponId() {
		return counponId;
	}

	public void setCounponId(Integer counponId) {
		this.counponId = counponId;
	}

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public Integer getApplication() {
		return application;
	}

	public void setApplication(Integer application) {
		this.application = application;
	}
}
