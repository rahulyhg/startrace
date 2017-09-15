package org.nobibi.startrace.account.web;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nobibi.startrace.account.bean.LoginLog;
import org.nobibi.startrace.account.bean.User;
import org.nobibi.startrace.account.service.LoginLogService;
import org.nobibi.startrace.account.service.UserService;
import org.nobibi.startrace.framework.base.constant.ResCode;
import org.nobibi.startrace.framework.base.constant.WebConst;
import org.nobibi.startrace.framework.base.web.BaseController;
import org.nobibi.startrace.framework.plugins.log.Log;
import org.nobibi.startrace.framework.utils.MD5Helper;
import org.nobibi.startrace.framework.utils.StringHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RestController
public class LoginController extends BaseController{
	
	private static final Logger LOGGER = Log.get(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginLogService loginLogService;
	
	@RequestMapping(value="/user/login")
	public Map<String, Object> login(HttpServletRequest request) {
		String username = this.getParams(request, "username");
		String passwd = this.getParams(request, "passwd");
		
		String result = ResCode.SUCCESS;
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		User dbUser = null;
		if (StringHelper.isNotBlank(username) && StringHelper.isNotBlank(passwd)) {
			
			dbUser = userService.getUser(username);
			
			User user = new User();
			user.setUsername(username);
			
			if (dbUser != null && MD5Helper.md5DoubleSalt(passwd, dbUser.getId()).equals(dbUser.getPassword())) {
				Algorithm algorithm = null;
				String token = null;
				try {
					//过期时间设置为次日凌晨3点.
					Calendar expiredTime = Calendar.getInstance();
					expiredTime.add(Calendar.DATE, 1);
					expiredTime.set(Calendar.HOUR_OF_DAY, 3);
					expiredTime.set(Calendar.MINUTE, 0);
					expiredTime.set(Calendar.SECOND, 0);
					
					algorithm = Algorithm.HMAC256(WebConst.TOKEN_JWT_SECRET);
					token = JWT.create()
							.withExpiresAt(new Date(new Date().getTime()))
					        .withIssuer(WebConst.TOKEN_JWT_ISSUER)
					        .withExpiresAt(expiredTime.getTime())
					        .withClaim("userid", dbUser.getId())
					        .withClaim("username", dbUser.getUsername())
					        .sign(algorithm);
					
					result = ResCode.SUCCESS;
					resultMap.put(WebConst.TOKEN_JWT, token);
					
				} catch (IllegalArgumentException | UnsupportedEncodingException e) {
					LOGGER.error("Gen JWT Token failed", e);
					result = ResCode.CREDENTIALS_INVALID;
				}
			} else {
				result = ResCode.CREDENTIALS_INVALID;
			}
		}
		
		// 记录登录日志
		LoginLog loginLog = new LoginLog();
		loginLog.setLoginIp(this.getIpAddress(request));
		loginLog.setLoginName(username);
		loginLog.setLoginTime(new Date());
		loginLog.setUserId(dbUser == null ? "" : dbUser.getId());
		loginLog.setLoginResult(result);
		
		loginLogService.save(loginLog);
		
		
		resultMap.put(WebConst.RESULT_KEY, result);
		
		return resultMap;

	} 
}
