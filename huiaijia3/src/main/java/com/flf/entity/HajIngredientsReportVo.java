package com.flf.entity;

import java.util.List;

/**
 * zsf
 */
public class HajIngredientsReportVo extends HajIngredientsReport {

	private String[] cateimgsArr;// 分类图集

	private List<HajIngredientsReportConfVo> confVoList;//分类对象集合

	private HajIngredientsReportConfVo confVo;//分类对象

	public HajIngredientsReportConfVo getConfVo() {
		return confVo;
	}

	public void setConfVo(HajIngredientsReportConfVo confVo) {
		this.confVo = confVo;
	}

	public List<HajIngredientsReportConfVo> getConfVoList() {
		return confVoList;
	}

	public void setConfVoList(List<HajIngredientsReportConfVo> confVoList) {
		this.confVoList = confVoList;
	}

	public String[] getCateimgsArr() {
		return cateimgsArr;
	}

	public void setCateimgsArr(String[] cateimgsArr) {
		this.cateimgsArr = cateimgsArr;
	}


}
