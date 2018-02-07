package com.flf.util;

import de.spinscale.elasticsearch.action.suggest.suggest.SuggestAction;
import de.spinscale.elasticsearch.action.suggest.suggest.SuggestRequest;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.mlt.MoreLikeThisRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: ElasticsearchUtil
 * @Description:elasticsearch数据操作工具类
 * 
 * 
 */
public class ElasticsearchUtil {

	static Log log = LogFactory.getLog(ElasticsearchUtil.class);
	/**
	 * 客户端对象
	 */
	private static TransportClient client = InitES.initESClient();

	static ElasticsearchUtil instance;

	public static ElasticsearchUtil getInstance() {
		synchronized (ElasticsearchUtil.class) {
			if (instance == null) {
				instance = new ElasticsearchUtil();
			}
		}
		return instance;
	}

	/**
	 * 
	 * @Title: createIndex (商品)
	 * @Description: 创建索引
	 * @param list
	 *            :数据对象
	 * @param indexName
	 *            ：数据库
	 * @param typeName
	 *            ：数据表
	 * @return int
	 * 
	 * @throws Exception
	 * @date 2014-6-5 下午2:21:31
	 */
	public static int createIndex(List<?> list, String indexName, String typeName, String analyzerName)
			throws Exception {
		int successResult = 0;
		String indexId = null;
		Map<String, Object> objMap = null;
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		buildIndexMapping(indexName, typeName, analyzerName);

		for (Object obj : list) {
			objMap = (Map<String, Object>) obj;
			successResult++;
			JSONObject jsonObject = JSONObject.fromObject(obj);
			if (null != objMap && null != objMap.get("id") && objMap.size() > 0) {
				indexId = objMap.get("id").toString();
			}
			bulkRequest.add(client.prepareIndex(indexName, typeName, indexId).setSource(jsonObject.toString()));
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();

		if (!bulkResponse.hasFailures()) {
			return successResult;
		} else {
			log.info(bulkResponse.buildFailureMessage());
		}
		return successResult;
	}


	/**
	 * 索引的mapping
	 * <p>
	 * 预定义一个索引的mapping,使用mapping的好处是可以个性的设置某个字段等的属性
	 * Es_Setting.INDEX_DEMO_01类似于数据库 mapping 类似于预设某个表的字段类型
	 * <p>
	 * Mapping,就是对索引库中索引的字段名及其数据类型进行定义，类似于关系数据库中表建立时要定义字段名及其数据类型那样， 不过es的
	 * mapping比数据库灵活很多，它可以动态添加字段。 一般不需要要指定mapping都可以，因为es会自动根据数据格式定义它的类型，
	 * 如果你需要对某 些字段添加特殊属性（如：定义使用其它分词器、是否分词、是否存储等），就必须手动添加mapping。
	 * 有两种添加mapping的方法，一种是定义在配 置文件中，一种是运行时手动提交mapping，两种选一种就行了。
	 *
	 * @throws Exception
	 *             Exception
	 */
	protected static void buildIndexMapping(String indexName, String typeName, String analyzerName) throws Exception {

		try {
			client.admin().indices().prepareCreate(indexName).execute().actionGet();

			XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("_timestamp")// 这个字段为时间戳字段.即你添加一条索引记录后,自动给该记录增加个时间字段(记录的创建时间),搜索中可以直接搜索该字段.
					.field("enabled", true).field("store", "no").field("analyzer", "ik").endObject()
					// properties下定义的name等等就是属于我们需要的自定义字段了,相当于数据库中的表字段
					// ,此处相当于创建数据库表
					.startObject("properties").startObject("id").field("type", "long").field("store", "yes").endObject()
					.startObject(analyzerName).field("type", "string").field("analyzer", "ik")
					.field("index", "analyzed").endObject().endObject().endObject();

			PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(typeName).source(mapping);

			client.admin().indices().putMapping(mappingRequest).actionGet();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			log.info("创建mapping");
		}

	}

	/**
	 * 
	 * @Title: searcher
	 * @Description: 搜索方法
	 * @param indexname
	 *            :类似数据库
	 * @param type
	 *            ：类似数据表
	 * @return SearchResponse
	 * 
	 * @date 2013-12-21 下午5:09:05
	 */
	public static SearchResponse searcher(String indexname, String type, int nowPage, int pageSize, String keyWord,
			String[] highFields, String name) {

		SearchResponse response = null;

		if (client != null) {
			// 创建查询索引
			SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexname);
			// 设置查询索引表名称
			searchRequestBuilder.setTypes(type);
			// 设置查询类型
			// 1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
			// 2.SearchType.SCAN = 扫描查询,无序
			// 3.SearchType.COUNT = 不设置的话,这个为默认值,还有的自己去试试吧
			searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

			// fieldQuery 这个必须是你的索引字段哦,不然查不到数据,这里我只设置两个字段 id ,title
			// String title = "title+-&&||!(){}[]^\"~*?:\\";
			keyWord = QueryParser.escape(keyWord);// 主要就是这一句把特殊字符都转义,那么lucene就可以识别

			// 设置查询关键词
			// fieldQuery 这个必须是你的索引字段哦,不然查不到数据,这里我只设置两个字段 id ,title
			// searchRequestBuilder.setQuery(QueryBuilders.fuzzyQuery(name,keyWord).boost(0.1f));
			// searchRequestBuilder.setQuery(QueryBuilders.matchQuery("name",
			// q));
			// searchRequestBuilder.setQuery(QueryBuilders.queryString(keyWord).boost(0.1f));

			// 查询过滤器过滤价格在4000-5000内
			// 这里范围为[4000,5000]区间闭包含,搜索结果包含价格为4000和价格为5000的数据
			// searchRequestBuilder.setFilter(FilterBuilders.rangeFilter("price")
			// .from(4000).to(5000));

			/** 多条件 **/
			// BoolQueryBuilder qb = QueryBuilders.boolQuery()
			// .must(new QueryStringQueryBuilder("北京").field("body"))
			// .should(new QueryStringQueryBuilder("太多").field("body"));
			// searchRequestBuilder.setQuery(qb);

			// SearchResponse response = builder.execute().actionGet();
			// System.out.println(response.getHits().getTotalHits());

			QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keyWord);

			// analyzer为ik分词,即输入的keywords通过ik进行分词。
			queryBuilder.analyzer("ik").field(name);

			searchRequestBuilder.setQuery(queryBuilder);

			// 设置查询数据的位置,分页用吧
			searchRequestBuilder.setFrom((nowPage - 1) * pageSize);
			// 设置查询结果集的最大条数
			searchRequestBuilder.setSize(pageSize);
			// 设置是否按查询匹配度排序
			searchRequestBuilder.setExplain(true);

			// 高亮字段
			for (String field : highFields) {
				searchRequestBuilder.addHighlightedField(field);
			}

			/*
			 * searchRequestBuilder .setHighlighterPreTags(
			 * "<font style='color:red'>");
			 * searchRequestBuilder.setHighlighterPostTags("</font>");
			 */

			// 最后就是返回搜索响应信息
			response = searchRequestBuilder.execute().actionGet();
			// try {
			// //防止出现：远程主机强迫关闭了一个现有的连接
			// Thread.sleep(20);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
		}
		return response;
	}

	/**
	 * 
	 * @Title: moreLikeThis
	 * @Description: 类似记录匹配
	 * @param indexname
	 * @param type
	 * @return SearchResponse
	 * 
	 * @date 2014-3-19 下午4:48:27
	 */
	public static SearchResponse moreLikeThis(String indexname, String type) {
		SearchResponse searchResponse = null;
		if (client != null) {
			MoreLikeThisRequestBuilder moreLikeThis = new MoreLikeThisRequestBuilder(client, indexname, type,
					"O5rg4PahSry1NSYBqLk--Q");
			searchResponse = moreLikeThis.setField("title", "content").setSearchFrom(0).setSearchSize(5).execute()
					.actionGet();

			// searchResponse = client
			// .prepareMoreLikeThis("anynote", "post", "id")
			// .setField("title", "content")
			// .setSearchFrom(0).setSearchSize(10).execute().actionGet();
			if (searchResponse != null) {
				long total = searchResponse.getHits().totalHits();
				System.out.println("命中总数：" + total);
			}

			// Float usetime = searchResponse.getTookInMillis()/1000f;
			// // 命中记录数
			// Long hits = searchResponse.getHits().totalHits();
			// for (SearchHit hit : searchResponse.getHits()) {
			//
			// // 打分
			// Float score = hit.getScore();
			// // 文章id
			// Integer id =
			// Integer.parseInt(hit.getSource().get("id").toString());
			//
			// String title = hit.getSource().get("title").toString();
			//
			// String content = hit.getSource().get("content").toString();
			// // 文章更新时间
			// Long updatetime =
			// Long.parseLong(hit.getSource().get("updatetime").toString());
			// }
		}
		return searchResponse;
	}

	/**
	 * 
	 * @Title: pinYinSearcher
	 * @Description: 拼音关键字检索
	 * @return
	 * @throws IOException
	 *             AnalyzeResponse
	 * 
	 * @date 2014-6-4 下午2:47:54
	 */
	public static AnalyzeResponse pinYinSearcher() throws IOException {
		AnalyzeResponse analyzeResponse = null;
		if (client != null) {
			// mmseg
			analyzeResponse = client.admin().indices().prepareAnalyze("anynote", "中国").setAnalyzer("pinyin").execute()
					.actionGet();
			// logger.info("size:{}", analyzeResponse.getTokens().size());
			List<AnalyzeToken> list = analyzeResponse.getTokens();
			for (AnalyzeToken token : list) {
				System.out.println("拼音搜索：" + token.getTerm());
			}
		}
		return analyzeResponse;
	}

	/**
	 * 
	 * @Title: get
	 * @Description: 获取单条记录
	 * @param indexname
	 * @param type
	 * @param id
	 * @return String
	 * 
	 * @date 2013-12-29 下午2:32:33
	 */
	public static boolean get(String indexname, String type, String id) {
		boolean result = false;
		try {
			if (client != null) {
				// 在这里创建我们要索引的对象
				GetResponse response = client.prepareGet(indexname, type, id).execute().actionGet();
				if (Integer.parseInt(response.getId()) > 0 && null != response.getSource()) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 更新索引记录
	 * @param request
	 * @return String
	 * 
	 * @date 2014-3-18 上午11:15:10
	 */
	public static String update(UpdateRequest request) {
		if (client != null) {
			// 在这里创建我们要索引的对象
			client.update(request);
		}
		return null;
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 根据记录ID删除
	 * @param indexname
	 * @param type
	 * @param id
	 * @return Map<String,Object>
	 * 
	 * @date 2013-12-29 下午2:39:56
	 */
	public static Map<String, Object> delete(String indexname, String type, String id) {
		if (client != null) {
			// 在这里创建我们要索引的对象
			DeleteResponse response = client.prepareDelete(indexname, type, id).execute().actionGet();
			return response.getHeaders();
		}
		return null;
	}

	/**
	 * 
	 * @Title: deleteIndexByQuery
	 * @Description: 删除指定类型下所有索引
	 * @param indexname
	 * @param type
	 *            void
	 * 
	 * @date 2013-12-29 下午2:46:26
	 */
	public static void deleteIndexByQuery(String indexname, String type) {
		if (client != null) {
			MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();// 查询所有的documents
			// 现在把blog索引post类型的索引全部删除,由于用了QueryBuilders.matchAllQuery(),匹配所有blog
			// post下的索引
			/*
			 * client.prepareDeleteByQuery(indexname).setQuery(allQueryBuilder)
			 * .setTypes(type).execute().actionGet();
			 */

			client.prepareDeleteByQuery(indexname).setQuery(allQueryBuilder).execute().actionGet();
		}
	}

	/**
	 * 
	 * @Title: findSuggestions
	 * @Description: 关键字搜索提示
	 * @param indexName
	 * @param field
	 * @param keyword
	 * @param size
	 * @param indices
	 * @return
	 * @throws Exception
	 *             List<String>
	 * 
	 * @date 2014-3-28 下午1:42:31
	 */
	public static List<String> findSuggestions(String indexName, String field, String keyword, Integer size,
			float indices) throws Exception {
		List<String> suggests = null;
		if (client != null) {
			// suggests = new SuggestRequestBuilder(client).setIndices(indices)
			// .field(field).term(keyword).size(size).similarity(0.5f).execute().actionGet()
			// .getSuggestions();
			SuggestRequest request = new SuggestRequest(indexName);
			request.term(keyword);
			request.field(field);
			request.size(size);
			request.similarity(indices);
			// request.analyzer("standard");
			// SuggestResponse response = client.execute(SuggestAction.INSTANCE,
			// request).actionGet();
			suggests = client.execute(SuggestAction.INSTANCE, request).actionGet().getSuggestions();
			log.info("模糊匹配：" + suggests);
		}
		return suggests;
	}

	/**
	 * 
	 * @Title: getHighlightFields
	 * @Description: 获取带有关键字高亮的内容信息
	 * @param hit
	 *            :记录行
	 * @param field
	 *            :字段
	 * @return String
	 * 
	 * @date 2014-3-18 上午11:17:09
	 */
	public static String getHighlightFields(SearchHit hit, String field) {
		String content = "";
		if (hit != null) {
			Map<String, HighlightField> result = hit.highlightFields();
			HighlightField contentField = result.get(field);
			if (contentField != null) {
				Text[] contentTexts = contentField.fragments();
				for (Text text : contentTexts) {
					content = text.toString();
				}
			} else {
				content = (String) hit.getSource().get(field);
			}
		}
		return content;
	}

	/**
	 * 
	 * @return:索引settings
	 * @throws Exception
	 */
	public static String getAnalysisSettings() throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject()
				// 主分片数量
				.field("number_of_shards", 5).field("number_of_replicas", 0).startObject("analysis")
				.startObject("filter")
				// 创建分词过滤器
				.startObject("pynGram").field("type", "nGram")
				// 从1开始
				.field("min_gram", 1).field("max_gram", 15).endObject().endObject()

				.startObject("analyzer")
				// 拼音analyszer
				.startObject("pyAnalyzer").field("type", "custom").field("tokenizer", "standard")
				.field("filter", new String[] { "lowercase", "pynGram" }).endObject().endObject().endObject()
				.endObject();
		System.out.println(mapping.string());
		return mapping.string();
	}

	public static void main(String[] args) {
		get("indexresidential", "typeresidential", "10");
	}

}
