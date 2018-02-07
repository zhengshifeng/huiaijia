package com.base.criteria;

import com.base.entity.BaseEntity;
import com.flf.entity.Page;
import com.flf.util.Tools;

import java.math.BigDecimal;

public class CommodityCategoryCriteria extends BaseEntity {

	private java.lang.Integer currentPage = 1; // 当前页

	private java.lang.Integer pageSize = 15; // 页大小

	private java.lang.String limitClause;// 分页字段

	private Page page;

	private Integer areasId;
	private String communityCode;
	private String cityCode;
	private String provinceCode;
	private String commodityName;
	private String city;
	private String province;
	private Integer parentTypeId;		//大类
	private String parentTypeName;
	private String typeName;			//大小类名
	private String commodityAttr;		//属性
	private String supplyNo;
	private String supplyName;
	private String orderByClause;
	private Integer todaySales;
	private String brand;				//品牌名称
	private Integer threec;				//三级类目id
	private Integer specialTopicId;		//专题
	private String plate;				//板块
	private String activityId2;			//活动和商品中间表的活动id(代替商品中的activityId)
	private Integer onec;				//一级类目id
	private Integer twoc;				//二级类目id

	private java.lang.Integer id;//
	private java.lang.String name;// 商品名称
	private java.lang.String description;// 商品简介
	private java.lang.String details;// 商品详情
	private java.lang.String imagePath;// 图片路径
	private java.lang.Integer clickNumber;// 点击量
	private BigDecimal originalPrice;// 原价
	private BigDecimal promotionPrice;// 促销价
	private java.lang.Integer salesVolume;// 销量
	private java.lang.Integer typeId;// 商品分类id(外键)
	private java.lang.String producer;// 产地
	private java.lang.Integer activityId;// 促销活动表Id（外键）
	private java.lang.String intrapartum;// 出产时间（当季等）
	private java.lang.String supplyChain;// 供应链
	private java.lang.Integer ishot;// 是否热门（1是0否）
	private java.lang.Integer isNew;// 是否新品（1是0否）
	private java.lang.String weight;// 重量
	private java.lang.String smallPic;// 小图
	private java.lang.Integer shelves;// 是否上架（1是0否）
	private java.lang.Integer isTop;// 是否置顶（1是0否）
	private BigDecimal costPrice; // 成本价
	private BigDecimal marketPrice; // 市场价
	private java.lang.Integer familyPreferences;// 家人喜好推荐(1是0否)
	private java.lang.String commodityNo;// 商品编号
	private Integer recommend; // 是否推荐
	private Integer searchCount;// 搜索统计次数
	private String alias; // 别名
	private String acidDischarge; // 排酸
	private Integer praise; // 好评量
	private String orderClassification;// 订单导出的分类（A,B,C,D,E）
	private String label3; // 列表中第三个标签显示的内容
	private String mark; // 商品图片角标(new: 新品; hot: 热销; recommend: 推荐)
	private Integer inventory; // 库存
	private Integer inventoryInit; // 初始库存
	private Integer mustBuy; // 入店必买
	private Integer stamp; // 印章
	private String discountWord; // 折扣显示的文字
	private Integer promotionAreaId; // 促销专区ID
	private BigDecimal grossWeight;// 毛重
	private BigDecimal packedWeight;// 包装重量
	private java.lang.Integer selfPackaging;// 自主包装
	private java.lang.Integer invalid;// 是否作废(0,否;1,是)
	private java.lang.String image3;//
	private java.lang.String description2;//
	private java.lang.Integer sort;// 排序
	private java.lang.Integer sortingBatch;// 分拣批次
	private java.lang.String sku;// 商品物料编码
	private java.lang.Integer warningInvt;// 库存警告值
	private java.lang.Integer syncInvt;// 是否同步ERP库存
	private Integer brandId;// 品牌ID
	private String storageNo;// 货位号
	private Integer shelvesWay;// 商品上下架的方式(1,手动下架;)
	private Integer attribute; //物料属性(0.自产、1.采购、2.备货)
	private Integer cloudsId; //K3 clouds中的id
	private BigDecimal percentLoss;  //损耗百分比
	private Integer isaddcate;// 是否加入类目(1:已加入类目,0未加入类目)


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

	public Integer getAreasId() {
		return areasId;
	}

	public void setAreasId(Integer areasId) {
		this.areasId = areasId;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Integer parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCommodityAttr() {
		return commodityAttr;
	}

	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}

	public String getSupplyNo() {
		return supplyNo;
	}

