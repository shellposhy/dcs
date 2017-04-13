package cn.com.dcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.dcs.dao.CrawlUnitMapper;
import cn.com.dcs.framework.base.Result;
import cn.com.dcs.model.CrawlUnit;

@Service
public class CrawlUnitService {
	@Resource
	private CrawlUnitMapper crawlUnitMapper;

	/**
	 * 分页查询站点
	 * 
	 * @param name
	 * @param firstSize
	 * @param size
	 * @return
	 */
	public Result<CrawlUnit> findResultByName(String name, Integer firstSize, Integer size) {
		Result<CrawlUnit> result = new Result<CrawlUnit>();
		result.setList(findByName(name, firstSize, size));
		result.setTotalCount(countByName(name));
		return result;
	}

	/**
	 * 分页查询站点
	 * 
	 * @param name
	 * @param firstSize
	 * @param size
	 * @return
	 */
	public List<CrawlUnit> findByName(String name, Integer firstSize, Integer size) {
		return crawlUnitMapper.findByName(name, firstSize, size);
	}

	/**
	 * 统计站点数据
	 * 
	 * @param name
	 * @return
	 */
	public int countByName(String name) {
		return crawlUnitMapper.countByName(name);
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	public List<CrawlUnit> findAll() {
		return crawlUnitMapper.findAll();
	}

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 */
	public CrawlUnit find(Integer id) {
		return crawlUnitMapper.find(id);
	}

	/**
	 * 新增数据
	 * 
	 * @param site
	 * @return
	 */
	public void insert(CrawlUnit site) {
		crawlUnitMapper.insert(site);
	}

	/**
	 * 更新数据
	 * 
	 * @param site
	 * @return
	 */
	public void update(CrawlUnit site) {
		crawlUnitMapper.update(site);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return
	 */
	public void delete(Integer id) {
		crawlUnitMapper.delete(id);
	}
}
