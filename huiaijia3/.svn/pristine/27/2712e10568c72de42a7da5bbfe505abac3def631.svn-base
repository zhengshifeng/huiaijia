package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.HajCouponUserMapper;
import com.flf.service.HajCouponResidentialService;
import com.flf.service.HajCouponUserService;
import com.flf.service.HajFrontUserService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.AreaCode;
import com.flf.util.DateUtil;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajCouponUserService<br>
 */
@Service("hajCouponUserService")
public class HajCouponUserServiceImpl extends BaseServiceImpl implements HajCouponUserService {
	private final static Logger log = Logger.getLogger(HajCouponUserServiceImpl.class);

	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;

	@Autowired(required = false)
	private HajCouponResidentialService residentialService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajCouponUserMapper dao;

	@Override
	public HajCouponUserMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCouponUser> listPage(HajCouponUser po) {
		return dao.listPage(po);
	}

	@Override
	public List<Map<String, Object>> getCouponListByUserId(Integer userId) {
		return dao.getCouponListByUserId(userId);
	}

	@Override
	public int getCouponUser(HajCouponUser couponUser) {
		return dao.getCouponUser(couponUser);
	}

	@Override
	public List<Map<String, Object>> getCouponCommodityById(Object couponId, String commodityNos) {
		return dao.getCouponCommodityById(couponId, commodityNos.split(","));
	}

	@Override
	public List<Map<String, Object>> getClearUserCouponByDate() {
		return dao.getClearUserCouponByDate();
	}

	@Override
	public void updateClearUserCoupon() {
		dao.updateClearUserCoupon();
	}

	@Override
	public void updateUserUsed(int id) {
		dao.updateUserUsed(id);
	}

	@Override
	public void updateUserCouponRead(int userId) {
		dao.updateUserCouponRead(userId);
	}

	@Override
	public List<Map<String, Object>> getCouponListByCommodity(Integer userId, String commodityNos) {
		return dao.getCouponListByCommodity(userId, commodityNos.split(","));
	}

	@Override
	public List<HajCouponUser> listByCouponId(Integer couponId) {
		return dao.listByCouponId(couponId);
	}

	@Override
	public int updateInvalidateByCouponId(Integer couponId) {
		return dao.updateInvalidateByCouponId(couponId);
	}

	@Override
	public int getCouponCount(Integer couponId) {
		return dao.getCouponCount(couponId);
	}

