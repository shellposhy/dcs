package cn.com.dcs.model;

import cn.com.dcs.framework.base.BaseEntity;
import cn.com.dcs.framework.base.constant.ECrawlStatus;

/**
 * 抓取站点对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CrawlUnit extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String domain;
	private String charset;
	private ECrawlStatus status;
	private Integer times;
	private String subUrl;
	private Integer intervalTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public ECrawlStatus getStatus() {
		return status;
	}

	public void setStatus(ECrawlStatus status) {
		this.status = status;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getSubUrl() {
		return subUrl;
	}

	public void setSubUrl(String subUrl) {
		this.subUrl = subUrl;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
}
