package com.alipay.util;

import com.alipay.config.AlipayConfig;
import com.flf.util.DateUtil;
import com.flf.util.HttpClientUtils;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用支付宝接口工具类
 *
 * @author SevenWong<br>
 * @date 2016年7月5日下午3:18:22
 */
public class AlipayUtil {

	private final static Logger log = Logger.getLogger(AlipayUtil.class);

	private final static String EMPTY = "";

	private final static String SINGLE_TRADE_QUERY = "single_trade_query";

	private final static String ACCOUNT_PAGE_QUERY = "account.page.query";

	private final static String ALIPAY_TRADE_REFUND = "alipay.trade.refund";

	private final static String REFUND_FASTPAY_BY_PLATFORM_NOPWD = "refund_fastpay_by_platform_nopwd";

	/**
	 * 获取单条交易记录xml数据
	 *
	 * @param out_trade_no
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年7月9日上午11:21:28
	 */
	private static String singleTradeQuery(String out_trade_no) {
		// 把请求参数打包成数组
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("service", SINGLE_TRADE_QUERY);
		reqParam.put("partner", AlipayConfig.partner);
		reqParam.put("_input_charset", AlipayConfig.input_charset);
		reqParam.put("out_trade_no", out_trade_no);

		String xmlText = EMPTY;
		try {
			// 建立请求
			xmlText = AlipaySubmit.buildRequest(EMPTY, EMPTY, reqParam);
			log.info("singleTradeQuery()xmlText:" + xmlText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlText;
	}

	/**
	 * 分页查询财务明细
	 *
	 * @author SevenWong<br>
	 * @date 2016年7月9日下午3:52:17
	 */
	@SuppressWarnings("unused")
	private static void accountPageQuery() {
		// 把请求参数打包成数组
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("service", ACCOUNT_PAGE_QUERY);
		reqParam.put("partner", AlipayConfig.partner);
		reqParam.put("_input_charset", AlipayConfig.input_charset);
		reqParam.put("gmt_start_time", "2016-06-30 00:00:00");
		reqParam.put("gmt_end_time", "2016-07-01 00:00:00");

		String xmlText = EMPTY;
		try {
			// 建立请求
			xmlText = AlipaySubmit.buildRequest(EMPTY, EMPTY, reqParam);
			log.info(xmlText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据支付宝返回的xml数据解析支付状态
	 * 于20170325调整为返回支付宝的trade_no，如果交易成功，则将trade_no写入数据库，以后可用于退款
	 *
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年7月8日下午3:27:54
	 */
	public static String alipayTradeStatus(String out_trade_no) {
		try {
			String xmlText = singleTradeQuery(out_trade_no);
			Document dd = DocumentHelper.parseText(xmlText);
			Element root = dd.getRootElement();
			String isSuccess = root.elementTextTrim("is_success");
			if ("T".equals(isSuccess)) {
				String tradeStatus = root.element("response").element("trade").elementTextTrim("trade_status");
				String tradeNo = root.element("response").element("trade").elementTextTrim("trade_no");
				log.info("alipayTradeStatus()-->tradeStatus:" + tradeStatus);
				if ("TRADE_SUCCESS".equals(tradeStatus)) {
					return tradeNo;
				}
			}
		} catch (Exception e) {
			log.info("alipayTradeStatus()解析支付宝交易状态异常...");
		}
		return null;
	}

	/**
	 * 通过单笔交易记录接口获取订单的退款金额，返回null或退款金额
	 *
	 * @param out_trade_no
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年7月9日上午11:22:07
	 */
	public static String alipayRefundStatus(String out_trade_no) {
		String xmlText = singleTradeQuery(out_trade_no);
		Document dd = null;
		String refundFee = null;
		try {
			dd = DocumentHelper.parseText(xmlText);
			String isSuccess = dd.getRootElement().elementTextTrim("is_success");
			if ("T".equals(isSuccess)) {
				Element tradeElement = dd.getRootElement().element("response").element("trade");
				refundFee = tradeElement.elementTextTrim("refund_fee");
				log.info("退款状态:" + tradeElement.elementTextTrim("refund_status"));
				log.info("退款金额:" + refundFee);
			}
		} catch (Exception e) {
			log.info("alipayRefundStatus()解析支付宝交易状态异常...");
		}

		return refundFee;
	}

	/**
	 * 支付宝异步通知时验证此通知是否由支付宝发起
	 *
	 * @param notify_id
	 * @return true/false
	 * @author SevenWong<br>
	 * @date 2016年10月31日下午2:27:14
	 */
	public static String notifyVerify(String notify_id) {
		String url = "https://mapi.alipay.com/gateway.do";

		Map<String, String> params = new HashMap<>();
		params.put("service", "notify_verify");
		params.put("partner", AlipayConfig.partner);
		params.put("notify_id", notify_id);

		try {
			return HttpClientUtils.simpleGetInvoke(url, params);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 支付宝无密退款接口
	 *
	 * @param trade_no      原付款支付宝交易号，非商家生成
	 * @param refund_amount 退款金额
	 * @param batch_num     退款笔数
	 * @param refundNo      商家退款流水号（3～24 位，流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”）。
	 * @param refundReason  退款理由，其中不能有“^”、“|”、“$”、“#”等影响 detail_data 的格式的特殊字符
	 * @return 返回退款申请状态
	 */
	public static boolean refundFastpayByPlatformNopwd(String trade_no, String refund_amount, int batch_num,
													   String refundNo, String refundReason) {
		Map<String, String> reqParam = new HashMap<>();
		reqParam.put("service", REFUND_FASTPAY_BY_PLATFORM_NOPWD);
		reqParam.put("partner", AlipayConfig.partner);
		reqParam.put("_input_charset", AlipayConfig.input_charset);
		Date date = new Date();
		reqParam.put("batch_no", DateUtil.date2Str(date, false) + refundNo);
		reqParam.put("refund_date", DateUtil.datetime2Str(date));
		reqParam.put("batch_num", String.valueOf(batch_num));
		reqParam.put("detail_data", trade_no + "^" + refund_amount + "^" + refundReason);

		log.info("支付宝无密退款请求数据:" + reqParam.toString());

		try {
			// 建立请求
			String xmlText = AlipaySubmit.buildRequest(EMPTY, EMPTY, reqParam);
			log.info("支付宝无密退款响应的xmlText:" + xmlText);

			if (Tools.isNotEmpty(xmlText)) {
				Document dd = DocumentHelper.parseText(xmlText);
				Element root = dd.getRootElement();
				String isSuccess = root.elementTextTrim("is_success");
				return "T".equals(isSuccess);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		// alipayTradeStatus("R20170612173029690");

		System.out.println(refundFastpayByPlatformNopwd("2017032521001004200229679008", "0.1", 1, "001", "refundByApplication"));

		// accountPageQuery("1", "2016-06-02 00:00:00", EMPTY);

		// System.out.println(notifyVerify("b901eb478abf8414aa699dc96abac24g2u"));
		// alipayRefundStatus("R20170615123343094");

	}

}
