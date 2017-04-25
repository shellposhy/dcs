package cn.com.dcs.crawl.constant;

import cn.com.dcs.framework.base.constant.EPageContentType;

/**
 * 数据抓取时，提取页面元素Key值列表
 * 
 * @author shishb
 * @version 1.0
 */
public class PageField {
	public final static String TITLE = "title";
	public final static String KEYWORD = "keyword";
	public final static String TAGS = "tags";
	public final static String SUMMARY = "summary";
	public final static String CONTENT = "content";

	public static String getContentFieldName(EPageContentType contentType) {
		String fieldName = "";
		switch (contentType) {
		case TITLE:
			fieldName = TITLE;
			break;
		case KEYWORD:
			fieldName = KEYWORD;
			break;
		case TAGS:
			fieldName = TAGS;
			break;
		case SUMMARY:
			fieldName = SUMMARY;
			break;
		case CONTENT:
			fieldName = CONTENT;
			break;
		default:
			break;
		}
		return fieldName;
	}
}
