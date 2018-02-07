package com.flf.controller.erp.job;

import com.flf.job.InviteQuarts;
import com.flf.job.NotifyUserCouponQuarts;
import com.flf.service.HajCommodityService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.HajCommodityUtil;
import com.flf.util.JSONUtils;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


/**
 * Controller测试类:测试调用erp接口，因为线上调用是通过定时器调用的，这里直接调用。
 * Created by Administrator on 2017/11/24.
 */
@Controller
@RequestMapping(value = "/job")
public class JobController {
	@Autowired
	ERPOrderQuarts quart;
	@Autowired
	NotifyUserCouponQuarts notifyUserCouponQuarts;

	@Autowired(required = false)
	private HajCommodityService service;

	@Autowired
	private RedisSpringProxy redisSpringProxy;
	@RequestMapping("/distributeCouponByDate")
	public void distributeCouponByDate() {
		notifyUserCouponQuarts.notifyDistributeCoupon();
	}

	@RequestMapping("/overdueCoupon")
	public void overdueCoupon() {

		notifyUserCouponQuarts.notifyOverdueCoupon();
	}
	@Autowired
	InviteQuarts inviteQuarts;
	@RequestMapping("/updateInviteStatus")
	public void updateInviteStatus(HttpServletResponse response) throws IOException {
		inviteQuarts.updateInviteStatus();
	}

	//生成ERP采购单
	@RequestMapping("/purchaseOrder")
	public void generatePurchaseOrder(HttpServletResponse response) throws IOException {
		String str = quart.generatePurchaseOrder();
		JSONUtils.printObject(str, response);

	}
	//生成ERP销售订单
	@RequestMapping("/salesOrder")
	public void generateSalesOrderOrder(HttpServletResponse response) throws IOException {
		String str = quart.generateSalesOrderOrder();
		JSONUtils.printObject(str, response);
	}

