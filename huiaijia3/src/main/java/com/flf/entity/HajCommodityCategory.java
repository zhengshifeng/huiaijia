package com.flf.entity;import com.base.entity.BaseEntity;/** *  * <br> * <b>功能：</b>HajCommodityCategoryEntity<br> */public class HajCommodityCategory extends BaseEntity {	private static final long serialVersionUID = 1L;	private Page page; // 用于分页		private Integer id;//	private Integer commodityId;// 商品id	private Integer cateId;// 类目id(APP三级类目)	private java.util.Date createTime;// 创建时间	private java.util.Date endTime;// 修改时间	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getCommodityId() {	    return this.commodityId;	}	public void setCommodityId(Integer commodityId) {	    this.commodityId=commodityId;	}	public Integer getCateId() {	    return this.cateId;	}	public void setCateId(Integer cateId) {	    this.cateId=cateId;	}	public java.util.Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(java.util.Date createTime) {	    this.createTime=createTime;	}	public java.util.Date getEndTime() {	    return this.endTime;	}	public void setEndTime(java.util.Date endTime) {	    this.endTime=endTime;	}	public Page getPage() {		return page;	}	public void setPage(Page page) {		this.page = page;	}}