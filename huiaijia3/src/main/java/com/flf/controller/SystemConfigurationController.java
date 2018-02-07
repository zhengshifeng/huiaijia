package com.flf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.base.criteria.Criteria;
import com.flf.entity.SystemConfiguration;
import com.flf.service.RedisSpringProxy;
import com.flf.service.SystemConfigurationService;
import com.flf.util.Const;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;

import net.sf.json.JsonConfig;

/**
 * 
 * <br>
 * <b>功能：</b>SystemConfigurationController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/configuration")
public class SystemConfigurationController {

	private final static Logger log = Logger.getLogger(SystemConfigurationController.class);
	@Autowired(required = false)
	private SystemConfigurationService systemConfigurationService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	/**
	 * 系统配置列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(SystemConfiguration configuration) {
		List<SystemConfiguration> configurationList = systemConfigurationService.listPageConfig(configuration);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("configuration");
		mv.addObject("configurationList", configurationList);
		mv.addObject("configuration", configuration);
		return mv;
	}

	/**
	 * 请求新增配置页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(Model model) {
		return "configuration_add";
	}

	/**
	 * 保存配置信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(SystemConfiguration configuration, HttpServletResponse response) {
		String result = "error";
		try {
			if (configuration.getId() == null || configuration.getId().intValue() == 0) {
				if (systemConfigurationService.add(configuration) > 0) {
					result = "success";

					// 加入缓存
					redisSpringProxy.save("SystemConfiguration_" + configuration.getName(), configuration.getValue());
				}
			} else {
				systemConfigurationService.update(configuration);

				// 更新缓存
				redisSpringProxy.delete("SystemConfiguration_" + configuration.getName());
				redisSpringProxy.delete("androidVersionRedis");
				redisSpringProxy.delete("iosVersionRedis");
				redisSpringProxy.save("SystemConfiguration_" + configuration.getName(), configuration.getValue());
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.getWriter().print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 请求编辑配置页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit(@RequestParam int configurationId) {
		ModelAndView mv = new ModelAndView();
		SystemConfiguration configuration;
		try {
			configuration = systemConfigurationService.queryById(configurationId);
			mv.addObject("configuration", configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("configuration_add");
		return mv;
	}

	/**
	 * 删除单个配置
	 * 
	 * @param userId
	 * @param out
	 */
	@RequestMapping(value = "/delete")
	public void delete(@RequestParam int id, PrintWriter out) {
		try {
			SystemConfiguration configuration = systemConfigurationService.queryById(id);
			systemConfigurationService.delete(id);
			redisSpringProxy.delete("SystemConfiguration_" + configuration.getName());
			redisSpringProxy.delete("androidVersionRedis");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write("success");
		out.close();
	}

	/**
	 * 获取所有redis key
	 */
	@RequestMapping(value = "/keys")
	public ModelAndView getKeys(String searchkey, String patternkey, String user_redis, String login_vcode) {
		ModelAndView mv = new ModelAndView();
		List<HashMap<String, Object>> keyList = new ArrayList<HashMap<String, Object>>();
		Set<String> sets = null;
		if (Tools.isNotEmpty(searchkey)) {
			sets = redisSpringProxy.getAllKeys(searchkey);
		} else if (Tools.isNotEmpty(patternkey)) {
			sets = redisSpringProxy.getAllKeys(patternkey + "*");
		} else {
			sets = redisSpringProxy.getAllKeys("*");
		}
		try {
			Iterator<String> it = sets.iterator();
			HashMap<String, Object> map = null;
			String key = null;

			while (it.hasNext()) {
				map = new HashMap<String, Object>();
				key = it.next();

				map = filterFlag(map, key, user_redis, login_vcode);
				if (!map.isEmpty()) {
					keyList.add(map);
				}
			}
			mv.setViewName("redis_configuration");
			mv.addObject("keyList", keyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("searchkey", searchkey);
		mv.addObject("patternkey", patternkey);
		mv.addObject("user_redis", user_redis);
		mv.addObject("login_vcode", login_vcode);
		return mv;
	}

	/**
	 * 由于缓存中的key的个数太多，特别是用户信息及登录验证码，因此默认不再缓存列表中显示这些值，在用户需要查看的时候才显示
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月2日下午4:09:42
	 * @param map
	 * @param key
	 * @param user_redis
	 * @param login_vcode
	 * @return
	 */
	private HashMap<String, Object> filterFlag(HashMap<String, Object> map, String key, String user_redis,
			String login_vcode) {
		String hajfrontUser = "hajfrontUser_";
		String loginVCode = "login_code_";

		if (key.contains(hajfrontUser) || key.contains(loginVCode)) {
			if (Tools.isEmpty(user_redis) && Tools.isEmpty(login_vcode)) {
				// 都没勾中，说明用户不需要查看这些信息，直接跳过put
			} else {
				if (Tools.isNotEmpty(user_redis) && Tools.isNotEmpty(login_vcode)) {
					// 都选中，视为不进行过滤
					map.put("redisKey", key);
				} else {
					// 选中其中一个时，开放选中的那个分类
					if (Tools.isNotEmpty(user_redis) && key.contains(hajfrontUser)) {
						map.put("redisKey", key);
					} else if (Tools.isNotEmpty(login_vcode) && key.contains(loginVCode)) {
						map.put("redisKey", key);
					}
				}
			}
		} else {
			// 如果key不包含用户信息及验证码，则不进行过滤
			map.put("redisKey", key);
		}

		return map;
	}

	/**
	 * 根据redis key查询value
	 */
	@RequestMapping(value = "/getValueByKey")
	public ModelAndView getValueByKey(@RequestParam String key) {
		ModelAndView mv = new ModelAndView();
		try {
			Object value = redisSpringProxy.read(key);
			if (null != value) {
				mv.addObject("redisValue", value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("redis_value");
		return mv;
	}

	/**
	 * 清空redis Key
	 */
	@RequestMapping(value = "/deleteRedisKey")
	public void deleteRedisKey(@RequestParam String redisKey, PrintWriter out) {
		try {
			if (Tools.notEmpty(redisKey)) {
				redisSpringProxy.delete(redisKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write("success");
		out.close();
	}

	/**
	 * 清空所有redis 缓存
	 */
	@RequestMapping(value = "/deleteAllRedisKey")
	public void deleteAllRedisKey(PrintWriter out) {
		try {
			redisSpringProxy.flushAll();
			systemConfigRedis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write("success");
		out.close();
	}

	/**
	 * 清空匹配redis 缓存
	 */
	@RequestMapping(value = "/deletelikeKeys")
	public void deletelikeKeys(@RequestParam String likeKeys, PrintWriter out) {
		try {
			if (Tools.notEmpty(likeKeys)) {
				redisSpringProxy.deletePattern(likeKeys + "*");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write("success");
		out.close();
	}

	/**
	 * 将系统配置写入缓存中，缓存中的系统配置不能删，否则可能导致系统或APP不可用
	 * 
	 * @author SevenWong<br>
	 * @date 2016年7月27日下午5:30:41
	 * @throws Exception
	 */
	private void systemConfigRedis() throws Exception {
		List<SystemConfiguration> list = systemConfigurationService.queryByList(new Criteria());

		RedisSpringProxy redisSpringProxy = null;
		redisSpringProxy = Const.WEB_APP_CONTEXT.getBean(RedisSpringProxy.class);

		for (SystemConfiguration configuration : list) {
			redisSpringProxy.save("SystemConfiguration_" + configuration.getName(), configuration.getValue());
		}
	}

	/**
	 * 获取安卓版本信息 key androidVersion
	 */
	@RequestMapping(value = "/getAndroidVersion")
	public void getAndroidVersion(HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			SystemConfiguration configuration = null;
			if (null != redisSpringProxy.read("androidVersionRedis")) {

				configuration = (SystemConfiguration) redisSpringProxy.read("androidVersionRedis");
			} else {
				configuration = systemConfigurationService.getConfigurationByName("getAndroidVersion");
				// 查询到结果就放入缓存中
				if (null != configuration) {
					redisSpringProxy.save("androidVersionRedis", configuration);
				}
			}

			JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "page", "createTime" });

			if (null != configuration) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("Configuration", configuration);
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} else {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "为空");
				jsonMap.put("Configuration", "");
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info(e.getMessage(), e);
		}

	}

	/**
	 * 获取ios版本信息 key androidVersion
	 */
	@RequestMapping(value = "/getIOSVersion")
	public void getIOSVersion(HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			SystemConfiguration configuration = null;
			if (null != redisSpringProxy.read("iosVersionRedis")) {

				configuration = (SystemConfiguration) redisSpringProxy.read("iosVersionRedis");
			} else {
				configuration = systemConfigurationService.getConfigurationByName("getIosVersion");
				// 查询到结果就放入缓存中
				if (null != configuration) {
					redisSpringProxy.save("iosVersionRedis", configuration);
				}
			}

			JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(new String[] { "page", "createTime" });

			if (null != configuration) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("Configuration", configuration);
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} else {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "为空");
				jsonMap.put("Configuration", "");
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info(e.getMessage(), e);
		}

	}

}
