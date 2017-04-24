package cn.com.dcs.model;

import cn.com.dcs.framework.base.BaseEntity;
import cn.com.dcs.framework.base.constant.EFormulaType;
import cn.com.dcs.framework.base.constant.EPageContentType;

/**
 * 抓取内容对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CrawlContent extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer unitID;
	private EPageContentType type;
	private EFormulaType formulaType;
	private String formula;
	private String memo;

	public Integer getUnitID() {
		return unitID;
	}

	public void setUnitID(Integer unitID) {
		this.unitID = unitID;
	}

	public EPageContentType getType() {
		return type;
	}

	public void setType(EPageContentType type) {
		this.type = type;
	}

	public EFormulaType getFormulaType() {
		return formulaType;
	}

	public void setFormulaType(EFormulaType formulaType) {
		this.formulaType = formulaType;
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
