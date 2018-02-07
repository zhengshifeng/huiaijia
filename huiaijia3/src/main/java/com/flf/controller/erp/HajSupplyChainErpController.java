package com.flf.controller.erp;

import com.flf.controller.erp.entity.ERPSupplierVo;
import com.flf.service.HajSupplyChainService;
import com.flf.util.HttpUtils;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 供应商接口，与金蝶对接
 */
@Controller
@RequestMapping(value = "/erp/supplyChain")
public class HajSupplyChainErpController {
	private final static Logger log = Logger.getLogger(HajSupplyChainErpController.class);

	@Autowired(required = false)
	private HajSupplyChainService service;

	/**
	 * 新增供应商
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String params = HttpUtils.readerRequest(request);
		ERPSupplierVo vo = JSONUtils.toBean(params, ERPSupplierVo.class);
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (vo.getFSUPPLIERID() != null && Tools.isNotEmpty(vo.getFOrgCode())) {
				log.info("同步erp新增供应商+[" + vo.getFSUPPLIERID() + "]开始");
				boolean result = service.saveSupplyChain(vo);
				if (result) {
					log.info("同步erp新增供应商+[" + vo.getFSUPPLIERID() + "]成功");
					jsonMap.put("isSuccess", true);
					jsonMap.put("Result", "");
				} else {
					log.info("同步erp新增供应商+[" + vo.getFSUPPLIERID() + "]已存在");
					jsonMap.put("isSuccess", false);
					jsonMap.put("Result", "供应商已存在");
				}
			} else {
				log.info("同步erp新增供应商 FSUPPLIERID: " + vo.getFSUPPLIERID() + "; FOrgCode: " + vo.getFOrgCode());
				jsonMap.put("isSuccess", false);
				jsonMap.put("Result", "供应商id与组织不能为null");
			}
		} catch (Exception e) {
			log.info("同步erp新增供应商+[" + vo.getFSUPPLIERID() + "]失败", e);
			jsonMap.put("isSuccess", false);
			jsonMap.put("Result", "新增供应商失败");
		} finally {
			try {
				log.info("同步erp新增供应商: " + jsonMap.get("Result").toString());
				JSONUtils.printObject(jsonMap, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改供应商
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String params = HttpUtils.readerRequest(request);
		//	JSONObject json = JSONUtils.toJSONObject(params);
		ERPSupplierVo vo = JSONUtils.toBean(params, ERPSupplierVo.class);
		Map<String, Object> jsonMap = new HashMap<>();
		if (vo.getFSUPPLIERID() != null && Tools.isNotEmpty(vo.getFOrgCode())) {
			log.info("同步erp修改供应商+[" + vo.getFSUPPLIERID() + "]开始");
			try {
				service.updateSupplyChainBycloudsSupplierId(vo);
				jsonMap.put("isSuccess", true);
				jsonMap.put("Result", "");
			} catch (Exception e) {
				e.printStackTrace();
				jsonMap.put("isSuccess", false);
				jsonMap.put("Result", "修改供应商失败");
			} finally {
				try {
					JSONUtils.printObject(jsonMap, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			jsonMap.put("isSuccess", false);
			jsonMap.put("Result", "供应商id与组织不能为null");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
