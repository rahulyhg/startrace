package org.nobibi.startrace.astro.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/api/natal")
public class NatalController {
	
	private static final Logger LOG = LoggerFactory.getLogger(NatalController.class);
	
	
	@RequestMapping(value="/")
	@ResponseBody
	public void addTaskWithSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		
		
	}
}