	@Override
	public Map<String, Object> saveUserCoupon(int userId, HajCouponInfo couponInfo, Map<String, Object> jsonMap) throws Exception {
		HajCouponInfoReturn couponReturn = new HajCouponInfoReturn();
		if (null != couponInfo && couponInfo.getStatus() != 3) {
			couponReturn.setCouponMoney(couponInfo.getCouponMoney());
			couponReturn.setCouponName(couponInfo.getName());
			couponReturn.setRemark(couponInfo.getRemark());
			HajCouponUser couponUser = new HajCouponUser();
			couponUser.setCouponId(couponInfo.getId());
			couponUser.setUserId(userId);
			HajFrontUser user = hajFrontUserService.queryById(userId);
			if (null != user && memberLimit(couponInfo, user)) {
				// 小区限制
				boolean isResidential = residentialService.getIsResidential(user.getVillageId(),
						couponInfo.getId());
				if (isResidential) {
					int couponInfoCounts = dao.getCouponCount(couponInfo.getId());
					if (couponInfo.getNumber() > couponInfoCounts) {
						int counts = dao.getCouponUser(couponUser);
						// 优惠券领取数量限制
						if (couponInfo.getLimitNumber() > counts) {
							// 相对时间
							if (couponInfo.getDateNumbers() > 0) {
								couponUser.setBeginTime(new Date());
								couponUser.setEndTime(DateUtil.getDayAfter(couponInfo.getDateNumbers() - 1));

								couponReturn.setBeginTime(DateUtil.date2Str(new Date()));
								couponReturn.setEndTime(DateUtil.date2Str(DateUtil.getDayAfter(couponInfo
										.getDateNumbers() - 1)));
							} else {
								couponUser.setBeginTime(couponInfo.getBeginTime());
								couponUser.setEndTime(couponInfo.getEndTime());
								couponReturn.setBeginTime(DateUtil.date2Str(couponInfo.getBeginTime()));
								couponReturn.setEndTime(DateUtil.date2Str(couponInfo.getEndTime()));
							}
							couponUser.setCreateTime(new Date());
							couponUser.setIsUsed(0);
							couponUser.setIsValidate(0);
							couponUser.setIsRead(0);

							dao.add(couponUser);
							jsonMap.put("error", "0");
							jsonMap.put("msg", "成功");
							jsonMap.put("couponInfo", couponReturn);
						} else {
							jsonMap.put("error", "3");
							jsonMap.put("msg", "你已经领取过该优惠券了");
						}
					} else {
						jsonMap.put("error", "6");
						jsonMap.put("msg", "已抢光了");
					}
				} else {
					jsonMap.put("error", "5");
					jsonMap.put("msg", "当前用户小区无法领取该优惠券");
				}
			} else {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "账号异常");
			}
		} else {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "该优惠券不存在");
		}

		return jsonMap;
	}

	/**
	 * 通过优惠券与用户信息判断该用户是否符合优惠券的会员限制
	 */
	private boolean memberLimit(HajCouponInfo couponInfo, HajFrontUser user) {
		Integer memberLimit = couponInfo.getMemberLimit();
		Integer isMember = Integer.valueOf(user.getIsmember());

		// memberLimit == 0代表无角色限制
		return memberLimit == 0 || memberLimit.equals(isMember);
	}

	@Override
	public List<String> getNewUserCoupon(Integer userId) throws Exception {
		HashMap<String, Object> userStatus = dao.getUserStatus(userId);
		if (userStatus == null) {
			log.info(userId + " 用户不存在");
			return null;
		}
		/*========================I'm a split line========================*/

		Integer isMember = Integer.valueOf(String.valueOf(userStatus.get("ismember")));
		int orderRecords = Integer.valueOf(String.valueOf(userStatus.get("orderRecords")));
		int communityStatus = Integer.valueOf(String.valueOf(userStatus.get("communityStatus")));

		// 一元购会员且无历史订单且小区已开通正常配送则满足领取条件
		boolean isNewUser = (isMember == 3 && orderRecords == 0 && communityStatus > 0);
		if (!isNewUser) {
			log.info(userId + " 不满足领取新用户优惠券条件");
			return null;
		}
		/*========================I'm a split line========================*/

		String areaCode = String.valueOf(userStatus.get("areaCode"));
		if (Tools.isEmpty(areaCode)) {
			log.info(userId + " areaCode为空");
			return null;
		}
		/*========================I'm a split line========================*/

		String newUsersCoupons;
		if (AreaCode.SZ.equals(areaCode)) {
			// 深圳新用户优惠券ID
			newUsersCoupons = (String) redisSpringProxy.read("SystemConfiguration_new_users_coupons_sz");
		} else if (AreaCode.GZ.equals(areaCode)) {
			// 广州新用户优惠券ID
			newUsersCoupons = (String) redisSpringProxy.read("SystemConfiguration_new_users_coupons_gz");
		} else {
			log.info(userId + " 未知的areaCode");
			return null;
		}
		/*========================I'm a split line========================*/

		log.info("后台配置的新人优惠券: " + newUsersCoupons);
		if (Tools.isEmpty(newUsersCoupons)) {
			log.info(areaCode + " 未设置新人优惠券ID");
			return null;
		}
		/*========================I'm a split line========================*/

		// 规范格式
		newUsersCoupons = newUsersCoupons.replace(" ", "");
		newUsersCoupons = newUsersCoupons.replace("，", ",");
		String[] newUsersCouponsArr = newUsersCoupons.split(",");

		// 转为list，方便操作
		List<String> newUsersCouponList = new ArrayList<>(Arrays.asList(newUsersCouponsArr));

		HashMap<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("couponIds", newUsersCouponsArr);
		List<Integer> receivedCoupons = dao.getReceivedCoupons(params);

		for (int i = 0; i < newUsersCouponList.size();) {
			// 如果某张优惠券之前已经获取过，则将其移除
			if (receivedCoupons.contains(Integer.valueOf(newUsersCouponList.get(i)))) {
				newUsersCouponList.remove(i);
				continue;
			}
			i++;
		}

		if (newUsersCouponList.size() < 1) {
			log.info("经筛选后已无可领取的优惠券");
			return null;
		}

		return newUsersCouponList;
	}

	@Override
	public List<Map<String, Object>> listCouponsByIds(Map<String, Object> map) {
		return dao.listCouponsByIds(map);
	}

	/**
	 * 查询当天派发的优惠卷
	 * @return
	 */
	public List<Map<String, Object>> getDistributeCouponByDate() {
		return dao.getDistributeCouponByDate();
	}

	/**
	 * 查询当天即将过期的优惠卷
	 * @return
	 */
	public List<Map<String, Object>> getOverdueCouponByDate() {
		return dao.getOverdueCouponByDate();
	}

}
