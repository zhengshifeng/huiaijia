package com.flf.job;

import com.flf.service.HajCommodityService;
import com.flf.service.HajOrderService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.HajCommodityUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 计算订单运费
 * 
 * @author li.yb
 *
 */
public class ClearOrderQuarts {
	private final static Logger log = Logger.getLogger(ClearOrderQuarts.class);

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	public void clearOrder() {
		log.info("进入清理超时未支付的订单，清理库存");
		HajClearOrder myTask;

		// 创建n个线程池
		ExecutorService executor = Executors.newFixedThreadPool(4);

		String orderClearTime = (String) redisSpringProxy.read("SystemConfiguration_orderClearTime");

		// 查询超过规定时间未支付的订单
		List<Map<String, Object>> orderMapList = hajOrderService.getClearOrderByDate(orderClearTime);

		for (Map<String, Object> obj : orderMapList) {
			myTask = new HajClearOrder(obj);
			executor.execute(myTask);
		}

		try {
			// 线程停止5秒
			Thread.sleep(5000);
			if (orderMapList.size() > 0) {
				// 上架完成之后清理缓存
				HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		log.info("订单清理调度完成...");
	}

	/**
	 * 清理超时未支付的订单
	 * 
	 * @author Hang
	 *
	 */
	class HajClearOrder extends Thread {
		private Map<String, Object> map;

		public HajClearOrder(Map<String, Object> dataMap) {
			this.map = dataMap;
		}

		@Override
		public synchronized void run() {
			log.info(currentThread().getName() + "启动准备处理数据");
			try {
				if (null != map && map.size() > 0) {
					// 更新订单为系统取消，释放库存 ，商品自动上架
					hajOrderService.updateClearOrder(map);
				}
			} catch (Exception e) {
				log.info(currentThread().getName() + "处理数据时出错", e);
			} finally {
				log.info(currentThread().getName() + "处理完成");
			}
		}
	}

}
