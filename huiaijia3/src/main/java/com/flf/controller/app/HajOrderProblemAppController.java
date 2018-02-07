package com.flf.controller.app;

import com.flf.service.HajOrderProblemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderProblemAppController<br>
 * <br>
 */ 
@Controller
public class HajOrderProblemAppController {
	
	private final static Logger log = Logger.getLogger(HajOrderProblemAppController.class);
	
	@Autowired(required = false)
	private HajOrderProblemService service; 
}
