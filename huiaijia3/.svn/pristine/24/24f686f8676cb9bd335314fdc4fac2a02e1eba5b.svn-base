package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajAreas;
import com.flf.entity.HajCommunityPersion;
import com.flf.entity.HajCouponResidential;
import com.flf.mapper.HajCouponResidentialMapper;
import com.flf.service.HajAreasService;
import com.flf.service.HajCommunityPersionService;
import com.flf.service.HajCouponResidentialService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponResidentialService<br>
 */
@Service("hajCouponResidentialService")
public class HajCouponResidentialServiceImpl extends BaseServiceImpl implements HajCouponResidentialService {
	private final static Logger log = Logger.getLogger(HajCouponResidentialServiceImpl.class);

	@Autowired
	private HajCouponResidentialMapper dao;

	@Autowired
	private HajAreasService areasService;

	@Autowired
	private HajCommunityPersionService communityService;

	@Override
	public HajCouponResidentialMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCouponResidential> listPage(HajCouponResidential po) {
		return dao.listPage(po);
	}

	@Override
	public List<Map<String, Object>> listWithTreeNodes(HajCouponResidential po) {
		List<HajAreas> areasList = null;
		try {
			areasList = areasService.getAreaByPCode(po.getAreaCode());
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}

		if (areasList != null) {
			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<String, Object> resultMap;

			List<HajCommunityPersion> communityList;
			for (HajAreas areas : areasList) {
				resultMap = new HashMap<>();
				resultMap.put("id", areas.getCode());
				resultMap.put("name", areas.getName());
				resultMap.put("pId", "0");
				resultMap.put("open", true);
				resultList.add(resultMap);

				communityList = communityService.getActiveCommunityByArea(areas.getCode());
				resultList.addAll(setCommunityList(communityList, po.getCouponId()));
			}
			return resultList;
		}
		return null;
	}

	private List<Map<String, Object>> setCommunityList(List<HajCommunityPersion> communityList, Integer couponId) {
		if (communityList != null) {
			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<String, Object> resultMap;

			List<Integer> communityByCoupon = dao.listResidentialByCoupon(couponId);

			for (HajCommunityPersion community : communityList) {
				resultMap = new HashMap<>();
				resultMap.put("id", community.getId());
				resultMap.put("name", community.getCommunityName());
				resultMap.put("pId", community.getCommunityCode());
				resultMap.put("checked", communityByCoupon.contains(community.getId()));
				resultList.add(resultMap);
			}
			return resultList;
		}
		return null;
	}

	@Override
	public void deleteByCoupon(Integer couponId) {
		dao.deleteByCoupon(couponId);
	}

	@Override
	public boolean save(Integer couponId, List<Integer> communityList) throws Exception {
		// 先清空之前选择的小区
		dao.deleteByCoupon(couponId);

		// 重新添加
		if (communityList != null && communityList.size() > 0) {
			dao.addBatch(communityList, couponId);
		}
		return true;
	}

	@Override
	public boolean getIsResidential(Integer villageId, Integer couponId) {
		HajCouponResidential couponResidential = new HajCouponResidential();
		couponResidential.setCommunityId(villageId);
		couponResidential.setCouponId(couponId);
		List<HajCouponResidential> list = dao.getIsResidential(couponResidential);
		if (null != list && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
