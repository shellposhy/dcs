package cn.com.dcs.model;

import cn.com.dcs.framework.base.BaseEntity;
import cn.com.dcs.framework.base.constant.ECrawlStatus;

/**
 * 抓取内容对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CrawlContent extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer linkID;
	private String name;
	private String code;
	private String formula;
	private ECrawlStatus status;
	private String savePath;

	public Integer getLinkID() {
		return linkID;
	}

	public void setLinkID(Integer linkID) {
		this.linkID = linkID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public ECrawlStatus getStatus() {
		return status;
	}

	public void setStatus(ECrawlStatus status) {
		this.status = status;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
