package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajSaleDept;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajSaleDeptDao<br>
 */
public interface HajSaleDeptMapper extends BaseDao {

	List<HajSaleDept> listPage(HajSaleDept po);

}
