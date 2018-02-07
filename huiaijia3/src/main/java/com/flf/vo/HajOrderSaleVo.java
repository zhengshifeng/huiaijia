package com.flf.vo;

import com.flf.entity.HajOrderSale;

/**
 * Created by SevenWong on 2017-5-11 011 14:22
 */
public class HajOrderSaleVo extends HajOrderSale {

	private Integer type; // 问题类型
	private Integer[] depts; // 责任部门
	private Integer obj; // 操作对象，整单或单个商品
	private Integer orderDetailId; // 单个商品的订单详情ID
	private String commodityNo;
	private String commodityName;
	private Integer number; // 处理数量
	private String pic; // 凭证

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer[] getDepts() {
		return depts;
	}

	public void setDepts(Integer[] depts) {
		this.depts = depts;
	}

	public Integer getObj() {
		return obj;
	}

	public void setObj(Integer obj) {
		this.obj = obj;
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
}
