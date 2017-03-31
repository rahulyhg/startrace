package org.nobibi.startrace.astro.constant;

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