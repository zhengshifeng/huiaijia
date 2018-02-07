package com.flf.controller.erp.job;

import com.flf.service.HajOrderService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.AreaCode;
import com.flf.util.JSONUtils;
import com.flf.util.RedisLock;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ERP订单统计定时任务
 * Created by Administrator on 2017/11/22.
 */
public class ERPOrderQuarts {

	private final static Logger log = Logger.getLogger(ERPOrderQuarts.class);

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajOrderService service;

	/**
	 * 生成ERP采购单
	 */
	public String  generatePurchaseOrder(){
		String str="";
		log.info("进入生成ERP采购订单调度方法start==============");
	 RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_erp_Purchase_Order_job", 10000, 20000);
		try{
			  boolean isTrue = lock.lock();
			log.info("key_purchase_redis_job,是否获取erp采购订单锁：" + isTrue);
		    if(isTrue) {
				log.info("进入ERP采购单锁");
		     	List<Map<String, Object>> list = service.generatePurchaseOrder(); //从数据库中获取参数,sql语句的查询别名要改下。
				if(!list.isEmpty()&&list.size()>0){
					for(Map<String ,Object> stringObjectMap :list){
					 String ForgCode= (String) stringObjectMap.get("FOrgCode");
					 if(ForgCode.equals(AreaCode.SZ)){
						 stringObjectMap.remove("FOrgCode");
						 stringObjectMap.put("FOrgCode",AreaCode.ERP_SZ);
					 }
					 if(ForgCode.equals(AreaCode.GZ)){
						 stringObjectMap.remove("FOrgCode");
						 stringObjectMap.put("FOrgCode",AreaCode.ERP_GZ);
					 }

					}
				}
				String parameter = JSONArray.fromObject(list).toString();
				log.info("请求参数:"+ parameter);
				log.info("采购订单传入erp");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年-MM月-dd日");
				Date date = new Date();
				String da = sdf.format(date);
				Service service=new Service();
				Call call= null;
				try {
					call = (Call) service.createCall();
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				call.setTargetEndpointAddress("http://120.77.202.243/LGCSWebService/POOrderService.asmx");
				call.setProperty("POOrderString",  parameter );
				call.addParameter(new QName("http://tempuri.org/","POOrderString"), XMLType.XSD_STRING, ParameterMode.IN);  //设置要传递的参数
				call.setReturnType(new QName("http://tempuri.org/"),String.class); //要返回的数据类型

				String rtn = null;
				try {
					rtn = (String) call.invoke(new QName("http://tempuri.org/","SavePOOrder"), new Object[]{parameter});
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				JSONObject json = JSONUtils.toJSONObject(rtn);
				JSONObject json1 = JSONUtils.toJSONObject(json.get("Result"));
				if (json1.get("isSuccess").equals("true")) {
					log.info(da + "采购订单传入erp成功");
					str=json1.toString();
				} else {
					String Result = (String) json1.get("Result");
					log.info(da + "采购订单传入erp失败" + Result);
					str=Result;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			// 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
	 	lock.unlock();
		}
     return str;

	}

	/**
	 * 生成ERP销售订单
	 */
	public String generateSalesOrderOrder(){
		String str=null;
		log.info("进入生成ERP销售订单调度方法start==============");
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_erp_Sales_Order_job", 10000, 20000);
		try{
		   boolean isTrue = lock.lock();
			log.info("key_erp_Sales_Order_job,是否获取erp销售订单锁：" + isTrue);
			if(isTrue) {
				log.info("进入ERP销售订单锁");
				List<Map<String, Object>> list = service.generateSalesOrderOrder();
				if(!list.isEmpty()&&list.size()>0){
					for(Map<String ,Object> stringObjectMap :list){
						String ForgCode= (String) stringObjectMap.get("FOrgCode");
						if(ForgCode.equals(AreaCode.SZ)){
							stringObjectMap.remove("FOrgCode");
							stringObjectMap.put("FOrgCode",AreaCode.ERP_SZ);
						}
						if(ForgCode.equals(AreaCode.GZ)){
							stringObjectMap.remove("FOrgCode");
							stringObjectMap.put("FOrgCode",AreaCode.ERP_GZ);
						}

					}
				}
				String parameter = JSONArray.fromObject(list).toString();
				log.info("请求参数: "+parameter);
				Service service=new Service();
				Call call= null;
				try {
					call = (Call) service.createCall();
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				//销售订单导入接口    http://ServerIP:8080/LGCSWebService /SEOrderService.asmx    SaveSEOrder   SEOrderString
				call.setTargetEndpointAddress("http://120.77.202.243/LGCSWebService/SEOrderService.asmx");
				call.setProperty("SEOrderString",  parameter );
				call.addParameter(new QName("http://tempuri.org/","SEOrderString"), XMLType.XSD_STRING, ParameterMode.IN);  //设置要传递的参数
				call.setReturnType(new QName("http://tempuri.org/"),String.class); //要返回的数据类型
				String rtn = null;
				try {
					rtn = (String) call.invoke(new QName("http://tempuri.org/","SaveSEOrder"), new Object[]{parameter});
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年-MM月-dd日");
				Date date = new Date();
				String da = sdf.format(date);
				JSONObject json = JSONUtils.toJSONObject(rtn);
				JSONObject json1 = JSONUtils.toJSONObject(json.get("Result"));
				if (json1.get("isSuccess").equals("true")) {
					log.info(da + "销售订单传入erp成功");
					str=json1.toString();
				} else {
					String Result = (String) json1.get("Result");
					str=Result;
					log.info(da + "销售订单传入erp失败" + Result);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
				// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
				// 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
				lock.unlock();
		}
		return str;
	}

	/**
	 * 生成ERP生产订单
	 */
	public String  generateProductionOrder(){
		String str=null;
		log.info("进入生成ERP生产订单调度方法start==============");
		RedisLock lock = new RedisLock(redisSpringProxy.getRedisTemplate(), "key_erp_Production_Order_job", 10000, 20000);
		try{
			 boolean isTrue = lock.lock();
 			if(isTrue) {
				log.info("进入ERP生产订单锁");
				List<Map<String, Object>> list = service.generateProductionOrder();
				if(!list.isEmpty()&&list.size()>0){
					for(Map<String ,Object> stringObjectMap :list){
						String ForgCode= (String) stringObjectMap.get("FOrgCode");
						if(ForgCode.equals(AreaCode.SZ)){
							stringObjectMap.remove("FOrgCode");
							stringObjectMap.put("FOrgCode",AreaCode.ERP_SZ);
						}
						if(ForgCode.equals(AreaCode.GZ)){
							stringObjectMap.remove("FOrgCode");
							stringObjectMap.put("FOrgCode",AreaCode.ERP_GZ);
						}

					}
				}
				String parameter = JSONArray.fromObject(list).toString();
				log.info("请求参数: "+parameter);
				log.info("生产订单传入erp");
				//生产订单导入接口   http://ServerIP:8080/LGCSWebService /WOOrderService.asmx  方法名：SaveWOOrder WOOrderString
				Service service=new Service();
				Call call= null;
				try {
					call = (Call) service.createCall();
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				call.setTargetEndpointAddress("http://120.77.202.243/LGCSWebService/WOOrderService.asmx");
				call.setProperty("WOOrderString",  parameter );
				call.addParameter(new QName("http://tempuri.org/","WOOrderString"), XMLType.XSD_STRING, ParameterMode.IN);  //设置要传递的参数
				call.setReturnType(new QName("http://tempuri.org/"),String.class); //要返回的数据类型
				String rtn = null;
				try {
					rtn = (String) call.invoke(new QName("http://tempuri.org/","SaveWOOrder"), new Object[]{parameter});
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年-MM月-dd日");
				Date date = new Date();
				String da = sdf.format(date);
				JSONObject json = JSONUtils.toJSONObject(rtn);
				JSONObject json1 = JSONUtils.toJSONObject(json.get("Result"));
				if (json1.get("isSuccess").equals("true")) {
					log.info(da + "生产订单传入erp成功");
					str=json1.toString();
				} else {
					String Result = (String) json1.get("Result");
					str=Result;
					log.info(da + "生产订单传入erp失败" + Result);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			// 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
			lock.unlock();
 		}
		return str;
	}




}
