package com.base.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

	private static final Logger log = Logger.getLogger(HttpUtil.class);

	public static String get(String url, Map<String, Object> paramsMap) {
		StringBuilder params = new StringBuilder();
		if (paramsMap != null) {
			url = url + "?";
			try {
				params = getRequestParams(paramsMap);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			url = url + params.substring(0, params.length() - 1);
		}

		HttpURLConnection con = null;
		try {
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		StringBuilder buffer = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static String sendPost(String url, Map<String, Object> paramsMap) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);

			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());

			StringBuilder params = getRequestParams(paramsMap);

			// 发送请求参数
			// log.info(url + "?" + params.toString());
			out.print(params.toString());

			// flush输出流的缓冲
			out.flush();

			log.info("responseCode: " + conn.getResponseCode());
			if (conn.getResponseCode() != 200) {
				return String.valueOf(conn.getResponseCode());
			}

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			log.info("send post error: ", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.info(ex.getMessage(), ex);
			}
		}
		return result.toString();
	}

	private static StringBuilder getRequestParams(Map<String, Object> params) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(URLEncoder.encode(e.getValue().toString().trim(), "UTF-8"));
				sb.append("&");
			}
		}
		return sb;
	}

	/**
	 * ajax跨域请求时需设置哪些来源允许跨域，*表示所有来源，可指定特定来源，如：http://192.168.0.77:8080/
	 *
	 * @param response
	 */
	public static void accessControlAllowOrigin(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	/**
	 * 根据浏览器类型对导出文件的文件名进行中文编码处理
	 */
	public static String encodeFilename(HttpServletRequest request, String filename) {
		String agent = request.getHeader("USER-AGENT").toLowerCase();
		try {
			if (agent.contains("msie")) {
				filename = URLEncoder.encode(filename, "UTF-8");
			} else {
				// firefox/safari不转码
				filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return filename;
	}

	public static String getIp(HttpServletRequest request) {
		try {
			String ip = request.getHeader("X-Forwarded-For");
			if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
				//多次反向代理后会有多个ip值，第一个ip才是真实ip
				int index = ip.indexOf(",");
				if (index != -1) {
					return ip.substring(0, index);
				} else {
					return ip;
				}
			}
			ip = request.getHeader("X-Real-IP");
			if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
				return ip;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request.getRemoteAddr();
	}

}
