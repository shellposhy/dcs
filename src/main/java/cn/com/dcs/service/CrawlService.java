package cn.com.dcs.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.management.JMException;

import org.springframework.stereotype.Service;

import cn.com.dcs.base.AppConfig;
import cn.com.dcs.crawl.monitor.CrawlMonitor;
import cn.com.dcs.crawl.pipeline.TextFilePipeline;
import cn.com.dcs.crawl.processor.CrawlPageProcessor;
import cn.com.dcs.model.CrawlContent;
import cn.com.dcs.model.CrawlUnit;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

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
	private AppConfig appConfig;
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
	 * 爬虫开始抓取页面
	 */
	public void start() {
		List<CrawlUnit> siteList = unitService.findAll();
		if (null != siteList && siteList.size() > 0) {
			for (CrawlUnit webSite : siteList) {
				Site site = Site.me().setDomain(webSite.getDomain()).setRetryTimes(1);
				List<CrawlContent> siteFields = contentService.findByUnitId(webSite.getId());
				CrawlPageProcessor processor = new CrawlPageProcessor(site, webSite, siteFields);
				Spider spider = Spider.create(processor).addUrl(webSite.getStartUrl())
						.addPipeline(new TextFilePipeline("d:\\dcs\\txt")).thread(5);
				try {
					monitor.register(spider);
				} catch (JMException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 爬虫监控
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
