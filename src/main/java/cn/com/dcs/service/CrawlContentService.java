package cn.com.dcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.dcs.dao.CrawlContentMapper;
import cn.com.dcs.framework.base.Result;
import cn.com.dcs.model.CrawlContent;

@Service
public class CrawlContentService {
	@Resource
	private CrawlContentMapper crawlContentMapper;

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	public List<CrawlContent> findAll() {
		return crawlContentMapper.findAll();
	}

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 */
	public CrawlContent find(Integer id) {
		return crawlContentMapper.find(id);
	}

	/**
	 * 新增数据
	 * 
	 * @param site
	 * @return
	 */
	public void insert(CrawlContent content) {
		crawlContentMapper.insert(content);
	}

	/**
	 * 更新数据
	 * 
	 * @param site
	 * @return
	 */
	public void update(CrawlContent content) {
		crawlContentMapper.update(content);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return
	 */
	public void delete(Integer id) {
		crawlContentMapper.delete(id);
	}

	/**
	 * 分页查询数据组装
	 * 
	 * @param unitId
	 * @param firstSize
	 * @param size
	 * @return
	 */
	public Result<CrawlContent> resultByUnitId(Integer unitId, Integer firstSize, Integer size) {
		Result<CrawlContent> result = new Result<CrawlContent>();
		result.setList(findByUnitId(unitId, firstSize, size));
		result.setTotalCount(countByUnitId(unitId));
		return result;
	}

	/**
	 * 根据站点查询抓取内容
	 * 
	 * @param siteId
	 * @return
	 */
	public List<CrawlContent> findByUnitId(Integer unitId) {
		return crawlContentMapper.findByUnitId(unitId);
	}

	/**
	 * 分页查询站点
	 * 
	 * @param name
	 * @param firstSize
	 * @param size
	 * @return
	 */
	public List<CrawlContent> findByUnitId(Integer unitId, Integer firstSize, Integer size) {
		return crawlContentMapper.findPageByUnitId(unitId, firstSize, size);
	}

	/**
	 * 统计站点数据
	 * 
	 * @param name
	 * @return
	 */
	public int countByUnitId(Integer unitId) {
		return crawlContentMapper.countByUnitId(unitId);
	}
}
