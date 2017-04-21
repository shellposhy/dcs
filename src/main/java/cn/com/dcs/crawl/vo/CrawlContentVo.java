package cn.com.dcs.crawl.vo;

import cn.com.dcs.model.CrawlContent;

/**
 * 抓取站点内容VO对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CrawlContentVo {
	private Integer id;
	private Integer unitID;
	private String type;
	private String formula;
	private String memo;
	// 扩展属性
	private String unitName;

	/**
	 * 对象转化
	 * 
	 * @param content
	 * @return
	 */
	public static CrawlContentVo staticVo(CrawlContent content, String unitName) {
		CrawlContentVo vo = new CrawlContentVo();
		vo.setId(content.getId());
		vo.setUnitID(content.getUnitID());
		vo.setType(content.getType().getTitle());
		vo.setFormula(content.getFormula());
		vo.setMemo(content.getMemo());
		vo.setUnitName(unitName);
		return vo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
