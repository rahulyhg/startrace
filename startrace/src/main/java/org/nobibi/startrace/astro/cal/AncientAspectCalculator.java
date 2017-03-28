package org.nobibi.startrace.astro.cal;

import java.util.HashMap;
import java.util.Map;

import org.nobibi.startrace.astro.bean.Aspect;
import org.nobibi.startrace.astro.bean.Aspect.AspectType;

import swisseph.SweConst;

/**
 * 古典模式相位计算方法
 * 依据星体的星光（Moiety of Orb）
 * 
 * @author apatheia
 *
 */
public class AncientAspectCalculator {
	
	private static Map<String,Double> influence = new HashMap<String,Double>();
	
	static {
		influence.put("P_"+SweConst.SE_SUN, 15.0);
		influence.put("P_"+SweConst.SE_MOON, 12.0);
		influence.put("P_"+SweConst.SE_MERCURY, 7.0);
		influence.put("P_"+SweConst.SE_VENUS, 8.0);
		influence.put("P_"+SweConst.SE_MARS, 8.0);
		influence.put("P_"+SweConst.SE_JUPITER, 9.0);
		influence.put("P_"+SweConst.SE_SATURN, 9.0);
		influence.put("P_"+SweConst.SE_URANUS, 5.0);
		influence.put("P_"+SweConst.SE_NEPTUNE, 5.0);
		influence.put("P_"+SweConst.SE_PLUTO, 5.0);
		influence.put("P_"+SweConst.SE_TRUE_NODE, 5.0);
		influence.put("P_"+SweConst.SE_ASC, 5.0);
		influence.put("P_"+SweConst.SE_MC, 5.0);
		
	}
	
	/**
	 * 计算两个行星之间的相位
	 * @param planet1
	 * @param longitude1
	 * @param planet2
	 * @param longitude2
	 * @return
	 */
	public static Aspect getAspects(int planet1, double longitude1, int planet2, double longitude2) {
		
		double d1 = influence.containsKey("P_"+planet1) ? influence.get("P_"+planet1) : 5.0;
		double d2 = influence.containsKey("P_"+planet2) ? influence.get("P_"+planet2) : 5.0;
		double d = (d1 + d2) / 2;
		
		double degree =  Math.abs(longitude1 - longitude2);
		
		for (AspectType type : AspectType.values()) {
			int value = type.getValue();
			double lower = value - d;
			double upper = value + d;
			if (degree >= lower && degree <= upper) {
				int[] exactValue = AstroCalculator.trans2Degree(degree);
				Aspect aspect = new Aspect(type);
				aspect.setDegree(exactValue[0]);
				aspect.setMinute(exactValue[1]);
				return aspect;
			}
			
		}
		return null;
	}
	
	
}
