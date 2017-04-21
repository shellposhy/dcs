package cn.com.dcs.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.common.collect.Lists;

import cn.com.dcs.base.AppConfig;
import cn.com.dcs.base.BaseController;
import cn.com.dcs.base.common.BaseMappingJsonView;
import cn.com.dcs.base.common.DataTablesVo;
import cn.com.dcs.base.common.JsonPara;
import cn.com.dcs.crawl.vo.CrawlContentVo;
import cn.com.dcs.framework.base.Result;
import cn.com.dcs.model.CrawlContent;
import cn.com.dcs.model.CrawlUnit;
import cn.com.dcs.service.CrawlContentService;
import cn.com.dcs.service.CrawlUnitService;

/**
 * 站点内容配置管理控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/site/content")
public class SiteContentController extends BaseController {

	@Resource
	private CrawlContentService crawlContentService;
	@Resource
	private CrawlUnitService crawlUnitService;
	@Resource
	private AppConfig appConfig;

	@RequestMapping("/{siteId}/list")
	public String list(@PathVariable Integer siteId, Model model) {
		model.addAttribute("siteId", siteId);
		return "/admin/view/content/list";
	}

	@RequestMapping("/{siteId}/s")
	public MappingJacksonJsonView search(@PathVariable Integer siteId, @RequestBody JsonPara[] jsonParas) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> jsons = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(jsons.get(JsonPara.DataTablesParaNames.sEcho));
		Integer firstSize = Integer.parseInt(jsons.get(JsonPara.DataTablesParaNames.iDisplayStart));
		DataTablesVo<CrawlContentVo> dataTablesVo = null;
		CrawlUnit site = crawlUnitService.find(siteId);
		Result<CrawlContent> result = crawlContentService.resultByUnitId(siteId, firstSize,
				appConfig.getAdminDataTablePageMinSize());
		List<CrawlContentVo> list = Lists.newArrayList();
		if (null != result && null != result.getList() && result.getList().size() > 0) {
			for (CrawlContent content : result.getList()) {
				list.add(CrawlContentVo.staticVo(content, null != site ? site.getName() : ""));
			}
		}
		if (null == result) {
			dataTablesVo = new DataTablesVo<CrawlContentVo>(sEcho, 0, 0, list);
		} else {
			dataTablesVo = new DataTablesVo<CrawlContentVo>(sEcho, result.getTotalCount(), result.getTotalCount(),
					list);
		}
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}
}
