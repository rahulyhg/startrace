package org.nobibi.startrace.framework.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebUtil {
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static void responseJson(HttpServletResponse response, Object jsonObject)
            throws IOException {
		responseJson(response, jsonObject , null);
    }
	
	public static void responseJson(HttpServletResponse response, Object jsonObject, String jsonpCallback)
            throws IOException {
		
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        Object result = jsonObject;
        if (jsonObject instanceof String) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("result", jsonObject);
            result = resultMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String jsonStr = mapper.writeValueAsString(result);
        
        String resp = jsonStr;
        
        if (!StringUtils.isEmpty(jsonpCallback)) {
        	resp = jsonpCallback+"("+jsonStr+")";
        }
        
        response.getWriter().write(resp);
        response.getWriter().flush();
    }
	
	public static Integer getIntParam(HttpServletRequest request, String paramName) {
		String val = request.getParameter(paramName);
		if (val != null && !"".equals(val.trim())) {
			return Integer.valueOf(val);
		}
		return null;
	}
	
	public static Integer getIntParam(HttpServletRequest request, String paramName, Integer defValue) {
		String val = request.getParameter(paramName);
		if (val != null && !"".equals(val.trim())) {
			return Integer.valueOf(val);
		} else {
			return defValue;
		}
		
	}
	public static Date getDateParam(HttpServletRequest request, String paramName, String pattern) {
		String val = request.getParameter(paramName);
		String pat = pattern == null ? DEFAULT_PATTERN : pattern;
		
		if (val != null && !"".equals(val.trim())) {
			SimpleDateFormat timeFmt = new SimpleDateFormat(pat);
			try {
				return timeFmt.parse(val);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static String getStrParam(HttpServletRequest request, String paramName) {
		return request.getParameter(paramName);
	}
	
	public static String getIpAddress(HttpServletRequest request) {
		List<String> checkList = new ArrayList<String>();
        checkList.add("X-Forwarded-For");
        checkList.add("Proxy-Client-IP");
        checkList.add("WL-Proxy-Client-IP");
        checkList.add("HTTP_CLIENT_IP");
        checkList.add("HTTP_X_FORWARDED_FOR");
        String ip = "";
        for (String header : checkList) {
            ip = request.getHeader(header);
            if (StringHelper.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }
        }
        if (StringHelper.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
	}
}
