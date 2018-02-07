package com.flf.job;

import com.flf.entity.HajOrder;
import com.flf.entity.HajPurchaseOrder;
import com.flf.service.HajOrderService;
import com.flf.service.HajPurchaseOrderService;
import com.flf.util.DateUtil;
import com.flf.util.wms.WmsSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步订单至京东wms平台
 */
public class WmsOrderQuarts {
	private final static Logger log = Logger.getLogger(WmsOrderQuarts.class);

	@Autowired
	private HajOrderService orderService;

	@Autowired
	private HajPurchaseOrderService purchaseOrderService;

	public void synchronizePoOrder() {
		log.info("同步采购单数据至京东wms平台~~~~start");
		try {
			List<HajPurchaseOrder> purchaseOrderList = purchaseOrderService.jdWmsPoOrder();
			WmsSender.addPoOrder(purchaseOrderList);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
	}

	public void synchronizeOrder() {
		log.info("同步销售订单数据至京东wms平台~~~~start");

		// 创建n个线程池
		ExecutorService executor = Executors.newFixedThreadPool(5);
		HajWmsOrderSender myTask;
		try {
			List<HajOrder> wmsSaleOrderList = orderService.wmsSaleOrderList();
			log.info("此次同步的销售订单数：" + wmsSaleOrderList.size());
			String today = DateUtil.date2Str(new Date(), false);
			for (HajOrder order : wmsSaleOrderList) {
				order.setOrderNo(today + "_" + order.getUserId());
				myTask = new HajWmsOrderSender(order);
				executor.execute(myTask);
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}

		executor.shutdown();
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
			log.info(currentThread().getName() + " start...; orderNo: " + order.getOrderNo());
			try {
				WmsSender.addOrder(order);
			} catch (Exception e) {
				log.info(currentThread().getName() + " error~.~; orderNo: " + order.getOrderNo(), e);
			} finally {
				log.info(currentThread().getName() + " end! orderNo: " + order.getOrderNo());
			}
		}
	}

}
