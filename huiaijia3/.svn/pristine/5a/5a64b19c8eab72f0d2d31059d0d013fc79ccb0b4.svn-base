package com.flf.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.flf.entity.WXRequestBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * XML与JavaBean相互转换工具类 File: XMLBeanUtils.java User: liyanbin
 */
public final class XMLBeanUtils {
	/**
	 * 将Bean转换为XML
	 * 
	 * @param clazzMap
	 *            别名-类名映射Map
	 * @param bean
	 *            要转换为xml的bean对象
	 * @return XML字符串
	 */
	public static String bean2xml(Map<String, Class> clazzMap, Object bean) {
		XStream xstream = new XStream();
		for (Iterator it = clazzMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Class> m = (Map.Entry<String, Class>) it.next();
			xstream.alias(m.getKey(), m.getValue());
		}
		String xml = xstream.toXML(bean);
		return xml;
	}

	/**
	 * 将XML转换为Bean
	 * 
	 * @param clazzMap
	 *            别名-类名映射Map
	 * @param xml
	 *            要转换为bean对象的xml字符串
	 * @return Java Bean对象
	 */
	public static Object xml2Bean(Map<String, Class> clazzMap, String xml) {
		XStream xstream = new XStream();
		for (Iterator it = clazzMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Class> m = (Map.Entry<String, Class>) it.next();
			xstream.alias(m.getKey(), m.getValue());
		}
		Object bean = xstream.fromXML(xml);
		return bean;
	}

	/**
	 * 获取XStream对象
	 * 
	 * @param clazzMap
	 *            别名-类名映射Map
	 * @return XStream对象
	 */
	public static XStream getXStreamObject(Map<String, Class> clazzMap) {
		XStream xstream = new XStream();
		for (Iterator it = clazzMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Class> m = (Map.Entry<String, Class>) it.next();
			xstream.alias(m.getKey(), m.getValue());
		}
		return xstream;
	}

	public static String parseXml(HttpServletRequest request) throws Exception {
		// 解析结果存储在HashMap
		String result = "";
		InputStream inputStream = request.getInputStream();

		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		in.close();
		return result;
	}

	public static String map2xml(Map<String, String> map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("xml");
		Iterator it = map.entrySet().iterator();
		Map.Entry entry = null;
		Element keyElement = null;
		while (it.hasNext()) {
			entry = (Map.Entry) it.next();
			entry.getKey();
			entry.getValue();
			keyElement = nodeElement.addElement((String) entry.getKey());
			keyElement.setText((String) entry.getValue());
		}
		return doc2string(document);
	}

	public static String doc2string(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			OutputFormat format = new OutputFormat("    ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	public static String getXmlParams(WXRequestBean requestParams) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("xml");
		Element keyElement = null;

		Field[] fields = requestParams.getClass().getDeclaredFields();
		String fieldValue = "";
		for (int i = 0; i < fields.length; ++i) {
			fieldValue = (String) getFieldValueByName(fields[i].getName(), requestParams);
			if (Tools.notEmpty(fieldValue)) {
				keyElement = nodeElement.addElement(fields[i].getName());
				keyElement.setText(fieldValue);
			}
		}
		return doc2string(document);
	}

	private static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[0]);
			Object value = method.invoke(o, new Object[0]);
			return value;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getXmlNodeValue(String xml, String node) {
		Document dd = null;
		String code_url = null;
		try {
			dd = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			return "";
		}
		if (dd != null) {
			Element root = dd.getRootElement();
			if (root == null) {
				return "";
			}
			Element codeUrl = root.element(node);
			if (codeUrl == null) {
				return "";
			}
			code_url = codeUrl.getText();
		}
		return code_url;
	}

	public static void main(String[] args) {
		WXRequestBean wxrequest = new WXRequestBean();
		wxrequest.setAppid("wxbcd030b1d0e41467");
		wxrequest.setBody("APP支付测试");
		wxrequest.setMch_id("1347875601");
		wxrequest.setNonce_str(MakeOrderNum.makeRandomNum());
		wxrequest.setNotify_url("http://115.29.170.224:8080/hajRecharge/hajRechargeNotify.action");
		wxrequest.setOut_trade_no(MakeOrderNum.makeRechargeNum());
		wxrequest.setSpbill_create_ip("115.29.170.224");
		wxrequest.setTotal_fee("1");
		wxrequest.setTrade_type("APP");

		XStream xs = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xs.alias("xml", WXRequestBean.class);
		String xml = xs.toXML(wxrequest);

		System.out.println("xml:" + xml);

	}

}