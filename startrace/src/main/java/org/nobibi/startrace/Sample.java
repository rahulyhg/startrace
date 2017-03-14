package org.nobibi.startrace;

import swisseph.SweDate;
import swisseph.SwissEph;

public class Sample {
	public static void main(String[] args) {
		//System.out.println(SweConst.SE_EPHE_PATH);
		//加载星历文件
		SwissEph eph = new SwissEph();
		eph.swe_set_ephe_path("D:\\Work\\workspace\\eclipse\\swisseph\\startrace\\resource\\swefile");
		
	
		SweDate date = new SweDate();
		//设置儒略日时间,需要从UTC转过来（年,月,日,小时-可为小数,）
		date.setJulDay(date.getJDfromUTC(1988, 11, 4, 1, 0, 0, true, true)[0]);		
		double[] result = new double[6];
		
		//计算, 参数为日期,行星标识,计算标识,结果
		eph.calc(date.getJulDay(), 0, 2, result);
		
		for (double key : result) {
			System.out.println(key);
		}
		
		
		
	}
}
