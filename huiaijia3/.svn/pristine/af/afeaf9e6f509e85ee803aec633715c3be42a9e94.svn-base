package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCouponInfo;
import com.flf.entity.HajCouponSendUser;
import com.flf.entity.HajFrontUser;
import com.flf.mapper.HajCouponSendUserMapper;
import com.flf.mapper.HajFrontUserMapper;
import com.flf.service.HajCouponSendUserService;
import com.flf.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.flf.util.ExcelUtil.getCellValue;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponSendUserService<br>
 */
@Service("hajCouponSendUserService")
public class HajCouponSendUserServiceImpl extends BaseServiceImpl implements HajCouponSendUserService {
	private final static Logger log = Logger.getLogger(HajCouponSendUserServiceImpl.class);
	
	@Autowired
	private HajCouponSendUserMapper dao;

	@Autowired
	private HajFrontUserMapper frontUserMapper;

	@Override
	public HajCouponSendUserMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCouponSendUser> listPage(HajCouponSendUser po) {
		return dao.listPage(po);
	}

	@Override
	public void deleteByCoupon(Integer couponId) {
		dao.deleteByCoupon(couponId);
	}

	@Override
	public Map<String,Object> batchImport_new(String filePath, HajCouponInfo couponInfo) throws IOException {
		XSSFWorkbook xwb = new XSSFWorkbook(filePath);
		// 读取第一张sheet表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		xwb.close();// 记得关闭流
		XSSFRow row;

		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Integer> userList = new ArrayList<>();
		StringBuilder errorUsers = new StringBuilder("不符合条件:");
		StringBuilder successUsers = new StringBuilder("执行成功的:");
		resultMap.put("result",false);

		if (sheet.getPhysicalNumberOfRows()<1) {
			resultMap.put("errorUsers","导入的数据行不能为空");
			log.info("导入的数据行不能为空，已阻止！");
			return resultMap;
		}if(sheet.getPhysicalNumberOfRows() > 10000){
			resultMap.put("errorUsers","单次导入的数据行不能超过10000行");
			log.info("单次导入的数据行超过10000行，已阻止！");
			return resultMap;
		}
		// 获取到所有 excel 内的号码
		StringBuilder items = new StringBuilder();
		for (int i = (sheet.getFirstRowNum() + 1), rows = sheet.getPhysicalNumberOfRows(); i < rows; i++) {
			row = sheet.getRow(i);
			String item = getCellValue(row.getCell(0));
			if (!Tools.isEmpty(item)) {
				item = item+",";
				items.append(item);
			}
		}

		// 批量查询出所有数据（in(excel 内的所有号码)）
		String[] arrs = items.toString().split(",");
		List<String> mblist = Arrays.asList(arrs);
		List<HajFrontUser> list = frontUserMapper.getUserInMobiles(mblist);
		StringBuilder listStr = new StringBuilder();
		// 筛选符合条件的号码，和不符合条件的号码
		for(HajFrontUser vo : list){
			listStr.append(vo.getMobilePhone());
			if(couponInfo.getMemberLimit()==0){	//无限制
				userList.add(vo.getId());
				successUsers.append(vo.getMobilePhone()+",");
			}else if (!couponInfo.getMemberLimit().toString().equals(vo.getIsmember())){	//不符合条件的号码
				errorUsers.append(vo.getMobilePhone()+",");
			}else{
				userList.add(vo.getId());
				successUsers.append(vo.getMobilePhone()+",");
			}
		}

		// 筛选出所有不存在数据库的号码
		for(String arr:arrs){
			if(!listStr.toString().contains(arr)){
				errorUsers.append(arr+",");
			}
		}

		// 批量发送优惠券
		if (userList.size() > 0) {
			// 因为之前可能存在发放失败，但是coupon_send_user表中已经导入成功的数据。因此导入之前先清掉这些数据。
			try {
				dao.deleteByCoupon(couponInfo.getId());
				dao.addBatch(userList, couponInfo.getId());
				resultMap.put("result",true);
			} catch (Exception e) {
				resultMap.put("errorUsers","系统异常，优惠券发放失败!");
				log.info("优惠券发放失败："+e.getMessage());
				e.printStackTrace();
			}
		}
		resultMap.put("userList",successUsers.toString());
		resultMap.put("errorUsers",errorUsers.toString());
		return resultMap;
	}

	@Override
	public String batchImport(String filePath, HajCouponInfo couponInfo) throws IOException {
		XSSFWorkbook xwb = new XSSFWorkbook(filePath);

		// 读取第一张sheet表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		xwb.close();// 记得关闭流

		XSSFRow row;
		String content;
		HajFrontUser frontUser;
		List<Integer> userList = new ArrayList<>();
		StringBuilder errorUsers = new StringBuilder("第");
		for (int i = (sheet.getFirstRowNum() + 1), rows = sheet.getPhysicalNumberOfRows(); i < rows; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			// 一次只能导入1000条数据
			if (rows > 1000) {
				errorUsers = new StringBuilder("单次导入的数据行不能超过1000行");
				log.info("单次导入的数据行超过1000行，已阻止！");
				break;
			}

			try {
				content = getCellValue(row.getCell(0));
				if (Tools.isEmpty(content)) {
					continue;
				}
				frontUser = frontUserMapper.getUserByMobile(content);
				if (frontUser != null) {
					if (couponInfo.getMemberLimit() != null && couponInfo.getMemberLimit() > 0) {
						if (!couponInfo.getMemberLimit().toString().equals(frontUser.getIsmember())) {
							errorUsers.append(i+1).append(',');
						} else {
							userList.add(frontUser.getId());
						}
					} else {
						userList.add(frontUser.getId());
					}
				} else {
					errorUsers.append(i+1).append(',');
				}
			} catch (Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		if (errorUsers.indexOf(",") > 0) {
			errorUsers = new StringBuilder(errorUsers.substring(0, errorUsers.length()-1));
			errorUsers.append("行的用户不满足优惠券领取的条件");
		} else if (errorUsers.toString().equals("第")) {
			errorUsers = new StringBuilder();
			if (userList.size() > 0) {
				// 因为之前可能存在发放失败，但是coupon_send_user表中已经导入成功的数据。因此导入之前先清掉这些数据。
				dao.deleteByCoupon(couponInfo.getId());
				dao.addBatch(userList, couponInfo.getId());
			} else {
				errorUsers = new StringBuilder("未读取到表格中的用户数据，发放失败");
			}
		}
		return errorUsers.toString();
	}

	@Override
	public List<Integer> listSendUserByCoupon(Integer couponId) {
		return dao.listSendUserByCoupon(couponId);
	}

}
