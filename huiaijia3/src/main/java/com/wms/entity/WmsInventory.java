package com.wms.entity;

/**
 * Created by SevenWong on 2017-4-17 017 14:22
 */
public class WmsInventory {

	private String sku;
	private Integer avlqty;

	public WmsInventory() {
	}

	public WmsInventory(String sku, Integer avlqty) {
		this.sku = sku;
		this.avlqty = avlqty;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getAvlqty() {
		return avlqty;
	}

	public void setAvlqty(Integer avlqty) {
		this.avlqty = avlqty;
	}

	@Override
	public String toString() {
		return "Inventory{" +
				"sku='" + sku + '\'' +
				", avlqty=" + avlqty +
				'}';
	}
}
