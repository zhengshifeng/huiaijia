package com.flf.controller.app;

import com.flf.service.HajCouponCommodityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponCommodityAppController<br>
 * <br>
 */ 
@Controller
public class HajCouponCommodityAppController {
	
	private final static Logger log = Logger.getLogger(HajCouponCommodityAppController.class);
	
	@Autowired(required = false)
	private HajCouponCommodityService service; 
}
