package com.utils;

import org.apache.poi.ss.formula.functions.T;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @ClassName: Result 
 * @Description: restful api 返回結果
 * @Copyright : 深圳市南山软件产业基地1A栋705
 * @Company : 深圳市橙社网络科技有限公司
 * @author : RockDing
 * @version : 1.0 
 * @date 2017年12月23日 上午11:49:26 
 * 
 * @param <T>
 */
public class Result{
	
	@JsonProperty("error")
	private String code;//狀態嗎
	private String msg;//消息描述
	private Object data;//數據
	
	public static   Result SUCCESS(Object data,String msg){
		Result result = new Result();
		result.code = ResultCode.SUCCESS;
		result.data = data;
		result.msg=msg;
		return result;
	}
	
	public static  Result SUCCESS(){
		Result result = new Result();
		result.code = ResultCode.SUCCESS;
		result.data = null;
		result.msg="成功！";
		return result;
	}
	
	public static  Result ERROR(String code,Object data,String msg){
		Result result = new Result();
		result.code = code;
		result.data=null;
		result.msg=msg;
		return result;
	}
	
	public  static Result valueOf(String code, Object data,String msg){
		Result result = new Result();
		result.code = code;
		result.data = data;
		result.msg=msg;
		return result;
	}
	
	public boolean isSuccess(){
		return this.code == ResultCode.SUCCESS;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
}
