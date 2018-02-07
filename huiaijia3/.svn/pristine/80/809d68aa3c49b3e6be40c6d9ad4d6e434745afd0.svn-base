package com.flf.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.flf.entity.Page;
import com.flf.util.ReflectHelper;
import com.flf.util.Tools;

/**
 * 后台分页插件
 * 
 * @author SevenWong<br>
 * @date 2016年7月28日下午5:59:00
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {

	// 数据库方言
	private static String dialect = "";
	
	// mapper.xml中需要拦截的ID(正则匹配)
	private static String pageSqlId = "";

	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler,
					"delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate,
					"mappedStatement");

			if (mappedStatement.getId().matches(pageSqlId)) {
				// 拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				
				// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				Object parameterObject = boundSql.getParameterObject();
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject尚未实例化！");
				} else {
					String sql = boundSql.getSql();
					Page page = null;
					if (parameterObject instanceof Page) {
						// 参数就是Page实体
						page = (Page) parameterObject;
						page.setEntityOrField(true);
					} else {
						// 参数为某个实体，该实体拥有Page属性
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject, "page");
						if (pageField != null) {
							page = (Page) ReflectHelper.getValueByFieldName(parameterObject, "page");
							if (page == null)
								page = new Page();
							page.setEntityOrField(false);
							
							// 通过反射，对实体对象设置分页对象
							ReflectHelper.setValueByFieldName(parameterObject, "page", page);
						} else {
							throw new NoSuchFieldException(parameterObject.getClass().getName() + "不存在 page 属性！");
						}
					}
					String pageSql = generatePageSql(sql, page);
					
					// 将分页sql语句反射回BoundSql.
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
				}
			}
		}
		return ivk.proceed();
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePageSql(String sql, Page page) {
		if (page != null && Tools.notEmpty(dialect)) {
			StringBuffer pageSql = new StringBuffer();
			if ("mysql".equals(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " + page.getCurrentResult() + "," + page.getShowCount());
			} else if ("oracle".equals(dialect)) {
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") as tmp_tb where ROWNUM<=");
				pageSql.append(page.getCurrentResult() + page.getShowCount());
				pageSql.append(") where row_id>");
				pageSql.append(page.getCurrentResult());
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (Tools.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (Tools.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

}
