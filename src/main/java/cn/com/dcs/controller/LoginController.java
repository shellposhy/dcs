package cn.com.dcs.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;

import cn.com.dcs.base.BaseController;
import cn.com.dcs.model.User;
import cn.com.dcs.service.UserService;
import cn.com.people.data.util.StringUtil;

/**
 * 登录处理类
 * 
 * @author shishb
 * @version V1.0
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
	private static Logger log = Logger.getLogger(LoginController.class.getName());

	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String admin(HttpServletRequest request) {
		log.debug("=======admin.index=======");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (null == currentUser) {
			return "/admin/login";
		}
		return "/admin/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		log.debug("=======admin.login=========");
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			model.addAttribute("from", request.getParameter("from"));
		}
		return "/admin/login";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		log.debug("=======admin.logout=========");
		request.getSession().setAttribute("currentUser", null);
		return "/admin/login";
	}

	/**
	 * 系统登录逻辑处理
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/security/check", method = RequestMethod.POST)
	public String excute(HttpServletRequest request) {
		log.debug("=======admin.security.check=========");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		User currentUser = userService.findByNameAndPass(name, StringUtil.encodeToMD5(password));
		if (null == currentUser) {
			return "/admin/login";
		}
		request.getSession().setAttribute("currentUser", currentUser);
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			return "redirect:" + request.getParameter("from");
		}
		return "redirect:/admin/index";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		log.debug("=======admin.index=========");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (null == currentUser) {
			return "/admin/login";
		} else {
			return "/admin/index";
		}
	}
}
