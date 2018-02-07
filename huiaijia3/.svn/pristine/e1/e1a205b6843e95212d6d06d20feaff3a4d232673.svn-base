package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityType;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityTypeService<br>
 */
public interface HajCommodityTypeService extends BaseService {

	List<HajCommodityType> getSonCommodityTypeList(HajCommodityType parentType) throws Exception;

	List<HajCommodityType> getCommodityTypeId(Integer typeId, Integer communityId);

	void delete(Integer[] ids) throws Exception;

	List<HajCommodityType> getBigTypeByattr(String commodityAttr);

	List<HajCommodityType> getTypeByParentId(int parentId);

	/**
	 * 更新商品分类时根据商品分类属性、大类、小类判断是否已存在该分类；<br>
	 * 如果是修改分类，则判断除该分类外是否还有同名（属性、大类、小类都一致）的分类
	 * 
	 * @author SevenWong<br>
	 * @date 2016年11月22日上午11:43:29
	 * @param po
	 * @return
	 */
	int getTypeCount(HajCommodityType po);

	/**
	 * 根据属性，大类ID将多级商品分类列表存到ModelAndView中
	 * @param mv
	 * @param commodityAttr
	 * @param parentTypeId
	 */
	void putTypeList2MV(ModelAndView mv, String commodityAttr, Integer parentTypeId);

	List<HajCommodityType> getParentTypeByAttr(HajCommodityType type);

	List<HajCommodityType> getTypeByPid(HajCommodityType type);

	List<HajCommodityType> getByCategoryId(Integer categoryId);

	List<HajCommodityType> list(String attr);
}
