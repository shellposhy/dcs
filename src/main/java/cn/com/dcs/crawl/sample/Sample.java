package cn.com.dcs.crawl.sample;

import javax.management.JMException;

import cn.com.dcs.crawl.constant.PageField;
import cn.com.dcs.crawl.pipeline.TextFilePipeline;
import cn.com.dcs.crawl.util.PageUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

public class Sample implements PageProcessor {

	private Site site = Site.me().setDomain("sina.com.cn").setRetryTimes(1).setSleepTime(1000).setTimeOut(5000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		site.setCharset(PageUtil.pageDefaultCharset(page.getHtml()));
		page.putField(PageField.TITLE, page.getHtml().xpath("//title"));
		page.putField(PageField.KEYWORD, page.getHtml().xpath("//meta[@name=keywords]"));
		page.putField(PageField.TAGS, page.getHtml().xpath("//meta[@name=tags]"));
		page.putField(PageField.SUMMARY, page.getHtml().xpath("//meta[@name=description]"));
		String content = page.getHtml().css("div.article-a__content").get();
		if (null == content || "".equals(content)) {
			content = page.getHtml().css("div.article").get();
		}
		page.putField(PageField.CONTENT, content);
		page.addTargetRequests(page.getHtml().links().regex("([a-zA-z]+://*.+news.sina.com.cn+[^\\s]*)").all());
	}

	public static void main(String[] args) throws JMException {
		Spider sina = Spider.create(new Sample()).addUrl("http://news.sina.com.cn")
				.addPipeline(new TextFilePipeline("d:\\dcs\\txt")).thread(5);
		SpiderMonitor.instance().register(sina);
		sina.run();
	}

}
