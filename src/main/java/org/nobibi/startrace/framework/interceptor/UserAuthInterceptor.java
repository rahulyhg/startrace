package org.nobibi.startrace.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nobibi.startrace.framework.base.constant.WebConst;
import org.nobibi.startrace.framework.plugins.log.Log;
import org.nobibi.startrace.framework.utils.StringHelper;
import org.nobibi.startrace.framework.utils.WebUtil;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class UserAuthInterceptor implements HandlerInterceptor{
	
	private static final Logger LOGGER = Log.get(UserAuthInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		Boolean result = false;
		
		String header = request.getHeader("Authorization");
		String token = "";
		if (StringHelper.isNotBlank(header)) {
			String[] auth = header.split(" ");
			if (auth.length > 1 && WebConst.TOKEN_AUTH_HEADER.equals(auth[0])) {
				token = auth[1];
			}
		}
		
		if (StringHelper.isNotBlank(token)) {
			try {
				Algorithm algorithm = Algorithm.HMAC256(WebConst.TOKEN_JWT_SECRET);
				    JWTVerifier verifier = JWT.require(algorithm)
				        .withIssuer(WebConst.TOKEN_JWT_ISSUER)
				        .build(); //Reusable verifier instance
				    verifier.verify(token);
				    result = true;
			} catch (JWTDecodeException e) {
				LOGGER.error("Token Decode Error, Token: "+token, e);
				WebUtil.responseJson(response, WebConst.JWT_EXCEPTION_TOKEN_INVALID);			
				return false;
			} catch(TokenExpiredException e2) {
				LOGGER.error("Token Expired Error, Token: "+token, e2);
				WebUtil.responseJson(response, WebConst.JWT_EXCEPTION_TOKEN_EXPIRED);
				return false;
			} catch(Exception e3) {
				LOGGER.error("Token Valid Error, Token: "+token, e3);
				WebUtil.responseJson(response, WebConst.JWT_EXCEPTION_TOKEN_INVALID);
				return false;
			}
		}

	    return result;
	}

}
