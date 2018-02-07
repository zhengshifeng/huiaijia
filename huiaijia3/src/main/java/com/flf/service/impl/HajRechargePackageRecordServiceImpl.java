package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajRechargePackageRecord;
import com.flf.mapper.HajRechargePackageRecordMapper;
import com.flf.service.HajRechargePackageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 充值套餐记录service
 */
@Service("hajRechargePackageRecordService")
public class HajRechargePackageRecordServiceImpl extends BaseServiceImpl implements HajRechargePackageRecordService {

	@Autowired
	private HajRechargePackageRecordMapper rechargePackageRecordDao;

	public HajRechargePackageRecordMapper getDao() {
		return rechargePackageRecordDao;
	}

	/**
	 * 获取充值套餐记录管理列表
	 */
	public List<HajRechargePackageRecord> listPageRechargePackageRecord(HajRechargePackageRecord hajRechargePackageRecord) {
		return rechargePackageRecordDao.listPageRechargePackageRecord(hajRechargePackageRecord);
	}

	public HajRechargePackageRecord getHajRechargePackageRecordById(int id) {
		return rechargePackageRecordDao.getHajRechargePackageRecordById(id);
	}

	public void save(HajRechargePackageRecord hajRechargePackageRecord) {
		if (hajRechargePackageRecord.getId() != null && hajRechargePackageRecord.getId() > 0) {
			rechargePackageRecordDao.updateRechargePackageRecord(hajRechargePackageRecord);
		} else {
			rechargePackageRecordDao.insertRechargePackageRecord(hajRechargePackageRecord);
		}
	}

	/***统计充值套餐记录数***/
	public Map<String, Object> queryTotalChargePackageRecord(HajRechargePackageRecord hajRechargePackageRecord) {
		return rechargePackageRecordDao.queryTotalChargePackageRecord(hajRechargePackageRecord);
	}

	@Override
	public List<Map<String, Object>> listAllRechargePackageRecord(HajRechargePackageRecord hajRechargePackageRecord) {
		return rechargePackageRecordDao.listAllRechargePackageRecord(hajRechargePackageRecord);
	}
}
