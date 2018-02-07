package com.flf.entity;

import com.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <br>
 * <b>功能：</b>HajSpecialTopicEntity<br>
 */
public class HajSpecialTopic extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private java.lang.Integer id;//
	private java.lang.String name;// 专题名
	private java.lang.String title;// 标题
	private java.lang.String banner;// 横幅图片
	private java.lang.String method;// 做法
	private java.lang.Integer sort;// 排序
	private java.lang.Integer invalid;// 失效(0,否;1,是)
	private java.lang.String url;// 专题链接

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date startTime;// 活动开始时间

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date endTime;// 活动结束时间

	private Page page;

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getTitle() {
		return this.title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getBanner() {
		return this.banner;
	}

	public void setBanner(java.lang.String banner) {
		this.banner = banner;
	}

	public java.lang.String getMethod() {
		return this.method;
	}

	public void setMethod(java.lang.String method) {
		this.method = method;
	}

	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	public java.lang.Integer getInvalid() {
		return this.invalid;
	}

	public void setInvalid(java.lang.Integer invalid) {
		this.invalid = invalid;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
