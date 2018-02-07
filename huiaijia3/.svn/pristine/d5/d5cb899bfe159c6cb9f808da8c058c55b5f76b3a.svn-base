package com.juhe;

import com.base.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SevenWong on 2017/9/2 10:56
 */
public class WeatherUtil {

	private static final Logger log = Logger.getLogger(WeatherUtil.class);
	private static final String URL = "http://v.juhe.cn/weather/index";
	private static final String KEY = "d2ea406f48e6901f622685e96db2552a";

	public static String tomorrow(String city) {
		String weatherTomorrow = "天气数据异常";

		Map<String, Object> params = new HashMap<>();
		params.put("format", "2");
		params.put("key", KEY);
		params.put("cityname", city);
		String result = HttpUtil.get(URL, params);

		// log.info(result);

		try {
			JSONObject json = JSONObject.fromObject(result);
			JSONObject jsonResult = (JSONObject) json.get("result");
			if (jsonResult != null) {
				// 根据key=future获取未来7天的天气数据
				List<Map<String, Object>> future = (List<Map<String, Object>>) jsonResult.get("future");

				// 然后从下标为1的数据获取明日的天气数据
				String weather = String.valueOf(future.get(1).get("weather"));
				String temperature = String.valueOf(future.get(1).get("temperature"));

				weatherTomorrow = city + " / 明日 / " + weather + " / " + temperature;
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}

		return weatherTomorrow;
	}

	public static void main(String[] args) {
		System.out.println(tomorrow("深圳市"));
	}

}
