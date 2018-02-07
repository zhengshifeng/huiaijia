package com.flf.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	public static Properties getProperties(String resourcePath){
		Properties properties = new Properties();
		try {
			properties.load(PropertyUtil.class.getClassLoader().getResourceAsStream(resourcePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
