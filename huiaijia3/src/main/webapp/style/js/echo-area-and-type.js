/**
 * @Author SevenWong 
 * @Date 2016-05-26
 * 城市数据以及商品类别的级别联动和数剧回显<br>
 * 
 * 具体的命名规则请参照supplyChainList.jsp
 * 
 */



/**
 * 初始化城市数据
 */
function initCity() {
	$('#cityCode').empty();
	$('#cityCode').append('<option value="0">市</option>');
}

/**
 * 初始化区域数据
 */
function initCommunity() {
	$('#communityCode').empty();
	$('#communityCode').append('<option value="0">区/县</option>');
}

/**
 * 初始化大类数据
 */
function initParentTypeId() {
	$('#parentTypeId').empty();
	$('#parentTypeId').append('<option value="0">大类</option>');
}

/**
 * 初始化小类数据
 */
function initTypeId() {
	$('#typeId').empty();
	$('#typeId').append('<option value="0">小类</option>');
}

/**
 * 根据父级id获取子地区列表
 * @param pCode
 * @param childrenArea
 */
function getAreaByPCode(pCode, childrenArea){
	var url = '/areas/getAreaByPCode.html?pCode='+pCode;
	$.get(url, function(data){
		if (data) {
			childrenArea.empty();
			childrenArea.append('<option value="0">不限</option>');
			$.each(data, function(i){
				childrenArea.append('<option value="'+this.code+'">'+this.name+'</option>');
			});
		}
	});
}

/**
 * 根据父级id获取子地区列表，去掉不限
 * @param pCode
 * @param childrenArea
 */
function getAreaByPCodeNoLimit(pCode, childrenArea){
	var url = '/areas/getAreaByPCode.html?pCode='+pCode;
	$.get(url, function(data){
		if (data) {
			childrenArea.empty();
			$.each(data, function(i){
				childrenArea.append('<option value="'+this.code+'">'+this.name+'</option>');
			});
		}
	});
}

$(function(){
	// 根据省份显示城市数据
	$('#provinceCode').change(function(){
		var provinceCode = $(this).val()
			initCity();
			initCommunity();
		if (provinceCode != '0') {
			getAreaByPCode(provinceCode, $('#cityCode'));
		}
	});
	
	// 根据城市显示区域数据
	$('#cityCode').change(function(){
		var cityCode = $(this).val()
		if (cityCode == '0') {
			initCommunity();
		} else {
			getAreaByPCode(cityCode, $('#communityCode'));
		}
	});
	
	// 根据区域显示小区数据
	$('#residential').change(function(){
		var villageName = $(this).val()
		if (villageName == '0') {
			initVillage();
		} else {
			getAreaByPCode(cityCode, $('#communityCode'));
		}
	});

	// 根据属性显示大类数据
	$('#commodityAttr').change(function(){
		var commodityAttr = $(this).val()
		initParentTypeId();
		initTypeId();
		if (commodityAttr != '0') {
			var url = "/commodityType/getBigType.html?commodityAttr="+commodityAttr;
			$.getJSON(url, function(data){
				if (data) {
					$.each(data, function(i){
						$('#parentTypeId').append('<option value="'+this.id+'">'+this.typeName+'</option>');
					});
				}
			});
		}
	});

	// 根据大类显示小类数据
	$('#parentTypeId').change(function(){
		var parentTypeId = $(this).val()
		initTypeId();
		if (parentTypeId != '0') {
			var url = "/commodityType/getSmallType.html?parentId="+parentTypeId;
			$.getJSON(url, function(data){
				if (data) {
					$.each(data, function(i){
						$('#typeId').append('<option value="'+this.id+'">'+this.typeName+'</option>');
					});
				}
			});
		}
	});
});