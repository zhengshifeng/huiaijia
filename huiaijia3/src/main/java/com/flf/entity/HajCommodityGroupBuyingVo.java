package com.flf.entity;

/**
 * Created by SevenWong on 2017/10/11 17:01
 */
public class HajCommodityGroupBuyingVo extends HajCommodityGroupBuying{

	private String commodityName;// 商品名
	private String[] sliderPicArr;// 轮播图
	private String[] detailPicArr;// 详情图

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String[] getSliderPicArr() {
		return sliderPicArr;
	}

	public void setSliderPicArr(String[] sliderPicArr) {
		this.sliderPicArr = sliderPicArr;
	}

	public String[] getDetailPicArr() {
		return detailPicArr;
	}

	public void setDetailPicArr(String[] detailPicArr) {
		this.detailPicArr = detailPicArr;
	}
}
