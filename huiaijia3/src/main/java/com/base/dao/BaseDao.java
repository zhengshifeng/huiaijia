package com.base.dao;

import java.util.List;

import com.base.criteria.Criteria;

public interface BaseDao {

	
	public <T> int add(T t);
	
	
	public <T> int update(T t);
	
	public <T> int updateBySelective(T t); 	
	
	public void delete(Object id);
	

	public int queryByCount(Criteria criteria);
	
	public <T> List<T> queryByList(Criteria criteria);
	
	
	public <T> T queryById(Object id);
}
