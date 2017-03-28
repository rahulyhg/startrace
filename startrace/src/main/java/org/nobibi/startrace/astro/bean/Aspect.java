package org.nobibi.startrace.astro.bean;

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


	public enum AspectType {
		
		Conjunction(0,  "Conjunction"), 
		Sextile(60, "Sextile"),
		Square(90, "Square"), 
		Trine(120, "Trine"), 
		Opposition(180, "Opposition");
		
		private int value;
		private String name;

		private AspectType(int value,  String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue(){
			return value;
		}

		public String getName() {
			return name;
		}

	}
	
}
