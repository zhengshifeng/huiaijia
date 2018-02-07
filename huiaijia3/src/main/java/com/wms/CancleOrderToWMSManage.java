package com.wms;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.flf.entity.WMSOrderCancle;
import com.flf.util.HttpClientUtil;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;

/**
 * 取消订单同步至WMS接口
 * 
 * @author Administrator
 *
 */
public class CancleOrderToWMSManage {
	private final static Logger log = Logger.getLogger(CancleOrderToWMSManage.class);

	public static String cancelOrderDN(WMSOrderCancle cancle) {
		String isSuccess = "";
		String cancleStr = JSONUtils.toJSONString(cancle);
		log.info("取消订单[" + cancle.getOrdeno() + "]同步至WMSjson字符串：" + cancleStr);

		String resultStr = HttpClientUtil.sendPost(API.CANCEL_DN, cancleStr, HttpClientUtil.CONTENT_TYPE_JSON);
		log.info("取消订单[" + cancle.getOrdeno() + "]同步至WMS订单的结果：" + resultStr);

		if (resultStr != null) {

			JSONObject jsonobject = JSONObject.fromObject(resultStr);
			HashMap<?, ?> resultMap = JSONUtils.toHashMap(jsonobject);
			isSuccess = resultMap.get("isSuccess").toString();

			log.info("【CancleOrderResultBean：是否成功的状态：】" + isSuccess);

			if (isSuccess.equals("false") && Tools.isNotEmpty(resultMap.get("result").toString())) {
				String cancelReason = resultMap.get("result").toString();
				JSONObject jsonobject1 = JSONObject.fromObject(cancelReason);
				HashMap<?, ?> map1 = JSONUtils.toHashMap(jsonobject1);
				log.info("reason:" + map1.get("reason"));
			}

		}

		return isSuccess;

	}

	public static void main(String[] args) {
		WMSOrderCancle wmsOrder = new WMSOrderCancle();
		wmsOrder.setOrdeno("33304042");
		wmsOrder.setWhcode("SZHAJ01");
		wmsOrder.setReason("取消原因");
		cancelOrderDN(wmsOrder);
	}

}
