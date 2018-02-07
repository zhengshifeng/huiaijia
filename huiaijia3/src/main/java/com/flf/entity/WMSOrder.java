package com.flf.entity;

import java.io.Serializable;
import java.util.Collection;

public class WMSOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ordeno;// "出库通知单号",
	private String fromty;// "来源订单类型",
	private String ordertype;// null,
	private String fromno;// "来源单号",
	private String opform;// "订单来源平台",
	private String priori;// "配货优先级",
	private String crtime;// "下单时间",
	private String patime;// "支付时间",
	private String adtime;// "审核时间",
	private String tocode;// "出库单标识",
	private String aduser;// "审核人",
	private String cruser;// null,
	private String whcode;// "仓库编码",
	private String cacode;// "物流公司编码",
	private String caname;// "物流公司名称",
	private String posfee;// 0,
	private String piscod;// false,
	private String isdelv;// false,
	private String isgift;// false,
	private String shopna;// "来源店铺名",
	private String shopno;// "来源店铺编号",
	private String bunick;// "会员昵称",
	private String custna;// "会员名称",
	private String recena;// "收件人姓名",
	private String postco;// "邮政编码",
	private String provco;// "省编码",
	private String provna;// "省名称",
	private String cityco;// "市编码",
	private String cityna;// "市名称",
	private String distco;// "区编码",
	private String distna;// "区名称",
	private String addres;// "收货地址",
	private String mobile;// "移动电话",
	private String teleph;// "固定电话",
	private String sememo;// "卖家留言",
	private String bumemo;// "买家留言",
	private String shmemo;// "商家留言",
	private String gymemo;// "订单发票",
	private String IsInvoice;// false,
	private String invoicedetail;// null,
	private String IsTopay;// "是否到付",
	private String ordeca;// 0,
	private String ActuallyPaid;// 0,
	private String pretype;// "预售类型",
	private String isspec;// "是否个性化包裹",
	private String specmk;// "个性化包裹内容",
	private String codnum;// null,
	private String busimode;// "业务模式",
	private String pickmode;// "捡货类型",
	private String IdCardNumber;// "身份证号",
	private String ispickUpOffLine;// "否把单据给到ssp",
	private String clientno;// 货主编号 HAJ

	private Collection<WMSOrderDetail> skulst; // 订单商品详情

	public String getOrdeno() {
		return ordeno;
	}

	public void setOrdeno(String ordeno) {
		this.ordeno = ordeno;
	}

	public String getFromty() {
		return fromty;
	}

	public void setFromty(String fromty) {
		this.fromty = fromty;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getFromno() {
		return fromno;
	}

	public void setFromno(String fromno) {
		this.fromno = fromno;
	}

	public String getOpform() {
		return opform;
	}

	public void setOpform(String opform) {
		this.opform = opform;
	}

	public String getPriori() {
		return priori;
	}

	public void setPriori(String priori) {
		this.priori = priori;
	}

	public String getCrtime() {
		return crtime;
	}

	public void setCrtime(String crtime) {
		this.crtime = crtime;
	}

	public String getPatime() {
		return patime;
	}

	public void setPatime(String patime) {
		this.patime = patime;
	}

	public String getAdtime() {
		return adtime;
	}

	public void setAdtime(String adtime) {
		this.adtime = adtime;
	}

	public String getTocode() {
		return tocode;
	}

	public void setTocode(String tocode) {
		this.tocode = tocode;
	}

	public String getAduser() {
		return aduser;
	}

	public void setAduser(String aduser) {
		this.aduser = aduser;
	}

	public String getCruser() {
		return cruser;
	}

	public void setCruser(String cruser) {
		this.cruser = cruser;
	}

	public String getWhcode() {
		return whcode;
	}

	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}

	public String getCacode() {
		return cacode;
	}

	public void setCacode(String cacode) {
		this.cacode = cacode;
	}

	public String getCaname() {
		return caname;
	}

	public void setCaname(String caname) {
		this.caname = caname;
	}

	public String getPosfee() {
		return posfee;
	}

	public void setPosfee(String posfee) {
		this.posfee = posfee;
	}

	public String getPiscod() {
		return piscod;
	}

	public void setPiscod(String piscod) {
		this.piscod = piscod;
	}

	public String getIsdelv() {
		return isdelv;
	}

	public void setIsdelv(String isdelv) {
		this.isdelv = isdelv;
	}

	public String getIsgift() {
		return isgift;
	}

	public void setIsgift(String isgift) {
		this.isgift = isgift;
	}

	public String getShopna() {
		return shopna;
	}

	public void setShopna(String shopna) {
		this.shopna = shopna;
	}

	public String getShopno() {
		return shopno;
	}

	public void setShopno(String shopno) {
		this.shopno = shopno;
	}

	public String getBunick() {
		return bunick;
	}

	public void setBunick(String bunick) {
		this.bunick = bunick;
	}

	public String getCustna() {
		return custna;
	}

	public void setCustna(String custna) {
		this.custna = custna;
	}

	public String getRecena() {
		return recena;
	}

	public void setRecena(String recena) {
		this.recena = recena;
	}

	public String getPostco() {
		return postco;
	}

	public void setPostco(String postco) {
		this.postco = postco;
	}

	public String getProvco() {
		return provco;
	}

	public void setProvco(String provco) {
		this.provco = provco;
	}

	public String getProvna() {
		return provna;
	}

	public void setProvna(String provna) {
		this.provna = provna;
	}

	public String getCityco() {
		return cityco;
	}

	public void setCityco(String cityco) {
		this.cityco = cityco;
	}

	public String getCityna() {
		return cityna;
	}

	public void setCityna(String cityna) {
		this.cityna = cityna;
	}

	public String getDistco() {
		return distco;
	}

	public void setDistco(String distco) {
		this.distco = distco;
	}

	public String getDistna() {
		return distna;
	}

	public void setDistna(String distna) {
		this.distna = distna;
	}

	public String getAddres() {
		return addres;
	}

	public void setAddres(String addres) {
		this.addres = addres;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTeleph() {
		return teleph;
	}

	public void setTeleph(String teleph) {
		this.teleph = teleph;
	}

	public String getSememo() {
		return sememo;
	}

	public void setSememo(String sememo) {
		this.sememo = sememo;
	}

	public String getBumemo() {
		return bumemo;
	}

	public void setBumemo(String bumemo) {
		this.bumemo = bumemo;
	}

	public String getShmemo() {
		return shmemo;
	}

	public void setShmemo(String shmemo) {
		this.shmemo = shmemo;
	}

	public String getGymemo() {
		return gymemo;
	}

	public void setGymemo(String gymemo) {
		this.gymemo = gymemo;
	}

	public String getIsInvoice() {
		return IsInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		IsInvoice = isInvoice;
	}

	public String getInvoicedetail() {
		return invoicedetail;
	}

	public void setInvoicedetail(String invoicedetail) {
		this.invoicedetail = invoicedetail;
	}

	public String getIsTopay() {
		return IsTopay;
	}

	public void setIsTopay(String isTopay) {
		IsTopay = isTopay;
	}

	public String getOrdeca() {
		return ordeca;
	}

	public void setOrdeca(String ordeca) {
		this.ordeca = ordeca;
	}

	public String getActuallyPaid() {
		return ActuallyPaid;
	}

	public void setActuallyPaid(String actuallyPaid) {
		ActuallyPaid = actuallyPaid;
	}

	public String getPretype() {
		return pretype;
	}

	public void setPretype(String pretype) {
		this.pretype = pretype;
	}

	public String getIsspec() {
		return isspec;
	}

	public void setIsspec(String isspec) {
		this.isspec = isspec;
	}

	public String getSpecmk() {
		return specmk;
	}

	public void setSpecmk(String specmk) {
		this.specmk = specmk;
	}

	public String getCodnum() {
		return codnum;
	}

	public void setCodnum(String codnum) {
		this.codnum = codnum;
	}

	public String getBusimode() {
		return busimode;
	}

	public void setBusimode(String busimode) {
		this.busimode = busimode;
	}

	public String getPickmode() {
		return pickmode;
	}

	public void setPickmode(String pickmode) {
		this.pickmode = pickmode;
	}

	public String getIdCardNumber() {
		return IdCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		IdCardNumber = idCardNumber;
	}

	public String getIspickUpOffLine() {
		return ispickUpOffLine;
	}

	public void setIspickUpOffLine(String ispickUpOffLine) {
		this.ispickUpOffLine = ispickUpOffLine;
	}

	public String getClientno() {
		return clientno;
	}

	public void setClientno(String clientno) {
		this.clientno = clientno;
	}

	public Collection<WMSOrderDetail> getSkulst() {
		return skulst;
	}

	public void setSkulst(Collection<WMSOrderDetail> skulst) {
		this.skulst = skulst;
	}

}
