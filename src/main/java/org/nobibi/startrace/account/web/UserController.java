package org.nobibi.startrace.account.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nobibi.startrace.account.bean.User;
import org.nobibi.startrace.account.service.UserService;
import org.nobibi.startrace.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user/register")
	public Map<String, Object> register(HttpServletRequest request, HttpServletResponse response) {
		
		String email = this.getParams(request, "email");
		String password = this.getParams(request, "password");
		String registerType = this.getParams(request, "registerType");
		
		User user = new User();
		user.setEmail(email);
		user.setRegisterType(registerType);
		user.setUsername(email.split("@")[0]);
		user.setPassword(password);
		
		userService.register(user);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		return resultMap;
	}
}
