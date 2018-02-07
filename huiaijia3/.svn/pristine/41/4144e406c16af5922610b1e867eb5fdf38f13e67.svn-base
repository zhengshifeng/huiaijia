package com.weixin;

import com.flf.entity.HajRecharge;
import com.flf.entity.WXRequestBean;
import com.flf.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.*;

/**
 * Created by SevenWong on 2017-3-24 024 10:58
 */
public class PayUtil {

	private static final Logger log = Logger.getLogger(PayUtil.class);

	public static BigDecimal ONE_HUNDRED = new BigDecimal("100");

	public static void main(String[] args) throws Exception {
		payQuery("R20171026062434378267");
		// refundQuery("R20170910192511000");
		// System.out.println(refund("R20170910192511000", "R20170910192511000", 5650, 5650));
	}

	/**
	 * 向微信添加预支付订单
	 */
	public static Map<String, Object> addWeixinOrder(HajRecharge recharge) {
		Map<String, Object> mapObj = new HashMap<>();

		String nonceStr = MakeOrderNum.makeRandomNum();
		mapObj.put("nonceStr", nonceStr);
		String out_trade_no = MakeOrderNum.makeRechargeNum();

		HashMap<String, Object> map = new HashMap<>();
		map.put("appid", Configure.getAppid());
		map.put("mch_id", Configure.getMchid());
		map.put("nonce_str", nonceStr);
		map.put("body", HajRechargeUtil.getRechargeFor(recharge.getRechargeFor()));
		map.put("out_trade_no", out_trade_no);
		map.put("total_fee", recharge.getMoney().toString());
		map.put("spbill_create_ip", Configure.getIP());
		map.put("notify_url", Configure.WX_PAY_NOTIFY_URL);

		if (recharge.getApplication() == 1) {
			map.put("trade_type", "JSAPI");
			map.put("openid", recharge.getOpenid());
		} else {
			map.put("trade_type", "APP");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		map.put("time_start", DateUtil.dateformat(calendar.getTime(), "yyyyMMddHHmmss"));

		calendar.add(Calendar.MINUTE, 5);
		calendar.add(Calendar.SECOND, 1);
		// 订单失效时间。注意：最短失效时间间隔必须大于5分钟
		map.put("time_expire", DateUtil.dateformat(calendar.getTime(), "yyyyMMddHHmmss"));

		String sign = generateSign(map);
		WXRequestBean wxRequestBean = new WXRequestBean();
		wxRequestBean.setAppid(Configure.getAppid());
		wxRequestBean.setMch_id(Configure.getMchid());
		wxRequestBean.setSign(sign);
		wxRequestBean.setBody(HajRechargeUtil.getRechargeFor(recharge.getRechargeFor()));
		wxRequestBean.setNonce_str(nonceStr);
		wxRequestBean.setNotify_url(Configure.WX_PAY_NOTIFY_URL);
		wxRequestBean.setOut_trade_no(out_trade_no);
		wxRequestBean.setSpbill_create_ip(Configure.getIP());
		wxRequestBean.setTotal_fee(recharge.getMoney().toString());
		wxRequestBean.setTrade_type(String.valueOf(map.get("trade_type")));
		if (recharge.getApplication() == 1) {
			// 小程序支付需要openid
			wxRequestBean.setOpenid(recharge.getOpenid());
		}
		wxRequestBean.setTime_start(String.valueOf(map.get("time_start")));
		wxRequestBean.setTime_expire(String.valueOf(map.get("time_expire")));

		String xmlData = XMLBeanUtils.getXmlParams(wxRequestBean);
		xmlData = xmlData.substring(xmlData.indexOf("<xml>"), xmlData.length());
		try {
			String returnStr = HttpClientUtil.sendPost(Configure.UNIFIED_ORDER_API, xmlData,
					HttpClientUtil.CONTENT_TYPE_XML, HttpClientUtil.UTF_8, HttpClientUtil.UTF_8);

			log.info("微信预支付returnStr: " + returnStr);

			if (Tools.isNotEmpty(returnStr)) {
				String prepay_id = XMLBeanUtils.getXmlNodeValue(returnStr, "prepay_id");
				mapObj.put("prepay_id", prepay_id);
				mapObj.put("out_trade_no", out_trade_no);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapObj;
	}

	/**
	 * 根据商户端订单号查询微信端订单交易状态
	 */
	public static Map<String, Object> payQuery(String orderNum) {
		Map<String, Object> returnMap = new HashMap<>();

		HashMap<String, Object> map = new HashMap<>();
		map.put("appid", Configure.getAppid());
		map.put("mch_id", Configure.getMchid());
		map.put("out_trade_no", orderNum);
		String nonceStr = MakeOrderNum.makeRandomNum();
		map.put("nonce_str", nonceStr);
		String sign = generateSign(map);

		WXRequestBean wxRequestBean =new WXRequestBean();
		wxRequestBean.setAppid(Configure.getAppid());
		wxRequestBean.setMch_id(Configure.getMchid());
		wxRequestBean.setSign(sign);
		wxRequestBean.setOut_trade_no(orderNum);
		wxRequestBean.setNonce_str(nonceStr);
		String xmlData = XMLBeanUtils.getXmlParams(wxRequestBean);
		xmlData = xmlData.substring(xmlData.indexOf("<xml>"), xmlData.length());

		try {
			String returnStr = HttpClientUtil.sendPost(Configure.PAY_QUERY_API, xmlData,
					HttpClientUtil.CONTENT_TYPE_XML, HttpClientUtil.UTF_8, HttpClientUtil.UTF_8);
			log.info("微信支付状态查询接口返回数据：\n" + returnStr);
			if (Tools.isNotEmpty(returnStr)) {
				// 返回状态码
				String returnCode = XMLBeanUtils.getXmlNodeValue(returnStr, "return_code");
				returnMap.put("return_code", returnCode);
				if ("FAIL".equals(returnCode)) {
					String return_msg = XMLBeanUtils.getXmlNodeValue(returnStr, "return_msg");
					returnMap.put("return_msg", return_msg);
				} else {
					String result_code = XMLBeanUtils.getXmlNodeValue(returnStr, "result_code");
					// 业务结果
					returnMap.put("result_code", result_code);
					if ("SUCCESS".equals(result_code)) {
						// 交易状态(SUCCESS—支付成功 ,REFUND—转入退款 ,NOTPAY—未支付
						// ,CLOSED—已关闭 ,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中
						// ,PAYERROR--支付失败(其他原因，如银行返回失败))
						String trade_state = XMLBeanUtils.getXmlNodeValue(returnStr, "trade_state");
						returnMap.put("trade_state", trade_state);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnMap;
	}

	/**
	 * 生成请求签名：
	 * <p>
	 * 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
	 * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
	 * 特别注意以下重要规则：
	 * ◆ 参数名ASCII码从小到大排序（字典序）；
	 * ◆ 如果参数的值为空不参与签名；
	 * ◆ 参数名区分大小写；
	 * ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
	 * ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
	 * <p>
	 * 第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
	 * 再将得到的字符串所有字符转换为大写，得到sign值signValue。
	 * <p>
	 * key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	 */
	private static String generateSign(HashMap<String, Object> map) {
		ArrayList<String> list = new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		sb.append("key=").append(Configure.getKey());
		return MD5.MD5Encode(sb.toString()).toUpperCase();
	}

	/**
	 * 根据商户订单号从微信客户端查询退款
	 */
	public static BigDecimal refundQuery(String orderNum) {
		BigDecimal refundMoney = BigDecimal.ZERO;

		HashMap<String, Object> map = new HashMap<>();
		map.put("appid", Configure.getAppid());
		map.put("mch_id", Configure.getMchid());
		map.put("out_trade_no", orderNum);
		String nonceStr = MakeOrderNum.makeRandomNum();
		map.put("nonce_str", nonceStr);
		String sign = generateSign(map);

		WXRequestBean wxRequestBean = new WXRequestBean();
		wxRequestBean.setAppid(Configure.getAppid());
		wxRequestBean.setMch_id(Configure.getMchid());
		wxRequestBean.setSign(sign);
		wxRequestBean.setOut_trade_no(orderNum);
		wxRequestBean.setNonce_str(nonceStr);
		String xmlData = XMLBeanUtils.getXmlParams(wxRequestBean);
		xmlData = xmlData.substring(xmlData.indexOf("<xml>"), xmlData.length());

		try {
			String returnStr = HttpClientUtil.sendPost(Configure.REFUND_QUERY_API, xmlData,
					HttpClientUtil.CONTENT_TYPE_XML, HttpClientUtil.UTF_8, HttpClientUtil.UTF_8);
			log.info("调用微信查询退款接口返回XML数据：\n" + returnStr);

			if (Tools.isNotEmpty(returnStr)) {
				// 返回状态码SUCCESS/FAIL/其他错误
				String returnCode = XMLBeanUtils.getXmlNodeValue(returnStr, "return_code");
				if (!"FAIL".equals(returnCode)) {
					// 业务结果
					String resultCode = XMLBeanUtils.getXmlNodeValue(returnStr, "result_code");

					if ("SUCCESS".equals(resultCode)) {
						// 退款笔数
						int refund_count = Integer.parseInt(XMLBeanUtils.getXmlNodeValue(returnStr, "refund_count"));
						log.info("退款笔数:" + refund_count);

						String refundFee; // 退款金额
						String refundStatus; // 退款状态
						for (int i = 0; i < refund_count; i++) {
							refundStatus = XMLBeanUtils.getXmlNodeValue(returnStr, "refund_status_" + i);
							if ("SUCCESS".equals(refundStatus) || "PROCESSING".equals(refundStatus)) {
								refundFee = XMLBeanUtils.getXmlNodeValue(returnStr, "refund_fee_" + i);
								refundMoney = refundMoney.add((new BigDecimal(refundFee))
										.divide(ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
								log.info("第 " + (i + 1) + " 笔退款，金额: " + refundFee + "(分)");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return refundMoney;
	}

	/**
	 * 微信申请退款接口（退款至APP收款账户）
	 * @param outTradeNo 商户侧传给微信的订单号
	 * @param outRefundNo 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 * @param totalFee 订单总金额，单位为分，只能为整数
	 * @param refundFee 退款总金额，订单总金额，单位为分，只能为整数
	 * @throws Exception
	 */
	public static boolean refund(String outTradeNo, String outRefundNo, int totalFee, int refundFee) throws Exception {
		log.info("退款至APP收款账户");

		CloseableHttpClient httpClient = initCert();
		HttpPost httpPost = new HttpPost(Configure.REFUND_API);

		HashMap<String, Object> map = new HashMap<>();
		map.put("appid", Configure.getAppid());
		map.put("mch_id", Configure.getMchid());
		map.put("op_user_id", Configure.getMchid());

		String nonceStr = MakeOrderNum.makeRandomNum();
		map.put("nonce_str", nonceStr);
		map.put("out_trade_no", outTradeNo);
		map.put("out_refund_no", outRefundNo);
		map.put("total_fee", totalFee);
		map.put("refund_fee", refundFee);
		String sign = generateSign(map);

		WXRequestBean wxRequestBean = new WXRequestBean();
		wxRequestBean.setAppid(Configure.getAppid());
		wxRequestBean.setMch_id(Configure.getMchid());
		wxRequestBean.setSign(sign);
		wxRequestBean.setNonce_str(nonceStr);
		wxRequestBean.setOut_trade_no(outTradeNo);
		wxRequestBean.setOut_refund_no(outRefundNo);
		wxRequestBean.setTotal_fee(String.valueOf(totalFee));
		wxRequestBean.setRefund_fee(String.valueOf(refundFee));
		wxRequestBean.setOp_user_id(map.get("op_user_id").toString());

		String xmlData = XMLBeanUtils.getXmlParams(wxRequestBean);
		xmlData = xmlData.substring(xmlData.indexOf("<xml>"), xmlData.length());

		log.info("微信申请退款请求数据xmlData: \n" + xmlData);

		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(xmlData, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String text;
			StringBuilder readerStr = new StringBuilder();
			while ((text = bufferedReader.readLine()) != null) {
				readerStr.append(text);
			}
			String returnStr = readerStr.toString();
			log.info("微信申请退款接口返回的xml参数: \n" + returnStr);

			if (Tools.isNotEmpty(returnStr)) {
				// 返回状态码SUCCESS/FAIL/其他错误
				String returnCode = XMLBeanUtils.getXmlNodeValue(returnStr, "return_code");
				if (!"FAIL".equals(returnCode)) {
					// 业务结果
					String resultCode = XMLBeanUtils.getXmlNodeValue(returnStr, "result_code");

					if ("SUCCESS".equals(resultCode)) {
						log.info("申请微信退款成功outTradeNo: " + outTradeNo);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 初始化微信认证商户证书
	 */
	private static CloseableHttpClient initCert() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream fileInputStream = new FileInputStream(new File(Configure.getCertLocalPath()));
		keyStore.load(fileInputStream, Configure.getCertPassword().toCharArray());

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray())
				.build();

		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
				sslcontext,
				new String[]{"TLSv1"},
				null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		return HttpClients.custom()
				.setSSLSocketFactory(sslConnectionSocketFactory)
				.build();
	}

}
