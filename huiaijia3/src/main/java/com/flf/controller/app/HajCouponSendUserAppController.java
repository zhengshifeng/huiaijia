package com.flf.controller.app;

import com.flf.service.HajCouponSendUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponSendUserAppController<br>
 * <br>
 */ 
@Controller
public class HajCouponSendUserAppController {
	
	private final static Logger log = Logger.getLogger(HajCouponSendUserAppController.class);
	
	@Autowired(required = false)
	private HajCouponSendUserService service;
}
