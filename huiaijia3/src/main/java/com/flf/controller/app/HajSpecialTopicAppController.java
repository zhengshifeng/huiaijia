package com.flf.controller.app;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.flf.service.HajSpecialTopicService;

/**
 * 
 * <br>
 * <b>功能：</b>HajSpecialTopicAppController<br>
 * <br>
 */ 
@Controller
public class HajSpecialTopicAppController {
	
	private final static Logger log = Logger.getLogger(HajSpecialTopicAppController.class);
	
	@Autowired(required = false)
	private HajSpecialTopicService service; 
}
