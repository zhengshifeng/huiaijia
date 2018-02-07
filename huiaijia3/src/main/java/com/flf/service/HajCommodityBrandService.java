package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityBrand;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityBrandService<br>
 */
public interface HajCommodityBrandService extends BaseService {

	List<HajCommodityBrand> listPage(HajCommodityBrand po);

	List<HajCommodityBrand> getAllBrands();

	HajCommodityBrand getByName(String name);

}
