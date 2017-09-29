package org.nobibi.startrace.account.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nobibi.startrace.account.bean.User;
import org.nobibi.startrace.account.service.UserService;
import org.nobibi.startrace.framework.base.web.BaseController;
import org.nobibi.startrace.framework.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/register")
	public Map<String, Object> register(HttpServletRequest request, HttpServletResponse response) {
		
		String email = this.getParams(request, "email");
		String password = this.getParams(request, "password");
		String registerType = this.getParams(request, "registerType");
		String mobile = this.getParams(request, "mobile");
		
		User user = new User();
		user.setEmail(email);
		user.setRegisterType(registerType);
		user.setUsername("mail".equals(registerType) ? email : mobile);
		user.setPassword(password);
		user.setMobile(mobile);
		
		userService.register(user);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		return resultMap;
	}
	
	@RequestMapping(value="/usernamerepeat")
	public Map<String, Object> checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String username = this.getParams(request, "username");
		boolean repeat = false;
		if (StringHelper.isNotBlank(username)) {
			repeat = userService.isUsernameRepeat(username);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		resultMap.put("repeat", repeat);
		return resultMap;		
	}
}
