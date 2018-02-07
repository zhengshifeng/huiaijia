package com.flf.vo;

import com.flf.entity.HajOrderSale;
import com.flf.entity.Page;

/**
 * Created by SevenWong on 2017/5/19 10:51
 */
public class HajOrderSaleDTO extends HajOrderSale {

	private Integer paymentWay; // 订单支付方式(0:余额;1:微信;2:支付宝)
	private String dateStart;
	private String dateEnd;
	private Integer sort;
	private String orderBy;

	private Page page;// 分页

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(Integer paymentWay) {
		this.paymentWay = paymentWay;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