	// 生成ERP生产订单
	@RequestMapping("/production")
	public void generateProductionOrder(HttpServletResponse response) throws IOException {
		String str = quart.generateProductionOrder();
		JSONUtils.printObject(str, response);
	}
	/**
	 * 测试新增物料
	 */
	@RequestMapping("/test")
	public void test() {
//		ERPMaterielVo vo = new ERPMaterielVo();
//		vo.setFMaterialId(122);
//		vo.setFItemName("abc");
//		vo.setFCostPrice(new BigDecimal(1));
//		vo.setFOrgCode("abc");
//		net.sf.json.JSONObject jsObject = net.sf.json.JSONObject.fromObject(vo);
//		String pam = jsObject.toString();
//		System.out.println(pam);
//		String s = HttpClientUtil.sendPost("http://115.29.170.224:8080/erp/materiel/save.action",
//				"",
//				HttpClientUtil.CONTENT_TYPE_JSON,
//				"UTF-8", "UTF-8");
//
//		System.out.println(s);

//		List<ERPPurchaseOrderDto> list = new ArrayList<>();
//		ERPPurchaseOrderDto dto = new ERPPurchaseOrderDto();
//		dto.setFOrgCode("100.01");
//		dto.setFItemCode("1.01.001");
//		dto.setFAmount(new BigDecimal(10));
//		list.add(dto);
//		String parameter = JSONArray.fromObject(list).toString();
////		log.info("请求参数:"+ parameter);
////		log.info("采购订单传入erp");
//		String result = HttpClientUtil.sendPost("http://120.77.202.243/LGCSWebService/WOOrderService.asmx",
//				parameter,
//				HttpClientUtil.CONTENT_TYPE_JSON,
//				"UTF-8", "UTF-8");
//		System.out.print(result);

		//String WOOrderString = "[{'FOrgCode':'100.01','FItemCode':'202000020','FQty':1.00}]";
		// String SEOrderString = "[{'FOrgCode':'100.01','FDate':'2017-11-11','FItemCode':'202000020','FQty':1.00,'FAmount':10.00}]";
//         String InventoryString="{\"FOrgCode\":\"100.01\",\"FStockCode\":\"\",\"FItemCode\":\"test678899\"} ";

		String POOrderString = "[{'FOrgCode':'100.01','FSupplierCode':'666','FDate':'2017-12-27','FItemCode':'2059353','FQty':1.0,'FAmount':15},{'FOrgCode':'100.02','FSupplierCode':'666','FDate':'2017-12-27','FItemCode':'2059353','FQty':1,'FAmount':3}]";

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("FOrgCode", "100.01");
		parameter.put("FItemCode", "test678899");
		parameter.put("FStockCode", "");


//		String  InventoryString= JSONObject.fromObject(parameter).toString();

		Service service = new Service();
		Call call = null;
		try {
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		//采购订单导入  http://ServerIP:8080/LGCSWebService/POOrderService.asmx  SavePOOrder  POOrderString
		//销售订单导入接口    http://ServerIP:8080/LGCSWebService /SEOrderService.asmx    SaveSEOrder   SEOrderString
		//生产订单导入接口   http://ServerIP:8080/LGCSWebService /WOOrderService.asmx  方法名：SaveWOOrder WOOrderString
		//即时库存查询接口 http://ServerIP:8080/LGCSWebService /InventoryService.asmx        SearchInventory   InventoryString
		call.setTargetEndpointAddress("http://120.77.202.243/LGCSWebService/POOrderService.asmx");
		call.setProperty("POOrderString", POOrderString);
		call.addParameter(new QName("http://tempuri.org/", "POOrderString"), XMLType.XSD_STRING, ParameterMode.IN);  //设置要传递的参数
		call.setReturnType(new QName("http://tempuri.org/"), String.class); //要返回的数据类型

		String rtn = null;
		try {
			rtn = (String) call.invoke(new QName("http://tempuri.org/", "SavePOOrder"), new Object[]{POOrderString});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//String rtn = (String)call.invoke(new String[]{WOOrderString});
		System.out.println("==" + rtn);


	}
	/**********短信推送*******/
	/***修改手机验证码推送***/
	@RequestMapping("/sendUpdatePhoneCode")
	public void sendUpdatePhoneCode(String phone,HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String title = "修改手机验证码！";
		String code = RandomStringUtils.randomNumeric(4);
		redisSpringProxy.saveEx("update_phone_code_" + phone, 5 * 60L, code);
		jsonMap.put("msg","success");
		jsonMap.put("code",code);
		jsonMap.put("title",title);
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/****修改密码验证码********/
	@RequestMapping("/sendUpdatePwdCode")
	public void sendUpdatePwdCode(String phone,HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String title = "修改登录密码！";
		String code = RandomStringUtils.randomNumeric(4);
		redisSpringProxy.saveEx("update_password_code_" + phone, 5 * 60L, code);
		jsonMap.put("msg","success");
		jsonMap.put("code",code);
		jsonMap.put("title",title);
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/sendPayPwdCode")
	public void sendPayPwdCode(String phone,HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String title = "开启支付,支付密码修改验证码！";
		String code = RandomStringUtils.randomNumeric(4);
		redisSpringProxy.saveEx("open_paypassword_code_" + phone, 5 * 60L, code);
		jsonMap.put("msg","success");
		jsonMap.put("code",code);
		jsonMap.put("title",title);
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/sendColsePayPwdCode")
	public void sendColsePayPwdCode(String phone,HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String title = "关闭支付密码验证码！";
		String code = RandomStringUtils.randomNumeric(4);
		redisSpringProxy.saveEx("close_paypassword_code_" + phone, 5 * 60L, code);
		jsonMap.put("msg","success");
		jsonMap.put("code",code);
		jsonMap.put("title",title);
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/sendRegisterCode")
	public void sendRegisterCode(String phone,HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String title = "发送邀请有礼注册验证码！";
		String code = RandomStringUtils.randomNumeric(4);
		redisSpringProxy.saveEx("register_code_" + phone, 5 * 60L, code);
		jsonMap.put("msg","success");
		jsonMap.put("code",code);
		jsonMap.put("title",title);
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/sendLoginCode")
	public void sendLoginCode(String phone,HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String title = "登录验证码！";
		String code = RandomStringUtils.randomNumeric(4);
		redisSpringProxy.saveEx("login_code_" + phone, 5 * 60L, code);
		jsonMap.put("msg","success");
		jsonMap.put("code",code);
		jsonMap.put("title",title);
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 清空商品（所有）相关的缓存，重建搜索引擎的索引
	 * 注：这两步操作不能放在同一个service方法中完成，因为事务未提交可能导致脏数据的产生
	 */
	@RequestMapping(value = "/clearAll")
	public void resetCommodityIndexAll(HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			HajCommodityUtil.resetCommodityRedisAndESIndex(service);
			jsonMap.put("error", "0");
			jsonMap.put("msg", "成功!");
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "失败!");
			e.printStackTrace();
		}finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}
