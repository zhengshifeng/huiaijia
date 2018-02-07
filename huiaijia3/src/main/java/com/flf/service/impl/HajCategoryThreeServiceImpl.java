package com.flf.service.impl;

import com.base.criteria.Criteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.HajCategoryThreeMapper;
import com.flf.mapper.HajCommodityCategoryMapper;
import com.flf.mapper.HajCommodityMapper;
import com.flf.service.HajCategoryThreeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCategoryThreeService<br>
 */
@Service("hajCategoryThreeService")
public class HajCategoryThreeServiceImpl extends BaseServiceImpl implements HajCategoryThreeService {
	private final static Logger log = Logger.getLogger(HajCategoryThreeServiceImpl.class);

	@Autowired
	private HajCategoryThreeMapper dao;

	@Autowired
	private HajCommodityMapper hcmDao;

	@Autowired
	private HajCommodityCategoryMapper hccmDao;

	@Override
	public HajCategoryThreeMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCategoryThree> listPage(HajCategoryThree po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajCategoryThree dto) {
		return dao.list4app(dto);
	}

	@Override
	public List<HajCategoryThree> getThreeList() {
		return dao.getThreeList();
	}

	@Override
	public List<HajCategoryThree> getTwoByparentId(Integer parentId) {
		return dao.getTwoByparentId(parentId);
	}


	@Override
	public <T> int add(T t) throws Exception {
		return super.add(t);
	}

	@Override
	public List<HajCategoryThree> getOneCategory(Integer oneId) {
		return dao.getOneCategory(oneId);
	}

	@Override
	public List<HajCategoryThree> getTwoCategory(Integer oneId, Integer twoId) {
		return dao.getTwoCategory(oneId,twoId);
	}

	@Override
	public List<HajCategoryThree> getThreeCategory(Integer twoId, Integer threeId) {
		return dao.getThreeCategory(twoId,threeId);
	}

	@Override
	public List<HajCategoryThreeVo> getTwoCategoryByOneId(Integer oneId) {
		return dao.getTwoCategoryByOneId(oneId);
	}


	@Override
	public List<HajCategoryThree> getThreeCategoryByTwoId(Integer twoId) {
		return dao.getThreeCategoryByTwoId(twoId);
	}


	@Override
	public List<Map<String, Object>> getCategoryListByThreeId(Criteria criteria) {
		return hcmDao.getCategoryListByThreeId(criteria);

	}

	@Override
	public int getCommodityByThreeId(Integer threeId) {
		return hccmDao.getCommodityByThreeId(threeId);
	}




}
