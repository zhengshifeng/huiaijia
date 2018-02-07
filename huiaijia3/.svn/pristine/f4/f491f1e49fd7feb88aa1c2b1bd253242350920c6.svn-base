package com.flf.job;


import com.flf.entity.HajOrder;
import com.flf.entity.invite.HajMemberInvitations;
import com.flf.service.HajOrderService;
import com.flf.service.RedisSpringProxy;
import com.flf.service.invite.HajMemberInvitationsService;
import com.flf.util.RedisLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
  ** 邀请有礼定时器
 *
 */
public class InviteQuarts {
	private final static Logger log = Logger.getLogger(InviteQuarts.class);
	@Autowired
	private RedisSpringProxy redisSpringProxy;
	@Autowired
	private HajMemberInvitationsService memberInvitationsService;
	@Autowired
	private HajOrderService  hajOrderService;

	/**
	 * 更新邀请新用户状态。对邀请用户下单成功（已完成，或已退款）状态 处理邀请状态
	 */
	public void updateInviteStatus(){
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_Invite_redis_job", 10000, 20000);
		try {
			boolean isTrue = lock.lock();
			log.info("key_Invite_redis_job,更新邀请状态：" + isTrue);
			if (isTrue) {
				List<HajMemberInvitations>  memberInvitationsList=memberInvitationsService.getMemberInvitationsList();
				if(memberInvitationsList.size()>0 && !memberInvitationsList.isEmpty())
				{
					for(HajMemberInvitations hajMemberInvitations : memberInvitationsList){
					  String invitee=hajMemberInvitations.getInvitee() ;//获取被邀请人
					  //通过被邀请人。查询订单，
						List<HajOrder> orderList=hajOrderService.getOrderByInviteList(invitee);
						if(!orderList.isEmpty()&&orderList.size()>0){
							memberInvitationsService.updateMemberInvitations(invitee);
						}
					}
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


}
