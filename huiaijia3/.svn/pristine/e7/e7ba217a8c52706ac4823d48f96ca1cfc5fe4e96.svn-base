package com.flf.service.impl;

import com.base.criteria.UserManagerCriteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.HajAreasMapper;
import com.flf.mapper.HajCommunityPersionMapper;
import com.flf.mapper.HajFrontUserMapper;
import com.flf.service.HajCommunityBuildingService;
import com.flf.service.HajFrontUserService;
import com.flf.service.HajFrontUserUpdateLogService;
import com.flf.service.IHajSMSPushService;
import com.flf.service.invite.HajInviteCodeService;
import com.flf.util.DateUtil;
import com.flf.util.MD5;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajFrontUserService<br>
 */
@Service("hajFrontUserService")
public class HajFrontUserServiceImpl extends BaseServiceImpl implements HajFrontUserService {

	private final static Logger log = Logger.getLogger(HajFrontUserServiceImpl.class);
	@Autowired
	private HajInviteCodeService inviteCodeService ;
	@Autowired
	private HajFrontUserMapper dao;

	@Autowired
	private HajCommunityPersionMapper communityPersionMapper;

	@Autowired(required = false)
	private IHajSMSPushService hajSMSPushService;

	@Autowired(required = false)
	private HajFrontUserUpdateLogService hajFrontUserUpdateLogService;

	@Autowired(required = false)
	private HajCommunityBuildingService communityBuildingService;

	@Autowired(required = false)
	private HajAreasMapper areaDao;

	@Override
	public HajFrontUserMapper getDao() {
		return dao;
	}

	@Override
	public boolean isUserByPhone(String mobilePhone) {
		try {
			int count = dao.isUserByPhone(mobilePhone);
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getUserManagerList(UserManagerCriteria criteria) {
		if (criteria.getPhoneList() != null && criteria.getPhoneList().length < 1) {
			criteria.setPhoneList(null);
		}

		Integer villageId = null;
		try {
			// 查询时，当 residential 为中文时，MySQL将自动把这个值转为数字0，
			// 这样查出来的数据就会有问题，所以这里需要将id跟residential分开保存
			villageId = Integer.valueOf(criteria.getResidential());
		} catch (NumberFormatException e) {
			// Nothing to do
		} finally {
			criteria.setVillageId(villageId);
		}

		return dao.userManagerList(criteria);
	}

	@Override
	public List<Map<String, Object>> getUserManagerById(String paramter) {
		return dao.getUserManagerById(paramter);
	}

	@Override
	public int updateUserBalancePoints(int userId, int rating, BigDecimal balance, HajFrontUser user) {
		if (user.getBalance().compareTo(balance) >= 0) {
			user.setBalance(balance);
			user.setRating(rating);
			user.setId(userId);
			return dao.updateUserBalancePoints(user);
		} else {
			return 0;
		}

	}

	@Override
	public void updateUserCancelOrder(HajOrder order) {
		HajFrontUser user = dao.queryById(order.getUserId());
		user.setBalance(order.getActualPayment().add(order.getDispensingTip()).add(order.getPostFee()));// 取消订单
		// 订单实付+配送小费+运费
		user.setRating(order.getPoints() + order.getDispensingTip().intValue() + order.getPostFee().intValue());
		user.setId(order.getUserId());
		dao.updateUserCancelOrder(user);
	}

	@Override
	public Map<String, Object> getOrderCountByTime(Map<String, Object> map) {
		return dao.getOrderCountByTime(map);
	}

	@Override
	public void updateUserBalancePoints(Integer userId, BigDecimal actualPayment) {
		HajFrontUser user = new HajFrontUser();
		user.setBalance(actualPayment);

		// 2017-10-23 售后退款的积分不需要修改
		//user.setRating(actualPayment.intValue());
		user.setId(userId);
		dao.updateSaleUserBalancePoints(user);
	}

	@Override
	public List<Map<String, Object>> querCustomer_dealList(HajCustomerDeal criteria) {
		return dao.listPageCustomerDeal(criteria);
	}

	@Override
	public XSSFWorkbook exportCustomerDeal(HajCustomerDeal customerDeal) {
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
		List<Map<String, Object>> communityPersions = dao.exportCustomerDeal(customerDeal);
		Map<String, Object> vo = null;

		int cellIndex = 0;
		for (int i = 0, len = communityPersions.size(); i < len; i++) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);
			cellIndex = 0;

			row.createCell(cellIndex++).setCellValue(vo.get("id").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("username").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("mobilePhone").toString());
			if (null != vo.get("receiver")) {
				row.createCell(cellIndex++).setCellValue(vo.get("receiver").toString());
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}
			if (null != vo.get("ismember") && vo.get("ismember").toString().equals("1")) {
				row.createCell(cellIndex++).setCellValue("仅注册");
			} else if (null != vo.get("ismember") && vo.get("ismember").toString().equals("2")) {
				row.createCell(cellIndex++).setCellValue("预备会员");
			} else if (null != vo.get("ismember") && vo.get("ismember").toString().equals("3")) {
				row.createCell(cellIndex++).setCellValue("一元购会员");
			} else if (null != vo.get("ismember") && vo.get("ismember").toString().equals("4")) {
				row.createCell(cellIndex++).setCellValue("会员取消");
			} else if (null != vo.get("ismember") && vo.get("ismember").toString().equals("5")) {
				row.createCell(cellIndex++).setCellValue("普通会员");
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}

			if (null != vo.get("residential")) {
				row.createCell(cellIndex++).setCellValue(vo.get("residential").toString());
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}

			if (null != vo.get("shippingAddress")) {
				row.createCell(cellIndex++).setCellValue(vo.get("shippingAddress").toString());
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}

			if (null != vo.get("address")) {
				row.createCell(cellIndex++).setCellValue(vo.get("address").toString());
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}

			row.createCell(cellIndex++).setCellValue(vo.get("rechargeMoney").toString());// 充值金额
			row.createCell(cellIndex++).setCellValue(vo.get("refundNum").toString());// 支付宝和微信退款
			row.createCell(cellIndex++).setCellValue(vo.get("orderMoney").toString());// 订单金额
			row.createCell(cellIndex++).setCellValue(vo.get("refundMoney").toString());// 后台退款
			// 充值金额+后台退款-订单金额
			Double selectMoney = Double.parseDouble(vo.get("rechargeMoney").toString())
					+ Double.parseDouble(vo.get("refundMoney").toString())
					- Double.parseDouble(vo.get("orderMoney").toString());
			row.createCell(cellIndex++).setCellValue(selectMoney);
			row.createCell(cellIndex++).setCellValue(vo.get("balance").toString());
		}

		return wkb;
	}

