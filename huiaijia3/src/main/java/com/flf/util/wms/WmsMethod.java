package com.flf.util.wms;

/**
 * Created by SevenWong on 2017/11/6 15:58
 */
public class WmsMethod {

	/**
	 * 商品分类新增下传
	 */
	public final static String CATEGORY_INFO_INSERT = "jingdong.jcloud.wms.master.categoryInfo.insert";

	/**
	 * 商品分类修改下传
	 */
	public final static String CATEGORY_INFO_UPDATE = "jingdong.jcloud.wms.master.categoryInfo.update";

	/**
	 * 添加主商品信息
	 */
	public final static String TRANSPORT_GOODS_INFO = "jingdong.eclp.goods.transportGoodsInfo";

	/**
	 * 修改主商品信息
	 */
	public final static String UPDATE_GOODS_INFO = "jingdong.eclp.goods.updateGoodsInfo";

	/**
	 * 销售出库单下发
	 */
	public final static String ADD_ORDER = "jingdong.eclp.order.addOrder";

	/**
	 * 采购入库单下发
	 */
	public final static String ADD_PO_ORDER = "jingdong.eclp.po.addPoOrder";

	/**
	 * 小区信息下传接口
	 */
	public final static String RECEIVE_COMMUNITY = "jingdong.eclp.master.receiveCommunity";

}
