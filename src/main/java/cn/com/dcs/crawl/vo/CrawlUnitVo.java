package cn.com.dcs.crawl.vo;

import cn.com.dcs.model.CrawlUnit;
import cn.com.people.data.util.DateTimeUtil;

/**
 * 抓取站点VO对象
 * 
 * @author shishb
 * @version 1.0
 */
public class CrawlUnitVo {
	private Integer id;
	private String name;
	private String domain;
	private String charset;
	private String status;
	private Integer times;
	private Integer intervalTime;
	private String createTime;
	private String updateTime;

	/**
	 * 对象转化
	 * 
	 * @param site
	 * @return
	 */
	public static CrawlUnitVo staticVo(CrawlUnit site) {
		CrawlUnitVo vo = new CrawlUnitVo();
		vo.setId(site.getId());
		vo.setName(site.getName());
		vo.setDomain(site.getDomain());
		vo.setCharset(site.getCharset());
		vo.setStatus(site.getStatus().getTitle());
		vo.setTimes(site.getTimes());
		vo.setIntervalTime(site.getIntervalTime());
		vo.setCreateTime(DateTimeUtil.format(site.getCreateTime(), "yyyy-MM-dd"));
		vo.setUpdateTime(DateTimeUtil.format(site.getUpdateTime(), "yyyy-MM-dd"));
		return vo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
