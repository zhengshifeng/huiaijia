package com.flf.job;

import com.flf.service.HajCommodityService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.HajCommodityUtil;
import com.flf.util.Tools;
import com.wms.InventorySync;
import com.wms.entity.WmsInventory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UpCommoidtyQuartz {
	private final static Logger log = Logger.getLogger(UpCommoidtyQuartz.class);

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public void pullOffShelves() {
		log.info("进入商品自动下架调度方法");

		// 自动下架的商品分类ID
		String typeIds = (String) redisSpringProxy.read("SystemConfiguration_upCommoidty");
		log.info("下架的分类ID:" + typeIds);
		commodityService.updateUpCommoidtyList(typeIds);

		// 自动下架的商品ID
		String commodityIds = (String) redisSpringProxy.read("SystemConfiguration_auto_put_on_cids");
		log.info("下架的商品ID:" + commodityIds);
		if (Tools.isNotEmpty(commodityIds)) {
			commodityService.updateShelvesById(commodityIds.split(","), 0);
		}

		HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);

		log.info("下架调度完成...");
	}

	public void pullOffShelves2() {
		log.info("进入商品18点自动下架调度方法");

		// 自动下架的商品ID
		String commodityIds = (String) redisSpringProxy.read("SystemConfiguration_auto_pull_off_shelves_cids_18");
		log.info("下架的商品ID:" + commodityIds);
		if (Tools.isNotEmpty(commodityIds)) {
			commodityService.updateShelvesById(commodityIds.split(","), 0);
		}

		HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);

		log.info("18点下架调度完成...");
	}

	public void putOnShelves() {
		log.info("进入商品自动上架调度方法");

		// 自动上架的商品分类ID
		String typeIds = (String) redisSpringProxy.read("SystemConfiguration_upCommoidty");
		log.info("上架的分类ID:" + typeIds);
		commodityService.shangJia(typeIds);

		// 自动上架的商品ID
		String commodityIds = (String) redisSpringProxy.read("SystemConfiguration_auto_put_on_cids");
		log.info("上架的商品ID:" + commodityIds);
		if (Tools.isNotEmpty(commodityIds)) {
			commodityService.updateShelvesById(commodityIds.split(","), 1);
		}

		// 这是18点下架后需自动上架的商品ID
		commodityIds = (String) redisSpringProxy.read("SystemConfiguration_auto_pull_off_shelves_cids_18");
		log.info("上架的商品ID:" + commodityIds);
		if (Tools.isNotEmpty(commodityIds)) {
			commodityService.updateShelvesById(commodityIds.split(","), 1);
		}

		HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);

		log.info("上架调度完成...");
	}

	public void resetGrossWeight() {
		log.info("调度，每日清空商品毛重");

		commodityService.updateResetGrossWeight();
	}

	/**
	 * 已重命名为 pullOffShelves
	 */
	@Deprecated
	public void upCommoidty() {
		putOffShelves();
	}

	/**
	 * 单词用错，
	 * 已重命名为 pullOffShelves
	 * 避免其他版本发布找不到方法，因此此处保留原方法名，调用新方法
	 */
	@Deprecated
	public void putOffShelves() {
		pullOffShelves();
	}

	/**
	 * 已重命名为 putOnShelves
	 */
	@Deprecated
	public void shangJia() {
		putOnShelves();
	}

	/**
	 * 同步wms库存
	 */
	public void wmsInventorySync() {
		log.info("库存同步调度开始执行...");
		// 需要同步库存的商品
		List<String> invtSyncSkuList = commodityService.getInvtSyncSkuList();
		String sku = StringUtils.join(invtSyncSkuList, ',');

		// 仓库编号，目前先使用一个仓库处理，日后可能涉及到多仓对不同小区
		Object warehouseNo = redisSpringProxy.read("SystemConfiguration_warehouse_no");
		List<WmsInventory> invtList = InventorySync.getInvtList(warehouseNo.toString(), sku);

		commodityService.wmsInventorySync(invtList);

		log.info("库存同步调度执行完成！");
	}

}
