package com.flf.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flf.entity.WMSOrderCancle;
import com.flf.service.HajOrderService;
import com.wms.CancleOrderToWMSManage;

/**
 * 
 * <br>
 * <b>功能：</b>HajWMSCanclController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajwmscancle")
public class HajWMSCancleController {

	@Autowired(required = false)
	private CancleOrderToWMSManage cancleOrderToWMSManage;

	@Autowired
	private HajOrderService hajOrderService;

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/wmsCancle")
	public void wmsCancle(String ordeno, HttpServletResponse response, PrintWriter out) throws Exception {
		String[] orders = ordeno.split(",");
		for (String order : orders) {
			WMSOrderCancle wmsOrder = new WMSOrderCancle();
			wmsOrder.setOrdeno(order);
			wmsOrder.setWhcode("SZHAJ01");
			wmsOrder.setReason("订单取消");
			String isSuccess = cancleOrderToWMSManage.cancelOrderDN(wmsOrder);

			if (isSuccess.equals("true")) {
				hajOrderService.updateWMShandleStatusByOrderNO(order);
				out.write("success");
			} else {
				out.write("false");
			}
		}
		out.close();
	}

	@RequestMapping(value = "/toWMSCancle")
	public String toWMSCancle(Model model) {
		return "wmscancle";
	}
}
