package cn.com.dcs.util;

import cn.com.dcs.crawl.pipeline.TextFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class CrawlTest implements PageProcessor {

	private Site site = Site.me().setDomain("sina.com.cn").setCharset("utf-8").setRetryTimes(1).setSleepTime(1000)
			.setTimeOut(5000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		page.putField("title", page.getHtml().xpath("//title"));
		page.putField("keywords", page.getHtml().xpath("//meta[@name='keywords']/tidyText()"));
		page.addTargetRequests(page.getHtml().links().regex("([a-zA-z]+://*.+news.sina.com.cn+[^\\s]*)").all());
	}

	public static void main(String[] args) {
		Spider.create(new CrawlTest()).addUrl("http://news.sina.com.cn")
				.addPipeline(new TextFilePipeline("d:\\dcs\\txt")).thread(5).run();
	}

}
