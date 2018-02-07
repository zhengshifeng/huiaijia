package com.base.service;

import com.base.criteria.Criteria;
import com.base.dao.BaseDao;

import java.util.List;

public interface BaseService {

	BaseDao getDao();

	<T> int add(T t) throws Exception;

	<T> void update(T t) throws Exception;

	<T> void updateBySelective(T t);

	void delete(Object... ids) throws Exception;

	int queryByCount(Criteria criteria) throws Exception;

	<T> List<T> queryByList(Criteria criteria) throws Exception;

	<T> T queryById(Object id) throws Exception;


}
