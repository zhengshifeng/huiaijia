package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.HajOrderDetailsMapper;
import com.flf.mapper.HajOrderMapper;
import com.flf.mapper.HajOrderPostFeeMapper;
import com.flf.service.*;
import com.flf.util.DateUtil;
import com.flf.util.Tools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderPostFeeService<br>
 */
@Service("hajOrderPostFeeService")
public class HajOrderPostFeeServiceImpl extends BaseServiceImpl implements HajOrderPostFeeService {
	private final static Logger log = Logger.getLogger(HajOrderPostFeeServiceImpl.class);

	@Autowired
	private HajOrderPostFeeMapper dao;

	@Autowired(required = false)
	private HajCommunityPersionService communityPersionService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajFrontUserService hajFrontUserService;

	@Autowired
	private HajTradingHistoryService hajTradingHistoryService;

	@Autowired
	private HajOrderDetailsMapper detailsDao;

	@Autowired
	private HajOrderMapper orderDao;

	@Autowired
	private HajIntegralDetailsService integralDetailsService;

	@Override
	public HajOrderPostFeeMapper getDao() {
		return dao;
	}

	@Override
	public int saveOrderPostFee(Map<String, Object> map, String commodityAttr) {
		int result = 0;
		try {
			// 数量
			int orderNum = Integer.parseInt(map.get("orderNum").toString());
			int userId = Integer.parseInt(map.get("userId").toString());

			BigDecimal actualPayment;
			if (null != commodityAttr) {
				// 验证用户今日订单是否有免单商品
				actualPayment = getTodayOrderMoneyNoCommoidty(commodityAttr, userId);
			} else {
				// 交易单价
				actualPayment = (BigDecimal) map.get("actualPayment");
			}

			String residential = map.get("residential").toString();

			log.info(DateUtil.dateToString(new Date()) + "生成订单运费数据 userId: " + userId + "; 数量: " + orderNum
					+ "; 订单总价: " + actualPayment);

			BigDecimal postFee = calOrderPostFee(residential, actualPayment, userId);

			// 运费大于0
			if (postFee.compareTo(BigDecimal.ZERO) > 0) {
				HajOrderPostFee orderPostFee = new HajOrderPostFee();
				orderPostFee.setIsPay(0);
				orderPostFee.setOrderMoney(actualPayment);
				orderPostFee.setOrderNum(orderNum);
				orderPostFee.setPostFee(postFee);
				orderPostFee.setUserId(userId);
				orderPostFee.setRemark(DateUtil.date2Str(new Date()));

				int id = dao.add(orderPostFee);
				log.info("插入订单运费返回的id: " + id);
				if (id > 0) {
					result = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private BigDecimal getTodayOrderMoneyNoCommoidty(String commodityAttr, int userId) {
		BigDecimal actualPayment = BigDecimal.ZERO;
		List<Map<String, Object>> orderList = orderDao.getOrderIdByUserId(userId);
		String orderId;
		for (Map<String, Object> obj : orderList) {
			orderId = obj.get("orderId").toString();
			int count = detailsDao.getTodayOrderMoneyNoCommoidty(orderId, commodityAttr);
			if (count > 0) {
				actualPayment = actualPayment.add(new BigDecimal(obj.get("actualPayment").toString()));
			}
		}
		return actualPayment;
	}

	@Override
	public int saveCalOrderPostFee(Map<String, Object> map) {
		int result = 0;
		try {
			// 数量
			int orderNum = Integer.parseInt(map.get("orderNum").toString());

			int userId = Integer.parseInt(map.get("userId").toString());

			// 交易单价
			BigDecimal actualPayment = (BigDecimal) map.get("actualPayment");

			String residential = map.get("residential").toString();

			log.info(DateUtil.dateToString(new Date()) + "生成订单运费数据 userId: " + userId + "; 数量: " + orderNum
					+ "; 订单总价: " + actualPayment);

			BigDecimal postFee = calOrderPostFee(residential, actualPayment, userId);

			// 运费大于0
			if (postFee.compareTo(BigDecimal.ZERO) > 0) {
				HajOrderPostFee orderPostFee = new HajOrderPostFee();
				orderPostFee.setIsPay(0);
				orderPostFee.setOrderMoney(actualPayment);
				orderPostFee.setOrderNum(orderNum);
				orderPostFee.setPostFee(postFee);
				orderPostFee.setUserId(userId);
				orderPostFee.setRemark("2017-03-09");

				int id = dao.add(orderPostFee);
				System.out.println("插入订单运费返回的id: " + id);
				if (id > 0) {
					result = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 计算运费
	 */
	public BigDecimal calOrderPostFee(String residential, BigDecimal actualPayment, int userId) {
		BigDecimal orderPostFee = BigDecimal.ZERO;
		try {
			HajFrontUser user = hajFrontUserService.queryById(userId);
			HajCommunityPersion community = communityPersionService.getHajCommunityByName(residential,
					user.getAreaCode());
			if (community != null && community.getNeedPostFee() != null && community.getNeedPostFee() != 0) {
				// 查询运费
				// 请求后台配置，获取收取运费的标准
				String postFeeStandard = (String) redisSpringProxy.read("SystemConfiguration_backpost_fee_standard");
				// postFeeStandard = 0#19#4;19#39#2;39#10000#0
				if (Tools.isNotEmpty(postFeeStandard)) {
					String[] postFees = postFeeStandard.split(";");
					String[] postFees1 = null;
					for (String s : postFees) {
						postFees1 = s.split("#");
						log.info("运费日志最小值：" + postFees1[0] + ",最大值：" + postFees1[1] + ",定值：" + postFees1[2]);

						if (actualPayment.doubleValue() >= new BigDecimal(postFees1[0]).doubleValue()
								&& actualPayment.doubleValue() < new BigDecimal(postFees1[1]).doubleValue()) {
							orderPostFee = new BigDecimal(postFees1[2]);
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderPostFee;
	}

	@Override
	public Map<String, Object> getHajOrderPostFeeByUserId(Integer userId) {
		return dao.getHajOrderPostFeeByUserId(userId);
	}

	/**
	 * 扣减用户运费
	 */
	@Override
	public int updateOrderPostFeeStatus(Integer orderPostFeeId) {
		int resultId = 0;
		try {
			HajOrderPostFee orderPostFee = dao.queryById(orderPostFeeId);
			if (orderPostFee.getIsPay() == 0) {
				// 修改订单运费记录
				resultId = dao.updateOrderPostFee(orderPostFee);
				if (resultId > 0) {
					hajFrontUserService.updateOrderPostFee(orderPostFee.getUserId(), orderPostFee.getPostFee());

					// 插入流水
					HajTradingHistory trading = new HajTradingHistory();
					trading.setTradingContent("扣减配送费：" + orderPostFee.getPostFee());
					trading.setCreateTime(DateUtil.dateToString(new Date()));
					trading.setMoney(orderPostFee.getPostFee());
					trading.setTradingStatus(1);
					trading.setUserId(orderPostFee.getUserId());
					trading.setType(0);// 减少
					hajTradingHistoryService.saveTradeRecord(trading);

					// 支付配送费后记录积分明细
					HajIntegralDetails integralDetails = new HajIntegralDetails();
					integralDetails.setUserId(orderPostFee.getUserId());
					integralDetails.setRemark("支付配送费奖励积分");
					integralDetails.setDetail("+" + orderPostFee.getPostFee().intValue());
					try {
						integralDetailsService.saveDetail(integralDetails);
					} catch (Exception e) {
						log.info("支付配送费后记录积分明细异常，useId: " + orderPostFee.getUserId(), e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultId;
	}

	@Override
	public void updateOrderPostStatusByUserId(Integer userId, int isPay) {
		int id = dao.getTopOrderPostfee(userId);
		HajOrderPostFee postFee = new HajOrderPostFee();
		postFee.setIsPay(isPay);
		postFee.setId(id);
		dao.updateOrderPostStatusByUserId(postFee);
	}

	/**
	 * 配送费预支付接口调用支付配送费接口
	 */
	@Override
	public void updateOrderPostRecharge(Integer rechargeId, int postFeeId) {
		dao.updateOrderPostRecharge(rechargeId, postFeeId);
	}

	/**
	 * 支付成功调用配送费接口
	 */
	@Override
	public int updateOrderPostFeeByRechargeId(Integer rechargeId) {
		int resultId = 0;
		try {
			HajOrderPostFee orderPostFee = dao.queryByrechargeId(rechargeId);
			if (orderPostFee.getIsPay() == 0) {
				// 修改订单运费记录
				resultId = dao.updateOrderPostFee(orderPostFee);

				if (resultId > 0) {
					// 更新用户积分
					hajFrontUserService.updateRechargeOrderPostFee(orderPostFee.getUserId(), orderPostFee.getPostFee());

					// 订单使用第三方支付完成后记录积分明细
					HajIntegralDetails integralDetails = new HajIntegralDetails();
					integralDetails.setUserId(orderPostFee.getUserId());
					integralDetails.setRemark("支付配送费奖励积分");
					integralDetails.setDetail("+" + orderPostFee.getPostFee().intValue());
					try {
						integralDetailsService.saveDetail(integralDetails);
					} catch (Exception e) {
						log.info("支付配送费后记录积分明细异常，useId: " + orderPostFee.getUserId(), e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultId;
	}

	/**
	 * 按条件，分组获取运费总额
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> getPostFeeSumByGroup(HajOrderPostFee postFee) {
		List<Map<String, Object>> list = dao.getPostFeeSumByGroup(postFee);
		//应收取运费总额
		BigDecimal receivableTotal = new BigDecimal(0.00);
		//实收运费总额
		BigDecimal paidTotal = new BigDecimal(0.00);
		if(CollectionUtils.isNotEmpty(list) ) {
			for (int i = 0; i < list.size();i++) {
				Map<String, Object> map = list.get(i);
				for (String key : map.keySet()) {
					if("isPay".equals(key) && "1".equals(String.valueOf(map.get(key))) ){
						paidTotal = paidTotal.add((BigDecimal)map.get("postFee"));
					}
					if("postFee".equals(key)){
						receivableTotal = receivableTotal.add((BigDecimal)map.get("postFee"));
					}
				}
			}
		}
		//代收取金额
		BigDecimal	notPaidTotal = receivableTotal.subtract(paidTotal);
		Map<String, BigDecimal> totalMap = new HashMap<String, BigDecimal>();
		totalMap.put("receivableTotal",receivableTotal);
		totalMap.put("paidTotal",paidTotal);
		totalMap.put("notPaidTotal",notPaidTotal);
		return totalMap;
	}

	@Override
	public List<Map<String, Object>> listPageHajOrderPostFee(HajOrderPostFee postFee) {
		return dao.listPageHajOrderPostFee(postFee);
	}

	@Override
	public XSSFWorkbook excelOrderPostFee(HajOrderPostFee postFee) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row);

		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		List<Map<String, Object>> orderPostFee = dao.excelOrderPostFee(postFee);
		Map<String, Object> vo;

		int cellIndex = 0;
		String returnStatus = "";
		for (int i = 0, len = orderPostFee.size(); i < len; i++, cellIndex = 0) {
			vo = orderPostFee.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("postFee")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("remark")));
			row.createCell(cellIndex++).setCellValue(getOrderStatus(getMapValue(vo.get("isPay")), returnStatus));

			if (vo.get("isPay").equals("0") || vo.get("isPay").equals("2")) {
				row.createCell(cellIndex++).setCellValue("0");
			} else {
				row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("postFee")));
			}
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("payTime")));
			if (getMapValue(vo.get("rechargeType")).equals("0")) {
				row.createCell(cellIndex++).setCellValue("余额");
			} else if (getMapValue(vo.get("rechargeType")).equals("1")) {
				row.createCell(cellIndex++).setCellValue("微信");
			} else if (getMapValue(vo.get("rechargeType")).equals("2")) {
				row.createCell(cellIndex++).setCellValue("支付宝");
			}
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("bankNo")));
		}
		return wkb;
	}

	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

	private void setDefaultXSSFRow(XSSFRow row) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("手机号码");
		row.createCell(cellIndex++).setCellValue("应收配送费");
		row.createCell(cellIndex++).setCellValue("生成时间");
		row.createCell(cellIndex++).setCellValue("支付状态");
		row.createCell(cellIndex++).setCellValue("实付配送费");
		row.createCell(cellIndex++).setCellValue("支付时间");
		row.createCell(cellIndex++).setCellValue("支付方式");
		row.createCell(cellIndex++).setCellValue("支付流水号");
	}

	/**
	 * 返回订单状态码对应的状态
	 */
	private String getOrderStatus(String status, String returnStatus) {
		switch (status) {
		case "0":
			returnStatus = "待支付";
			break;
		case "1":
			returnStatus = "已支付";
			break;
		case "2":
			returnStatus = "后台取消";
			break;
		}
		return returnStatus;
	}
}
