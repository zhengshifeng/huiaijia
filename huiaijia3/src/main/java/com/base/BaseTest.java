package com.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by SevenWong on 2017/2/15 16:12
 */

// 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
@RunWith(SpringJUnit4ClassRunner.class)

// 让Junit加载spring的配置文件
@ContextConfiguration({"classpath:spring/ApplicationContext-mvc.xml", "classpath:spring/ApplicationContext.xml"})
public class BaseTest {

}
