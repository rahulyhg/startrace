package org.nobibi.startrace.astro.cal;

import java.util.List;

import org.nobibi.startrace.astro.constant.Sign;

import swisseph.SweConst;

public class AncientNaturalCalculator {
	
	public static List<String> getPower(int planet, double longitude) {
		return null;
	}
	
	public static int getRuler() {
		return 1;
	}
	
	/**
	 * 判断行星的得时,失时状态
	 * 昼星:日木土
	 * 夜星:月金火
	 * 得时:白天生人,昼星处于地平面上且落入阳性星座。夜晚生人,夜星处于地平面上且落入阴性星座。
	 * 失时:白天生人,夜星处于地平面上且落入阳性星座。夜晚生人,昼星处于地平面上且落入阴性星座。
	 * 
	 * @param planet
	 * @param longitude
	 * @param asc
	 * @return [1,-1,0] ->[得时,失时,不得时也不失时]
	 */
	public static int getDomain(int planet, double planetLong, double sunLong, double asc) {
		
		int result = 0;
				
		boolean day = AstroCalculator.isOnTheHorizon(asc, sunLong); //是否为日生人
		boolean horizon = AstroCalculator.isOnTheHorizon(asc, planetLong); //星体是否在地平线上
		boolean dayPlanet = planet == SweConst.SE_SUN || planet == SweConst.SE_JUPITER || planet == SweConst.SE_SATURN;		
		boolean nightPlanet = planet == SweConst.SE_MOON || planet == SweConst.SE_VENUS || planet == SweConst.SE_MARS;		
		String sign = AstroCalculator.getSign(planetLong);
		boolean yang = Sign.getSignByName(sign).getYy() == 0;
		
		if ((day && dayPlanet && horizon && yang) ||
			(!day && nightPlanet && horizon && !yang)) {
			result = 1;
		}
		if ((day && nightPlanet && horizon && yang) ||
			(!day && dayPlanet && horizon && !yang)) {
			result = -1;
		}
		
		return result;
	}
	
	
}
