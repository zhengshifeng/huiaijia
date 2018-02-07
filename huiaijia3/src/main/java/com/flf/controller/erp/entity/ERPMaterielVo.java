package com.flf.controller.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ERP物料VO
 * Created by Administrator on 2017/11/20.
 */
public class ERPMaterielVo implements Serializable {

	private Integer FMaterialId;       //物料id   对应cloudsId
	private String FItemCode;    	   //物料代码 对应商品表中字段sku
	private String FItemDesc;   	   //规格型号 对应weight
	private String FItemAddress;	   //产地    对应producer
	private BigDecimal FCostPrice;     //成本价   对应costPrice
	private BigDecimal FSalePrice;     //销售价   对应originalPrice
	private BigDecimal FReferenPricee; //参考价   对应marketPrice
	private String FSupplyCode;        //默认供应商代码  对应supplyChain
	private String FItemName;          //物料名称  对应name
	private String FOrgCode;           //城市     对应areaCode

	public void setFOrgCode(String FOrgCode) {
		this.FOrgCode = FOrgCode;
	}

	public String getFOrgCode() {
		return FOrgCode;
	}



	public void setFItemName(String FItemName) {
		this.FItemName = FItemName;
	}

	public String getFItemName() {
		return FItemName;
	}



	public Integer getFMaterialId() {

		return FMaterialId;
	}

	public String getFItemCode() {
		return FItemCode;
	}

	public String getFItemDesc() {
		return FItemDesc;
	}

	public String getFItemAddress() {
		return FItemAddress;
	}

	public BigDecimal getFCostPrice() {
		return FCostPrice;
	}

	public BigDecimal getFSalePrice() {
		return FSalePrice;
	}

	public BigDecimal getFReferenPricee() {
		return FReferenPricee;
	}

	public String getFSupplyCode() {
		return FSupplyCode;
	}

	public void setFMaterialId(Integer FMaterialId) {
		this.FMaterialId = FMaterialId;
	}


	public void setFItemCode(String FItemCode) {
		this.FItemCode = FItemCode;
	}

	public void setFItemDesc(String FItemDesc) {
		this.FItemDesc = FItemDesc;
	}

	public void setFItemAddress(String FItemAddress) {
		this.FItemAddress = FItemAddress;
	}

	public void setFCostPrice(BigDecimal FCostPrice) {
		this.FCostPrice = FCostPrice;
	}

	public void setFSalePrice(BigDecimal FSalePrice) {
		this.FSalePrice = FSalePrice;
	}

	public void setFReferenPricee(BigDecimal FReferenPricee) {
		this.FReferenPricee = FReferenPricee;
	}

	public void setFSupplyCode(String FSupplyCode) {
		this.FSupplyCode = FSupplyCode;
	}


}
