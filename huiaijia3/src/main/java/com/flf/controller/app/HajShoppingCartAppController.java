package com.flf.controller.app;

import com.flf.entity.HajShoppingCart;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>购物车<br>
 * <br>
 */
@Controller
public class HajShoppingCartAppController {

	private final static Logger log = Logger.getLogger(HajShoppingCartAppController.class);
	@Autowired
	private RedisSpringProxy redisSpringProxy;

	/**
	 * 添加购物车 方法已去掉
	 */
	@RequestMapping(value = "/addShoppingCart")
	public void addShoppingCart(@RequestParam int userId, @RequestParam List<HajShoppingCart> cartlist,
			@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign) && cartlist.size() > 0) {
				/*
				 * for(HajShoppingCart cart:cartlist) {
				 * if(null!=cart.getCommodityName() && null!=cart.getNumber() &&
				 * null!=cart.getUserId() && null!=cart.getPrice()) {
				 * log.info("进入用户添加购物车接口，用户id："+cart.getUserId()+",商品名称："+cart.
				 * getCommodityName()+",商品数量："+cart.getNumber()+",单价："+cart.
				 * getPrice()); //添加到redis缓存中 int id =
				 * hajShoppingCartService.add(cartlist);
				 * 
				 * jsonMap.put("error", "0"); jsonMap.put("msg", "成功");
				 * jsonMap.put("shoppingCart", cart); }else {
				 * jsonMap.put("error", "0"); jsonMap.put("msg", "为空");
				 * jsonMap.put("shoppingCart", cart); }
				 * JSONUtils.printObject(jsonMap,response); }
				 */
				if (null != redisSpringProxy.read(userId + "_cart")) {
					redisSpringProxy.delete(userId + "_cart");
				}
				redisSpringProxy.save(userId + "_cart", cartlist);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("user", "");
				JSONUtils.printObject(jsonMap, response);
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
