package cn.com.dcs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.dcs.framework.base.dao.BaseDao;
import cn.com.dcs.model.CrawlUnit;

/**
 * 抓取站点Dao
 * 
 * @author shishb
 * @version 1.0
 */
public interface CrawlUnitMapper extends BaseDao<CrawlUnit> {

	/**
	 * 分页查询站点
	 * 
	 * @param name
	 * @param firstSize
	 * @param size
	 * @return
	 */
	public List<CrawlUnit> findByName(@Param("name") String name, @Param("firstSize") Integer firstSize,
			@Param("size") Integer size);

	/**
	 * 统计站点数据
	 * 
	 * @param name
	 * @return
	 */
	int countByName(@Param("name") String name);
}
