package com.flf.job;

import com.flf.service.HajOrderService;
import com.flf.service.HajPurchaseOrderService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.DateUtil;
import com.flf.util.RedisLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class myQuarts {
	private final static Logger log = Logger.getLogger(myQuarts.class);

	@Autowired(required = false)
	private HajPurchaseOrderService hajPurchaseOrderService;

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public void tips() {
		log.info("进入生成采购单调度方法start==============");
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_purchase_redis_job", 10000, 20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_purchase_redis_job,是否获取采购单锁：" + isTrue);

			if (isTrue) {
				log.info("进入采购单锁：");

				// 为了避免生成采购单的期间内有人卡点支付，导致卡点支付的单未生成采购单，因此这里的实际生成时间减掉1分钟
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 1);

				String nowDate = DateUtil.datetime2Str(calendar.getTime());

				// 创建5个线程池
				ExecutorService executor = Executors.newFixedThreadPool(5);
				HajPurchaseOrder myTask;

				// 查询当天的订单
				List<Map<String, Object>> orderMapList = hajOrderService.getOrderByDate(nowDate);

				log.info("更新订单生成采购订单的状态 handleStatus = 4 " + nowDate);
				hajOrderService.updateOrderHandleStatus(nowDate);

				for (Map<String, Object> obj : orderMapList) {
					myTask = new HajPurchaseOrder(obj);
					executor.execute(myTask);
				}

				log.info("生成采购订单的时间nowDate：" + nowDate);
				hajPurchaseOrderService.updateOrderHandle(nowDate);
				log.info("进入生成采购单调度方法over=======================");
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
	 * 自动生成采购单工作线程
	 * 
	 * @author Hang
	 *
	 */
	class HajPurchaseOrder extends Thread {
		private Map<String, Object> map;

		public HajPurchaseOrder(Map<String, Object> dataMap) {
			this.map = dataMap;
		}

		@Override
		public synchronized void run() {
			log.info(currentThread().getName() + "启动准备处理数据");

			try {
				if (null != map && map.size() > 0) {
					// 插入采购单号
					hajPurchaseOrderService.savePurchaseOrder(map);
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
