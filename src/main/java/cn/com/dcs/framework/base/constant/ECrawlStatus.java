package cn.com.dcs.framework.base.constant;

/**
 * 抓取状态枚举
 * 
 * @author shishb
 * @version 1.0
 */
public enum ECrawlStatus {
	UnStart("未开始"), Starting("抓取中"), Error("异常"), Finish("完成");
	private String title;

	public static ECrawlStatus valueOf(int index) {
		return ECrawlStatus.values()[index];
	}

	private ECrawlStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
