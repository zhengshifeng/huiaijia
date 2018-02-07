package com.utils;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @ClassName: IdGeneraterUtils 
 * @Description: 唯一ID生成工具
 * @Copyright : 深圳市南山软件产业基地1A栋705
 * @Company : 深圳市橙社网络科技有限公司
 * @author : RockDing
 * @version : 1.0 
 * @date 2017年12月23日 上午9:48:22 
 *
 */
public class IdGeneraterUtils {
	
	/**
	 * @Title: generateInviteCode 
	 * @Description: 生成邀请码
	 * @param prefix
	 * @param randDanNum
	 * @return
	 * String    返回类型 
	 * @throws
	 */
	public static String generateInviteCode(String prefix,Integer ranDomNum) {
		
		return generateCodeByTimeAndrandDegite( prefix, ranDomNum);
	}
	
	/**
	 * @Title: generateCodeByTimeAndrandDegite 
	 * @Description: 生成随机代码 
	 * @param prefix
	 * @param ranDomNum
	 * @return
	 * String    返回类型 
	 * @throws
	 */
	private static String generateCodeByTimeAndrandDegite(String prefix,Integer ranDomNum) {
		StringBuilder sb=new StringBuilder();
		if(StringUtils.isBlank(prefix)|ranDomNum==null) {
			return null;
		}
		sb.append(prefix).append(System.currentTimeMillis()).append(generateNum(ranDomNum));
		return sb.toString();
	}
	
	
	private static String generateNum(Integer ranDomNum) {
		StringBuilder sb=new StringBuilder();
		Random random = new Random();
		for (int i = 0; i <ranDomNum; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		while(true) {
		System.out.println(generateInviteCode("IV", 5));
		}
	}
}
