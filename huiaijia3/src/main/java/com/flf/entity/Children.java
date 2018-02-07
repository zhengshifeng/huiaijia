package com.flf.entity;

import com.base.entity.BaseEntity;

public class Children extends BaseEntity {
	private static final long serialVersionUID = 8309828691023563809L;
	private java.lang.Integer id;//
	private java.lang.String typeName;// 商品类别名称
	private java.lang.String description;// 商品分类简介
	private String commodityAttr;// 商品属性（生鲜、海淘...）
	private String icon;// 类型图标
	private Integer sort; // 排序（按数字大小升序）

	public java.lang.String getTypeName() {
		return typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommodityAttr() {
		return commodityAttr;
	}

	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
