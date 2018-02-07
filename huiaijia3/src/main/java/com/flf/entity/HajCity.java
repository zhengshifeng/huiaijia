package com.flf.entity;

import java.util.List;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajAreasEntity<br>
 */
public class HajCity extends BaseEntity {
	private static final long serialVersionUID = 6778750033861502889L;

	private java.lang.String code;// 编号
	private java.lang.String name;// 名称

	private List<HajAreas> hajAreas;// 市级

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public List<HajAreas> getHajAreas() {
		return hajAreas;
	}

	public void setHajAreas(List<HajAreas> hajAreas) {
		this.hajAreas = hajAreas;
	}

}
