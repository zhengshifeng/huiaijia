package com.wms;

import com.flf.util.HttpClientUtil;
import com.flf.util.JSONUtils;
import com.wms.entity.WmsInventory;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by SevenWong on 2017-3-23 023 16:32
 * WMS 库存同步
 */
public class InventorySync {

	private static final Logger log = Logger.getLogger(InventorySync.class);


	/**
	 * 根据仓库编号及商品sku查询库存信息
	 * @param whcode 仓库编号
	 * @param skucode 商品sku，多个用","隔开 (如: SP161221105812421,SP161221105812421)
	 * @return 返回商品编号及库存量列表
	 */
	public static List<WmsInventory> getInvtList(String whcode, String skucode) {
		List<WmsInventory> wmsInventoryList = null;
		List<Map<String, Object>> entityList = new ArrayList<>();
		Map<String, Object> entity = new HashMap<>();
		entity.put("whcode", whcode);
		entity.put("skucode", skucode);
		entityList.add(entity);

		String result = HttpClientUtil.sendPost(API.SEARCH_INVENTORY,
				JSONObject.valueToString(entityList),
				HttpClientUtil.CONTENT_TYPE_JSON);
		log.info(result);
		HashMap map = JSONUtils.toHashMap(result);

		if (Objects.equals(true, map.get("isSuccess"))) {
			List<Map<String, Object>> list = JSONUtils.toList(map.get("response"));
			Object sku;
			String avlqty;
			wmsInventoryList = new ArrayList<>();
			for (Map<String, Object> objectMap : list) {
				if ((sku = objectMap.get("sku")) != null) {
					avlqty = String.valueOf(objectMap.get("avlqty"));
					wmsInventoryList.add(new WmsInventory(String.valueOf(sku),Integer.valueOf(avlqty)));
				}
			}
		}

		return wmsInventoryList;
	}

	public static void main(String[] args) {
		List<WmsInventory> invtList = getInvtList("SZHAJ01", "SP160929123642717");
		System.out.println(invtList);
	}

}
