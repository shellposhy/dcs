package cn.com.dcs.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.dcs.crawl.monitor.CrawlMonitor;

/**
 * 爬虫服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class CrawlService {

	private static CrawlMonitor monitor = null;

	@Resource
	private CrawlUnitService unitService;
	@Resource
	private CrawlContentService contentService;

	/**
	 * 初始化监控类
	 * 
	 * @return
	 */
	@PostConstruct
	public synchronized CrawlMonitor init() {
		if (monitor == null) {
			monitor = CrawlMonitor.instance();
		}
		return monitor;
	}

	/**
	 * 监控爬虫，并返回爬虫状态
	 * 
	 * @return
	 */
	public void monitor() {

	}

	/**
	 * 获得爬虫监控对象
	 * 
	 * @return
	 */
	public static CrawlMonitor getMonitor() {
		return monitor;
	}

}
