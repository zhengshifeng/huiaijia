package com.flf.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.mapper.HajCommodityMapper;
import com.flf.mapper.HajUserFamilyMapper;
import com.flf.service.HajUserFamilyService;
import com.flf.service.RedisSpringProxy;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserFamilyService<br>
 */
@Service("hajUserFamilyService")
public class HajUserFamilyServiceImpl extends BaseServiceImpl implements HajUserFamilyService {
	private final static Logger log = Logger.getLogger(HajUserFamilyServiceImpl.class);
	@Autowired
	private HajUserFamilyMapper dao;
	@Autowired
	private RedisSpringProxy redisSpringProxy;
	@Autowired
	private HajCommodityMapper commodityDao;

	@Override
	public HajUserFamilyMapper getDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> getAllCommodityList() throws Exception {
		List<Map<String, Object>> allCommodityList = null;
		Object commodityPriceObj = redisSpringProxy.read("commodityPriceByName");
		if (null != commodityPriceObj) {
			log.info("进入获取商品缓存接口：commodityPriceByName：");
			allCommodityList = (List<Map<String, Object>>) commodityPriceObj;
		} else {
			// 缓存查询所有商品
			allCommodityList = commodityDao.getAllCommodityPrice();
			log.info("进入获取商品查询数据库接口：allCommodityList：");
			if (null != allCommodityList && allCommodityList.size() > 0) {
				redisSpringProxy.save("commodityPriceByName", allCommodityList);
			}
		}
		return allCommodityList;
	}

	@Override
	public Map<String, Object> queryPriceByName(String name) throws Exception {
		List<Map<String, Object>> allCommodityList = getAllCommodityList();
		if (null != allCommodityList && allCommodityList.size() > 0) {
			for (Map<String, Object> mapObject : allCommodityList) {
				if (mapObject.get("name").equals(name)) {
					return mapObject;
				}
			}
		}
		return null;
	}

}
