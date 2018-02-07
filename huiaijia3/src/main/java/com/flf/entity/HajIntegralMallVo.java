package com.flf.entity;

/**
 * Created by SevenWong on 2017/8/23 15:35
 *
 * 用于作为积分商城管理页面的视图层扩展
 *
 */
public class HajIntegralMallVo extends HajIntegralMall {

	private Integer totalOfExchanged; // 已兑换数

	public Integer getTotalOfExchanged() {
		return totalOfExchanged;
	}

	public void setTotalOfExchanged(Integer totalOfExchanged) {
		this.totalOfExchanged = totalOfExchanged;
	}
}
