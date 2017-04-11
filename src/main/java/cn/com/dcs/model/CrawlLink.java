package cn.com.dcs.model;

import cn.com.dcs.framework.base.BaseEntity;
import cn.com.dcs.framework.base.constant.ECrawlStatus;

/**
 * 抓取内容链接对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CrawlLink extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String uuid;
	private Integer unitID;
	private String link;
	private String name;
	private ECrawlStatus status;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getUnitID() {
		return unitID;
	}

	public void setUnitID(Integer unitID) {
		this.unitID = unitID;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ECrawlStatus getStatus() {
		return status;
	}

	public void setStatus(ECrawlStatus status) {
		this.status = status;
	}

}
