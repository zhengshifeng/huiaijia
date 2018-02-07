package com.flf.entity.wms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by SevenWong on 2017/11/14 16:15
 */
public class WmsSaleOrder {

	private String isvUUID; //ISV出库单号
	private String isvSource; //ISV来源编号
	private String shopNo; //店铺编号
	private String departmentNo; //事业部编号（货主）
	private String warehouseNo; //仓库编号
	private String shipperNo; //承运商编号
	private String salePlatformSource; //销售平台来源
	private Date salesPlatformCreateTime; //销售平台下单时间
	private String consigneeName; //收货人姓名
	private String consigneeMobile; //收货人手机
	private Date expectDate; //期望发货时间
	private String addressProvince; //收货人省
	private String addressCity; //收货人市
	private String addressCounty; //收货人县
	private String addressTown; //收货人镇
	private String consigneeAddress; //收货人地址
	private BigDecimal receivable; //货到付款
	private String consigneeRemark; //客户留言 
	private String orderMark; //订单标记位
	private String goodsNo; //商品编号
	private String price; //商品金额
	private String quantity; //商品的出库数量
	private String wlyInfo;

	public String getIsvUUID() {
		return isvUUID;
	}

	public void setIsvUUID(String isvUUID) {
		this.isvUUID = isvUUID;
	}

	public String getIsvSource() {
		return isvSource;
	}

	public void setIsvSource(String isvSource) {
		this.isvSource = isvSource;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getWarehouseNo() {
		return warehouseNo;
	}

	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}

	public String getShipperNo() {
		return shipperNo;
	}

	public void setShipperNo(String shipperNo) {
		this.shipperNo = shipperNo;
	}

	public String getSalePlatformSource() {
		return salePlatformSource;
	}

	public void setSalePlatformSource(String salePlatformSource) {
		this.salePlatformSource = salePlatformSource;
	}

	public Date getSalesPlatformCreateTime() {
		return salesPlatformCreateTime;
	}

	public void setSalesPlatformCreateTime(Date salesPlatformCreateTime) {
		this.salesPlatformCreateTime = salesPlatformCreateTime;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public Date getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}

	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressCounty() {
		return addressCounty;
	}

	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}

	public String getAddressTown() {
		return addressTown;
	}

	public void setAddressTown(String addressTown) {
		this.addressTown = addressTown;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public BigDecimal getReceivable() {
		return receivable;
	}

	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}

	public String getConsigneeRemark() {
		return consigneeRemark;
	}

	public void setConsigneeRemark(String consigneeRemark) {
		this.consigneeRemark = consigneeRemark;
	}

	public String getOrderMark() {
		return orderMark;
	}

	public void setOrderMark(String orderMark) {
		this.orderMark = orderMark;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getWlyInfo() {
		return wlyInfo;
	}

	public void setWlyInfo(String wlyInfo) {
		this.wlyInfo = wlyInfo;
	}
}
