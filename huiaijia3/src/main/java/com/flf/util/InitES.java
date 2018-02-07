package com.flf.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * 初始化连接es服务端,这里相当于dao层..
 * 
 * 
 */
public class InitES {

	static Log log = LogFactory.getLog(InitES.class);
	/**
	 * 静态,单例...
	 */
	private static TransportClient client;

	public static TransportClient initESClient() {
		try {
			if (client == null) {
				// 配置你的es,现在这里只配置了集群的名,默认是elasticsearch,跟服务器的相同
				Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch")
						.put("discovery.type", "zen")// 发现集群方式
						.put("discovery.zen.minimum_master_nodes", 2)// 最少有2个master存在
						.put("discovery.zen.ping_timeout", "200ms")// 集群ping时间，太小可能会因为网络通信而导致不能发现集群
						.put("discovery.initial_state_timeout", "500ms").put("gateway.type", "local")// (fs,
																										// none,
																										// local)
						.put("index.number_of_shards", 1).put("action.auto_create_index", false)// 配置是否自动创建索引
						.put("cluster.routing.schedule", "50ms")// 发现新节点时间

						.build();
				// 从属性文件中获取搜索服务器相对域地址
				String transportAddresses = Config.getProperty("transportAddresses", "");
				// 集群地址配置
				List<InetSocketTransportAddress> list = new ArrayList<InetSocketTransportAddress>();
				if (null != transportAddresses) {
					String[] strArr = transportAddresses.split(",");
					for (String str : strArr) {
						String[] addressAndPort = str.split(":");
						String address = addressAndPort[0];
						int port = Integer.valueOf(addressAndPort[1]);

						InetSocketTransportAddress inetSocketTransportAddress = new InetSocketTransportAddress(address,
								port);
						list.add(inetSocketTransportAddress);
					}
				}
				// 这里可以同时连接集群的服务器,可以多个,并且连接服务是可访问的
				InetSocketTransportAddress addressList[] = list.toArray(new InetSocketTransportAddress[list.size()]);
				// Object addressList[]=(Object [])list.toArray();

				client = new TransportClient(settings).addTransportAddresses(addressList);
				// 这里可以同时连接集群的服务器,可以多个,并且连接服务是可访问的 192.168.1.102
				// client = new TransportClient(settings).addTransportAddresses(
				// new InetSocketTransportAddress("192.168.1.103", 9300));
				//
				// Client client = new TransportClient()
				// .addTransportAddress(new
				// InetSocketTransportAddress("192.168.0.149", 9300))
				// .addTransportAddress(new
				// InetSocketTransportAddress("192.168.0.162", 9300));

				// 改变shards数目：
				/*
				 * client.admin().indices().prepareUpdateSettings("test")
				 * .setSettings(ImmutableSettings.settingsBuilder().put(
				 * "index.number_of_replicas", 2)).execute().actionGet();
				 */
			}
		} catch (Exception e) {
			// if (log.isDebugEnabled()) {
			// log.debug("方法AppCommentAction-deleteAppComment,参数信息：commentid" );
			// }
			log.info("获取客户端对象异常：" + e.getMessage());
		}
		return client;
	}

	public static void closeESClient() {
		if (client != null) {
			client.close();
		}
	}
}