package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajRefundsRecord;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */
public interface HajRefundsRecordMapper  extends BaseDao {
	void insertRefundsRecord(HajRefundsRecord hajRefundsRecord);
	List<HajRefundsRecord> getRefundsRecordById(int id);
}
