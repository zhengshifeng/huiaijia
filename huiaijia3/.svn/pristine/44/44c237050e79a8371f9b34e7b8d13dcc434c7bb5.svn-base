package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.*;
import com.flf.service.HajCouponInfoService;
import com.flf.util.DateUtil;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajCouponInfoService<br>
 */
@Service("hajCouponInfoService")
public class HajCouponInfoServiceImpl extends BaseServiceImpl implements HajCouponInfoService {
	private final static Logger log = Logger.getLogger(HajCouponInfoServiceImpl.class);

	@Autowired
	private HajCouponInfoMapper dao;

	@Autowired
	private HajCouponCommodityMapper couponCommodityMapper;

	@Autowired
	private HajCouponResidentialMapper couponResidentialMapper;

	@Autowired
	private HajCouponSendUserMapper couponSendUserMapper;

	@Autowired
	private HajCouponUserMapper couponUserMapper;

	@Autowired
	private HajFrontUserMapper frontUserMapper;

	@Override
	public HajCouponInfoMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCouponInfo> listPage(HajCouponInfo po) {
		return dao.listPage(po);
	}

	@Override
	public HajCouponInfo getCouponInfoByNo(String couponNo) {
		return dao.getCouponInfoByNo(couponNo);
	}

	@Override
	public String updateCouponIssue(HajCouponInfoVo vo, HajCouponInfo couponInfo) {
		// 小区限制
		List<Integer> residentialList = couponResidentialMapper.listResidentialByCoupon(couponInfo.getId());

		// 商品限制
		List<String> commodityList = couponCommodityMapper.listCommodityByCoupon(couponInfo.getId());

		if (residentialList.size() == 0 || commodityList.size() == 0) {
			return "请先设置小区与商品";
		}

		if (vo.getSendType() == 1) {
			// 1.系统发放
			// 不能超过发放数量
			Integer number = couponInfo.getNumber();

			// 会员限制
			Integer memberLimit = couponInfo.getMemberLimit();

			// --指定用户派发--
			List<Integer> userList = new ArrayList<>();
			if (vo.getUserLimit() == 1) {
				userList = couponSendUserMapper.listSendUserByCoupon(vo.getId());
			}

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("residentialList", residentialList);
			paramMap.put("userList", userList);
			paramMap.put("member", memberLimit);

			List<Integer> userIds = frontUserMapper.getUsersByCouponLimit(paramMap);

			if (userIds.size() > number) {
				return "检测到符合条件的用户" + userIds.size() + "个，优惠券数量不足，派发失败";
			} else if (userIds.size() == 0) {
				return "检测到符合条件的用户" + userIds.size() + "个，优惠券未派发";
			} else {
				try {
					issueByUserList(userIds, couponInfo);
				} catch (ParseException e) {
					log.info(e.getMessage(), e);
					return "派发失败，请联系管理员";
				}

				// 更新优惠券为已发放
				HajCouponInfo updateCoupon = new HajCouponInfo();
				updateCoupon.setId(vo.getId());
				updateCoupon.setSendType(vo.getSendType());

				// 发放时将优惠券开启
				updateCoupon.setStatus(2);
				dao.updateBySelective(updateCoupon);
			}
		} else if (vo.getSendType() == 2) {
			// 2.兑换码兑换（仅做修改即可）
			if (Tools.isNotEmpty(vo.getCouponNo()) && dao.checkTheSameCoupon(vo.getCouponNo()) == 0) {
				issueByCode(vo);
			} else {
				return "派发失败，兑换码为空或已存在该优惠券";
			}
		}

		return "success";
	}

	@Override
	public List<HajCouponUseInfoVo> listPageCouponUseInfo(HajCouponUseInfoVo vo) {
		return dao.listPageCouponUseInfo(vo);
	}

	@Override
	public List<HajCouponInfo> listTodayInvalidCoupon() {
		return dao.listTodayInvalidCoupon();
	}

	private void issueByCode(HajCouponInfoVo vo) {
		HajCouponInfo couponInfo = new HajCouponInfo();
		couponInfo.setId(vo.getId());
		couponInfo.setSendType(vo.getSendType());
		couponInfo.setCouponNo(vo.getCouponNo());

		// 发放时将优惠券开启
		couponInfo.setStatus(2);
		dao.updateBySelective(couponInfo);
	}

