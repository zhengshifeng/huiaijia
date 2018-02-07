package com.flf.mapper;

import com.base.criteria.UserManagerCriteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajCustomerDeal;
import com.flf.entity.HajFinancial;
import com.flf.entity.HajFrontUser;
import com.flf.entity.HajFrontUserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajFrontUserDao<br>
 */
public interface HajFrontUserMapper extends BaseDao {

	int isUserByPhone(String mobilePhone);

	List<Map<String, Object>> userManagerList(UserManagerCriteria criteria);

	List<Map<String, Object>> getUserManagerById(String paramter);

	int updateUserBalancePoints(HajFrontUser user);

	void updateUserCancelOrder(HajFrontUser user);

	Map<String, Object> getOrderCountByTime(Map<String, Object> map);

	void updateUserMoneyByRecharge(HajFrontUser fuser);

	void updateUserDistributionBoxMoney(HajFrontUser fuser);

	void updateAllMemberStatus(Integer villageId);

	void updateSaleUserBalancePoints(HajFrontUser user);

	List<Map<String, Object>> listPageCustomerDeal(HajCustomerDeal criteria);

	List<Map<String, Object>> exportCustomerDeal(HajCustomerDeal customerDeal);

	List<Map<String, Object>> getUserCommodityType(String userId);

	int checkAddressUniqueness(HajFrontUser user);

	void updateUserShippingAddress(HajFrontUser user);

	List<String> getCommunitiesByUserIds(Integer[] ids);

	HashMap<String, String> getUserByCondition(String userCondition);

	int updateUserMoney(Integer userId);

	void updateUserwithdrawBalance(HajFrontUser user);

	HajFrontUser getUserByMobile(String mobile);

	List<HajFrontUser> getUserInMobiles(List<String> mobiles);

	int updateOrderPostFee(HajFrontUser user);

	void updateRechargeOrderPostFee(HajFrontUser user);

	HajFrontUserDTO getById4API(Integer userId);

	List<Integer> getUsersByCouponLimit(Map<String, Object> paramMap);

	Double getWithdrawalIngMoney(int userId);

	Double getWithdrawalMoney(int userId);

	List<Map<String, Object>> excelbatchCustomer(HajFinancial orderVo);

	Double getPostFeeUserId(int userId);

	Double getActualPaymentUserId(int userId);

	Double getRefundMoneyUserId(int userId);

	HashMap<String, Object> statisticsOrderByUserId(Map<String, Object> params);

	List<HashMap<String, Object>> statisticsOrderCommodityTypeByUserId(Map<String, Object> params);

	void updateFrontUser(HajFrontUser user);
	HajFrontUser getFrontUserById(int id);

}
