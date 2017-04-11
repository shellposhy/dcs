package cn.com.dcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.dcs.framework.base.dao.BaseDao;
import cn.com.dcs.model.CrawlLink;

/**
 * 抓取链接Dao
 * 
 * @author shishb
 * @version 1.0
 */
public interface CrawlLinkMapper extends BaseDao<CrawlLink> {

	/**
	 * 获得站点的所有链接
	 * 
	 * @param siteId
	 * @return
	 */
	public List<CrawlLink> findBySiteId(@Param("siteId") Integer siteId);
}
