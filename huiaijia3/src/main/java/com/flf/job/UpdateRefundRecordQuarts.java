package com.flf.job;

import com.flf.entity.HajRecharge;
import com.flf.entity.HajRechargeVo;
import com.flf.service.HajRechargeService;
import com.flf.util.HajRefund;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateRefundRecordQuarts {

	private final static Logger log = Logger.getLogger(UpdateRefundRecordQuarts.class);

	@Autowired(required = false)
	private HajRechargeService hajRechargeService;

	public void tips() {
		log.info("开始更新流水退款金额,退款状态，退款更新时间");
		HajRefund myTask;
		List<HajRecharge> refundList = hajRechargeService.queryRechargeListForRerund(new HajRechargeVo());
		if (refundList != null && refundList.size() > 0) {
			// 创建n个线程池
			ExecutorService executor = Executors.newFixedThreadPool(4);
			for (HajRecharge hajRecharge : refundList) {
				myTask = new HajRefund(hajRecharge, hajRechargeService);
				executor.execute(myTask);
			}
			executor.shutdown();
			System.out.println("Finished all threads");
		}

		log.info("结束更新流水退款金额,退款状态，退款更新时间");
	}

}
