package com.flf.controller.app;

import com.flf.service.HajCommodityBrandService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityBrandAppController<br>
 * <br>
 */ 
@Controller
public class HajCommodityBrandAppController {
	
	private final static Logger log = Logger.getLogger(HajCommodityBrandAppController.class);
	
	@Autowired(required = false)
	private HajCommodityBrandService service; 
}
