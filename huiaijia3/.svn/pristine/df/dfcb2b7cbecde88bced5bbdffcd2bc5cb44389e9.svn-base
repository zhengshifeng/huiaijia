package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.HajIntegralMallMapper;
import com.flf.service.*;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajIntegralMallService<br>
 */
@Service("hajIntegralMallService")
public class HajIntegralMallServiceImpl extends BaseServiceImpl implements HajIntegralMallService {
	private final static Logger log = Logger.getLogger(HajIntegralMallServiceImpl.class);

	@Autowired(required = false)
	private HajFrontUserService frontUserService;

	@Autowired(required = false)
	private HajCouponInfoService couponInfoService;

	@Autowired(required = false)
	private HajCouponUserService couponUserService;

	@Autowired(required = false)
	private HajIntegralDetailsService integralDetailsService;

	@Autowired
	private HajIntegralMallMapper dao;

	@Override
	public HajIntegralMallMapper getDao() {
		return dao;
	}

	@Override
	public List<HajIntegralMallVo> listPage(HajIntegralMall po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajFrontUser user) {
		user.setAreaCode(Tools.getAreaCode(user.getAreaCode()));
		List<HashMap<String, Object>> list = dao.list4app(user);
		for (HashMap<String, Object> map : list) {
			setCommodityStatus(map);
		}

		return list;
	}

	private void setCommodityStatus(HashMap<String, Object> map) {
		Integer exchangeLimit = Integer.valueOf(String.valueOf(map.get("exchangeLimit")));
		Integer limitCount = Integer.valueOf(String.valueOf(map.get("limitCount")));
		Integer yourExchanged = Integer.valueOf(String.valueOf(map.get("yourExchanged")));
		Integer totalOfExchanged = Integer.valueOf(String.valueOf(map.get("totalOfExchanged")));
		Integer total = Integer.valueOf(String.valueOf(map.get("total")));

		Integer status;
		if (total <= totalOfExchanged) {
			// 该商品的的发行量 <= 被兑换的数量则显示：已抢光
			status = 2;
		} else {
			if (exchangeLimit == 1 || (yourExchanged < limitCount)) {
				// 该商品对于该用户还有兑换次数则显示：立即兑换
				status = 0;
			} else {
				// 该商品对于该用户没有兑换次数则显示：已兑换
				status = 1;
			}
		}
		map.put("status", status);
	}

	@Override
	public Map<String, Object> saveIntegralExchange(Map<String, Object> jsonMap, Integer id, Integer userId) throws Exception{
		HajIntegralMall integralMall = dao.queryById(id);

		HajCouponInfo couponInfo = couponInfoService.queryById(integralMall.getCommodityId());

		HajFrontUser frontUser = frontUserService.queryById(userId);

		log.info("用户当前积分: " + frontUser.getRating() + "; 优惠券所需的积分: " + integralMall.getIntegral());

		if (frontUser.getRating() >= integralMall.getIntegral()) {
			jsonMap = couponUserService.saveUserCoupon(userId, couponInfo, jsonMap);
			if (jsonMap.get("error").toString().equals("0")) {
				// 扣除用户积分
				HajFrontUser updateUser = new HajFrontUser();
				updateUser.setRating(frontUser.getRating() - integralMall.getIntegral());
				updateUser.setId(userId);
				frontUserService.updateBySelective(updateUser);

				// 积分兑换商品记录积分明细
				HajIntegralDetails integralDetails = new HajIntegralDetails();
				integralDetails.setUserId(userId);
				integralDetails.setRemark("积分兑换-" + integralMall.getCommodityName());
				integralDetails.setDetail("-" + integralMall.getIntegral());
				try {
					integralDetailsService.saveDetail(integralDetails);
				} catch (Exception e) {
					log.info("取消订单记录积分明细异常，useId: " + userId, e);
				}

				// 将用户抵扣后剩余的积分返回给用户
				jsonMap.put("integral", updateUser.getRating());
			} else {
				log.info(jsonMap.get("msg"));
			}
		} else {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "积分不足");
		}
		return jsonMap;
	}

}
