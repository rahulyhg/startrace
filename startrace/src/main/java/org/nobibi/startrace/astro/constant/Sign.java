package org.nobibi.startrace.astro.constant;

public enum Sign {
	
	Aries(1, "Aries", "Ari", 0, 3, 1),
	Taurus(2, "Taurus", "Tau", 1, 1, 2),
	Gemini(3, "Gemini", "Gem", 0, 4, 2),
	Cancer(4, "Cancer", "Cnc", 1, 2, 1),
	Leo(5, "Leo", "Leo", 0, 3, 2),
	Virgo(6, "Virgo", "Vir", 1, 1, 3),
	Libra(7, "Libra", "Lib", 0, 4, 1),
	Scorpio(8, "Scorpio", "Sco", 1, 2, 2),
	Sagittarius(9, "Sagittarius", "Sgr", 0, 3, 3),
	Capricorn(10, "Capricorn", "Cap", 1, 1, 1),
	Aquarius(11, "Aquarius", "Agr", 0, 4, 2),
	Pisces(12, "Pisces", "Psc", 1, 2, 3);
	
	
	private Sign(int order, String name, String shortName, int yy, int element, int feature) {
		this.order = order;
		this.name = name;
		this.shortName = shortName;
		this.yy = yy;
		this.element = element;
	}

	private int order;
	
	private String name;
	
	private String shortName;
	
	//[阴,阳]->[1,0]
	private int yy;
	//[土,水,火,风]->[1,2,3,4]
	private int element;
	//[基本,固定,变动]->[1,2,3]
	private int feature;

	public int getOrder() {
		return order;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public int getYy() {
		return yy;
	}

	public int getElement() {
		return element;
	}

	public int getFeature() {
		return feature;
	}
	
	public static Sign getSignByName(String name) {
		for (Sign sign : Sign.values()) {
			if (sign.getName().equals(name)) {
				return sign;
			}
		}
		return null;
	}
}
