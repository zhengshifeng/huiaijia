package com.flf.controller.erp.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/20.
 */
public class ERPSupplierVo implements Serializable {

	private Integer FSUPPLIERID; //K3 clouds中的id 对应cloudsSupplierId
	private String  FSupplyCode; //供应商代码       对应supplyNo
	private String  FSupplyName; //供应商名称       对应name
	private String  FOrgCode;    //城市

	public String getFOrgCode() {
		return FOrgCode;
	}

	public void setFOrgCode(String FOrgCode) {
		this.FOrgCode = FOrgCode;
	}

	public Integer getFSUPPLIERID() {
		return FSUPPLIERID;
	}

	public String getFSupplyCode() {
		return FSupplyCode;
	}

	public String getFSupplyName() {
		return FSupplyName;
	}

	public void setFSUPPLIERID(Integer FSUPPLIERID) {
		this.FSUPPLIERID = FSUPPLIERID;
	}

	public void setFSupplyCode(String FSupplyCode) {
		this.FSupplyCode = FSupplyCode;
	}

	public void setFSupplyName(String FSupplyName) {
		this.FSupplyName = FSupplyName;
	}



}
