package com.flf.controller;

import com.flf.entity.HajOrder;
import com.flf.entity.HajPurchaseOrder;
import com.flf.service.HajOrderService;
import com.flf.service.HajPurchaseOrderService;
import com.flf.util.wms.WmsSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by SevenWong on 2017/12/1 19:20.
 */
@Controller
@RequestMapping(value = "/wmsTest")
public class WmsTestController {
	private final static Logger log = Logger.getLogger(WmsTestController.class);

	@Autowired
	private HajOrderService orderService;

	@Resource
	private HajPurchaseOrderService purchaseOrderService;

	@RequestMapping(value = "/testPoOrder")
	public void testPoOrder(PrintWriter out) {
		log.info("同步采购单数据至京东wms平台~~~");
		try {
			List<HajPurchaseOrder> purchaseOrderList = purchaseOrderService.jdWmsPoOrder();
			String result = WmsSender.addPoOrder(purchaseOrderList);
			out.write(result);
			out.close();
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
	}


	@RequestMapping(value = "/testOrder")
	public void synchronizePoOrder(PrintWriter out) {
		log.info("同步销售订单数据至京东wms平台~~~~");

		WmsTestController.HajWmsOrderSender myTask;

		// 创建n个线程池
		ExecutorService executor = Executors.newFixedThreadPool(5);
		try {
			List<HajOrder> wmsSaleOrderList = orderService.wmsSaleOrderList();
			for (HajOrder order : wmsSaleOrderList) {
				myTask = new WmsTestController.HajWmsOrderSender(order);
				executor.execute(myTask);
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}

		executor.shutdown();
		out.write("true");
		out.close();
	}

	/**
	 * 同步订单工作线程
	 */
	class HajWmsOrderSender extends Thread {
		private HajOrder order;

		HajWmsOrderSender(HajOrder order) {
			this.order = order;
		}

		@Override
		public synchronized void run() {
			log.info(currentThread().getName() + "启动准备处理数据");
			try {
				WmsSender.addOrder(order);
			} catch (Exception e) {
				log.info(currentThread().getName() + "处理数据时出错", e);
			} finally {
				log.info(currentThread().getName() + "处理完成");
			}
		}
	}

}
