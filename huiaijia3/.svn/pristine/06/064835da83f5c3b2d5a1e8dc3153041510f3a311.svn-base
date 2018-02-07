package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodityGroupBuying;
import com.flf.entity.HajCommodityGroupBuyingVo;
import com.flf.mapper.HajCommodityGroupBuyingMapper;
import com.flf.service.HajCommodityGroupBuyingService;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityGroupBuyingService<br>
 */
@Service("hajCommodityGroupBuyingService")
public class HajCommodityGroupBuyingServiceImpl extends BaseServiceImpl implements HajCommodityGroupBuyingService {
	private final static Logger log = Logger.getLogger(HajCommodityGroupBuyingServiceImpl.class);
	
	@Autowired
	private HajCommodityGroupBuyingMapper dao;
 
	@Override
	public HajCommodityGroupBuyingMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCommodityGroupBuying> listPage(HajCommodityGroupBuying po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajCommodityGroupBuying dto) {
		return dao.list4app(dto);
	}

	@Override
	public HashMap<String, Object> detail4app(HajCommodityGroupBuying dto) {
		HashMap<String, Object> map = dao.detail4app(dto);

		String sliderPic = String.valueOf(map.get("sliderPic"));
		if (Tools.isNotEmpty(sliderPic)) {
			String[] sliderPicArr = sliderPic.split(",");
			map.put("sliderPic", sliderPicArr);
		}

		String detailPic = String.valueOf(map.get("detailPic"));
		if (Tools.isNotEmpty(detailPic)) {
			String[] detailPicArr = detailPic.split(",");
			map.put("detailPic", detailPicArr);
		}
		return map;
	}

	@Override
	public HajCommodityGroupBuyingVo getByCommodityId(Integer commodityId) {
		return dao.getByCommodityId(commodityId);
	}
}
