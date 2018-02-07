package com.flf.listener;

import com.base.criteria.Criteria;
import com.flf.entity.SystemConfiguration;
import com.flf.service.RedisSpringProxy;
import com.flf.service.SystemConfigurationService;
import com.flf.util.Const;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class WebAppContextListener implements ServletContextListener {

	private final static Logger log = Logger.getLogger(WebAppContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.info("contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		log.info("获取Spring WebApplicationContext");

		// 从数据库中读取系统配置表中所有的数据，放到缓存中，以便调用
		// 注：系统配置表如果有做增删改操作时，应当及时更新缓存
		SystemConfigurationService configurationService;
		configurationService = Const.WEB_APP_CONTEXT.getBean(SystemConfigurationService.class);
		try {
			Criteria criteria = new Criteria();
			criteria.setPageSize(1000);
			List<SystemConfiguration> list = configurationService.queryByList(criteria);

			RedisSpringProxy redisSpringProxy;
			redisSpringProxy = Const.WEB_APP_CONTEXT.getBean(RedisSpringProxy.class);

			for (SystemConfiguration configuration : list) {
				redisSpringProxy.save("SystemConfiguration_" + configuration.getName(), configuration.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
