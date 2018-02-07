package com.flf.constant;

/**
 * Created by SevenWong on 2017-5-12 012 11:39
 */
public class HajOrderProblemType {

	public static final int OTHER = 0; // 其他
	public static final int MISS = 1; // 未送达
	public static final int UNKNOW = 100; // 无法判断
	public static final int LESS = 101; // 少送
	public static final int WRONG = 102; // 错送
	public static final int UNDERWEIGHT = 103; // 重量不足
	public static final int QUALITY_PROBLEM = 104; // 品质问题
	public static final int DESTROY = 105; // 受外力破坏
	public static final int EXPIRE = 106; // 过期
	public static final int UNREFRIGERATED = 107; // 未冷藏
	public static final int OUT_OF_STOCK = 108; // 仓库缺货

	public static String getType(int type) {
		String realValue;
		switch (type) {
			case MISS:
				realValue = "未送达";
				break;
			case UNKNOW:
				realValue = "无法判断";
				break;
			case LESS:
				realValue = "少送";
				break;
			case WRONG:
				realValue = "错送";
				break;
			case UNDERWEIGHT:
				realValue = "重量不足";
				break;
			case QUALITY_PROBLEM:
				realValue = "品质问题";
				break;
			case DESTROY:
				realValue = "受外力破坏";
				break;
			case EXPIRE:
				realValue = "过期";
				break;
			case UNREFRIGERATED:
				realValue = "未冷藏";
				break;
			case OUT_OF_STOCK:
				realValue = "仓库缺货";
				break;
			default:
				realValue = "其他";
		}
		return realValue;
	}

}
