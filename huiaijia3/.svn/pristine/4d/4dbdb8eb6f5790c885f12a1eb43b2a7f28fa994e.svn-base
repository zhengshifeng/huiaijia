package com.flf.service.impl;

import com.base.dao.BaseDao;
import com.base.service.BaseServiceImpl;
import com.flf.entity.HajAreas;
import com.flf.entity.HajCommodity;
import com.flf.entity.HajCommodityVo;
import com.flf.entity.User;
import com.flf.mapper.HajCommodityMapper;
import com.flf.service.HajAreasService;
import com.flf.service.HajCommodityPriceService;
import com.flf.util.Const;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.flf.util.ExcelUtil.getCellValue;

/**
 * Created by SevenWong on 2017/9/4 16:57
 */
@Service("hajCommodityPriceService")
public class HajCommodityPriceServiceImpl extends BaseServiceImpl implements HajCommodityPriceService {
	private final static Logger log = Logger.getLogger(HajCommodityPriceServiceImpl.class);

	@Autowired
	private HajCommodityMapper dao;

	@Override
	public BaseDao getDao() {
		return dao;
	}

	@Autowired(required = false)
	private HajAreasService areasService;

	@Override
	public XSSFWorkbook export(HajCommodityVo params) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(10);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row);

		// ============================================================================
		// 开始写入商品数据
		// ============================================================================
		List<HajCommodityVo> commodityList = dao.getAllCommodity4Export(params);
		HajCommodityVo vo;

		// excel中列的索引
		int cellIndex;
		for (int i = 0, len = commodityList.size(); i < len; i++) {
			cellIndex = 0;
			vo = commodityList.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(vo.getCity()==null?"":vo.getCity());
			row.createCell(cellIndex++).setCellValue(vo.getSupplyName()==null?"":vo.getSupplyName());
			row.createCell(cellIndex++).setCellValue(vo.getCommodityAttr()==null?"":vo.getCommodityAttr());
			row.createCell(cellIndex++).setCellValue(vo.getParentTypeName()==null?"":vo.getParentTypeName());
			row.createCell(cellIndex++).setCellValue(vo.getTypeName()==null?"":vo.getTypeName());
			row.createCell(cellIndex++).setCellValue(vo.getName()==null?"":vo.getName());
			row.createCell(cellIndex++).setCellValue(vo.getCommodityNo()==null?"":vo.getCommodityNo());
			row.createCell(cellIndex++).setCellValue(vo.getSku()==null?"":vo.getSku());
			row.createCell(cellIndex++).setCellValue(vo.getWeight()==null?"":vo.getWeight());
			row.createCell(cellIndex++).setCellValue((vo.getCostPrice()==null?0.00:vo.getCostPrice()).toString());
			row.createCell(cellIndex++).setCellValue((vo.getOriginalPrice()==null?0.00:vo.getOriginalPrice()).toString());
			row.createCell(cellIndex++).setCellValue((vo.getMarketPrice()==null?0.00:vo.getMarketPrice()).toString());
			row.createCell(cellIndex++).setCellValue((vo.getPromotionPrice()==null?0.00:vo.getPromotionPrice()).toString());
		}
		return wkb;
	}

	/**
	 * 导出商品价格列表时设置excel标题
	 */
	private void setDefaultXSSFRow(XSSFRow row) {
		int cellIndex = 0;

		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("供应城市");
		row.createCell(cellIndex++).setCellValue("供应商名称");
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("商品名称");
		row.createCell(cellIndex++).setCellValue("商品编号");
		row.createCell(cellIndex++).setCellValue("商品物料号");
		row.createCell(cellIndex++).setCellValue("规格");
		row.createCell(cellIndex++).setCellValue("成本价");
		row.createCell(cellIndex++).setCellValue("售价");
		row.createCell(cellIndex++).setCellValue("参考价");
		row.createCell(cellIndex++).setCellValue("活动价");
	}

	@Override
	public String batchUpdate(String filePath, HttpServletRequest request) throws Exception {
		// 正常情况下返回success，异常时返回异常行的序列及提示信息
		StringBuilder result = new StringBuilder("");

		StringBuilder areaLimitTip = new StringBuilder();
		boolean dataError = false;

		String separator = ",";

		// 构造 XSSFWorkbook 对象，filePath 传入文件路径
		log.info("批量修改商品价格的文件路径：" + filePath);
		XSSFWorkbook xwb = new XSSFWorkbook(filePath);

		// 读取第一张sheet表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		xwb.close();// 记得关闭流

		XSSFRow row;
		//获取表头名称数据
		row = sheet.getRow(sheet.getFirstRowNum());
		//判断模板是否符合要求
		if ("序号".equals(getCellValue(row.getCell(0)))
				&& "供应城市".equals(getCellValue(row.getCell(1)))
				&& "供应商名称".equals(getCellValue(row.getCell(2)))
				&& "属性".equals(getCellValue(row.getCell(3)))
				&& "大类".equals(getCellValue(row.getCell(4)))
				&& "小类".equals(getCellValue(row.getCell(5)))
				&& "商品名称".equals(getCellValue(row.getCell(6)))
				&& "商品编号".equals(getCellValue(row.getCell(7)))
				&& "商品物料号".equals(getCellValue(row.getCell(8)))
				&& "规格".equals(getCellValue(row.getCell(9)))
				&& "成本价".equals(getCellValue(row.getCell(10)))
				&& "售价".equals(getCellValue(row.getCell(11)))
				&& "参考价".equals(getCellValue(row.getCell(12)))
				&& "活动价".equals(getCellValue(row.getCell(13)))
				) {
			HajCommodity commodity;
			String sequence = null;
			Integer idInDB;

			String areaCode;
			User userSession = (User) request.getSession().getAttribute(Const.SESSION_USER);

			// 待添加的商品列表，如数据都校验通过，才能开始做插入操作
			List<HajCommodity> commodityUpdateList = new ArrayList<>();

			List<HajAreas> cityList = areasService.listCity();

			// 循环输出表格中的内容
			// 变量i从真实数据行开始读取
			for (int i = (sheet.getFirstRowNum() + 1), rows = sheet.getPhysicalNumberOfRows(); i < rows; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

				// 一次只能导入1000条数据
				if (rows > 1000) {
					break;
				}

				try {
					// 序号无需写入数据库
					sequence = getCellValue(row.getCell(0));
					if (sequence.indexOf(".") > 0) {
						sequence = sequence.substring(0, sequence.indexOf("."));
					}

					// 序列号为空时，该行视为无效
					if (Tools.isEmpty(sequence)) {
						break;
					}

					// -------------------------------------------------
					// 开始封装数据，特殊字段添加校验
					// -------------------------------------------------

					idInDB = dao.getIdByCommodityNo(getCellValue(row.getCell(7)));
					if (idInDB == null) {
						dataError = true;
						break;
					}

					// 校验用户对应的城市权限
					// 先根据用户输入的“城市名”查找对应的areaCode，如果找不到对应的城市，或者用户没有该城市下的权限，则给出对应的提示
					areaCode = this.getCodeByCity(cityList, getCellValue(row.getCell(1)));
					if (Tools.isEmpty(areaCode) ||
							(Tools.isNotEmpty(userSession.getAreaCode())
									&& !Objects.equals(userSession.getAreaCode(), areaCode))) {
						areaLimitTip.append(sequence).append(separator);
						continue;
					}

					commodity = new HajCommodity();
					commodity.setId(idInDB);

					this.setModifyValues(commodity, row);

					commodityUpdateList.add(commodity);
				} catch (NumberFormatException e) {
					result.append("序号[");
					result.append(sequence);
					result.append("]价格格式异常，请修改后重新上传！\n\r");
					log.info(result.toString());
					break;
				}
			}
			result = batchImportResults(result, areaLimitTip, commodityUpdateList, dataError);
		} else {
			result.append("所选文件不符合模板要求,请重新选择正确的模板文件上传!");
			return result.toString();
		}

		return result.toString();
	}

	private void setModifyValues(HajCommodity commodity, XSSFRow row) {
		int cellIndex = 10;
		commodity.setCostPrice(new BigDecimal(getCellValue(row.getCell(cellIndex++))));
		commodity.setOriginalPrice(new BigDecimal(getCellValue(row.getCell(cellIndex++))));
		commodity.setMarketPrice(new BigDecimal(getCellValue(row.getCell(cellIndex++))));
		String content = getCellValue(row.getCell(cellIndex));
		if (Tools.isEmpty(content)) {
			content = "0";
		}
		commodity.setPromotionPrice(new BigDecimal(content));
	}

	private String getCodeByCity(List<HajAreas> cityList, String city) {
		String areaCode = "";
		for (HajAreas areas : cityList) {
			if (areas.getName().equals(city)) {
				areaCode = areas.getCode();
				break;
			}
		}
		return areaCode;
	}

	private StringBuilder batchImportResults(StringBuilder result, StringBuilder areaLimitTip,
											 List<HajCommodity> commodityUpdateList,
											 boolean dataError) throws Exception {
		// 整理提示信息
		if (!dataError && Tools.isEmpty(areaLimitTip.toString())
				&& Tools.isEmpty(result.toString())) {
			// 所有验证通过后，写入数据库
			for (HajCommodity commodity : commodityUpdateList) {
				if (commodity != null) {
					dao.updateBySelective(commodity);
				}
			}
			if (Tools.isEmpty(result.toString())) {
				result = new StringBuilder("success");
			}
		} else {
			if (Tools.notEmpty(areaLimitTip.toString())) {
				result.append("序号为[");
				result.append(areaLimitTip.substring(0, areaLimitTip.length() - 1));
				result.append("]的商品信息有误，请检查!!本次批量调整失败，请修改后重新上传。\n");
			}
			if (dataError) {
				result = new StringBuilder("表格数据异常");
			}
		}
		return result;
	}

}
