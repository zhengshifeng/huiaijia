package com.base.criteria;

import com.base.entity.BaseEntity;
import com.flf.entity.Page;

import java.util.HashMap;
import java.util.List;

public class PromotionAreaCriteria extends BaseEntity {

	private java.lang.Integer currentPage = 1; // 当前页

	private java.lang.Integer pageSize = 15; // 页大小

	private java.lang.String limitClause;// 分页字段

	private java.lang.Integer id;//
	private java.lang.String name;// 专区名
	private java.lang.String description;// 专区描述
	private java.lang.String banner;// 横幅图片地址
	private java.lang.Integer display;// 是否在APP显示该专区(0:否,1:是)
	private java.lang.Integer sort;// 专区的排序
	private String attr; // 属性
	private String banner3_2; // 横幅图片地址(3.2.*版本图片变大)
	private java.lang.Integer categoryId;// 类目ID
	private java.lang.Integer indexDisplay;// 是否在APP首页显示
	private java.lang.Integer categoryDisplay;// 是否在APP分类页显示
	private java.lang.String categoryBanner;// 分类页显示的banner图
	private java.lang.Integer areaType;// 专区类型:1品牌专区,2推荐专题,3商品类目,4商品分类....
	private java.lang.Integer areaTypeId;// 类型id
	private java.lang.String homeBanner;//

	private String code; // 城市编码

	private Page page;
	private List<HashMap<String, Object>> commodityList;


	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getLimitClause() {
		int pageOffset = (this.currentPage - 1) * this.pageSize;
		limitClause = " limit " + pageOffset + "," + pageSize;
		return limitClause;
	}

	public void setLimitClause(String limitClause) {
		this.limitClause = limitClause;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getBanner3_2() {
		return banner3_2;
	}

	public void setBanner3_2(String banner3_2) {
		this.banner3_2 = banner3_2;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getIndexDisplay() {
		return indexDisplay;
	}

	public void setIndexDisplay(Integer indexDisplay) {
		this.indexDisplay = indexDisplay;
	}

	public Integer getCategoryDisplay() {
		return categoryDisplay;
	}

	public void setCategoryDisplay(Integer categoryDisplay) {
		this.categoryDisplay = categoryDisplay;
	}

	public String getCategoryBanner() {
		return categoryBanner;
	}

	public void setCategoryBanner(String categoryBanner) {
		this.categoryBanner = categoryBanner;
	}

	public Integer getAreaTypeId() {
		return areaTypeId;
	}

	public void setAreaTypeId(Integer areaTypeId) {
		this.areaTypeId = areaTypeId;
	}

	public String getHomeBanner() {
		return homeBanner;
	}

	public void setHomeBanner(String homeBanner) {
		this.homeBanner = homeBanner;
	}

	public List<HashMap<String, Object>> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(List<HashMap<String, Object>> commodityList) {
		this.commodityList = commodityList;
	}
}
