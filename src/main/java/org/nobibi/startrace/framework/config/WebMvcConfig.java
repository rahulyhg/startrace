package org.nobibi.startrace.framework.config;

import org.nobibi.startrace.framework.interceptor.UserAuthInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		
		registry.addInterceptor(new UserAuthInterceptor())
				.addPathPatterns("/user/**");
		
	}
	
}
