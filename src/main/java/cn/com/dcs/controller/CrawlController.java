package cn.com.dcs.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.com.dcs.base.BaseController;
import cn.com.dcs.base.common.BaseMappingJsonView;
import cn.com.dcs.service.CrawlService;

/**
 * 抓取控制类
 * 
 * @author shishb
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/crawl")
public class CrawlController extends BaseController {

	@Resource
	private CrawlService crawlService;

	/**
	 * 开启爬虫程序
	 * 
	 * @param jsonParas
	 */
	// @RequestMapping(value = "/start", method = RequestMethod.POST)
	@RequestMapping("/{id}/start")
	public MappingJacksonJsonView start(@PathVariable Integer id) {
		MappingJacksonJsonView mv = new BaseMappingJsonView();
		crawlService.start(id);
		mv.addStaticAttribute("result", HttpStatus.OK.value());
		return mv;
	}
}
