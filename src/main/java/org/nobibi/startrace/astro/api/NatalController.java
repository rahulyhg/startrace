package org.nobibi.startrace.astro.api;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.nobibi.startrace.astro.bean.Aspect;
import org.nobibi.startrace.astro.bean.PlanetState;
import org.nobibi.startrace.astro.cal.AncientAspectCalculator;
import org.nobibi.startrace.astro.cal.AncientNaturalCalculator;
import org.nobibi.startrace.astro.cal.AstroCalculator;
import org.nobibi.startrace.framework.base.constant.ResCode;
import org.nobibi.startrace.framework.utils.WebUtil;
import org.nobibi.startrace.swisseph.SwissEphHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import swisseph.SweConst;

/**
 * 
 * 本命盘相关API
 * @author apatheia
 *
 */

@Controller
@RequestMapping(value="/api/natal")
public class NatalController {
	
	//private static final Logger LOG = LoggerFactory.getLogger(NatalController.class);
	
	
	@RequestMapping(value="/ancient")
	@ResponseBody
	public void getAncient(@RequestParam(name="date",required=false) String dateStr,
						 @RequestParam(name="longitude",required=true) double longitude,
						 @RequestParam(name="latitude",required=true) double latitude,
						 @RequestParam(name="hsys",required=false) Integer hsys,
						 @RequestParam(name="jsonpCallback",required=false) String jsonpCallback,
						 HttpServletResponse response) throws IOException, ParseException {
		
		Calendar cal = Calendar.getInstance();
		if (!StringUtils.isEmpty(dateStr)) {
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
		}
		cal.add(Calendar.HOUR_OF_DAY, -8);
		
		hsys = hsys == null ? SweConst.SE_HSYS_ALCABITIUS : hsys;
		
		SwissEphHelper swissEph = SwissEphHelper.getInstance();
		
		//1.宫位数据
		double[] houses = swissEph.calHouses(cal, longitude, latitude, hsys);

		
		//2.获取行星状态数据
		double sunLongitude = 0;
		List<PlanetState> planets = new ArrayList<PlanetState>();
		for (int i = SweConst.SE_SUN; i <= SweConst.SE_TRUE_NODE; i++) {
			if (i == SweConst.SE_MEAN_NODE) continue; 
			
			PlanetState planet = new PlanetState();
			double[] pos = swissEph.calPlanetPos(cal, i); //行星位置
			int[] planetDegree = AstroCalculator.getSignDegree(pos[0]); //所在星座度数
			if (i == SweConst.SE_SUN) sunLongitude = pos[0];	//记录太阳位置,用于后续计算
			planet.setNO(i);
			planet.setName(swissEph.getPlanetName(i));
			planet.setSign(AstroCalculator.getSign(pos[0]));
			planet.setDegree(planetDegree[0]);
			planet.setMinute(planetDegree[1]);
			planet.setSecond(planetDegree[2]);
			planet.setLongitude(pos[0]);
			planet.setHouse(getPlanetHouse(houses, pos[0]));
			
			Map<String,Object> attributes = new HashMap<String,Object>();
			attributes.put("retrograde", pos[3] < 0); //逆行
			attributes.put("power", ""); //先天力量
			attributes.put("domain", AncientNaturalCalculator.getDomain(i, pos[0], sunLongitude, houses[1])); //得时失时
			attributes.put("burn", i == SweConst.SE_SUN ? 0 : AstroCalculator.calPlanetBurn(sunLongitude, pos[0])); //灼烧状态
			attributes.put("anareticDegrees", AstroCalculator.isAnareticDegrees(pos[0]));
			planet.setAttributes(attributes);
			planets.add(planet);
		}
		
		
		//3.相位数据.计算日月水金火木土天海冥北交上升天顶
		List<PlanetState> points = new ArrayList<PlanetState>(); //存储需要计算相位的行星/虚点
		points.addAll(planets);
		points.add(new PlanetState(1001, houses[1]));
		points.add(new PlanetState(1002, houses[10]));
		
		//存储返回的相位数据
		Map<String, List<Map<String,Object>>> aspectMap = new HashMap<String, List<Map<String,Object>>>();
		for (PlanetState p1 : points) {
			List<Map<String,Object>> aspectList = new ArrayList<Map<String,Object>>();
			for (PlanetState p2 : points) {
				if (p1.getNO() != p2.getNO()) {
					Aspect aspect = AncientAspectCalculator.getAspects(p1.getNO(), p1.getLongitude(), p2.getNO(), p2.getLongitude());
					if (aspect != null) {
						Map<String,Object> m = new HashMap<String,Object>();
						m.put("dist", p2.getNO());
						m.put("type", aspect.type.getValue());
						m.put("degree", aspect.getDegree());
						m.put("minute", aspect.getMinute());
						aspectList.add(m);
					}
				}
			}
			aspectMap.put("P_"+p1.getNO(), aspectList);
		}
		
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", ResCode.SUCCESS);
		resultMap.put("planets", planets);
		resultMap.put("houses", houses);
		resultMap.put("aspects", aspectMap);
		
		WebUtil.responseJson(response, resultMap, jsonpCallback);
		
	}
	
	private int getPlanetHouse(double[] houses, double longitude) {
		for (int i = 1; i < houses.length; i++ ) {
			int next = i == 12 ? 1 : i+1;
			if (longitude >= houses[i] && longitude < houses[next] ||
					houses[i] > houses[next] && (longitude > houses[i] || longitude < houses[next])) {
				return i;
			}
		}
		return -1;
	}
	
}
