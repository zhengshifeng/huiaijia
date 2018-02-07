package com.flf.entity;

import java.io.Serializable;

public class WMSOrderDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sku;// "SKU编码",
	private String numsku;// "平台SKU编码",
	private String qty;// 0,
	private String pri;// 0,
	private String amo;// 0,
	private String pro;// "优惠金额",
	private String pay;// 0,
	private String lotKey;// null,
	private String lotAttribute01;// null,
	private String lotAttribute02;// null,
	private String lotAttribute03;// null,
	private String lotAttribute04;// null,
	private String lotAttribute05;// null,
	private String lotAttribute06;// null,
	private String lotAttribute07;// null,
	private String lotAttribute08;// null,
	private String lotAttribute09;// null,
	private String lotAttribute10;// null,
	private String dis;// 0,
	private String cost;// 0,
	private String nam;// "商品名称（针对组合商品）",
	private String com;// "是否为组合商品",
	private String pre;// "是否预售",
	private String lineno;// "行号",
	private String isgift;// false,
	private String codets;// "行邮税号"

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNumsku() {
		return numsku;
	}

	public void setNumsku(String numsku) {
		this.numsku = numsku;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPri() {
		return pri;
	}

	public void setPri(String pri) {
		this.pri = pri;
	}

	public String getAmo() {
		return amo;
	}

	public void setAmo(String amo) {
		this.amo = amo;
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getLotKey() {
		return lotKey;
	}

	public void setLotKey(String lotKey) {
		this.lotKey = lotKey;
	}

	public String getLotAttribute01() {
		return lotAttribute01;
	}

	public void setLotAttribute01(String lotAttribute01) {
		this.lotAttribute01 = lotAttribute01;
	}

	public String getLotAttribute02() {
		return lotAttribute02;
	}

	public void setLotAttribute02(String lotAttribute02) {
		this.lotAttribute02 = lotAttribute02;
	}

	public String getLotAttribute03() {
		return lotAttribute03;
	}

	public void setLotAttribute03(String lotAttribute03) {
		this.lotAttribute03 = lotAttribute03;
	}

	public String getLotAttribute04() {
		return lotAttribute04;
	}

	public void setLotAttribute04(String lotAttribute04) {
		this.lotAttribute04 = lotAttribute04;
	}

	public String getLotAttribute05() {
		return lotAttribute05;
	}

	public void setLotAttribute05(String lotAttribute05) {
		this.lotAttribute05 = lotAttribute05;
	}

	public String getLotAttribute06() {
		return lotAttribute06;
	}

	public void setLotAttribute06(String lotAttribute06) {
		this.lotAttribute06 = lotAttribute06;
	}

	public String getLotAttribute07() {
		return lotAttribute07;
	}

	public void setLotAttribute07(String lotAttribute07) {
		this.lotAttribute07 = lotAttribute07;
	}

	public String getLotAttribute08() {
		return lotAttribute08;
	}

	public void setLotAttribute08(String lotAttribute08) {
		this.lotAttribute08 = lotAttribute08;
	}

	public String getLotAttribute09() {
		return lotAttribute09;
	}

	public void setLotAttribute09(String lotAttribute09) {
		this.lotAttribute09 = lotAttribute09;
	}

	public String getLotAttribute10() {
		return lotAttribute10;
	}

	public void setLotAttribute10(String lotAttribute10) {
		this.lotAttribute10 = lotAttribute10;
	}

	public String getDis() {
		return dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getPre() {
		return pre;
	}

	public void setPre(String pre) {
		this.pre = pre;
	}

	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
	}

	public String getIsgift() {
		return isgift;
	}

	public void setIsgift(String isgift) {
		this.isgift = isgift;
	}

	public String getCodets() {
		return codets;
	}

	public void setCodets(String codets) {
		this.codets = codets;
	}

}
