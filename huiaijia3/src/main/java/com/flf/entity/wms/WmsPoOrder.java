package com.flf.entity.wms;

/**
 * Created by SevenWong on 2017/11/22 14:45
 *
 * wms采购单实体信息
 *
 */
public class WmsPoOrder {

	private String spPoOrderNo; // 外部采购订单号
	private String deptNo; // 事业部编号（货主）
	private String whNo; // 入库库房编号
	private String supplierNo; // 供应商编号
	private String deptGoodsNo; // 事业部商品编号
	private String numApplication; // 申请入库数量
	private String goodsStatus; // 商品状态(1良品、2残品、3样品)

	public String getSpPoOrderNo() {
		return spPoOrderNo;
	}

	public void setSpPoOrderNo(String spPoOrderNo) {
		this.spPoOrderNo = spPoOrderNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getWhNo() {
		return whNo;
	}

	public void setWhNo(String whNo) {
		this.whNo = whNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getDeptGoodsNo() {
		return deptGoodsNo;
	}

	public void setDeptGoodsNo(String deptGoodsNo) {
		this.deptGoodsNo = deptGoodsNo;
	}

	public String getNumApplication() {
		return numApplication;
	}

	public void setNumApplication(String numApplication) {
		this.numApplication = numApplication;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
}
