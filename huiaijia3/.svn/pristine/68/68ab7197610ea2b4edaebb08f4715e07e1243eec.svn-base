package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajRefundsRecord;
import com.flf.mapper.HajRefundsRecordMapper;
import com.flf.service.HajRefundsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  充值套餐退款记录serviceImpl
 */

@Service("hajRefundsRecordService")
public class HajRefundsRecordServiceImpl extends BaseServiceImpl implements HajRefundsRecordService {
	@Autowired
	private HajRefundsRecordMapper refundsRecordDao;
	public HajRefundsRecordMapper getDao() {
		return refundsRecordDao;
	}

	@Override
	public void insertRefundsRecord(HajRefundsRecord hajRefundsRecord) {
		refundsRecordDao.insertRefundsRecord(hajRefundsRecord);
	}

	@Override
	public List<HajRefundsRecord> getRefundsRecordById(int id) {
		return refundsRecordDao.getRefundsRecordById(id);
	}
}
