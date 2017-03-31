package com.nobibi.startrace;

import java.util.Calendar;

import org.nobibi.startrace.astro.bean.Aspect;
import org.nobibi.startrace.astro.cal.AncientAspectCalculator;
import org.nobibi.startrace.astro.cal.AstroCalculator;
import org.nobibi.startrace.swisseph.SwissEphHelper;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;
import junit.framework.TestCase;

public class SwissEphTest extends TestCase{
	
	
	public void testCalPlanet() {
		SwissEphHelper swe = SwissEphHelper.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -8);
		double[] result = swe.calPlanetPos(cal, SweConst.SE_MOON);
		
		System.out.println(result[0]);
	
		
		
		SweDate sweDate = new SweDate();
		SwissEph swiss = new SwissEph("E:\\swisseph\\swe_file");
		double[] result2 = new double[9];
		
		
		swiss.swe_calc(sweDate.getJulDay(), SweConst.SE_MOON, SweConst.SEFLG_SPEED, result2,null);
		
		System.out.println(result2[0]);
		
		int[] r1 = AstroCalculator.transDigit2Degree(result[0]);
		int[] r2 = AstroCalculator.transDigit2Degree(result2[0]);
		System.out.println(r1[0]+"-"+r1[1]+"-"+r1[2]);
		System.out.println(r2[0]+"-"+r2[1]+"-"+r2[2]);
	}
	
	public void testCalHouses() {
		SwissEphHelper swe = SwissEphHelper.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -8);
		double[] result = swe.calHouses(cal, 116.46, 39.92, SweConst.SE_HSYS_ALCABITIUS);
		for (double d : result) {
			System.out.println(d);
		}
	}
	
	
	
	public void testAncientAspect() {
		Aspect aspect = AncientAspectCalculator.getAspects(0, 9.848504856428372, 1, 40.552667876777065);
	
		System.out.println(aspect);
	}
	
	public void testSign() {
		
		String sign = AstroCalculator.getSign(31.1644);
		int[] degree = AstroCalculator.getSignDegree(31.1644);
		System.out.println(sign+":"+degree[0]+"度"+degree[1]+"分");
	}
	
	public void testDegree() {
		int[] a = AstroCalculator.transDigit2Degree(39.92);
		System.out.println(a[1]+"-"+a[2]);
		
		double[] digits = {0,
				73.89690143603613,
				95.85576724903423,
				116.47083727955302,
				139.8830997841984,
				170.46342306841598,
				211.19728313769525,
				253.89690143603613,
				275.8557672490342,
				296.47083727955305,
				319.8830997841984,
				350.46342306841603,
				31.197283137695266};
		
		for (double k : digits) {
			int[] d = AstroCalculator.getSignDegree(k);
			System.out.println(d[0]+"-"+d[1]+"-"+d[2]);
		}
	}
	
	public void testHorizon() {
		System.out.println(AstroCalculator.isOnTheHorizon(150.17825207725, 10.761247643232698));
	}
}
