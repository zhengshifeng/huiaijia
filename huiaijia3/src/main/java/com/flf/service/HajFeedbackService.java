package com.flf.service;

import java.util.List;

import com.base.service.BaseService;
import com.flf.entity.HajFeedbackVo;

/**
 * 
 * <br>
 * <b>功能：</b>HajFeedbackService<br>
 */
public interface HajFeedbackService extends BaseService {

	List<HajFeedbackVo> listPage(HajFeedbackVo vo);

}
