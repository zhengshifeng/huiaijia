package com.flf.job;

import com.flf.entity.HajCouponInfo;
import com.flf.service.HajCouponInfoService;
import com.flf.service.HajCouponUserService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.RedisLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 清算用户过期优惠卷
 * 
 * @author li.yb
 *
 */
public class ClearUserCouponQuarts {
	private final static Logger log = Logger.getLogger(ClearUserCouponQuarts.class);

	@Autowired(required = false)
	private HajCouponInfoService couponInfoService;

	@Autowired(required = false)
	private HajCouponUserService service;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public void clearUserCoupon() {
		log.info("清算用户过期优惠卷");

		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_clearUserCoupon_redis_job", 10000,
				20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_clearUserCoupon_redis_job,是否获取清算用户过期优惠卷：" + isTrue);
			if (isTrue) {

				// 创建n个线程池
				ExecutorService executor = Executors.newFixedThreadPool(4);

				// 清理用户领取的过期优惠券
				service.updateClearUserCoupon();

				// =============================================================
				// 结束因设置了绝对时间而到期的优惠券活动，顺便将用户领取的这些优惠券也设为失效
				log.info("开始清理因设置了绝对时间而到期的优惠券活动");
				List<HajCouponInfo> todayInvalidCoupon = couponInfoService.listTodayInvalidCoupon();
				HajCouponInfo couponInfo;
				for (HajCouponInfo coupon : todayInvalidCoupon) {
					couponInfo = new HajCouponInfo();
					couponInfo.setId(coupon.getId());
					couponInfo.setStatus(3);
					couponInfoService.updateBySelective(couponInfo);

					service.updateInvalidateByCouponId(coupon.getId());
				}

				executor.shutdown();
			}
		} catch (InterruptedException e) {
			log.info(e.getMessage(), e);
		} finally {
			// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			// 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
			lock.unlock();
		}

		log.info("Finished all threads");
	}

}
