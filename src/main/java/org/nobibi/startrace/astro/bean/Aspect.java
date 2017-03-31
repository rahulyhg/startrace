package org.nobibi.startrace.astro.bean;

import org.nobibi.startrace.astro.constant.AspectType;

public class Aspect {
	
	public AspectType type;
	public int degree;
	public int minute;
		
	public Aspect(AspectType type) {
		super();
		this.type = type;
	}
	
	
	public int getDegree() {
		return degree;
	}


	public void setDegree(int degree) {
		this.degree = degree;
	}


	public int getMinute() {
		return minute;
	}


	public void setMinute(int minute) {
		this.minute = minute;
	}
	
}
