package com.flf.service.impl;

import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajCommodityFailure;
import com.flf.entity.HajCommodityVo;
import com.flf.mapper.HajCommodityFailureMapper;
import com.flf.service.HajCommodityFailureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("hajCommodityFailureService")
public class HajCommodityFailureServiceImpl implements HajCommodityFailureService {

	@Autowired
	private HajCommodityFailureMapper dao;

	@Override
	public BaseDao getDao() {
		return this.dao;
	}

	@Override
	public <T> int add(T hajCommodityFailure) throws Exception {
		return dao.add(hajCommodityFailure);
	}

	@Override
	public <T> void update(T t) throws Exception {

	}

	@Override
	public <T> void updateBySelective(T t) {

	}

	@Override
	public void delete(Object... ids) throws Exception {
		for (Object id : ids) {
			dao.delete(id);
		}
	}

	@Override
	public int queryByCount(Criteria criteria) throws Exception {
		return 0;
	}

	@Override
	public <T> List<T> queryByList(Criteria criteria) throws Exception {
		return null;
	}

	@Override
	public <T> T queryById(Object id) throws Exception {
		return null;
	}

	@Override
	public List<HashMap<String, Object>> getCommodityList(HajCommodityVo vo) {
		/*
		 * Page page = new Page(); page.setShowCount(30); vo.setPage(page);
		 */
		return dao.listPage(vo);
	}

	@Override
	public Integer isExist(HajCommodityFailure commodityFailure) {
		return dao.isExist(commodityFailure);
	}

	@Override
	public List<Integer> getCommodityIdsByComnunityId(Integer communityId) {
		return dao.getCommodityIdsByComnunityId(communityId);
	}

}
