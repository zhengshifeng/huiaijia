package com.flf.job;

import com.flf.service.HajOrderService;
import com.flf.service.HajSortingOrderService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.RedisLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddSortingOrderQuarts {
	private final static Logger log = Logger.getLogger(AddSortingOrderQuarts.class);

	@Autowired(required = false)
	private HajSortingOrderService hajSortingOrderService;

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public void sorting() {
		log.info("进入分拣单调度方法");
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_sortredis_job", 10000, 20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_sortredis_job,是否获取分拣单锁：" + isTrue);

			if (isTrue) {
				log.info("进入分拣单锁：");
				// 创建5个线程池
				ExecutorService executor = Executors.newFixedThreadPool(4);
				HajPurchaseOrder myTask;

				// 查询当天的订单
				List<Map<String, Object>> orderMapList = hajOrderService.getSortingOrderByDate();

				for (Map<String, Object> obj : orderMapList) {
					myTask = new HajPurchaseOrder(obj);
					executor.execute(myTask);
				}

				executor.shutdown();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			// 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
			lock.unlock();
		}

		log.info("分拣单调度完成");
	}

	/**
	 * 自动生成分拣单工作线程
	 */
	class HajPurchaseOrder extends Thread {
		private Map<String, Object> map;

		HajPurchaseOrder(Map<String, Object> dataMap) {
			this.map = dataMap;
		}

		@Override
		public synchronized void run() {
			log.info(currentThread().getName() + "启动准备处理数据");
			try {
				if (null != map && map.size() > 0) {
					// 插入采购单号
					int result = hajSortingOrderService.saveSortingOrder(map);
					if (result > 0) {
						// 更新订单状态
						hajOrderService.updateOrderSorting();
					}
				}
			} catch (Exception e) {
				log.info(currentThread().getName() + "处理数据时出错", e);
			} finally {
				log.info(currentThread().getName() + "处理完成");
			}
		}
	}

}
