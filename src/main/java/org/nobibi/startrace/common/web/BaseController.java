package org.nobibi.startrace.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	
	protected String getParams(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		return value == null ? null : value.trim();
	}
}