	public void setSupplyNo(String supplyNo) {
		this.supplyNo = supplyNo;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public Integer getTodaySales() {
		return todaySales;
	}

	public void setTodaySales(Integer todaySales) {
		this.todaySales = todaySales;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getThreec() {
		return threec;
	}

	public void setThreec(Integer threec) {
		this.threec = threec;
	}

	public Integer getSpecialTopicId() {
		return specialTopicId;
	}

	public void setSpecialTopicId(Integer specialTopicId) {
		this.specialTopicId = specialTopicId;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getActivityId2() {
		return activityId2;
	}

	public void setActivityId2(String activityId2) {
		this.activityId2 = activityId2;
	}

	public Integer getOnec() {
		return onec;
	}

	public void setOnec(Integer onec) {
		this.onec = onec;
	}

	public Integer getTwoc() {
		return twoc;
	}

	public void setTwoc(Integer twoc) {
		this.twoc = twoc;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getClickNumber() {
		return clickNumber;
	}

	public void setClickNumber(Integer clickNumber) {
		this.clickNumber = clickNumber;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getIntrapartum() {
		return intrapartum;
	}

	public void setIntrapartum(String intrapartum) {
		this.intrapartum = intrapartum;
	}

	public String getSupplyChain() {
		return supplyChain;
	}

	public void setSupplyChain(String supplyChain) {
		this.supplyChain = supplyChain;
	}

	public Integer getIshot() {
		return ishot;
	}

	public void setIshot(Integer ishot) {
		this.ishot = ishot;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSmallPic() {
		return smallPic;
	}

	public void setSmallPic(String smallPic) {
		this.smallPic = smallPic;
	}

	public Integer getShelves() {
		return shelves;
	}

	public void setShelves(Integer shelves) {
		this.shelves = shelves;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getFamilyPreferences() {
		return familyPreferences;
	}

	public void setFamilyPreferences(Integer familyPreferences) {
		this.familyPreferences = familyPreferences;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(java.lang.String commodityNo) {
		if (Tools.notEmpty(commodityNo)) {
			commodityNo = commodityNo.trim();
		}
		this.commodityNo = commodityNo;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Integer searchCount) {
		this.searchCount = searchCount;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAcidDischarge() {
		return acidDischarge;
	}

	public void setAcidDischarge(String acidDischarge) {
		this.acidDischarge = acidDischarge;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public String getOrderClassification() {
		return orderClassification;
	}

	public void setOrderClassification(String orderClassification) {
		this.orderClassification = orderClassification;
	}

	public String getLabel3() {
		return label3;
	}

	public void setLabel3(String label3) {
		this.label3 = label3;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Integer getInventoryInit() {
		return inventoryInit;
	}

	public void setInventoryInit(Integer inventoryInit) {
		this.inventoryInit = inventoryInit;
	}

	public Integer getMustBuy() {
		return mustBuy;
	}

	public void setMustBuy(Integer mustBuy) {
		this.mustBuy = mustBuy;
	}

	public Integer getStamp() {
		return stamp;
	}

	public void setStamp(Integer stamp) {
		this.stamp = stamp;
	}

	public String getDiscountWord() {
		return discountWord;
	}

	public void setDiscountWord(String discountWord) {
		this.discountWord = discountWord;
	}

	public Integer getPromotionAreaId() {
		return promotionAreaId;
	}

	public void setPromotionAreaId(Integer promotionAreaId) {
		this.promotionAreaId = promotionAreaId;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getPackedWeight() {
		return packedWeight;
	}

	public void setPackedWeight(BigDecimal packedWeight) {
		this.packedWeight = packedWeight;
	}

	public Integer getSelfPackaging() {
		return selfPackaging;
	}

	public void setSelfPackaging(Integer selfPackaging) {
		this.selfPackaging = selfPackaging;
	}

	public Integer getInvalid() {
		return invalid;
	}

	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSortingBatch() {
		return sortingBatch;
	}

	public void setSortingBatch(Integer sortingBatch) {
		this.sortingBatch = sortingBatch;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getWarningInvt() {
		return warningInvt;
	}

	public void setWarningInvt(Integer warningInvt) {
		this.warningInvt = warningInvt;
	}

	public Integer getSyncInvt() {
		return syncInvt;
	}

	public void setSyncInvt(Integer syncInvt) {
		this.syncInvt = syncInvt;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getStorageNo() {
		return storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}

	public Integer getShelvesWay() {
		return shelvesWay;
	}

	public void setShelvesWay(Integer shelvesWay) {
		this.shelvesWay = shelvesWay;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getCloudsId() {
		return cloudsId;
	}

	public void setCloudsId(Integer cloudsId) {
		this.cloudsId = cloudsId;
	}

	public BigDecimal getPercentLoss() {
		return percentLoss;
	}

	public void setPercentLoss(BigDecimal percentLoss) {
		this.percentLoss = percentLoss;
	}

	public Integer getIsaddcate() {
		return isaddcate;
	}

	public void setIsaddcate(Integer isaddcate) {
		this.isaddcate = isaddcate;
	}
}
