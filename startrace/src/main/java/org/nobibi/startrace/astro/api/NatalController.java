package org.nobibi.startrace.astro.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.nobibi.startrace.astro.bean.PlanetState;
import org.nobibi.startrace.astro.cal.AstroCalculator;
import org.nobibi.startrace.swisseph.SwissEphHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import swisseph.SweConst;

@Controller
@RequestMapping(value="/api/natal")
public class NatalController {
	
	//private static final Logger LOG = LoggerFactory.getLogger(NatalController.class);
	
	
	@RequestMapping(value="/")
	@ResponseBody
	public void getNatal(@RequestParam(name="date",required=true) Date date,
						 @RequestParam(name="longitude",required=true) double longitude,
						 @RequestParam(name="latitude",required=true) double latitude,
						 HttpServletResponse response) throws IOException {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -8);
		
		SwissEphHelper swissEph = SwissEphHelper.getInstance();
		
		//获取行星状态数据
		List<PlanetState> planets = new ArrayList<PlanetState>();
		for (int i = SweConst.SE_SUN; i < SweConst.SE_TRUE_NODE; i++) {
			if (i == SweConst.SE_MEAN_NODE) continue; 
			
			PlanetState planet = new PlanetState();
			double[] pos = swissEph.calPlanetPos(cal, i); //行星位置
			int[] planetDegree = AstroCalculator.getSignDegree(pos[0]); //所在星座度数
			planet.setNO(i);
			planet.setName(swissEph.getPlanetName(i));
			planet.setSign(AstroCalculator.getSign(pos[0]));
			planet.setDegree(planetDegree[0]);
			planet.setMinute(planetDegree[1]);
			planet.setLongitude(pos[0]);
			
			Map<String,Object> attributes = new HashMap<String,Object>();
			attributes.put("retrograde", pos[0] < 0); //逆行
			attributes.put("power", "");
			planets.add(planet);
		}
		
		
		//宫位数据
		double[] houses = swissEph.calHouses(cal, longitude, latitude, SweConst.SE_HSYS_ALCABITIUS);
		
		//相位数据
		
		
		
	}
}
