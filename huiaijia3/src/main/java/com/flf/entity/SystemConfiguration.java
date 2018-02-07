package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>SystemConfigurationEntity<br>
 */
public class SystemConfiguration extends BaseEntity {
	private static final long serialVersionUID = -6171929942205372936L;
	private java.lang.Integer id;//
	private java.lang.String name;// 配置名称
	private java.lang.String value;// 配置值
	private java.lang.String createTime;// 配置时间
	private java.lang.String configDescribe;// 描述

	private Page page;

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getValue() {
		return this.value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

	public java.lang.String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public java.lang.String getconfigDescribe() {
		return configDescribe;
	}

	public void setconfigDescribe(java.lang.String configDescribe) {
		this.configDescribe = configDescribe;
	}

}
