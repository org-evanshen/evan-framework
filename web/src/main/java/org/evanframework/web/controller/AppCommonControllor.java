package org.evanframework.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.evanframework.utils.DateUtils;
import org.evanframework.web.utils.ValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.evanframework.web.WebCoreConstants;

/**
 * 公用控制器
 * <p>
 * 
 * @author shen.wei
 * @version 2011-3-23 下午04:27:01
 */
@Controller
public class AppCommonControllor {

	@Autowired
	private ValidateCodeUtil validateCodeUtil;

	/**
	 * 校验码
	 */
	@RequestMapping(value = "/validateCode")
	public void getValidateCodeImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "height", required = false, defaultValue = "30") int height,
			@RequestParam(value = "validateCodeKey", required = false, defaultValue = "") String validateCodeKey)
					throws IOException {
		validateCodeKey = validateCodeKey + request.getSession().getId();// 应用端可以传入前缀，支持一个页面需要多个验证码
		validateCodeUtil.renderValidateCodeImage(validateCodeKey, response, 4, height);
	}

	/**
	 * 验证校验码
	 */
	@RequestMapping(value = "/validateCode-check")
	@ResponseBody
	public String checkValidateCode(@RequestParam("validateCode") String validateCode, HttpServletRequest request) {
		return String.valueOf(validateCodeUtil.validateCode(request.getSession().getId(), validateCode));
	}

	@RequestMapping(value = "/getsystemdate")
	@ResponseBody
	public String getDate() {
		return DateUtils.format(new Date(),DateUtils.FORMAT_LONG_STRING);
	}

	@RequestMapping(value = WebCoreConstants.PAGE_NOT_FIND)
	public String urlNoFind(HttpServletRequest request, Model model) {
		return WebCoreConstants.PAGE_NOT_FIND_PATH;
	}

	@RequestMapping(value = WebCoreConstants.URL_MSG)
	public String msg(HttpServletRequest request, Model model) {
		return WebCoreConstants.PAGE_MSG_VM_PATH;
	}

}
