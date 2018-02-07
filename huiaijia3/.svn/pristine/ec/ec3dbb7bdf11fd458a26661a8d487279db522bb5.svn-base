package com.flf.service;

import com.base.criteria.UserManagerCriteria;
import com.base.service.BaseService;
import com.flf.entity.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajFrontUserService<br>
 */
/**
 * @author SevenWong<br>
 * @date 2016年9月18日下午3:16:30
 */
public interface HajFrontUserService extends BaseService {

	boolean isUserByPhone(String mobilePhone);

	List<Map<String, Object>> getUserManagerList(UserManagerCriteria criteria);

	List<Map<String, Object>> getUserManagerById(String paramter);

	int updateUserBalancePoints(int userId, int rating, BigDecimal actualPayment, HajFrontUser user);

	void updateUserCancelOrder(HajOrder order);

	// 统计时间段区间订单数，平均订单金额，所以订单金额
	Map<String, Object> getOrderCountByTime(Map<String, Object> map);

	void updateUserBalancePoints(Integer userId, BigDecimal actualPayment);

	List<Map<String, Object>> querCustomer_dealList(HajCustomerDeal criteria);

	XSSFWorkbook exportCustomerDeal(HajCustomerDeal customerDeal);

	List<Map<String, Object>> getUserCommodityType(String parameter);

	/**
	 * 根据小区名及小区地址检查用户地址唯一性
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月23日上午10:10:40
	 * @param user
	 * @return
	 */
	int checkAddressUniqueness(HajFrontUser user);

	/**
	 * 根据用户ID批量更新用户状态，并同步更新用户所在小区会员人数
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月18日下午3:16:37
	 * @param ids
	 *            用户ID
	 * @param status
	 *            会员状态
	 * @param operator
	 * @return
	 */
	boolean updateUsersStatus(Integer[] ids, Integer status, String operator);

	HajFrontUser save(String mobilePhone, String machineNumber, String phoneModel,String passWord);
	/**
	 * 根据用户id批量修改用户小区名称
	 * @param ids
	 * @param communityName
	 * @param operator
	 * @return
	 */

	boolean updateUsersCommunityName(Integer[] ids, String communityName, String operator);


	/**
	 * 更新用户最后一次登录的机型、设备号及登录时间
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月21日下午1:53:56
	 * @param id
	 * @param machineNumber
	 * @param phoneModel
	 */
	void updateLoginInfo(Integer id, String machineNumber, String phoneModel);

	HashMap<String, String> getUserByCondition(String userCondition);

	int updateUserMoney(Integer userId);

	void updateUserwithdrawBalance(Integer id, BigDecimal intValue, HajFrontUser userManager);

	/**
	 * 根据手机号查询用户详情
	 * 
	 * @param mobile
	 * @return
	 */
	HajFrontUser getUserByMobile(String mobile);

	int updateOrderPostFee(Integer userId, BigDecimal postFee);

	void updateRechargeOrderPostFee(Integer userId, BigDecimal postFee);

	/**
	 * 获取用户个人信息，涉及到隐私的字段需要加密，所以特别写了此接口用户API交互
	 * 
	 * @param userId
	 * @return
	 */
	HajFrontUserDTO getById4API(Integer userId);

	XSSFWorkbook excelbatchCustomer(HajFinancial orderVo);

	HashMap<String, Object> statisticsOrderByUserId(Map<String, Object> params);

	List<HashMap<String, Object>> statisticsOrderCommodityTypeByUserId(Map<String, Object> params);

	void updateFrontUser(HajFrontUser user);
}
