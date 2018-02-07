package com.flf.controller.xcx;

import com.flf.util.HttpClientUtil;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import com.weixin.xcx.XcxConfig;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SevenWong on 2017/10/19 14:27
 */
@Controller
@RequestMapping(value = "/xcx")
public class HajWxLogin {
	private final static Logger log = Logger.getLogger(HajWxLogin.class);

	@RequestMapping(value = "/getOpenid")
	public void getOpenid(HttpServletResponse response,
					  @RequestParam String sign, @RequestParam String jsCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String params = "appid=" + XcxConfig.WX_APPID;
				params = params + "&secret=" + XcxConfig.WX_SECRET;
				params = params + "&js_code=" + jsCode;
				params = params + "&grant_type=authorization_code";

				String result = HttpClientUtil.sendPost(XcxConfig.WX_LOGIN_JSCODE_URL, params, HttpClientUtil.CONTENT_TYPE_JSON);

				log.info("result: " + result);

				JSONObject jsonobject = JSONObject.fromObject(result);
				HashMap resultMap = JSONUtils.toHashMap(jsonobject);
				String openid = String.valueOf(resultMap.get("openid"));

				if (Tools.isNotEmpty(openid)) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("openid", openid);
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", String.valueOf(resultMap.get("errmsg")));
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
