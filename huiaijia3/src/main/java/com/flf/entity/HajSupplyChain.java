package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajSupplyChainEntity<br>
 */
public class HajSupplyChain extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;// 主键（建索引）
	private java.lang.String name;// 供应链名称
	private java.lang.String supplyNo;// 供应链编号
	private java.lang.String address;// 地址
	private java.lang.String docker;// 供应商对接人
	private java.lang.String cellphone;// 联系方式
	private java.lang.Integer areaId;// 供应地区（外键）
	private java.lang.Integer typeId;// 商品类型（外键）
	private java.lang.String hajdocker;// 汇爱家对接人
	private java.lang.String hajcellphone;// 汇爱家联系方式
	private java.lang.Integer status;// 合作状态（1合作中 2等待审批 3解除合作）
	private java.lang.String beginTime;// 合作开始时间
	private java.lang.String endTime;// 合作结束时间
	private java.lang.String contractPeriod;// 合同期限
	private java.lang.String payTime;// 支付周期
	private java.lang.String payStatus;// 支付方式
	private java.lang.String payee;// 收款人
	private java.lang.String payeeCellphone;// 收款人联系方式
	private java.lang.String createTime;// 录入时间
	private java.lang.String remark1;// 备注字段1
	private java.lang.String remark2;// 备注字段2
	private java.lang.String remark3;// 备注字段3
	private java.lang.Integer cloudsSupplierId; //clouds中的供应商id

	public Integer getCloudsSupplierId() {
		return cloudsSupplierId;
	}

	public void setCloudsSupplierId(Integer cloudsSupplierId) {
		this.cloudsSupplierId = cloudsSupplierId;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getSupplyNo() {
		return this.supplyNo;
	}

	public void setSupplyNo(java.lang.String supplyNo) {
		this.supplyNo = supplyNo;
	}

	public java.lang.String getAddress() {
		return this.address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getDocker() {
		return this.docker;
	}

	public void setDocker(java.lang.String docker) {
		this.docker = docker;
	}

	public java.lang.String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(java.lang.String cellphone) {
		this.cellphone = cellphone;
	}

	public java.lang.Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(java.lang.Integer areaId) {
		this.areaId = areaId;
	}

	public java.lang.Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(java.lang.Integer typeId) {
		this.typeId = typeId;
	}

	public java.lang.String getHajdocker() {
		return this.hajdocker;
	}

	public void setHajdocker(java.lang.String hajdocker) {
		this.hajdocker = hajdocker;
	}

	public java.lang.String getHajcellphone() {
		return this.hajcellphone;
	}

	public void setHajcellphone(java.lang.String hajcellphone) {
		this.hajcellphone = hajcellphone;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(java.lang.String beginTime) {
		this.beginTime = beginTime;
	}

	public java.lang.String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}

	public java.lang.String getContractPeriod() {
		return this.contractPeriod;
	}

	public void setContractPeriod(java.lang.String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public java.lang.String getPayTime() {
		return this.payTime;
	}

	public void setPayTime(java.lang.String payTime) {
		this.payTime = payTime;
	}

	public java.lang.String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}

	public java.lang.String getPayee() {
		return this.payee;
	}

	public void setPayee(java.lang.String payee) {
		this.payee = payee;
	}

	public java.lang.String getPayeeCellphone() {
		return this.payeeCellphone;
	}

	public void setPayeeCellphone(java.lang.String payeeCellphone) {
		this.payeeCellphone = payeeCellphone;
	}

	public java.lang.String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}

	public java.lang.String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	public java.lang.String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}
}
