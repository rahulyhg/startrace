package org.nobibi.startrace.astro.cal;

public class AstroCalculator {
	
	public static final String[] SIGN = {"Aries","Taurus","Gemini","Cancer","Leo","Virgo","Libra","Scorpio","Sagittarius","Capricorn","Aquarius","Pisces"};

	
	/**
	 * 根据黄经度数获取星座名称
	 * @param longitude
	 * @return signName
	 */
	public static String getSign(double longitude) {		
		return SIGN[(int)Math.floor(longitude/30)];
	}
	
	/**
	 * 根据黄经位置获取所在星座的度数
	 * @param longitude
	 * @return [degree,minute,second]
	 */
	public static int[] getSignDegree(double longitude) {
		double f = longitude % 30;
		return transDigit2Degree(f);
	}
	
	/**
	 * 将小数形式转换为度分秒格式
	 * @param longitude
	 * @return [degree,minute,second]
	 */
	public static int[] transDigit2Degree(double longitude) {
		int[] degree = new int[3];
		degree[0] = (int) Math.floor(longitude);
		double m = (longitude - degree[0]) * 60;
		degree[1] = (int)m;
		degree[2] = (int)((m - degree[1]) * 60);
		return degree;
	}
	
	/**
	 * 计算行星的燃烧状态
	 * 0: 无
	 * 1: 在日核内 
	 * 2: 灼烧
	 * 3: 在日光下
	 * @param sunLng
	 * @param planetLng
	 * @return 
	 */
	public static int calPlanetBurn(double sunLng, double planetLng) {
		double angle = Math.abs(sunLng - planetLng);
		int result = 0;
		
		if (angle > 17) {
			return result;
		}
		
		if (angle <= 18/60) {
			result = 1;
		} else if (angle <= 8.5) {
			result = 2;
		} else if (angle <= 17) {
			result = 3;
		}
		return result;
	}
	
	/**
	 * 根据黄经判断星体是否在地平线上.
	 * @param asc  上升点的黄经
	 * @param longitude 星体的黄经
	 * @return
	 */
	public static boolean isOnTheHorizon(double asc, double longitude) {
		double des = asc + 180;
		des = des > 360 ? des - 360 : des;
		
		boolean horizon = !((longitude >= asc && longitude < des) ||
						((longitude >= asc && longitude < 360) && longitude < des));
		
		return horizon;
	}
	
	/**
	 * 判断星体或虚点是否处于崎度
	 * @param longitude
	 * @return
	 */
	public static boolean isAnareticDegrees(double longitude) {
		int[] degrees = getSignDegree(longitude);
		return degrees[0] == 29;
	}
}
