package cn.com.dcs.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.common.collect.Lists;

import cn.com.dcs.base.AppConfig;
import cn.com.dcs.base.BaseController;
import cn.com.dcs.base.ControllerOperator;
import cn.com.dcs.base.common.BaseMappingJsonView;
import cn.com.dcs.base.common.DataTablesVo;
import cn.com.dcs.base.common.JsonPara;
import cn.com.dcs.crawl.vo.CrawlContentVo;
import cn.com.dcs.framework.base.Result;
import cn.com.dcs.framework.base.constant.EPageContentType;
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
	private Logger log = Logger.getLogger(SiteContentController.class.getName());

	@Resource
	private CrawlContentService crawlContentService;
	@Resource
	private CrawlUnitService crawlUnitService;
	@Resource
	private AppConfig appConfig;

	@RequestMapping
	public String preList() {
		log.debug("======site.content.pre.list======");
		List<CrawlUnit> result = crawlUnitService.findAll();
		if (null != result && result.size() > 0) {
			return "redirect:/admin/site/content/" + result.get(0).getId() + "/list";
		}
		return "/admin/site/new";
	}

	@RequestMapping("/{siteId}/list")
	public String list(@PathVariable Integer siteId, Model model) {
		log.debug("======site.content.list======");
		model.addAttribute("siteId", siteId);
		return "/admin/view/content/list";
	}

	@RequestMapping("/{siteId}/new")
	public String preNew(@PathVariable Integer siteId, Model model) {
		log.debug("======site.content.new======");
		CrawlContent crawlContent = new CrawlContent();
		crawlContent.setUnitID(siteId);
		model.addAttribute("crawlContent", crawlContent);
		model.addAttribute("contentType", EPageContentType.values());
		return "/admin/view/content/edit";
	}

	@RequestMapping("/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {
		log.debug("======site.content.edit======");
		CrawlContent crawlContent = crawlContentService.find(id);
		model.addAttribute("crawlContent", crawlContent);
		model.addAttribute("contentType", EPageContentType.values());
		return "/admin/view/content/edit";
	}

	@RequestMapping("/{siteId}/s")
	public MappingJacksonJsonView search(@PathVariable Integer siteId, @RequestBody JsonPara[] jsonParas) {
		log.debug("======site.content.search======");
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

	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid final CrawlContent content, BindingResult result, final Model model,
			final HttpServletRequest request) {
		log.debug("======site.content.save======");
		return super.save(content, result, model, new ControllerOperator() {
			public void operate() {
				if (null != content.getId() && content.getId().intValue() > 0) {
					crawlContentService.update(content);
				} else {
					crawlContentService.insert(content);
				}
			}

			public void onFailure() {
			}

			public String getSuccessView() {
				return "redirect:/admin/site/content/" + content.getUnitID() + "/list";
			}

			public String getFailureView() {
				if (null != content.getId()) {
					return "redirect:/admin/site/content/" + content.getId() + "/edit";
				}
				return "redirect:/admin/site/content/" + content.getUnitID() + "/new";
			}
		});
	}
}
