package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajRechargePackage;
import com.flf.mapper.HajRechargePackageMapper;
import com.flf.service.HajRechargePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 充值套餐service
 */
@Service("hajRechargePackageService")
public class HajRechargePackageServiceImpl extends BaseServiceImpl implements HajRechargePackageService {

	@Autowired
	private HajRechargePackageMapper rechargePackageDao;
	public HajRechargePackageMapper getDao() {
		return rechargePackageDao;
	}

	/***充值套餐list***/
	public List<HajRechargePackage> listPageRechargePackage(HajRechargePackage hajRechargePackage) {
		return rechargePackageDao.listPageRechargePackage(hajRechargePackage);
	}

	/***保存修改充值套餐***/
	public void save(HajRechargePackage hajRechargePackage) {
		if (hajRechargePackage.getId() != null && hajRechargePackage.getId() > 0) {
			rechargePackageDao.updateRechargePackage(hajRechargePackage);
		} else {
			rechargePackageDao.insertRechargePackage(hajRechargePackage);

		}
	}

	/***获取充值套餐详情***/
	public HajRechargePackage getHajRechargePackageById(int id) {
		return rechargePackageDao.getHajRechargePackageById(id);
	}

	/**app接口，获取可用充值套餐列表***/
	public List<HajRechargePackage> getRechargePackageList() {
		return rechargePackageDao.getRechargePackageList();
	}
}
