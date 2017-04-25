package cn.com.dcs.crawl.model;

import java.util.List;

import com.google.common.base.Strings;

import cn.com.dcs.crawl.constant.PageField;
import cn.com.dcs.crawl.util.PageUtil;
import cn.com.dcs.framework.base.constant.EPageContentType;
import cn.com.dcs.model.CrawlContent;
import cn.com.dcs.model.CrawlUnit;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 通用爬虫页面处理类
 */
public class CrawlPageProcessor implements PageProcessor {
	// attribute definition
	private Site site;
	private CrawlUnit unit;
	private List<CrawlContent> siteFields;

	// constructor
	public CrawlPageProcessor(Site site, CrawlUnit unit, List<CrawlContent> siteFields) {
		this.site = site;
		this.unit = unit;
		this.siteFields = siteFields;
	}

	/**
	 * 爬虫数据抓取处理方法
	 * 
	 * @param page
	 * @return
	 */
	public void process(Page page) {
		if (null != siteFields && siteFields.size() > 0) {
			// Encoding Settings
			site.setCharset(PageUtil.pageDefaultCharset(page.getHtml()));
			for (EPageContentType type : EPageContentType.values()) {
				List<CrawlContent> result = CrawlContent.staticContentList(siteFields, type);
				if (null != result && result.size() > 0) {
					// Only one formula
					if (result.size() == 1) {
						page.putField(PageField.getContentFieldName(result.get(0).getType()),
								getPageSelectable(result.get(0), page));
					}
					// multiple formulas
					else {
						for (CrawlContent content : result) {
							String value = getPageSelectable(content, page).get();
							if (!Strings.isNullOrEmpty(value)) {
								page.putField(PageField.getContentFieldName(result.get(0).getType()),
										getPageSelectable(content, page));
								break;
							}
						}
					}
				}
			}
			// Get the current page all other url
			page.addTargetRequests(page.getHtml().links().regex(unit.getSubUrl()).all());
		}
	}

	/**
	 * 获取抓取的内容值
	 * 
	 * @param content
	 * @param page
	 * @return
	 */
	public Selectable getPageSelectable(CrawlContent content, Page page) {
		Selectable selectable = null;
		switch (content.getFormulaType()) {
		case XPath:
			selectable = page.getHtml().xpath(content.getFormula());
			break;
		case Css:
			selectable = page.getHtml().css(content.getFormula());
			break;
		case Regex:
			selectable = page.getHtml().regex(content.getFormula());
			break;
		default:
			break;
		}
		return selectable;
	}

	// getter and setter
	public Site getSite() {
		return site;
	}

	public CrawlUnit getUnit() {
		return unit;
	}

	public List<CrawlContent> getSiteFields() {
		return siteFields;
	}

}
