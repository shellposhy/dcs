package cn.com.dcs.framework.base.constant;

/**
 * 抓取内容枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum EPageContentType {
	TITLE("标题"), KEYWORD("关键词"), TAGS("标签"), SUMMARY("摘要/描述"), CONTENT("内容");

	private String title;

	public static EPageContentType valueof(int index) {
		return EPageContentType.values()[index];
	}

	private EPageContentType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
