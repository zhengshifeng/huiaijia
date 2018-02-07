package com.flf.entity;

public class HajCommodityWastageRateVO extends HajCommodityWastageRate {
	private static final long serialVersionUID = -7746809245981510774L;

	private String beginDate;
	private String endDate;
	private String commodityAttr;
	private Integer parentTypeId;
	private Integer typeId;

	private Page page;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCommodityAttr() {
		return commodityAttr;
	}

	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}

	public Integer getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Integer parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
