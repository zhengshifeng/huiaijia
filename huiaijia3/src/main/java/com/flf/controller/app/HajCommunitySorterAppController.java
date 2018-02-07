package com.flf.controller.app;

import com.flf.service.HajCommunitySorterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunitySorterAppController<br>
 * <br>
 */ 
@Controller
public class HajCommunitySorterAppController {
	
	private final static Logger log = Logger.getLogger(HajCommunitySorterAppController.class);
	
	@Autowired(required = false)
	private HajCommunitySorterService service;
}
