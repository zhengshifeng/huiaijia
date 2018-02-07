package com.wms;

/**
 * Created by SevenWong on 2017-3-23 023 16:32
 */
public interface API {

	String DOMAIN = "http://112.74.41.143:33318";

	String SEARCH_INVENTORY = DOMAIN + "/WebApi/Api/Inventory/SearchInventory";

	String CREATE_DN = DOMAIN + "/WebApi/Api/DN/CreateDN";

	String CANCEL_DN = DOMAIN + "/WebApi/Api/DN/CancelDN";
}
