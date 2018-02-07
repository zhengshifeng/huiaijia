package com.flf.mapper;

import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajIngredientsReport;
import com.flf.entity.HajIngredientsReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajIngredientsReportDao<br>
 */
public interface HajIngredientsReportMapper extends BaseDao {

	List<HajIngredientsReport> listPage(HajIngredientsReport po);

	List<HashMap<String, Object>> list4app(HajIngredientsReport dto);

	/**
	 * 条件查询列表
	 * @param po
	 * @return
	 */
	List<HajIngredientsReport> listPageByCondition(HajIngredientsReport po);

	/**
	 * 获取回显数据
	 * @param reportId
	 * @return
	 */
	HajIngredientsReportVo getByReportId(@Param("reportId")Integer reportId);

	/**
	 * 获取最新的食材检测报告
	 * @param areaCode
	 * @return
	 */
	HajIngredientsReport getNewReport4app(@Param("areaCode") String areaCode);

	/**
	 * 获取分页检测报告记录
	 * @param criteria
	 * @return
	 */
	List<HajIngredientsReport> reportListPage(Criteria criteria);

}
