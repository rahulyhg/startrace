package com.nobibi.startrace;

import java.util.Calendar;

import org.nobibi.startrace.swisseph.SwissEphHelper;

import swisseph.SweConst;
import junit.framework.TestCase;

public class SwissEphTest extends TestCase{
	
	
	public void testCalPlanet() {
		SwissEphHelper swe = SwissEphHelper.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -8);
		double[] result = swe.calPlanetPos(cal, 0);
		for (double d : result) {
			System.out.println(d);
		}
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
	
	public void testCalPhase() {
		SwissEphHelper swe = SwissEphHelper.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -8);
		double[] result = swe.calPhase(cal, SweConst.SE_MERCURY);
		for (double d : result) {
			System.out.println(d);
		}
	}
}
