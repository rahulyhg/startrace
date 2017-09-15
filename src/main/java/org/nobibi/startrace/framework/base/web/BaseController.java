package org.nobibi.startrace.framework.base.web;

import javax.servlet.http.HttpServletRequest;

import org.nobibi.startrace.framework.utils.WebUtil;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	
	protected String getParams(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		return value == null ? null : value.trim();
	}
	
	protected String getIpAddress(HttpServletRequest request) {
		return WebUtil.getIpAddress(request);
	}
}
