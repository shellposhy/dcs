package cn.com.dcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.dcs.framework.base.dao.BaseDao;
import cn.com.dcs.model.CrawlContent;

/**
 * 抓取内容Dao
 * 
 * @author shishb
 * @version 1.0
 */
public interface CrawlContentMapper extends BaseDao<CrawlContent> {

	/**
	 * 根据站点查询抓取内容
	 * 
	 * @param siteId
	 * @return
	 */
	public List<CrawlContent> findBySiteId(@Param("siteId") Integer siteId);
}
