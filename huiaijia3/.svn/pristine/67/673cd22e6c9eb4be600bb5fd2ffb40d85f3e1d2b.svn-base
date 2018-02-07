package com.flf.job;

import com.flf.service.HajOrderPostFeeService;
import com.flf.service.HajOrderService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.RedisLock;
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
public class calOrderPostFeeQuarts {
	private final static Logger log = Logger.getLogger(AddSortingOrderQuarts.class);

	@Autowired(required = false)
	private HajOrderPostFeeService hajOrderPostFeeService;

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public void orderPostFee() {
		log.info("进入计算运费调度方法");

		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_postFee_redis_job", 10000, 20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_postFee_redis_job,是否获取运费锁：" + isTrue);

			if (isTrue) {
				CalOrderPostFee myTask;

				// 创建n个线程池
				ExecutorService executor = Executors.newFixedThreadPool(4);
				String commodityAttr = (String) redisSpringProxy.read("SystemConfiguration_postFee_commodityAttr");

				// 查询当天的订单
				List<Map<String, Object>> orderMapList = hajOrderService.getOrderPostFeeByDate();

				for (Map<String, Object> obj : orderMapList) {
					myTask = new CalOrderPostFee(obj, commodityAttr);
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

		System.out.println("Finished all threads");
	}

	/**
	 * 自动计算运费工作线程
	 * 
	 * @author Hang
	 *
	 */
	class CalOrderPostFee extends Thread {
		private Map<String, Object> map;
		private String thisAttr;

		public CalOrderPostFee(Map<String, Object> dataMap, String commodityAttr) {
			this.map = dataMap;
			this.thisAttr = commodityAttr;
		}

		@Override
		public synchronized void run() {
			log.info(currentThread().getName() + "启动准备处理数据");
			try {
				if (null != map && map.size() > 0) {
					// 计算运费
					int result = hajOrderPostFeeService.saveOrderPostFee(map, thisAttr);
					if (result > 0) {
						// 更新订单状态
						hajOrderService.updateOrderPostFee();
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
