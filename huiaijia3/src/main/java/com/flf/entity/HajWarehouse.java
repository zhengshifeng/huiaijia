package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajWarehouseEntity<br>
 */
public class HajWarehouse extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;// 主键（建索引）
	private java.lang.String whcode;// 仓库编号
	private java.lang.String whname;// 仓库名称
	private java.lang.String createTime;// 创建时间
	private java.lang.String region;// 地区
	private java.lang.String remark;// 备注

	private Page page;// 分页

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getWhcode() {
		return this.whcode;
	}

	public void setWhcode(java.lang.String whcode) {
		this.whcode = whcode;
	}

	public java.lang.String getWhname() {
		return this.whname;
	}

	public void setWhname(java.lang.String whname) {
		this.whname = whname;
	}

	public java.lang.String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getRegion() {
		return region;
	}

	public void setRegion(java.lang.String region) {
		this.region = region;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
}
