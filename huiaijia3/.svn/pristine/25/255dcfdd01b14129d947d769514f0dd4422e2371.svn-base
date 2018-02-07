package com.flf.controller;

import com.flf.entity.HajCommunityBuilding;
import com.flf.entity.HajCommunityPersion;
import com.flf.entity.HajCommunityUnit;
import com.flf.service.HajCommunityBuildingService;
import com.flf.service.HajCommunityPersionService;
import com.flf.service.HajCommunityUnitService;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/communityUnit")
public class HajCommunityUnitController {

	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	private HajCommunityUnitService service;

	@Autowired
	private HajCommunityPersionService hajCommunityPersionService;

	@Autowired
	private HajCommunityBuildingService communityBuildingService;

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam Integer communityId) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<HajCommunityUnit> unitList = service.getUnitList(communityId);
		HajCommunityPersion community = hajCommunityPersionService.queryById(communityId);
		mv.addObject("unitList", unitList);
		mv.addObject("community", community);
		mv.setViewName("communityUnit");
		return mv;
	}

	/**
	 * 初始化该小区的单元分类
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月22日上午9:58:24
	 * @param addressSpecification
	 * @return
	 */
	private static List<List<String>> initUnitTypeList(String addressSpecification) {
		String[] arr1 = addressSpecification.split(";");
		String[] arr2;
		String[] arr3;

		// 单元种类，前端用作循环显示多少个select
		List<List<String>> unitList = new ArrayList<>();

		// 具体保存各栋单元的名字,每个select中具体的值
		List<String> subUnitList;

		for (String anArr1 : arr1) {
			// #号前为第一项，看作该选项的分类
			arr2 = anArr1.split("#");
			arr3 = new String[0];

			if (arr2.length > 1) {
				arr3 = arr2[1].split("@");
			}

			// 初始化select中的值
			subUnitList = new ArrayList<>();

			// 将arr2中的第一个直接保存，无需循环
			subUnitList.add(arr2[0]);
			for (String anArr3 : arr3) {
				subUnitList.add(anArr3.replace("无", ""));
			}
			unitList.add(subUnitList);
		}

		return unitList;
	}

	/**
	 * 根据地址规则生成所有单元地址组合
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月24日下午3:15:35
	 * @return
	 */
	private static List<String> generateUnitList(List<List<String>> list) {
		int listSize = list.size() - 1;

		StringBuilder unit = new StringBuilder();
		List<String> unitList = new ArrayList<>();

		if (0 < listSize) {
			// 第1层
			for (int i = 1; i < list.get(0).size(); i++) {
				if (1 < listSize) {
					// 第2层
					for (int j = 1; j < list.get(1).size(); j++) {
						if (2 < listSize) {
							// 第3层
							for (int k = 1; k < list.get(2).size(); k++) {
								if (3 < listSize) {
									// 第4层
									for (int l = 1; l < list.get(3).size(); l++) {
										if (4 < listSize) {
											// 第5层
											for (int m = 1; m < list.get(4).size(); m++) {
												unit.append(list.get(0).get(i)).append(list.get(1).get(j));
												unit.append(list.get(2).get(k)).append(list.get(3).get(l));
												unit.append(list.get(4).get(m));
												unitList.add(unit.toString());
												unit = new StringBuilder();
											}
										} else {
											unit.append(list.get(0).get(i)).append(list.get(1).get(j));
											unit.append(list.get(2).get(k)).append(list.get(3).get(l));
											unitList.add(unit.toString());
											unit = new StringBuilder();
										}
									}
								} else {
									unit.append(list.get(0).get(i)).append(list.get(1).get(j));
									unit.append(list.get(2).get(k));
									unitList.add(unit.toString());
									unit = new StringBuilder();
								}
							}
						} else {
							unit.append(list.get(0).get(i)).append(list.get(1).get(j));
							unitList.add(unit.toString());
							unit = new StringBuilder();
						}
					}
				} else {
					unit.append(list.get(0).get(i));
					unitList.add(unit.toString());
					unit = new StringBuilder();
				}
			}
		}

		return unitList;
	}

	/**
	 * 保存新增或修改的单元
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月24日下午3:25:23
	 * @param communityUnit
	 * @param response
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommunityUnit communityUnit, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("result", "error");
		jsonMap.put("msg", "未知错误！");
		try {
			HajCommunityUnit unit = service.getUnit(communityUnit);
			if (unit == null) {
				service.add(communityUnit);
				jsonMap.put("result", "success");
				jsonMap.put("msg", "保存成功！");
			} else {
				jsonMap.put("result", "error");
				jsonMap.put("msg", "该单元已存在，ID为：" + unit.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 批量生成单元，并依序保存到数据库
	 *
	 * @author SevenWong<br>
	 * @date 2016年9月24日下午3:24:49
	 * @param communityId
	 * @param response
	 */
	@RequestMapping(value = "/batchGenerate")
	public void batchGenerate(Integer communityId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("result", "error");
		jsonMap.put("msg", "未知错误！");
		try {
			HajCommunityPersion community = hajCommunityPersionService.queryById(communityId);
			if (community != null) {
				List<HajCommunityBuilding> buildingList = communityBuildingService.getList4api(communityId);
				List<HajCommunityUnit> unitTypeList = generateUnitList2(buildingList);
				HajCommunityUnit communityUnit;
				HajCommunityUnit unit;
				for (int i = 0, len = unitTypeList.size(); i < len; i++) {
					communityUnit = new HajCommunityUnit();
					communityUnit.setUnit(unitTypeList.get(i).getUnit());
					communityUnit.setCommunityId(community.getId());
					communityUnit.setBuildingId(unitTypeList.get(i).getBuildingId());
					unit = service.getUnit(communityUnit);
					if (unit == null) {
						communityUnit.setSort(i);
						service.add(communityUnit);
					} else {
						if (!Objects.equals(communityUnit.getBuildingId(), unit.getBuildingId())) {
							// 根据 unit id 更新buildingId，其他字段不更新
							communityUnit.setId(unit.getId());
							communityUnit.setUnit(null);
							communityUnit.setCommunityId(null);
							service.updateBySelective(communityUnit);
						}
					}
				}
			}

			jsonMap.put("result", "success");
			jsonMap.put("msg", "已完成！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<HajCommunityUnit> generateUnitList2(List<HajCommunityBuilding> list) {
		HajCommunityUnit communityUnit;
		StringBuilder unit = new StringBuilder();
		List<HajCommunityUnit> unitList = new ArrayList<>();

		// 先在循环外声明下一层的list
		List<HajCommunityBuilding> list2;

		// 开始第1层
		for (HajCommunityBuilding aList : list) {
			list2 = aList.getSubList();
			if (list2.size() > 0) {
				// 先在循环外声明下一层的list
				List<HajCommunityBuilding> list3;

				// 开始第2层
				for (HajCommunityBuilding aList2 : list2) {
					list3 = aList2.getSubList();
					if (list3.size() > 0) {
						// 先在循环外声明下一层的list
						List<HajCommunityBuilding> list4;

						// 开始第3层
						for (HajCommunityBuilding aList3 : list3) {
							list4 = aList3.getSubList();
							if (list4.size() > 0) {
								// 先在循环外声明下一层的list
								List<HajCommunityBuilding> list5;

								// 开始第4层
								for (HajCommunityBuilding aList4 : list4) {
									list5 = aList4.getSubList();
									if (list5.size() > 0) {
										// 先在循环外声明下一层的list
										List<HajCommunityBuilding> list6;

										// 开始第5层
										for (HajCommunityBuilding aList5 : list5) {
											list6 = aList5.getSubList();
											if (list6.size() > 0) {
												// 如果有第6层的话....
											} else {
												unit.append(aList.getName()).append(aList2.getName())
														.append(aList3.getName()).append(aList4.getName())
														.append(aList5.getName());
												communityUnit = new HajCommunityUnit();
												communityUnit.setUnit(unit.toString());
												communityUnit.setBuildingId(aList5.getId());
												unitList.add(communityUnit);
												unit = new StringBuilder();
											}
										}
									} else {
										unit.append(aList.getName()).append(aList2.getName())
												.append(aList3.getName()).append(aList4.getName());
										communityUnit = new HajCommunityUnit();
										communityUnit.setUnit(unit.toString());
										communityUnit.setBuildingId(aList4.getId());
										unitList.add(communityUnit);
										unit = new StringBuilder();
									}
								}
							} else {
								unit.append(aList.getName()).append(aList2.getName()).append(aList3.getName());
								communityUnit = new HajCommunityUnit();
								communityUnit.setUnit(unit.toString());
								communityUnit.setBuildingId(aList3.getId());
								unitList.add(communityUnit);
								unit = new StringBuilder();
							}
						}
					} else {
						unit.append(aList.getName()).append(aList2.getName());
						communityUnit = new HajCommunityUnit();
						communityUnit.setUnit(unit.toString());
						communityUnit.setBuildingId(aList2.getId());
						unitList.add(communityUnit);
						unit = new StringBuilder();
					}
				}
			} else {
				unit.append(aList.getName());
				communityUnit = new HajCommunityUnit();
				communityUnit.setUnit(unit.toString());
				communityUnit.setBuildingId(aList.getId());
				unitList.add(communityUnit);
				unit = new StringBuilder();
			}
		}
		return unitList;
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer id, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "1");
		jsonMap.put("msg", "未知错误");
		try {
			// 删除前先检查该地址是否有用户正在使用
			Integer countOfUnitUsed = service.getCountOfUnitUsed(id);
			if (countOfUnitUsed > 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "删除失败！已有用户使用");
			} else {
				service.delete(id);

				jsonMap.put("error", "0");
				jsonMap.put("msg", "已删除");
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer communityId) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<HajCommunityUnit> unitList = service.getUnitList(communityId);
		HajCommunityPersion community = hajCommunityPersionService.queryById(communityId);
		mv.addObject("unitList", unitList);
		mv.addObject("community", community);
		mv.setViewName("communityUnitEdit");
		return mv;
	}

	@RequestMapping(value = "/updateSort", method = RequestMethod.POST)
	public void updateSort(@RequestParam String params, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "1");
		jsonMap.put("msg", "未知错误！");
		try {
			List<Map<String, Object>> list = JSONUtils.toList(params);
			List<HajCommunityUnit> unitList = new ArrayList<>();
			HajCommunityUnit unit;
			for (Map<String, Object> map : list) {
				unit = new HajCommunityUnit();
				unit.setId(Integer.valueOf(map.get("id").toString()));
				unit.setSort(Integer.valueOf(map.get("sort").toString()));
				unitList.add(unit);
			}

			// 上面循环复制没问题后，再循环进行修改
			for (HajCommunityUnit anUnit : unitList) {
				service.updateBySelective(anUnit);
			}

			jsonMap.put("error", "0");
			jsonMap.put("msg", "保存成功！");
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String str = "期#一期@二期@三期;栋#一栋@二栋@三栋;座#1座@2座@3座@4座@无;单元#A单元@B单元@C单元;号#";
		List<String> batchGenerateUnitType = generateUnitList(initUnitTypeList(str));
		for (String s : batchGenerateUnitType) {
			System.out.println(s);
		}
	}

}
