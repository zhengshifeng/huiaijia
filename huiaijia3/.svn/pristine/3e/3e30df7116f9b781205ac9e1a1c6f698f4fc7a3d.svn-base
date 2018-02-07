package com.flf.job;

import com.flf.entity.OrderVO;
import com.flf.service.HajOrderService;
import com.flf.service.RedisSpringProxy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步订单至wms平台
 * @author li.yb
 *
 */
public class SynchronizeOrderWMSQuarts {
	private final static Logger log = Logger.getLogger(SynchronizeOrderWMSQuarts.class);

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public void synchronizeOrder() {
		log.info("进入同步订单调度方法开始==================");

		HajSynchronizeOrder myTask;
		// 创建n个线程池
		ExecutorService executor = Executors.newFixedThreadPool(4);
		// A,B,C,D,E分类配置参数
		String orderClassification = (String) redisSpringProxy.read("SystemConfiguration_orderClassification");
		String wmsOrderflag = (String) redisSpringProxy.read("SystemConfiguration_wmsOrderflag");
		OrderVO orderVo = new OrderVO();
		try {
			List<Map<String, Object>> orderMapList = hajOrderService.listAllSynchronizeOrder(orderVo);
			for (Map<String, Object> obj : orderMapList) {
				myTask = new HajSynchronizeOrder(obj, orderClassification, wmsOrderflag);
				executor.execute(myTask);
			}
			log.info("同步订单调度方法结束==================");
		} catch (Exception e) {
			e.printStackTrace();
		}

		executor.shutdown();
		System.out.println("Finished all threads");
	}

	/**
	 * 同步订单工作线程
	 */
	class HajSynchronizeOrder extends Thread {
		private Map<String, Object> map;
		private String orderClassification;
		private String wmsOrderflag;

		public HajSynchronizeOrder(Map<String, Object> dataMap, String orderClassification, String wmsOrderflag) {
			this.map = dataMap;
			this.orderClassification = orderClassification;
			this.wmsOrderflag = wmsOrderflag;
		}

		@Override
		public synchronized void run() {
			log.info(currentThread().getName() + "启动准备处理数据");
			try {
				if (null != map && map.size() > 0) {
					if (wmsOrderflag.equals("1")) {
						hajOrderService.synchronizeOrder(map, orderClassification);
					} else {
						hajOrderService.synchronizehebingOrder(map);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info(currentThread().getName() + "处理数据时出错" + e);
			} finally {
				log.info(currentThread().getName() + "处理完成");
			}
		}
	}

}
