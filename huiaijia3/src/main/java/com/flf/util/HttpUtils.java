package com.flf.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/11/25.
 */
public class HttpUtils {

	private final static Logger log = Logger.getLogger(HttpUtils.class);
	/**
	 * 通过流读取请求参数
	 * @param request
	 * @return lyg
	 * @throws Exception
	 */
	public static String readerRequest(HttpServletRequest request) throws Exception
	{
		if (null == request)
		{
			log.error("请求不能为空！");
			throw new Exception("请求不能为空！");
		}
		StringBuffer rs = new StringBuffer();
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				rs.append(line);
			}
			log.info("收到POST请求:" + rs.toString());
		}
		catch (Exception e)
		{
			log.error(e);
			e.printStackTrace();
		}
		finally
		{
			try
			{
				//关闭流
				if (null != in)
				{

					in.close();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rs.toString();
	}
}
