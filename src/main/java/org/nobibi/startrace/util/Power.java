package org.nobibi.startrace.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import swisseph.SweConst;

public class Power {
	
	public static void main(String[] args) {
		
		Map<String,List<String>> maps = new HashMap<String,List<String>>();
		
		
		for (int p = SweConst.SE_SUN; p <= SweConst.SE_CHIRON; p++) {
	        if (p == SweConst.SE_EARTH) continue;
	        List<String> power = new ArrayList<String>();
	        
	        for (int i = 0; i < 360;i++) {
	        	StringBuilder sb = new StringBuilder();
	        	switch(p){
	        		case SweConst.SE_SUN:
	        			if (bet(i,0,30)) {
	        				sb.append("4,");
	        			} else if (bet(i,120,150)) {
	        				sb.append("5,");
	        			} else if (bet(i,180,210)) {
	        				sb.append("-4,");
	        			} else if (bet(i,300,330)) {
	        				sb.append("-5,");
	        			}
	        			break;
	        		case SweConst.SE_MOON:
	        			if (bet(i,30,60)) {
	        				sb.append("4,");
	        			} else if (bet(i,90,120)) {
	        				sb.append("5,");
	        			} else if (bet(i,210,240) ) {
	        				sb.append("-4,");
	        			} else if (bet(i,270,300)) {
	        				sb.append("-5,");
	        			}
	        			break;
	        		case SweConst.SE_MERCURY:
	        			if (bet(i,150,180)) {
	        				sb.append("4,5,");
	        			} else if (bet(i,60,90)) {
	        				sb.append("5,");
	        			} else if (bet(i,330,360) ) {
	        				sb.append("-4,");
	        			} else if (bet(i,240,270)) {
	        				sb.append("-5,");
	        			}	
	        			break;
	        		case SweConst.SE_VENUS:
	        			if (bet(i,330,360)) {
	        				sb.append("4,");
	        			} else if (bet(i,30,60) || bet(i, 180, 210)) {
	        				sb.append("5,");
	        			} else if (bet(i,150,180) ) {
	        				sb.append("-4,");
	        			} else if (bet(i,210,240) || bet(i,0,30)) {
	        				sb.append("-5,");
	        			}
	        			break;
	        		case SweConst.SE_MARS:
	        			if (bet(i,270,300)) {
	        				sb.append("4,");
	        			} else if (bet(i,0,30) || bet(i, 210, 240)) {
	        				sb.append("5,");
	        			} else if (bet(i,90,120) ) {
	        				sb.append("-4,");
	        			} else if (bet(i,180,210) || bet(i,30,60)) {
	        				sb.append("-5,");
	        			}	
	        			break;
	        		case SweConst.SE_JUPITER:
	        			if (bet(i,90,120)) {
	        				sb.append("4,");
	        			} else if (bet(i,240,270) || bet(i, 330, 360)) {
	        				sb.append("5,");
	        			} else if (bet(i,270,300) ) {
	        				sb.append("-4,");
	        			} else if (bet(i,150,180) || bet(i,60,90)) {
	        				sb.append("-5,");
	        			}
	        			break;
	        		case SweConst.SE_SATURN:
	        			if (bet(i,180,210)) {
	        				sb.append("4,");
	        			} else if (bet(i,270,330)) {
	        				sb.append("5,");
	        			} else if (bet(i,0,30) ) {
	        				sb.append("-4,");
	        			} else if (bet(i,90,150)) {
	        				sb.append("-5,");
	        			}
	        			break;
	        	}
	        		
	        	if (sb.length() > 0) {
	        		sb.deleteCharAt(sb.length() - 1);
	        	}		
	        	
	        	power.add(sb.toString());
	        	
			}
	        maps.put("P_"+p, power);
		}
		
		
		//System.out.println(maps);
		
		JSONObject obj = JSONObject.fromObject(maps);
		System.out.println(obj.toString());
		
	}
	
	
	
	public static boolean bet(int i, int start, int end) {
		if (i >= start && i < end) {
			return true;
		}
		return false;
	}
	
	public static String getTri(int p, int pos) {
		return null;
	}
}
