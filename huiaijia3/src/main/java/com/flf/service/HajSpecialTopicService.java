package com.flf.service;

import java.util.List;

import com.base.service.BaseService;
import com.flf.entity.HajSpecialTopic;

/**
 * 
 * <br>
 * <b>功能：</b>HajSpecialTopicService<br>
 */
public interface HajSpecialTopicService extends BaseService {

	List<HajSpecialTopic> listPage(HajSpecialTopic po);

	//获取有效专题活动列表
	List<HajSpecialTopic> getSpecialTopicList();

}