	private void setDefaultXSSFRow(XSSFRow row) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("用户编号");
		row.createCell(cellIndex++).setCellValue("用户名");
		row.createCell(cellIndex++).setCellValue("手机号码");
		row.createCell(cellIndex++).setCellValue("收货人");
		row.createCell(cellIndex++).setCellValue("用户角色");
		row.createCell(cellIndex++).setCellValue("小区");
		row.createCell(cellIndex++).setCellValue("小区地址");
		row.createCell(cellIndex++).setCellValue("用户住址");
		row.createCell(cellIndex++).setCellValue("充值金额");
		row.createCell(cellIndex++).setCellValue("充值退款");
		row.createCell(cellIndex++).setCellValue("消费金额");
		row.createCell(cellIndex++).setCellValue("消费退款");
		row.createCell(cellIndex++).setCellValue("条件余额");
		row.createCell(cellIndex++).setCellValue("实时余额");
	}

	@Override
	public List<Map<String, Object>> getUserCommodityType(String userId) {
		return dao.getUserCommodityType(userId);
	}

	@Override
	public int checkAddressUniqueness(HajFrontUser user) {
		return dao.checkAddressUniqueness(user);
	}

	@Override
	public boolean updateUsersStatus(Integer[] ids, Integer status, String operator) {
		try {
			// 批量更新用户状态
			HajFrontUser user;
			HajFrontUserUpdateLog updateLog;
			String userStatus = "未知";
			for (Integer id : ids) {
				user = new HajFrontUser();
				user.setId(id);
				user.setIsmember(status.toString());
				if (status == 3) {
					// 一元购会员添加会员日期
					user.setMemberBeginTime(DateUtil.datetime2Str(new Date()));
					user.setMemberTerm("365");

					// 清掉预约状态和时间
					user.setIsAppointment(2);
					user.setAppointmentTime("");
				} else {
					user.setMemberBeginTime("");
					user.setMemberTerm("");

					// 调整为仅注册时，清空小区相关信息
					if (status == 1) {
						user.setResidential("");
						user.setAddress("");
						user.setVillageId(0);
						user.setShippingAddress("");
						user.setCommunityUnitId(0);
						user.setHouseNumber("");
					}
				}
				dao.updateBySelective(user);

				// ===========================
				// 用户成为一元购会员或者取消会员短信提醒
				// ===========================
				if (status == 3 || status == 4) {
					user = dao.queryById(id);
					if (user != null && Tools.isNotEmpty(user.getMobilePhone())) {
						if (status == 3) {
							hajSMSPushService.becameVIP(user.getMobilePhone(), user.getResidential());
						} else {
							hajSMSPushService.cancleVIP(user.getMobilePhone(), user.getResidential());
						}
					}
				}

				// ===========================
				// 修改客户信息时做操作记录
				// ===========================
				updateLog = new HajFrontUserUpdateLog();
				updateLog.setOperator(operator);
				updateLog.setFrontUserId(id);
				updateLog.setCreateTime(new Date());

				// 1仅注册 2预备会员 3一元购会员 4会员取消 5普通会员
				switch (status) {
					case 1:
						userStatus = "仅注册";
						break;
					case 2:
						userStatus = "预备会员";
						break;
					case 3:
						userStatus = "一元购会员";
						break;
					case 4:
						userStatus = "会员取消";
						break;
					case 5:
						userStatus = "普通会员";
						break;
					default:
						break;
				}

				updateLog.setRecord("用户角色更改为：" + userStatus);
				hajFrontUserUpdateLogService.add(updateLog);
			}

			// 获取所有用户所在的小区
			List<String> communitiesByUserIds = dao.getCommunitiesByUserIds(ids);

			// 调用小区的批量更新小区会员人数接口
			for (String community : communitiesByUserIds) {
				communityPersionMapper.updateMemberCount(community);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public HajFrontUser save(String mobilePhone, String machineNumber, String phoneModel,String passWord) {
		HajFrontUser user = new HajFrontUser();
		user.setDistributionBoxMoney(BigDecimal.ZERO);
		user.setRating(0);
		user.setMobilePhone(mobilePhone);
		user.setUsername(Tools.mobileEncrypt(mobilePhone));
		user.setBalance(BigDecimal.ZERO);
		user.setIsmember("1");
		user.setVersion(1);
		user.setPayPasswordStatus(2);//设置支付状态 未开启
		user.setMachineNumber(machineNumber);
		user.setPhoneModel(phoneModel);
		user.setLastLoginTime(new Date());
		user.setIsNewMember(0); //新用户
		if(passWord!=null && passWord!=""){
			user.setPassword(MD5.MD5Encode(passWord));
		}
		dao.add(user);
		/****生成邀请码*********/
		inviteCodeService.insertInviteCode(user.getId().toString(), mobilePhone);
		return user;
	}

	/**
	 * 批量修改用户小区
	 * @param ids
	 * @param communityName
	 * @param operator
	 * @return
	 */
	@Override
	public boolean updateUsersCommunityName(Integer[] ids, String communityName, String operator) {
		try {

			// 批量更新用户小区名称
			HajFrontUser user;					//用户信息表
			HajFrontUserUpdateLog updateLog;	//用户与操作者关系表
			for (Integer id : ids) {

				//根据用户id返回用户信息
				user = dao.getFrontUserById(id);

				/*String areaCode = user.getAreaCode();
				//获取新小区信息
				HajCommunityPersion community = communityPersionMapper.getCommunityByName(communityName,areaCode);*/

				//根据小区名字获取小区信息
				List<HajCommunityPersion> list = communityPersionMapper.getCommunityListByCommunityName(communityName);
				HajCommunityPersion community = list.get(0);
				HajCommunityPersion oldCommunity =null;
				//获取原小区信息
				if (user.getVillageId()!=null && user.getVillageId()!=0) {
					oldCommunity = communityPersionMapper.queryById(user.getVillageId());
					//把原小区详细地址和收货地址初始化
					user.setAddress("");								//用户详细地址
					user.setShippingAddress(community.getAddress());	//小区地址
					user.setHouseNumber("");							//门牌号
					user.setCommunityUnitId(0);
					user.setBuildingId(0);
				}

				//根据小区地区表地址获取城市编码
				String communityCode = community.getCommunityCode();
				HajAreas area = areaDao.getAreaByCode(communityCode);

				//判断用户是否已在该小区
				if (user.getVillageId()!=community.getId()) {
					user.setResidential(communityName);		//设置用户小区名称
					user.setVillageId(community.getId());	//小区id
					user.setAreaCode(area.getPCode());

					//判断该小区是否激活
					if (community.getStatus() == 1) {				//激活
						//判断会员是否已满
						if (community.getMemberStatus() == 1) {    //一元购会员已满
							if ("3".equals(user.getIsmember())) {  //原来是一元购会员
								//发送取消一元购会员信息
								hajSMSPushService.cancleVIP(user.getMobilePhone(), user.getResidential());
								if (oldCommunity!=null) {
									oldCommunity.setMembersNumber(oldCommunity.getMembersNumber()-1);	//原小区会员人数-1
								}
							}
							user.setIsmember("5");                 //设为普通会员
							community.setRegistererNumber(community.getRegistererNumber()+1);			//注册人数＋1
							if (oldCommunity!=null) {
								oldCommunity.setRegistererNumber(oldCommunity.getRegistererNumber()-1);	//原小区注册人数-1
							}
						} else {
							//判断其原来是否为一元购会员,否则设其为一元购会员
							if (!"3".equals(user.getIsmember())) {
								user.setIsmember("3");
								// 一元购会员添加会员日期
								user.setMemberBeginTime(DateUtil.datetime2Str(new Date()));
								user.setMemberTerm("365");

								// 清掉预约状态和时间
								user.setIsAppointment(2);
								user.setAppointmentTime("");

								//成为一元购会员的提示信息
								hajSMSPushService.becameVIP(user.getMobilePhone(), user.getResidential());

							} else {
								if (oldCommunity!=null) {
									oldCommunity.setMembersNumber(oldCommunity.getMembersNumber()-1);	//原小区会员人数-1
								}
							}

							community.setMembersNumber(community.getMembersNumber()+1);             //会员人数+1
							community.setRegistererNumber(community.getRegistererNumber()+1);       //注册人数＋1
							if (oldCommunity!=null) {
								oldCommunity.setRegistererNumber(oldCommunity.getRegistererNumber()-1);	//原小区注册人数-1
							}

							//判断该小区会员数是否已满
							if (community.getMemberStatus() >= community.getPlanMemberNumber()) {
								community.setMemberStatus(1);    //设为满员
							}
						}

					} else {											//未激活小区
						if ("3".equals(user.getIsmember())) {
							if (oldCommunity!=null) {
								oldCommunity.setMembersNumber(oldCommunity.getMembersNumber()-1);	//原小区会员人数-1
							}
							//发送取消一元购会员信息
							hajSMSPushService.cancleVIP(user.getMobilePhone(), user.getResidential());
						}
						if (community.getMemberStatus() == 1) {         //满员
							user.setIsmember("5");                  	//设为普通会员
						} else {
							user.setIsmember("2");						//设为预备会员
						}
						community.setRegistererNumber(community.getRegistererNumber() + 1);     //注册人数＋1
						if (oldCommunity!=null) {
							oldCommunity.setRegistererNumber(oldCommunity.getRegistererNumber()-1);	//原小区注册人数-1
						}
					}

					dao.updateBySelective(user);							//更新用户信息
					communityPersionMapper.updateBySelective(community);	//更新小区信息
					if (oldCommunity!=null) {
						communityPersionMapper.updateBySelective(oldCommunity);	//更新原小区信息
					}

					// ===========================
					// 修改客户信息时做操作记录
					// ===========================
					updateLog = new HajFrontUserUpdateLog();
					updateLog.setOperator(operator);
					updateLog.setFrontUserId(id);
					updateLog.setCreateTime(new Date());

					updateLog.setRecord("用户小区更改为：" + communityName);
					hajFrontUserUpdateLogService.add(updateLog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}




	@Override
	public void updateLoginInfo(Integer id, String machineNumber, String phoneModel) {
		if (id != null && id > 0) {
			HajFrontUser user = new HajFrontUser();
			user.setId(id);
			user.setMachineNumber(machineNumber);
			user.setPhoneModel(phoneModel);
			user.setLastLoginTime(new Date());
			dao.updateBySelective(user);
		}
	}

	@Override
	public HashMap<String, String> getUserByCondition(String userCondition) {
		return dao.getUserByCondition(userCondition);
	}

	@Override
	public int updateUserMoney(Integer userId) {
		return dao.updateUserMoney(userId);
	}

	@Override
	public void updateUserwithdrawBalance(Integer id, BigDecimal balance, HajFrontUser user) {
		if (user.getBalance().compareTo(balance) >= 0) {
			user.setBalance(balance);
			user.setId(id);
			dao.updateUserwithdrawBalance(user);
		}
	}

	@Override
	public HajFrontUser getUserByMobile(String mobile) {
		return dao.getUserByMobile(mobile);
	}

	@Override
	public int updateOrderPostFee(Integer userId, BigDecimal postFee) {
		HajFrontUser user = new HajFrontUser();
		user.setId(userId);
		user.setBalance(postFee);
		user.setRating(postFee.intValue());
		return dao.updateOrderPostFee(user);
	}

	@Override
	public void updateRechargeOrderPostFee(Integer userId, BigDecimal postFee) {
		HajFrontUser user = new HajFrontUser();
		user.setId(userId);
		user.setRating(postFee.intValue());
		dao.updateRechargeOrderPostFee(user);
	}

	@Override
	public HajFrontUserDTO getById4API(Integer userId) {
		HajFrontUserDTO userDTO = dao.getById4API(userId);
		if (userDTO != null) {
			if (userDTO.getUsername() != null) {
				// 为了保护用户隐私，需要将用户手机号加密。
				// 这里将username加密加密一次是因为早期版本默认将明文的手机号作为username的值保存
				// 不需要对mobilePhone进行加密的原因请在对应的mapper中查看
				userDTO.setUsername(Tools.mobileEncrypt(userDTO.getUsername()));
			}

			try {
				// 这里主要目的是为了使每一级楼栋名称中间用“;”隔开，因此需要做如下处理：
				// 用户表中保存的是最后一级的楼栋ID，因此循环读取每一级楼栋名，并拼接起来
				// 为了始终保持第一级楼栋名在最前面显示，需要用到两个变量来保存，每次旧的楼栋替换最新的
				if (userDTO.getBuildingId() != null && userDTO.getBuildingId() > 0) {
					StringBuilder addressSplitOld = new StringBuilder();
					StringBuilder addressSplitNew = new StringBuilder();

					HajCommunityBuilding building = communityBuildingService.queryById(userDTO.getBuildingId());
					if (building != null) {
						addressSplitOld.append(building.getName());
						do {
							building = communityBuildingService.queryById(building.getParentId());
							if (building != null) {
								addressSplitNew = new StringBuilder();
								addressSplitNew.append(building.getName()).append(';').append(addressSplitOld);

								addressSplitOld = new StringBuilder(addressSplitNew);
							}
						} while (building != null && building.getParentId() > 0);
						addressSplitNew = new StringBuilder(addressSplitOld);
					}

					if (Tools.isNotEmpty(addressSplitNew.toString())) {
						// 拼接楼层与门牌号
						if (userDTO.getFloor() != null) {
							addressSplitNew.append(';').append(userDTO.getFloor()).append("层");
						}
						if (userDTO.getHouseNumber() != null) {
							addressSplitNew.append(';').append(userDTO.getHouseNumber()).append("号");
						}
						userDTO.setAddressSplit(addressSplitNew.toString());
					}
				}
			} catch (Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		return userDTO;
	}

	@Override
	public XSSFWorkbook excelbatchCustomer(HajFinancial orderVo) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setCustomerXSSFRow(row);

		List<Map<String, Object>> communityPersions = dao.excelbatchCustomer(orderVo);
		Map<String, Object> vo;
		String returnStatus = "";

		int cellIndex;
		for (int i = 0, len = communityPersions.size(); i < len; i++) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);
			cellIndex = 0;

			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(Tools.getCityNameByAreaCode(getMapValue(vo.get("areaCode"))));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("residential")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("address")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("rechargeMoney")));
			// 查询用户提现中,提现成功的余额
			int userId = Integer.parseInt(vo.get("userId").toString());

			// 余额支付的配送费
			Double postFee = dao.getPostFeeUserId(userId);
			// 余额支付的订单金额
			Double actualPayment = dao.getActualPaymentUserId(userId);
			// 余额退款的金额
			Double refundMoney = dao.getRefundMoneyUserId(userId);
			// 余额支付的金额【订单+配送费】-余额退款的金额
			Double consumptionFee = postFee + actualPayment - refundMoney;

			row.createCell(cellIndex++).setCellValue(getMapValue(consumptionFee));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("withdrawalIngMoney")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("withdrawalMoney")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("balance")));
		}

		return wkb;

	}

	@Override
	public HashMap<String, Object> statisticsOrderByUserId(Map<String, Object> params) {
		return dao.statisticsOrderByUserId(params);
	}

	@Override
	public List<HashMap<String,Object>> statisticsOrderCommodityTypeByUserId(Map<String, Object> params) {
		return dao.statisticsOrderCommodityTypeByUserId(params);
	}

	public void updateFrontUser(HajFrontUser user) {
		dao.updateFrontUser(user);
	}

	private void setCustomerXSSFRow(XSSFRow row) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("注册手机号码");
		row.createCell(cellIndex++).setCellValue("城市");
		row.createCell(cellIndex++).setCellValue("小区名称");
		row.createCell(cellIndex++).setCellValue("用户住址");
		row.createCell(cellIndex++).setCellValue("充值余额");
		row.createCell(cellIndex++).setCellValue("消费余额");
		row.createCell(cellIndex++).setCellValue("提现中余额");
		row.createCell(cellIndex++).setCellValue("提现成功余额");
		row.createCell(cellIndex++).setCellValue("当前余额");
	}

	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}
}
