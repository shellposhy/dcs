package cn.com.dcs.model;

import cn.com.dcs.framework.base.BaseEntity;

/**
 * 抓取内容对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CrawlContent extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer unitID;
	private String type;
	private String formula;
	private String memo;

	public Integer getUnitID() {
		return unitID;
	}

	public void setUnitID(Integer unitID) {
		this.unitID = unitID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
