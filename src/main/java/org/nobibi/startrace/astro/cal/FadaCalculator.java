package org.nobibi.startrace.astro.cal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import swisseph.SweConst;

public class FadaCalculator {
	
	//行星掌管顺序-日生人 ： 太阳-金星-水星-月亮-土星-木星-火星-北交点-南交点
	private static int[] PLANETS_ORDER_DAY = {SweConst.SE_SUN, SweConst.SE_VENUS, SweConst.SE_MERCURY, 
		SweConst.SE_MOON, SweConst.SE_SATURN, SweConst.SE_JUPITER, SweConst.SE_MARS, 
		SweConst.SE_TRUE_NODE, SweConst.SE_MEAN_NODE };
	
	// 行星掌管年限-日生,顺序与PLANETS_ORDER_DAY对应
	private static int[] PLANETS_LIMIT_DAY = {10, 8, 13, 9, 11, 12, 7, 3, 2};
	
	//行星掌管顺序-日生人 ： 月亮-土星-木星-火星-北交点-南交点-太阳-金星-水星
	private static int[] PLANETS_ORDER_NIGHT = {SweConst.SE_MOON, SweConst.SE_SATURN, 
		SweConst.SE_JUPITER, SweConst.SE_MARS, SweConst.SE_TRUE_NODE, SweConst.SE_MEAN_NODE, 
		SweConst.SE_SUN, SweConst.SE_VENUS, SweConst.SE_MERCURY};
	
	// 行星掌管年限-夜生,顺序与PLANETS_ORDER_NIGHT 对应
	private static int[] PLANETS_LIMIT_NIGHT = {9, 11, 12, 7, 3, 2, 10, 8, 13};
	
	// 小限顺序
	private static int[] PLANETS_ORDER_SEC = {SweConst.SE_SUN, SweConst.SE_VENUS, SweConst.SE_MERCURY, 
		SweConst.SE_MOON, SweConst.SE_SATURN, SweConst.SE_JUPITER, SweConst.SE_MARS};
	
	/**
	 * 获取法达星限表
	 * @param birthTime 出生时间
	 * @param day 是否是日生人
	 * @return
	 */
	public static List<Fada> getFada(Calendar birthTime, boolean day) {
		
		List<Fada> list = new ArrayList<Fada>();
		int[] planetsOrder = day ? PLANETS_ORDER_DAY : PLANETS_ORDER_NIGHT;
		int[] planetsLimit = day ? PLANETS_LIMIT_DAY : PLANETS_LIMIT_NIGHT;
		
		// 行星大限开始时间
		Calendar planetStartTime = birthTime;
		for (int i = 0; i < planetsOrder.length; i++) {
			//记录大限行星开始时间
			if (i != 0) {
				planetStartTime.add(Calendar.YEAR, planetsLimit[i-1]);
			} 
			//记录大限行星结束时间
			int limit = planetsLimit[i];
			Calendar planetEndTime = Calendar.getInstance();
			planetEndTime.setTimeInMillis(planetStartTime.getTimeInMillis());
			planetEndTime.add(Calendar.YEAR, limit);
			
			// 处理7大行星的小限
			if (planetsOrder[i] != SweConst.SE_TRUE_NODE && planetsOrder[i] != SweConst.SE_MEAN_NODE) {
				// 计算小限行星
				int[] secPlanets = getSecPlanets(planetsOrder[i]); 
				for (int k = 0; k < secPlanets.length; k++) {
					Calendar cc = Calendar.getInstance();
					cc.setTimeInMillis(planetStartTime.getTimeInMillis());
					cc.add(Calendar.DATE, (int)(k * (getDays(planetStartTime, planetEndTime)/7.0)));
					list.add(new Fada(planetsOrder[i], secPlanets[k], cc));
				}
			} else { // 处理南北交点
				Calendar nodeC = Calendar.getInstance();
				nodeC.setTimeInMillis(planetStartTime.getTimeInMillis());
				list.add(new Fada(planetsOrder[i], planetsOrder[i], nodeC));
			}
		}
		return list;
		
	}
	
	
	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 4);
		List<Fada> list = getFada(cal, false);
		
		for (Fada f : list) {
			Calendar c = f.getStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(f.getMainBounds()+","+f.getSecondaryBounds()+"   "+sdf.format(new Date(c.getTimeInMillis())));
		}
		
	}
	
	/**
	 * 获取两个日期的间隔天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	private static int getDays(Calendar date1, Calendar date2) {
		return (int)Math.abs((date1.getTimeInMillis()- date2.getTimeInMillis())/(3600*1000*24));
	}
	
	// 获取某个行星大限下的小限行星顺序
	private static int[] getSecPlanets(int planet) {
		int[] planets = new int[7];
		
		for (int i = 0; i < PLANETS_ORDER_SEC.length; i++) {
			if (PLANETS_ORDER_SEC[i] == planet) {
				for (int k = 0; k < 7; k++) {
					planets[k] = PLANETS_ORDER_SEC[i];
					i = i == 6 ? 0 : i+1;
				}
				break;
			}
		}
		
		return planets;
	}
}
class Fada {
	// 大限行星
	int mainBounds;
	// 小限行星
	int secondaryBounds;
	// 开始时间
	Calendar startDate;	
	
	public Fada(int mainBounds, int secondaryBounds, Calendar startDate) {
		super();
		this.mainBounds = mainBounds;
		this.secondaryBounds = secondaryBounds;
		this.startDate = startDate;
	}
	public int getMainBounds() {
		return mainBounds;
	}
	public void setMainBounds(int mainBounds) {
		this.mainBounds = mainBounds;
	}
	public int getSecondaryBounds() {
		return secondaryBounds;
	}
	public void setSecondaryBounds(int secondaryBounds) {
		this.secondaryBounds = secondaryBounds;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
}
