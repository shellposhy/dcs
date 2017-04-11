package cn.com.dcs.base;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import cn.com.dcs.framework.view.HtmlEscapeEditor;

/**
 * 基础控制类
 * 
 * @author shishb
 * @version 1.0
 */
@SuppressWarnings("restriction")
@Controller
public class BaseController {
	private static Logger log = Logger.getLogger(BaseController.class.getName());
	@Resource
	protected ApplicationContext appContext;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new HtmlEscapeEditor());
	}

	/**
	 * 保存
	 * 
	 * @param obj
	 * @param result
	 * @param model
	 * @param operator
	 * @return
	 */
	public String save(Object obj, BindingResult result, Model model, ControllerOperator operator) {
		if (result.hasErrors()) {
			log.error(result.getAllErrors());
			model.addAttribute(obj);
			return operator.getFailureView();
		}
		try {
			operator.operate();
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			String errMsg = e.getRootCause().getMessage();
			errMsg = obj.getClass().getSimpleName() + "."
					+ errMsg.substring(errMsg.lastIndexOf("_") + 1, errMsg.length() - 1);
			String fieldName = errMsg.substring(errMsg.indexOf(".") + 1);
			errMsg = appContext.getMessage(errMsg, null, Locale.getDefault());
			result.rejectValue(fieldName, "field.duplicate", new String[] { errMsg }, "已有相同的存在");
			model.addAttribute(obj);
			operator.onFailure();
			return operator.getFailureView();
		} catch (TypeMismatchDataAccessException e) {
			e.printStackTrace();
			String errMsg = e.getRootCause().getMessage();
			errMsg = obj.getClass().getSimpleName() + "."
					+ errMsg.substring(errMsg.lastIndexOf("_") + 1, errMsg.length() - 1);
			String fieldName = errMsg.substring(errMsg.indexOf(".") + 1);
			errMsg = appContext.getMessage(errMsg, null, Locale.getDefault());
			result.rejectValue(fieldName, "field.typeMismatch", new String[] { errMsg }, "数据类型不匹配");
			model.addAttribute(obj);
			operator.onFailure();
			return operator.getFailureView();
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			String errMsg = e.getRootCause().getMessage();
			String fieldName = errMsg.substring(errMsg.indexOf("'") + 1, errMsg.lastIndexOf("'")).toLowerCase();
			errMsg = obj.getClass().getSimpleName() + "." + fieldName;
			errMsg = appContext.getMessage(errMsg, null, Locale.getDefault());
			result.rejectValue(fieldName, "field.integrity", new String[] { errMsg }, "数据长度超过限制，或有空值");
			model.addAttribute(obj);
			operator.onFailure();
			return operator.getFailureView();
		}
		return operator.getSuccessView();
	}

	@ExceptionHandler(Exception.class)
	public String handleAllExceptions(Exception ex, HttpServletRequest request) {
		ex.printStackTrace();
		request.setAttribute("exception", ex);
		return "/exception";

	}

}