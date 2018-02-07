package com.flf.controller.erp;

import com.flf.controller.erp.entity.ERPMaterielVo;
import com.flf.service.HajCommodityService;
import com.flf.util.HttpUtils;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 物料接口，与金蝶对接
 */

@Controller
@RequestMapping(value = "/erp/materiel")
public class HajCommodityErpController {
	private final static Logger log = Logger.getLogger(HajCommodityErpController.class);

	@Autowired
	private HajCommodityService service;

	//新增物料
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
// 		JSONObject json = JSONUtils.toJSONObject(params);
		String params = HttpUtils.readerRequest(request);
		ERPMaterielVo materielVo = (ERPMaterielVo)JSONUtils.toBean(params,ERPMaterielVo.class);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if(materielVo.getFMaterialId() != null){
			log.info("同步erp新增物料id["+materielVo.getFMaterialId()+"]开始");
			try {
				boolean  result = service.saveCommodity(materielVo);
				if(result){
					log.info("同步erp新增物料id["+materielVo.getFMaterialId()+"]成功");
					jsonMap.put("isSuccess",true);
					jsonMap.put("Result","");
				}else {
					log.info("同步erp新增物料id["+materielVo.getFMaterialId()+"]已存在");
					jsonMap.put("isSuccess",false);
					jsonMap.put("Result","物料已存在");
				}
			}catch (Exception e){
				e.printStackTrace();
				log.info("同步erp新增物料id["+materielVo.getFMaterialId()+"]失败");
				jsonMap.put("isSuccess",false);
				jsonMap.put("Result","新增物料失败");
			}finally {
				try {
					JSONUtils.printObject(jsonMap, response);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}else{
			jsonMap.put("isSuccess",false);
			jsonMap.put("Result","物料id不能为null");
			try{
				JSONUtils.printObject(jsonMap, response);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	//修改物料
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		JSONObject json = JSONUtils.toJSONObject(params);
		String params = HttpUtils.readerRequest(request);
		ERPMaterielVo materielVo = (ERPMaterielVo)JSONUtils.toBean(params,ERPMaterielVo.class);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if(materielVo.getFMaterialId() != null){
			log.info("同步erp修改物料id["+materielVo.getFMaterialId()+"]开始");
			try {
				service.updateCommodity(materielVo);
				jsonMap.put("isSuccess",true);
				jsonMap.put("Result","");
			}catch (Exception e){
				e.printStackTrace();
				log.info("同步erp修改物料id["+materielVo.getFMaterialId()+"]失败");
				jsonMap.put("isSuccess",false);
				jsonMap.put("Result","修改物料失败");
			}finally {
				try{
					JSONUtils.printObject(jsonMap, response);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}else{
			jsonMap.put("isSuccess",false);
			jsonMap.put("Result","物料id不能为null");
			try{
				JSONUtils.printObject(jsonMap, response);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