	private void issueByUserList(List<Integer> userList, HajCouponInfo couponInfo) throws ParseException {
		if (userList == null || userList.size() == 0) {
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(new Date());
		Date nowDate = sdf.parse(format);
		List<HajCouponUser> couponUserList = new ArrayList<>();
		HajCouponUser couponUser;
		for (Integer userId : userList) {
			couponUser = new HajCouponUser();
			couponUser.setUserId(userId);
			couponUser.setIsRead(0);
			couponUser.setCouponId(couponInfo.getId());
			couponUser.setIsUsed(0);
			couponUser.setCreateTime(new Date());
			if (couponInfo.getInvalidWay() == 0) { // 绝对时间
				couponUser.setBeginTime(couponInfo.getBeginTime());
				couponUser.setEndTime(couponInfo.getEndTime());
			} else { // 相对时间
				couponUser.setBeginTime(couponUser.getCreateTime());

				// 相对时间根据创建时间加上优惠券设定的截止天数
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(couponUser.getCreateTime());
				calendar.add(Calendar.DATE, couponInfo.getDateNumbers() - 1);
				couponUser.setEndTime(calendar.getTime());
			}

			couponUser.setIsValidate(0);
			if (nowDate.getTime() - couponUser.getEndTime().getTime() > 0) {
				// 当优惠券结束时间小于当前时间时，优惠券设为过期
				couponUser.setIsValidate(1);
			}

			couponUserList.add(couponUser);
		}
		couponUserMapper.addBatch(couponUserList);
	}

	@Override
	public Double queryRateById(Integer couponId) {
		return dao.queryRateById(couponId);
	}

	@Override
	public XSSFWorkbook export2excel(HajCouponInfo vo) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(10);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row);

		// ============================================================================
		// 开始写入商品数据
		// ============================================================================
		HajCouponUseInfoVo dto = new HajCouponUseInfoVo();
		dto.getPage().setShowCount(60000);
		dto.setId(vo.getId());
		List<HajCouponUseInfoVo> couponInfoList = dao.listPageCouponUseInfo(dto);

		// excel中列的索引
		int cellIndex;

		HajCouponUseInfoVo info;
		String content;
		for (int i = 0, len = couponInfoList.size(); i < len; i++) {
			cellIndex = 0;
			info = couponInfoList.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(info.getMobilePhone());
			row.createCell(cellIndex++).setCellValue(info.getCommunityName());
			row.createCell(cellIndex++).setCellValue(DateUtil.datetime2Str(info.getCreateTime()));
			switch (info.getSendType()) {
				case 1:
					content = "系统发放";
					break;
				case 2:
					content = "兑换码兑换";
					break;
				default:
					content = "";
			}
			row.createCell(cellIndex++).setCellValue(content);
			switch (info.getIsUsed()) {
				case 0:
					content = "未使用";
					break;
				case 1:
					content = "已使用";
					break;
				default:
					content = "";
			}
			row.createCell(cellIndex++).setCellValue(content);
			switch (info.getIsValidate()) {
				case 0:
					content = "有效";
					break;
				case 1:
					content = "失效";
					break;
				default:
					content = "";
			}
			row.createCell(cellIndex++).setCellValue(content);
			row.createCell(cellIndex++).setCellValue(info.getOrderNo());
			content = "";
			if (info.getActualPayment() != null) {
				content = String.valueOf(info.getActualPayment());
			}
			row.createCell(cellIndex).setCellValue(content);
		}

		return wkb;
	}

	@Override
	public List<HajCouponInfo> listByCouponIds(List<String> couponList) {
		return dao.listByCouponIds(couponList);
	}

	private void setDefaultXSSFRow(XSSFRow row) {
		int cellIndex = 0;

		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("手机号码");
		row.createCell(cellIndex++).setCellValue("小区名称");
		row.createCell(cellIndex++).setCellValue("领取时间");
		row.createCell(cellIndex++).setCellValue("领取方式");
		row.createCell(cellIndex++).setCellValue("使用情况");
		row.createCell(cellIndex++).setCellValue("有效期");
		row.createCell(cellIndex++).setCellValue("订单号");
		row.createCell(cellIndex).setCellValue("订单实付");
	}

}
