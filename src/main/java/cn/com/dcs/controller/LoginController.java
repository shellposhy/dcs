package cn.com.dcs.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;

import cn.com.dcs.base.BaseController;
import cn.com.dcs.framework.tree.MenuTreeNode;
import cn.com.dcs.model.User;
import cn.com.dcs.service.UserActionService;
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
	@Resource
	private UserActionService userActionService;

	@RequestMapping(method = RequestMethod.GET)
	public String admin(HttpServletRequest request) {
		log.debug("=======admin.index=======");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (null == currentUser) {
			return "/admin/login";
		}
		return "redirect:/admin/site";
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/security/check", method = RequestMethod.POST)
	public String excute(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("=======admin.security.check=========");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		User currentUser = userService.findByNameAndPass(name, StringUtil.encodeToMD5(password));
		if (null == currentUser) {
			return "/admin/login";
		}
		MenuTreeNode menuTreeNode = userActionService.getMenuTree();
		ObjectMapper om = new ObjectMapper();
		String jsonActionTree = om.writeValueAsString(menuTreeNode);
		request.getSession().setAttribute("jsonActionTree", jsonActionTree);
		request.getSession().setAttribute("currentUser", currentUser);
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			return "redirect:" + request.getParameter("from");
		}
		return "redirect:/admin/site";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		log.debug("=======admin.index=========");
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if (null == currentUser) {
			return "/admin/login";
		} else {
			return "redirect:/admin/site";
		}
	}
}
