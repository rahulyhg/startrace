package org.nobibi.startrace.astro.cal;

public class AstroCalculator {
	
	public static final String[] SIGN = {"Aries","Taurus","Gemini","Cancer","Leo","Virgo","Libra","Scorpio","Sagittarius","Capricorn","Aquarius","Pisces"};

	
	/**
	 * 根据黄纬度数获取星座名称
	 * @param longitude
	 * @return
	 */
	public static String getSign(double longitude) {		
		return SIGN[(int)Math.floor(longitude/30)];
	}
	
	/**
	 * 
	 * @param longitude
	 * @return
	 */
	public static int[] getSignDegree(double longitude) {
		double f = longitude % 30;
		return trans2Degree(f);
	}
	
	/**
	 * 
	 * @param longitude
	 * @return
	 */
	public static int[] trans2Degree(double longitude) {
		int[] degree = new int[2];
		degree[0] = (int) Math.floor(longitude);
		degree[1] = (int)((longitude - degree[0])*60);
		return degree;
	}
}
