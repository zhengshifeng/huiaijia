package com.flf.entity;


/**
 * 后台分页
 * 
 * @author SevenWong<br>
 * @date 2016年7月28日下午5:58:19
 */
public class Page {
	private static StringBuffer sb = null;

	private int showCount = 15; // 每页显示记录数
	private int currentPage; // 当前页
	private int currentResult; // 当前记录起始索引
	private boolean entityOrField; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private String pageStr; // 最终页面显示的底部翻页导航，详细见：getPageStr();

	public int getCurrentPage() {
		if (currentPage <= 0)
			currentPage = 1;
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageStr() {
		sb = new StringBuffer();
		sb.append("\n<ul>\n");
		sb.append("    <li><a href=\"javascript:;\" onclick=\"nextPage(1)\">首页</a></li>\n");
		sb.append("    <li><a href=\"javascript:;\" onclick=\"nextPage(");
		sb.append(currentPage - 1);
		sb.append(")\">上页</a></li>\n");
		sb.append("    <li><a href=\"javascript:;\" onclick=\"nextPage(");
		sb.append(currentPage + 1);
		sb.append(")\">下页</a></li>\n");
		sb.append("    <li class=\"pageinfo\">第");
		sb.append(currentPage);
		sb.append("页</li>\n");
		sb.append("</ul>\n");
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("function nextPage(page){\n");
		sb.append("    if (true && document.forms[0]) {\n");
		sb.append("        var url = document.forms[0].getAttribute(\"action\");\n");
		sb.append("        if (url.indexOf('?')>-1) {\n");
		sb.append("            url += \"&");
		sb.append(entityOrField ? "currentPage" : "page.currentPage");
		sb.append("=\";\n");
		sb.append("        } else {\n");
		sb.append("            url += \"?");
		sb.append(entityOrField ? "currentPage" : "page.currentPage");
		sb.append("=\";\n");
		sb.append("        }\n");
		sb.append("        document.forms[0].action = url+page;\n");
		sb.append("        document.forms[0].submit();\n");
		sb.append("    } else {\n");
		sb.append("        var url = document.location+'';\n");
		sb.append("        if (url.indexOf('?')>-1) {\n");
		sb.append("            if (url.indexOf('currentPage')>-1) {\n");
		sb.append("                var reg = /currentPage=\\d*/g;\n");
		sb.append("                url = url.replace(reg,'currentPage=');\n");
		sb.append("            } else {\n");
		sb.append("                url += \"&");
		sb.append(entityOrField ? "currentPage" : "page.currentPage");
		sb.append("=\";\n");
		sb.append("            }\n");
		sb.append("        } else {\n");
		sb.append("            url += \"?");
		sb.append(entityOrField ? "currentPage" : "page.currentPage");
		sb.append("=\";\n");
		sb.append("        }\n");
		sb.append("        document.location = url + page;\n");
		sb.append("    }\n");
		sb.append("}\n");
		sb.append("</script>\n");
		pageStr = sb.toString();
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getCurrentResult() {
		currentResult = (getCurrentPage() - 1) * getShowCount();
		if (currentResult < 0)
			currentResult = 0;
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

}
