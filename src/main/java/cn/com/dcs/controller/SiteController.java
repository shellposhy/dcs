package cn.com.dcs.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.com.dcs.base.AppConfig;
import cn.com.dcs.base.BaseController;
import cn.com.dcs.base.ControllerOperator;
import cn.com.dcs.base.common.BaseMappingJsonView;
import cn.com.dcs.base.common.DataTablesVo;
import cn.com.dcs.base.common.JsonPara;
import cn.com.dcs.base.common.SystemConstant;
import cn.com.dcs.framework.base.Result;
import cn.com.dcs.framework.base.constant.ECrawlStatus;
import cn.com.dcs.model.CrawlUnit;
import cn.com.dcs.service.CrawlUnitService;
import cn.com.dcs.vo.CrawlUnitVo;

/**
 * 站点管理控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/site")
public class SiteController extends BaseController {
	@Resource
	private CrawlUnitService crawlUnitService;
	@Resource
	private AppConfig appConfig;

	@RequestMapping
	public String list() {
		return "/admin/view/site/list";
	}

	@RequestMapping("/s")
	public MappingJacksonJsonView search(@RequestBody JsonPara[] jsonParas) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		Map<String, String> jsons = JsonPara.getParaMap(jsonParas);
		int sEcho = Integer.parseInt(jsons.get(JsonPara.DataTablesParaNames.sEcho));
		Integer firstSize = Integer.parseInt(jsons.get(JsonPara.DataTablesParaNames.iDisplayStart));
		String name = jsons.get(JsonPara.DataTablesParaNames.sSearch);
		DataTablesVo<CrawlUnitVo> dataTablesVo = null;
		Result<CrawlUnit> result = crawlUnitService.findResultByName(Strings.isNullOrEmpty(name) ? null : name,
				firstSize, appConfig.getAdminDataTablePageMinSize());
		List<CrawlUnitVo> list = Lists.newArrayList();
		if (null != result && null != result.getList() && result.getList().size() > 0) {
			for (CrawlUnit site : result.getList()) {
				list.add(CrawlUnitVo.staticVo(site));
			}
		}
		if (null == result) {
			dataTablesVo = new DataTablesVo<CrawlUnitVo>(sEcho, 0, 0, list);
		} else {
			dataTablesVo = new DataTablesVo<CrawlUnitVo>(sEcho, result.getTotalCount(), result.getTotalCount(), list);
		}
		mv.addStaticAttribute("dataTablesVo", dataTablesVo);
		return mv;
	}

	@RequestMapping("/new")
	public String preNew(Model model) {
		model.addAttribute("crawlUnit", new CrawlUnit());
		return "/admin/view/site/edit";
	}

	@RequestMapping("/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {
		CrawlUnit site = crawlUnitService.find(id);
		model.addAttribute("crawlUnit", site);
		return "/admin/view/site/edit";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody JsonPara jsonPara) {
		String[] idStr = jsonPara.value.split(SystemConstant.COMMA_SEPARATOR);
		for (int i = 0; i < idStr.length; i++) {
			crawlUnitService.delete(Integer.parseInt(idStr[i]));
		}
		return "/admin/view/site/list";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid final CrawlUnit site, BindingResult result, final Model model,
			final HttpServletRequest request) {
		return super.save(site, result, model, new ControllerOperator() {

			public void operate() {
				if (null != site.getId() && site.getId().intValue() > 0) {
					site.setUpdateTime(new Date());
					crawlUnitService.update(site);
				} else {
					site.setStatus(ECrawlStatus.UnStart);
					site.setTimes(0);
					site.setIntervalTime(SystemConstant.CRAWL_INTERVALTIME);
					site.setCreateTime(new Date());
					site.setUpdateTime(new Date());
					crawlUnitService.insert(site);
				}
			}

			public void onFailure() {
			}

			public String getSuccessView() {
				return "redirect:/admin/site";
			}

			public String getFailureView() {
				if (null != site.getId()) {
					return "redirect:/admin/site/" + site.getId() + "/edit";
				}
				return "redirect:/admin/site/new";
			}
		});
	}
}
