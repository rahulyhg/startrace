package org.nobibi.startrace.swisseph;

import java.util.Calendar;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

/**
 * 对swissEph的封装,方便调用
 * @author apatheia
 *
 */
public class SwissEphHelper {
	
	
	private static SwissEphHelper instance = new SwissEphHelper();
	
	private SwissEph swissEph = null;
	
	private SwissEphHelper() {
		//this.swissEph = new SwissEph();
		this.swissEph = new SwissEph("C:\\Users\\apatheia\\git\\startrace\\startrace\\src\\main\\resources\\swefile");
	}
	
	public static SwissEphHelper getInstance() {
		return instance;
	}
	
	
	public String getPlanetName(int planet) {
		return swissEph.swe_get_planet_name(planet);
	}
	
	/**
	 * 计算行星位置
	 * 返回double[5]
	 * double[0] 黄经
	 * double[1] 黄纬
	 * double[2] 距离
	 * double[3] 黄经速度,负值则代表逆行
	 * double[4] 黄纬速度
	 * double[5] speed in dist
	 * 
	 * @param utc UTC时间,传入时需先行计算时差,如北京时间需要将时间减8小时
	 * @param planet 需要计算的行星,参见 SweConst.SE_*
	 * @return double[longitude, latitude, distance, speed in long, speed in lat, speed in dist] 
	 */
	public double[] calPlanetPos(Calendar utc,int planet) {
		double[] result = new double[6];
		SweDate sweDate = getSweDate(utc);
		swissEph.swe_calc(sweDate.getJulDay()+SweDate.getDeltaT(sweDate.getJulDay()), planet, SweConst.SEFLG_SPEED, result, null);
		
		return result;
	}
	
	/**
	 * 通过时间,经纬度,及指定的分宫制,计算1-12宫的宫头位置。	
	 *  
	 * @param utc utc时间
	 * @param geolon 经度
	 * @param geolat 纬度
	 * @param hsys 分宫制,参见 SweConst.SE_HSYS_*
	 * @return  (double[13]) The house cusps are returned here in cusp[1...12] for the houses 1 to 12.
	 */
	public double[] calHouses(Calendar utc, double geolon, double geolat, int hsys) {
		double[] cusp = new double[13];
		int result = swissEph.swe_houses(getSweDate(utc).getJulDay(),
				0, geolat, geolon, hsys, cusp, new double[10]);
		if (result == SweConst.OK) {
			return cusp;
		}
		return null;
	}
	
	
	
	
	/**
	 * 根据UTC时间获取SweDate
	 * @param utc
	 * @return
	 */
	private SweDate getSweDate(Calendar utc) {
		double minute = utc.get(Calendar.MINUTE) / 60.0;
		double sec = utc.get(Calendar.MINUTE) / 3600.0;
		double hour = utc.get(Calendar.HOUR_OF_DAY) + minute + sec;
		SweDate sweDate = new SweDate(utc.get(Calendar.YEAR),utc.get(Calendar.MONTH)+1,
				utc.get(Calendar.DAY_OF_MONTH),hour);
		
		return sweDate;
	}
}
