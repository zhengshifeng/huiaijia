package com.flf.entity;

import java.util.List;
import java.util.Map;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserFamilyEntity<br>
 */
public class HajUserFamily extends BaseEntity {
	private static final long serialVersionUID = 6538152595876471825L;
	private java.lang.Integer id;//
	private java.lang.Integer userId;// 用户编号
	private java.lang.Integer familyId;// 家庭成员编号
	private String nameRemark; // 家庭昵称备注
	private String commoditys; // 喜好的菜品
	private String photo; // 头像

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	private List<Map<String, Object>> list;

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public String getNameRemark() {
		return nameRemark;
	}

	public void setNameRemark(String nameRemark) {
		this.nameRemark = nameRemark;
	}

	public String getCommoditys() {
		return commoditys;
	}

	public void setCommoditys(String commoditys) {
		this.commoditys = commoditys;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.Integer getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(java.lang.Integer familyId) {
		this.familyId = familyId;
	}
}
