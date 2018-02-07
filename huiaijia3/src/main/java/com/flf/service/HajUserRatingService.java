package com.flf.service;

import java.util.List;

import com.base.service.BaseService;
import com.flf.entity.HajUserRating;

/**
 *
 * <br>
 * <b>功能：</b>HajUserRatingService<br>
 */
public interface HajUserRatingService extends BaseService {

	List<HajUserRating> listPage(HajUserRating po);

}
