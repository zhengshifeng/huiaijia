package com.flf.job;

import com.flf.service.HajCommodityService;
import com.flf.service.HajOrderService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.RedisLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateHajOrderStatusQuarts {
	private final static Logger log = Logger.getLogger(UpdateHajOrderStatusQuarts.class);

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private HajCommodityService commodityService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public void tips() {
		log.info("进入修改订单为已完成调度方法");
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_redis_job", 10000, 20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_redis_job,是否获取锁：" + isTrue);

			if (isTrue) {
				log.info("进入锁：");
				// 修改前一天已支付未配送的订单
				hajOrderService.updateHajOrderStatus();

				// 完成此调度后，将前一天商品销量统计到商品表中
				commodityService.updateSalesVolume();
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
}
