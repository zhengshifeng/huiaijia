package com.flf.job;


import com.flf.service.HajCouponUserService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.RedisLock;
import com.flf.util.gexin.AppPush;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 推送优惠卷，使用，过期通知

 */
public class NotifyUserCouponQuarts {
	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajCouponUserService  hajCouponUserService;

	public static final String title ="汇爱家";
	public static final String body="【恭喜您获得小二派发的优惠券啦【立即查看】】";
	public static final String notifyType="refund";

	public static final String systemCouponType="coupon_system"; //系统派发优惠卷

	public static final String  overdueCouponType="coupon_will_overdue";  //过期优惠卷


	private final static Logger log = Logger.getLogger(NotifyUserCouponQuarts.class);

	/**
	 * 通知派发优惠卷通知
	 */
	public void notifyDistributeCoupon(){
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_notifyDistributeCoupon_redis_job", 10000, 20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_notifyDistributeCoupon_redis_job,通知派发优惠卷：" + isTrue);
			if (isTrue) {
				//查询当天派发的优惠卷
				List<Map<String, Object>>  couponMapList=hajCouponUserService.getDistributeCouponByDate();
				for(Map <String ,Object> resultMap:couponMapList){
					log.info("推送派发优惠卷通知用户userId=" + resultMap.get("userId").toString());
					AppPush.sendByAlias(resultMap.get("userId").toString(), title, body, systemCouponType);
				}
//				AppPush.sendByAlias("214", title, body, systemCouponType);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			// 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
			lock.unlock();
		}



	}

	/**
	 * 通知即将过期优惠卷通知
	 */
	public void  notifyOverdueCoupon(){
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_notifyOverdueCoupon_redis_job", 10000, 20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_notifyOverdueCoupon_redis_job,通知即将过期优惠卷通知：" + isTrue);
			if (isTrue) {
				//查询当天即将过期优惠卷
				List<Map<String, Object>> couponMapList= hajCouponUserService.getOverdueCouponByDate();
				for(Map <String ,Object> resultMap:couponMapList){
					log.info("推送过期优惠券通知用户userId=" + resultMap.get("userId").toString());
					AppPush.sendByAlias(resultMap.get("userId").toString(), title, "【您有"+resultMap.get("number").toString()+"张优惠券在今日过期哦【立即使用】】", overdueCouponType);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			// 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
			lock.unlock();
		}

	}


	public static void   main(String  []args){

		    //214
		AppPush.sendByAlias("214", title, body, systemCouponType);
	}



}
